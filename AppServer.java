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

    private static AppServer appServer;

    public AppServer() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        appServer = new AppServer();

        Naming.rebind("//localhost/appserver", appServer);

        // TODO: start server
    }

    @Override
    public ArrayList<ArrayList<Flight>> check(Calendar departure_date, String departure_location, Calendar arrival_date, String arrival_location, int passengers) throws RemoteException {
        return new ArrayList<>();
        // TODO: Add connection to DB Server
    }

    @Override
    public boolean book(String departure_flight_code, String arrival_flight_code, int passengers) throws RemoteException {
        try {
            Socket call = new Socket("localhost", 51234);
            ObjectOutputStream output = new ObjectOutputStream(call.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(call.getInputStream());

            output.writeUTF("BOOK");
            output.flush();
            ArrayList<Object> parameters = new ArrayList<>();
            parameters.add(departure_flight_code);
            parameters.add(arrival_flight_code);
            parameters.add(passengers);
            output.writeObject(parameters);
            boolean result = input.readBoolean();

            input.close();
            output.close();
            call.close();
            return result;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }
}