import java.util.*;

public class GameHost {

    private int idNum = 100;
    private Map<Integer,Player> playerList = new HashMap<>();
    private List<Player> winList = new ArrayList<>();

    //Creates new player
    public int createPlayer() {
        int[] number_list = numbersGenerator();
        Player player = new Player(idNum,number_list[0],number_list[1],number_list[2]);
        playerList.put(idNum,player);

        return idNum++;
    }
    //Creates Dummy Player
    public void createDummyPlayer(int playerId, int wins, int luckyGuess) {
        Player player = new Player(playerId, wins, luckyGuess);
        winList.add(player);
    }

    //Generates 3 random numbers into int[] array
    public int[] numbersGenerator() {
        int[] number_list = new int[3];
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            // Generate a random number between 1 and 10
            int randomNumber = random.nextInt(10) + 1;
            number_list[i] = randomNumber;
        }
        return number_list;
    }

    //Generates new questionBoard for player
    public void generateNewBoard(int playerId) {
        int[] number_list = numbersGenerator();
        playerList.get(playerId).changeNewBoard(number_list[0],number_list[1],number_list[2]);
    }

    //Test Function to return questionBoard ( Position : Number)
    public Map<Integer, Integer> get3numbers(int playerID) {
        return playerList.get(playerID).getQuestionBoard();
    }

    //Retrieves current player info from playerList
    public List<String> getAllPlayerInfo() {
        List<String> allPlayerInfo = new ArrayList<>();
        for (Player player : playerList.values()) {
            String line = player.getPlayerId() + " " + player.getScore() + " " + player.getWins();
            allPlayerInfo.add(line);
        }
        return allPlayerInfo;
    }

    /*
    Takes in playerId and 2 inputs by user.
    Checks input against questionBoard, modify score and returns response
    If position has already been answered, server will validate and respond accordingly
     */
    public String guessTheNumber(int playerId,int number, int position) {
            Player player = playerList.get(playerId);
            var guessList = player.getQuestionBoard();
            player.addGuessedNumbers(number);
            if(guessList.get(position)==-1)
                return ("Position " + position + " has been answered. Try another position.");
            else if(guessList.containsValue(number)) {
                if(guessList.get(position) == number) {
                    if(player.getCorrectCounter()==3)
                        player.setLuckyGuessBool(true);
                    player.addCorrectNumbers(number);
                    player.addCorrectPosition(number,position);
                    player.changeQuestionBoard(position);
                    player.setCorrectCounter();
                    return ("Correct Guess! " + number + " is in Position " + position);
                }
                else {
                    player.setLuckyGuessBool(false);
                    player.deductGuessCount();
                    player.addCorrectNumbers(number);
                    return ("Board contains " + number + " but not in position " + position);
                }
            }
            else {
                player.setLuckyGuessBool(false);
                player.deductGuessCount();
                return ("Board does not contain " + number);
            }
    }

    //Retrieves various hints when user guessed wrongly
    public List<String> getHintList(int playerId) {
        Player player = playerList.get(playerId);
        List<String> hintList = new ArrayList<>();
        hintList.add("Number of guessed left: " + player.getGuessCount());
        hintList.add("Numbers used: " + player.getGuessedNumbers());
        hintList.add("Correct numbers" + player.getCorrectNumbers());
        hintList.addAll(player.getCorrectPosition());
        return hintList;
    }

    //Checks how many guesses left for player. Returns response and updates stats if reaches 0
    public String checkGuessCount(int playerId) {
        Player player = playerList.get(playerId);
        if(player.getCorrectCounter() <=0) {
            System.out.println("Player " + playerId + " scored " + player.getGuessCount() + " points.");
            if(player.getLuckyGuessBool())
                player.addLuckyGuess();
            player.addScore();
            generateNewBoard(playerId);
            player.resetPlayerStats();
            return "correct";
        }
        else if(player.getGuessCount() <=0) {
            generateNewBoard(playerId);
            player.resetPlayerStats();
            return "lose";
        }
        else return "continue";
    }

    //Checks score of player. If reaches 50, will reset scores for all current players.
    //Inserts winning player into winList
    public String checkScore(int playerId) {
        synchronized (playerList){
            Player winner = playerList.get(playerId);
            if(winner.getScore()>=50) {
                winner.addWins();
                if (!winList.contains(winner))
                    winList.add(winner);
                winner.resetPlayerStats();
                generateNewBoard(playerId);
                //RESETS SCORES FOR EVERYONE TO 0, DOES NOT CHANGE ANYTHING ELSE
                for (Player player : playerList.values())
                    player.resetScore();
                return "win";
            }
            return "continue";
        }
    }

    //Sorting List by most Wins
    public Comparator<Player> compareByWins = Comparator.comparing(Player::getWins).reversed();
    public void sortByWins(List<Player> players) {
        Collections.sort(players,compareByWins);
    }

    //Sorting List by most Lucky Guesses
    public  Comparator<Player> compareByLuckyGuess = Comparator.comparing(Player::getLuckyGuess).reversed();
    public void sortByLuckyGuess(List<Player>players) {
        Collections.sort(players, compareByLuckyGuess);
    }

    //Display all timer winners on server
    public void showWinners() {
            System.out.print("Winner List: ");
            for(Player player : winList) {
                System.out.print(player.getPlayerId() + " | ");
            }
            System.out.println();
    }

    //Displays top 10 winners on server
    public void showTop10Winners() {
            List<Player> sortedList = new ArrayList<>(winList);
            Collections.sort(sortedList,compareByWins);
            System.out.print("Top 10 Winners: ");
            int count = Math.min(sortedList.size(),10);
            for(int i=0 ; i<count ;i++){
                Player player = sortedList.get(i);
                System.out.print(player.getPlayerId() + ": " + player.getWins() + " wins | ");
            }
            System.out.println();
    }

    //Displays top 3 lucky guessers on server
    public void showTop3LuckyPlayers() {
            List<Player> sortedList = new ArrayList<>(winList);
            Collections.sort(sortedList, compareByLuckyGuess);
            System.out.print("Top 3 Lucky Guessers: ");
            int count = Math.min(sortedList.size(),3);
            for (int i=0 ; i<count ;i++){
                Player player = sortedList.get(i);
                System.out.print(player.getPlayerId() + ": " + player.getLuckyGuess() + " One shot Guesses | ");
            }
            System.out.println();
    }

    //Removers player from playerList
    public void removePlayer(int playerId) {
        synchronized (playerList) {
            playerList.remove(playerId);
            System.out.println("Player " + playerId + " has disconnected.");
        }
    }
}
