import java.util.*;

public class Player {
    private int initialGuessCount = 10;
    private final int playerId;
    private int guessCount = initialGuessCount;
    private int correctCounter = 3;
    private int score = 0;
    private int wins = 0;
    private int luckyGuess = 0;
    private boolean luckyGuessBool;

    //questionBoard contains (Position : Number)
    private Map<Integer,Integer> questionBoard = new HashMap<>();

    //hintList contains guesses remaining, numbers used, and correct guesses
    private List<Integer> guessedNumbers = new ArrayList<>();
    private List<Integer> correctNumbers = new ArrayList<>();
    private List<String> correctPosition = new ArrayList<>();

    public Player(int playerId, int num1, int num2, int num3) {
        this.playerId = playerId;
        questionBoard.put(1,num1);
        questionBoard.put(2,num2);
        questionBoard.put(3,num3);
    }

    public Player(int playerId, int wins, int luckyGuess) {
        this.playerId = playerId;
        this.wins = wins;
        this. luckyGuess = luckyGuess;
    }

    public void resetPlayerStats() {
        correctCounter = 3;
        guessCount = initialGuessCount;
        guessedNumbers.clear();
        correctNumbers.clear();
        correctPosition.clear();
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getGuessCount() {
        return guessCount;
    }

    public void deductGuessCount() {
        this.guessCount -=1;
    }

    public int getScore() {
        return score;
    }

    public void addScore() {
        score += guessCount;
    }

    public void resetScore() {
        score =0;
    }

    public int getCorrectCounter() {
        return correctCounter;
    }

    public void setCorrectCounter() {
        correctCounter-=1;
    }

    public Map<Integer, Integer> getQuestionBoard() {
        return questionBoard;
    }

    public void changeQuestionBoard(int position) {
        questionBoard.put(position,-1);
    }

    public void changeNewBoard(int num1, int num2, int num3) {
        questionBoard.put(1,num1);
        questionBoard.put(2,num2);
        questionBoard.put(3,num3);
    }

    public List<Integer> getGuessedNumbers() {
        return guessedNumbers;
    }

    public void addGuessedNumbers(int number) {
        guessedNumbers.add(number);
    }

    public List<Integer> getCorrectNumbers() {
        return correctNumbers;
    }

    public void addCorrectNumbers(int number) {
        correctNumbers.add(number);
    }

    public List<String> getCorrectPosition() {
        return correctPosition;
    }

    public void addCorrectPosition(int number, int position) {
        correctPosition.add("Position " + position + "| Number: " + number + " (Correct)");
    }

    public int getWins() {
        return wins;
    }

    public void addWins() {
        wins+=1;
    }

    public int getLuckyGuess() {
        return luckyGuess;
    }

    public void addLuckyGuess() {
        luckyGuess+=1;
    }

    public boolean getLuckyGuessBool() {
        return luckyGuessBool;
    }

    public void setLuckyGuessBool(boolean luckyGuessBool) {
        this.luckyGuessBool = luckyGuessBool;
    }
}


