package clueGame;


import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Set;

public abstract class Player {
	protected String name;
	protected Color color;
	protected String colorString;
	protected int row;
	protected int col;
	protected ArrayList<Card> myCards;
	protected ArrayList<Card> shownCards;
	protected Board board;
	//may or may not use
	//protected BoardCell boardCell;
	
	public Player(){
		myCards = new ArrayList<Card>();
		shownCards = new ArrayList<Card>();
		
	}
	public Card disproveSuggestion(String person, String room, String weapon){
		
		for(Card card: myCards)
		{
			if((card.getName().equals(person) || card.getName().equals(room) || card.getName().equals(weapon)) && !shownCards.contains(card))
			{
				shownCards.add(card);
				return card;
			}
		}
		return null;
	}
	
	// Be sure to trim the color, we don't want spaces around the name
	public void setPlayerColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			this.color = (Color)field.get(null); } 
		catch (Exception e) {  
			this.color = null; // Not defined } 
		}
	}
	//Used for tests!
	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (object == null || object.getClass() != getClass()) {
			result = false;
		} else {
			Player player = (Player) object;
			if (this.name.equals( player.getName() ) && this.getColorString().equals( player.getColorString() ) ) {
				result = true;
			}
		}
		return result;
	}
	public boolean isHuman(){
		return this instanceof HumanPlayer;
	}
	public void addCard(Card card)
	{
		myCards.add(card);
		
	}
	
	
	//return true if a card has already been seen
	//so duplicate guesses are not made
	public abstract BoardCell pickLocation(Set<BoardCell> targets);
	
	//GETTERS AND SETTERS
	public String getName(){
		return this.name;
	}
	public String getColorString(){
		return this.colorString;
	}
	public Color getColor(){
		return this.color;
	}
	public int getRow(){
		return this.row;
	}
	public int getCol(){
		return this.col;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public ArrayList<Card> getCards()
	{
		//System.out.println("return cards");
		return myCards;
	}
	//for Test purposes only
	public void setCardHand(ArrayList<Card> testCards)
	{
		myCards = testCards;
	}
	
}
