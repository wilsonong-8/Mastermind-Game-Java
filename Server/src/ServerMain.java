import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    private final static int port = 8000;
    private static final GameHost gamehost = new GameHost();

    public static void main(String[] args) {
        gamehost.createDummyPlayer(90,2,1);
        gamehost.createDummyPlayer(91,2,0);
        gamehost.createDummyPlayer(92,1,2);
        gamehost.createDummyPlayer(93,1,1);
        gamehost.createDummyPlayer(94,1,0);
        gamehost.createDummyPlayer(95,1,0);
        gamehost.createDummyPlayer(96,1,0);
        gamehost.createDummyPlayer(97,1,0);
        gamehost.createDummyPlayer(98,1,0);

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