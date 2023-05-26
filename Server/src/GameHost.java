import java.util.*;

public class GameHost {

    private int idNum = 100;

    private Map<Integer,Player> playerList = new HashMap<>();
    private List<Player> winList = new ArrayList<>();



    public int createPlayer() {
        int[] number_list = numbersGenerator();
        Player player = new Player(idNum,number_list[0],number_list[1],number_list[2]);
        playerList.put(idNum,player);

        return idNum++;
    }

    public void generateNewBoard(int playerId) {
        int[] number_list = numbersGenerator();
        playerList.get(playerId).changeNewBoard(number_list[0],number_list[1],number_list[2]);
    }

    //Test Function to get the 3 guesses out ( Position : Number)
    public Map<Integer, Integer> get3numbers(int playerID) {
        return playerList.get(playerID).getQuestionBoard();
    }

    public List<String> getAllPlayerInfo() {
        List<String> allPlayerInfo = new ArrayList<>();
        for (Player player : playerList.values()) {
            String line = player.getPlayerId() + " " + player.getScore() + " " + player.getWins();
            allPlayerInfo.add(line);
        }
        return allPlayerInfo;
    }

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
                    return ("Correct Guess! " + number + " is in " + position);

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

    public List<String> getHintList(int playerId) {
        Player player = playerList.get(playerId);
        List<String> hintList = new ArrayList<>();
        hintList.add("Number of guessed left: " + player.getGuessCount());
        hintList.add("Numbers used: " + player.getGuessedNumbers());
        hintList.add("Correct numbers" + player.getCorrectNumbers());
        hintList.addAll(player.getCorrectPosition());
        return hintList;
    }

    public String checkGuessCount(int playerId) {
        Player player = playerList.get(playerId);
        if(player.getCorrectCounter() <1) {
            if(player.getLuckyGuessBool())
                player.addLuckyGuess();
            player.addScore();
            generateNewBoard(playerId);
            player.resetPlayerStats();
            return "correct";
        }
        else if(player.getGuessCount() <1) {
            generateNewBoard(playerId);
            player.resetPlayerStats();
            return "lose";
        }
        else return "continue";
    }

    public String checkScore(int playerId) {
        Player player = playerList.get(playerId);
        if(player.getScore()>=50) {
            player.addWins();
            if (!winList.contains(player))
                winList.add(player);
            player.resetPlayerStats();
            player.resetScore();
            generateNewBoard(playerId);
            return "win";
        }
        return "continue";
        //RESET THE GAME (IF HAVE TO RESET GAME FOR ALL PLAYERS)
        //ENTER CODE HERE
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

    public void showWinners() {
            System.out.print("Winner List: ");
            for(Player player : winList) {
                System.out.print(player.getPlayerId() + " | ");
            }
            System.out.println();
    }

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

    public void removePlayer(int playerId) {
        playerList.remove(playerId);
        System.out.println("Player " + playerId + " has disconnected.");
    }


}
