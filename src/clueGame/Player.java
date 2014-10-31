package clueGame;


import java.awt.Color;
import java.awt.Graphics;
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
	protected static ArrayList<Card> seenCards;
	protected Board board;
	protected char lastRoomVisited;
	//may or may not use
	//protected BoardCell boardCell;
	
	public Player(){
		myCards = new ArrayList<Card>();
		seenCards = new ArrayList<Card>();
	}
	public Card disproveSuggestion(String person, String room, String weapon){
		ArrayList<Card> possibleWrongCards = new ArrayList<Card>();
		Card disprovedCard=null;
		for(Card card: myCards)
		{
			if((card.getName().equals(person) || card.getName().equals(room) || card.getName().equals(weapon)) && !seenCards.contains(card))
			{
				possibleWrongCards.add(card);
			}
		}
		if(possibleWrongCards.size() == 0)
		{
			return null;
		}
		else if(possibleWrongCards.size() == 1)
		{
			disprovedCard= possibleWrongCards.get(0);
		}
		else if(possibleWrongCards.size() > 1)
		{
			int randomNum = (int)(Math.random()*possibleWrongCards.size());
			disprovedCard= possibleWrongCards.get(randomNum);
		}
		
		if(disprovedCard!=null){
			updateSeen(disprovedCard);
		}
		return disprovedCard;
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
	public abstract BoardCell pickLocation(Set<BoardCell> targets);
	
	public void draw(Graphics g, Board board){
		g.setColor(this.color);
		BoardCell[][] layout=board.getLayout1();
		BoardCell cell = layout[0][0];
		g.fillOval(this.col*cell.CELL_WIDTH+2, this.row*cell.CELL_HEIGHT, cell.CELL_WIDTH-4, cell.CELL_HEIGHT);
		g.setColor(Color.BLACK);
		g.drawOval(this.col*cell.CELL_WIDTH+2, this.row*cell.CELL_HEIGHT, cell.CELL_WIDTH-4, cell.CELL_HEIGHT);
	}
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
	public void updateSeen(Card seen){
		seenCards.add(seen);
	}
	public void setLastRoomVisited(char room){
		lastRoomVisited=room;
	}
	//for Test purposes only
	public void setCardHand(ArrayList<Card> testCards)
	{
		myCards = testCards;
	}
	public void setShownCards(ArrayList<Card> shownCards) {
		this.seenCards = shownCards;	
	}
	public ArrayList<Card> getShownCards()
	{
		return seenCards;
	}
	public void clearShownCards(){
		seenCards.clear();
	}
	
}
