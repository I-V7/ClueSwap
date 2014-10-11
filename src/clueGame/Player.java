package clueGame;


import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Player {
	protected String name;
	protected Color color;
	protected String colorString;
	protected int row;
	protected int col;
	protected ArrayList<Card> myCards;
	
	public Player(){
		
	}
	public Card disproveSuggestoiin(String person, String room, String weapon){
		return new Card("","");
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
		return false;
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
	
}
