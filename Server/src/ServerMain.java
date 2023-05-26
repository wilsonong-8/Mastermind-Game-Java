import java.io.IOException;
import java.net.HttpCookie;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    public final static int port = 8000;
    public static final GameHost gamehost = new GameHost();


    public static void main(String[] args) {
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for players...");
            while(true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket, gamehost)).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}