import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private GameHost gameHost;

    public ClientHandler(Socket socket,GameHost gameHost) {
        this.socket = socket;
        this.gameHost = gameHost;
    }

    @Override
    public void run() {
        int playerId = 0;
        System.out.println("Client Handler running");
        try( Scanner read = new Scanner(socket.getInputStream());
             PrintWriter write = new PrintWriter(socket.getOutputStream(), true)) {
            try {
                playerId = gameHost.createPlayer();
                while (read.hasNextLine()) {
                    String line = read.nextLine();
                    String[] substring = line.split(" ");

                    switch (substring[0]) {
                        case ("getPlayerId"):
                            write.println(playerId);
                            // TEST FUNCTION : Get 3 numbers from playerId
                            System.out.println(gameHost.get3numbers(playerId));
                            break;

                        case ("getAllPlayerInfo"):
                            var allPlayerInfo = gameHost.getAllPlayerInfo();
                            write.println(allPlayerInfo.size());
                            allPlayerInfo.stream().forEach((p) -> write.println(p));
                            break;

                        case ("guessTheNumber") :
                            int guessNumber = Integer.parseInt(substring[1]);
                            int guessPosition = Integer.parseInt(substring[2]);
                            String guessAnswer= gameHost.guessTheNumber(playerId,guessNumber,guessPosition);
                            write.println(guessAnswer);
                            if(!guessAnswer.contains("Correct Guess!")) {
                                var hintList= gameHost.getHintList(playerId);
                                write.println(hintList.size());
                                for(String s : hintList) {
                                    write.println(s);
                                }
                            }
                            break;

                        case ("checkCount") :
                            String countReply = gameHost.checkGuessCount(playerId);
                            write.println(countReply);
                            break;

                        case ("checkScore") :
                            String scoreReply = gameHost.checkScore(playerId);
                            write.println(scoreReply);
                            gameHost.showWinners();
                            gameHost.showTop10Winners();
                            gameHost.showTop3LuckyPlayers();
                            break;

                        default:
                            throw new Exception("Unknown command " + substring[0]);
                    }

                }

                } catch (Exception e) {
                e.printStackTrace();
                }
            } catch (Exception e) {
            e.printStackTrace();
            } finally {
            gameHost.removePlayer(playerId);
            try {
                socket.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

