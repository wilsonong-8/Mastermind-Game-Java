import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class Client implements AutoCloseable {
    final int port = 8000;
    private final Scanner read;
    private final PrintWriter write;

    public Client() throws Exception {
        Socket socket = new Socket("localhost",port);
        read = new Scanner(socket.getInputStream());
        write = new PrintWriter(socket.getOutputStream(),true);

        System.out.println("Client Class connected");

    }

    public int getPlayerID() {
        write.println("getPlayerId");
        return Integer.parseInt(read.nextLine());
    }

    public List<String> getAllPlayerInfo() {
        List<String> playerInfo = new ArrayList<>();
        write.println("getAllPlayerInfo");
        int size = Integer.parseInt(read.nextLine());
        for (int i = 0; i < size; i++) {
            String line = read.nextLine();
            String subString[] = line.split(" ");
            String line2 = "Player ID: " + subString[0] + " Score: " + subString[1] + " Wins: " + subString[2];
            playerInfo.add(line2);
        }
        return  playerInfo;
    }

    public List<String> guessTheNumber(int number, int position) {
        List<String> hintList = new ArrayList<>();
        write.println("guessTheNumber" + " " + number + " " + position);
        String line = read.nextLine();
        hintList.add(line);
        if (line.contains("Correct Guess!") || line.contains("Try another position")) {
            return hintList;
        }
        int size = Integer.parseInt(read.nextLine());
        for (int i = 0; i < size; i++) {
            String line2 = read.nextLine();
            hintList.add(line2);
        }
        return hintList;
    }

    public String checkGuessCount() {
        write.println("checkCount");
        String countReply = read.nextLine();
        return countReply;
    }

    public String checkScore() {
        write.println("checkScore");
        String scoreReply = read.nextLine();
        return scoreReply;
    }



    @Override
    public void close() throws Exception {
        read.close();
        write.close();
    }
}
