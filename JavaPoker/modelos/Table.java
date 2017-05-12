package javapoker.modelos;

public class Table {

    private int playerNumber;
    private int pote;
    private int biggestBet;

    private Deck deck;
    private String[] cardsInTable = new String[5];

    private Player mainPlayer;

    private Bot[] bots;

    public Table(int _botsNumber, int _chipAmmount) {
        setPlayerNumber(_botsNumber + 1);
        deck = new Deck();
        bots = new Bot[_botsNumber];
    }

    public void flop() {
        this.cardsInTable[0] = deck.giveCards();
        this.cardsInTable[1] = deck.giveCards();
        this.cardsInTable[2] = deck.giveCards();
    }

    public void turnRiver() {
        if (this.cardsInTable[3] == null) {
            this.cardsInTable[3] = deck.giveCards();
        } else {
            this.cardsInTable[4] = deck.giveCards();
        }
    }

    public Deck getDeck() {
        return deck;
    }

    public void setPlayer(Player player) {
        mainPlayer = player;
    }

    public void setBots(Bot[] bots) {
        this.bots = bots;
    }

    public Player getPlayer() {
        return mainPlayer;
    }

    public Bot getBot(int index) {
        return bots[index];
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPote() {
        return pote;
    }

    public void setPote(int value) {
        this.pote = value;
    }

    public String getCardsInTable(int index) {
        return cardsInTable[index];
    }

    public void setCardsInTable(String[] cardsInTable) {
        this.cardsInTable = cardsInTable;
    }

    public int getBiggestBet() {
        return biggestBet;
    }

    public void setBiggestBet(int biggestBet) {
        this.biggestBet = biggestBet;
    }

}
