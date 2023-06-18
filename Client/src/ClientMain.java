import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) {

        try {
                Scanner in = new Scanner(System.in);
            try (Client client = new Client()) {

                while (true) {
                    /*
                    First While Loop will retrieve playerID and current players in game
                     */
                    int playerID = client.getPlayerId();

                    System.out.println("Your ID: " + playerID);
                    //Gets AllPlayerInformation including ID and Score
                    var playerInfo = client.getAllPlayerInfo();
                    System.out.println("------------------------------------");
                    playerInfo.stream().forEach((p)-> System.out.println(p));
                    System.out.println("------------------------------------");
                    /*
                    2nd While Loop Starts the guessing game
                     */
                    while (true) {

                        int numberGuess = validationCheck(in,1,10);

                        int positionGuess = validationCheck(in,1,3);

                        var reply = client.guessTheNumber(numberGuess,positionGuess);
                        reply.stream().forEach((r)-> System.out.println(r));

                        String checkCountReply = client.checkGuessCount();
                        if (checkCountReply.equals("correct")) {
                            String checkScoreReply = client.checkScore();
                            if(checkScoreReply.equals("win")) {
                                System.out.println("Congratulations! You won the game!");
                            }
                            else {
                                System.out.println("You guessed all 3 numbers correctly!");
                            }
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
    /*
    Validation Check for input of number between 1-10 and position between 1-3
    Takes in 3 arguments
     */
    public static int validationCheck(Scanner in, int moreThan, int lessThan) {
        int outputNum;
        do {
            System.out.println("Guess a number between " + moreThan + " to " + lessThan);
            String inputString = in.nextLine();
            if(inputString.isEmpty())
                System.out.println("Input cannot be empty");
            else if(!inputString.matches("\\d+"))
                System.out.println("Please only input numbers");
            else {
                outputNum = Integer.parseInt(inputString);
                if(outputNum >= moreThan && outputNum <=lessThan)
                    break;
                else
                    System.out.println("Invalid Number Entered. Please try again");
            }
        }
        while (true);
        return outputNum;
    }
    /*
    User Prompt to continue or stop the game.
    Takes in only Integer 1 or 2
     */
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
}