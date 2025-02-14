import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * AppServer
 */
public class AppServer extends UnicastRemoteObject implements AppServerInterface {

    private static final long serialVersionUID = 6248933011470405333L;
    private static AppServer appServer;

    public AppServer() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        // initialize server
        appServer = new AppServer();
        // define server address
        Naming.rebind("//localhost/appserver", appServer);
    }

    @Override
    public ArrayList<ArrayList<Flight>> check(Calendar departure_date, String departure_location, Calendar arrival_date, String arrival_location, int passengers) throws RemoteException {
        try {
            // connect to dbServer
            Socket call = new Socket("localhost", 1337);
            ObjectOutputStream output = new ObjectOutputStream(call.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(call.getInputStream());

            // send message to server
            output.writeObject(new Message(departure_date, departure_location, arrival_date, arrival_location, passengers));
            // receive available flights
            ArrayList<ArrayList<Flight>> available_flights = (ArrayList<ArrayList<Flight>>) input.readObject();

            // close connections
            input.close();
            output.close();
            call.close();
            return available_flights;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean book(String departure_flight_code, String arrival_flight_code, int passengers) throws RemoteException {
        try {
            // connect to dbServer
            Socket call = new Socket("localhost", 1337);
            ObjectOutputStream output = new ObjectOutputStream(call.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(call.getInputStream());

            // send message to server
            output.writeObject(new Message(departure_flight_code, arrival_flight_code, passengers));
            // receive booking confirmation
            boolean result = (boolean) input.readObject();

            // close connections
            input.close();
            output.close();
            call.close();
            return result;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}