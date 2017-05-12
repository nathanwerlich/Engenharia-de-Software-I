package javapoker.modelos;

public class Hand extends Deck{
	
	private String[] hand = new String[2];
	
	public Hand(String _card1, String _card2) {
		hand[0] = _card1;
		hand[1] = _card2;
	}
	
	public String getFirstCard() {
		return hand[0];
	}
	public String getSecondCard() {
		return hand[1];
	}
}
