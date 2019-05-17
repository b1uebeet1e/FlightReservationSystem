import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * AppServerInterface
 */
public interface AppServerInterface extends Remote {

    public ArrayList<ArrayList<Flight>> check(Calendar departure_date, String departure_location, Calendar arrival_date, String arrival_location, int passengers) throws RemoteException;
    
    public boolean book(String departure_flight_code, String arrival_flight_code, int passengers) throws RemoteException;
}