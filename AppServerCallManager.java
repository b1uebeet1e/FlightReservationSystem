import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * AppServerCallManager
 */
public class AppServerCallManager implements Runnable{
    private DBServer server;
    private Socket call;

    public AppServerCallManager(Socket call, DBServer server) {
        this.call = call;
        this.server = server;
    }

    @Override
    public void run() {
        try {

            ObjectOutputStream outputStream = new ObjectOutputStream(call.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(call.getInputStream());
            
            Message message = (Message) inputStream.readObject();

            if (message.getMessage().equals("CHECK")) {
                outputStream.writeObject(server.check(message.getDeparture_date(), message.getDeparture_location(), message.getArrival_date(), message.getArrival_location(), message.getPassengers()));
            } else if (message.getMessage().equals("BOOK")) {
                outputStream.writeObject(server.book(message.getDeparture_flight_code(), message.getArrival_flight_code(), message.getPassengers()));
            }

            inputStream.close();
            outputStream.close();
            call.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    
}