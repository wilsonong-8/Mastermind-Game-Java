import java.util.Scanner;

public class ClientMain {

    public static void startOrStop(Scanner in) throws Exception {
        int startOrStop;
        do {
            System.out.println("Enter 1 to Continue or 2 to Exit.");
            String startOrStopString = in.nextLine();
            if(startOrStopString.isEmpty())
                System.out.println("Input cannot be empty");
            else if(!startOrStopString.matches("\\d+"))
                System.out.println("Please only input numbers");
            else {
                startOrStop = Integer.parseInt(startOrStopString);
                if(startOrStop == 1)
                    break;
                else if(startOrStop ==2)
                    throw new Exception("Thank you for playing");
                else
                    System.out.println("Invalid Number Entered, Please try again");
            }
        }
        while (true);
    }
    public static void main(String[] args) {

        try {
                Scanner in = new Scanner(System.in);

            try (Client client = new Client()) {

                while (true) {
                    //Gets PlayerID
                    int playerID = client.getPlayerID();

                    System.out.println("Your ID: " + playerID);
                    //Gets AllPlayerInformation including ID and Score
                    var playerInfo = client.getAllPlayerInfo();
                    System.out.println("------------------------------------");
                    playerInfo.stream().forEach((p)-> System.out.println(p));
                    System.out.println("------------------------------------");

                    while (true) {
                        //Guess Game
                        int numberGuess;
                        do {
                            System.out.println("Guess a number between 1 to 10:");
                            String numberGuessString = in.nextLine();
                            if(numberGuessString.isEmpty())
                                System.out.println("Input cannot be empty");
                            else if(!numberGuessString.matches("\\d+"))
                                System.out.println("Please only input numbers");
                            else {
                                   numberGuess = Integer.parseInt(numberGuessString);
                                   if(numberGuess >=1 && numberGuess <=10)
                                       break;
                                   else
                                       System.out.println("Invalid Number Entered. Please try again");
                            }
                        }
                        while (true);

                        int positionGuess;
                        do {
                            System.out.println("Guess the position between 1 to 3:");
                            String positionGuessString = in.nextLine();
                            if(positionGuessString.isEmpty())
                                System.out.println("Input cannot be empty");
                            else if(!positionGuessString.matches("\\d+"))
                                System.out.println("Please only input numbers");
                            else {
                                positionGuess = Integer.parseInt(positionGuessString);
                                if(positionGuess>=1 && positionGuess<=3)
                                    break;
                                else
                                    System.out.println("Invalid Number Entered, Please try again");
                            }
                        }
                        while (true);

                        var reply = client.guessTheNumber(numberGuess,positionGuess);
                        reply.stream().forEach((r)-> System.out.println(r));


                        String checkCountReply = client.checkGuessCount();
                        if (checkCountReply.equals("correct")) {
                            String checkScoreReply = client.checkScore();
                            if(checkScoreReply.equals("win")) {
                                System.out.println("You won the game!");
                                startOrStop(in);
                                break;
                            }
                            else
                                System.out.println("You guessed correctly!");
                                startOrStop(in);
                                break;
                        }
                        else if (checkCountReply.equals("lose")) {
                            System.out.println("You lose!");
                            startOrStop(in);
                            break;
                        }
                    }
                }
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}