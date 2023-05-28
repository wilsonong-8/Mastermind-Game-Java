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
        System.out.println("Welcome to MasterMind! You have 10 chances to guess 3 numbers in their correct positions. " +
                "\nScore 50 points to win!");
    }

    public int getPlayerId() {
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
        String spacing = "------------------------------------";
        hintList.add(spacing);
        hintList.add(line);
        if (line.contains("Correct Guess!") || line.contains("Try another position")) {
            hintList.add(spacing);
            return hintList;
        }
        int size = Integer.parseInt(read.nextLine());
        for (int i = 0; i < size; i++) {
            String line2 = read.nextLine();
            hintList.add(line2);
        }
        hintList.add(spacing);
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
