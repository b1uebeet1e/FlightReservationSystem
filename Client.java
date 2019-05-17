import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Client
 */
public class Client extends UnicastRemoteObject {

    private static final long serialVersionUID = -1876860991259021461L;
    private static AppServerInterface appServer;

    public Client() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        appServer = (AppServerInterface) Naming.lookup("//localhost/appserver");
        System.out.println(appServer.book("SA195192359", "AS195212030", 50));
        System.out.println(appServer.book("SA195192359", "AS195212030", 1150));
    }
}