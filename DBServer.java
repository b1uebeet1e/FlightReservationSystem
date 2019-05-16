import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * DBServer
 */
public class DBServer {

	private ArrayList<Flight> flights;
    private ServerSocket server;

    public DBServer() {
        flights = new ArrayList<>();
        flights.add(new Flight(new GregorianCalendar(2019, 5, 19, 23, 59), "Samos", "Athens", "SA195192359", 200, 100, 119.99));
        flights.add(new Flight(new GregorianCalendar(2019, 5, 19, 17, 00), "Samos", "Athens", "SA195191700", 200, 100, 150));
        flights.add(new Flight(new GregorianCalendar(2019, 5, 21, 20, 30), "Athens", "Samos", "AS195212030", 200, 100, 80));
        flights.add(new Flight(new GregorianCalendar(2019, 5, 21, 06, 15), "Athens", "Samos", "SA195210615", 200, 100, 115));
    }

    public void start(int port) throws IOException {
        server = new ServerSocket(port);

        while (true) {
            Socket app_server_call = server.accept();

            Runnable manage_call = () -> {
                try {
                    Socket tmpSocket = app_server_call;

                    ObjectOutputStream outputStream = new ObjectOutputStream(tmpSocket.getOutputStream());
                    ObjectInputStream inputStream = new ObjectInputStream(tmpSocket.getInputStream());
                    
                    String message = inputStream.readUTF();
                    ArrayList<Object> parameters = (ArrayList<Object>) inputStream.readObject();

                    if (message.equals("CHECK")) {
                        outputStream.writeObject(check((Calendar)parameters.get(0), (String)parameters.get(1), (Calendar)parameters.get(2), (String)parameters.get(3), (int)parameters.get(4)));
                    } else if (message.equals("BOOK")) {
                        outputStream.writeBoolean(book((String)parameters.get(0), (String)parameters.get(1), (int)parameters.get(2)));
                    }

                    inputStream.close();
                    outputStream.close();
                    tmpSocket.close();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            };
            new Thread(manage_call).start();
        }
    }

    public ArrayList<ArrayList<Flight>> check(Calendar departure_date, String departure_location, Calendar arrival_date, String arrival_location, int passengers) {
        ArrayList<ArrayList<Flight>> available_flights = new ArrayList<>();
        ArrayList<Flight> departures = new ArrayList<>();
        ArrayList<Flight> arrivals = new ArrayList<>();

        for (Flight flight : flights) {
            if (passengers <= flight.getAvailable_seats()) {
                if (departure_location.equals(flight.getDeparture_location()) && departure_date.get(Calendar.DAY_OF_YEAR) == flight.getDate_time().get(Calendar.DAY_OF_YEAR) && departure_date.get(Calendar.YEAR) == flight.getDate_time().get(Calendar.YEAR)) {
                    departures.add(flight);
                } else if (arrival_location.equals(flight.getArrival_location()) && arrival_date.get(Calendar.DAY_OF_YEAR) == flight.getDate_time().get(Calendar.DAY_OF_YEAR) && arrival_date.get(Calendar.YEAR) == flight.getDate_time().get(Calendar.YEAR)) {
                    arrivals.add(flight);
                }
            }
        }

        return available_flights;
    }

    public boolean book(String departure_flight_code, String arrival_flight_code, int passengers) {
        int found_first = -1;
        int found_second = -1;

        for (int i = 0; i < flights.size(); i++) {
            if ((departure_flight_code.equals(flights.get(i).getFlight_code()) || arrival_flight_code.equals(flights.get(i).getFlight_code())) && passengers <= flights.get(i).getAvailable_seats()) {
                if (found_first < 0) found_first = i;
                else if (found_second < 0) found_second = i;
            }
        }

        if (found_first < 0 || found_second < 0) return false;

        flights.get(found_first).book_seats(passengers);
        flights.get(found_second).book_seats(passengers);

        return true;
    }


    public static void main(String[] args) throws IOException {
        new DBServer().start(51234);
    }
}