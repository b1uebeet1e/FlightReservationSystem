import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Client
 */
public class Client{

    private static AppServerInterface appServer;

    public Client() throws RemoteException, MalformedURLException, NotBoundException {
        // connect to server
        appServer = (AppServerInterface) Naming.lookup("//localhost/appserver");
    }

    // ask appServer for available flights
    public ArrayList<ArrayList<Flight>> check(Calendar departure_date, String departure_location, Calendar arrival_date, String arrival_location, int passengers) throws RemoteException {
        return appServer.check(departure_date, departure_location, arrival_date, arrival_location, passengers);
    }

    // ask appServer to book flights
    public boolean book(String departure_flight_code, String arrival_flight_code, int passengers) throws RemoteException {
        return appServer.book(departure_flight_code, arrival_flight_code, passengers);
    }
}