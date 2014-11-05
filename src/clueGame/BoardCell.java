package clueGame;

import java.awt.Graphics;

public abstract class BoardCell {
	
	protected int row;
	protected int column;
	//gui instance variables
	public final int CELL_WIDTH=34;
	public final int CELL_HEIGHT=24;
	Boolean target = false;
	public Boolean isWalkway() {
		return false;
	}
	
	public Boolean isRoom() {
		return false;
	}
	
	public Boolean isDoorway() {
		return false;
	}
	
	// getter for rows
	public int getRow() {
		return row;
	}
	
	// getter for columns
	public int getCol() {
		return column;
	}
	
	public void setAsTarget()
	{
		target = true;
	}
	
	public void setAsNotTarget()
	{
		target = false;
	}
	
	// TODO add an abstract method named draw
	public abstract void draw(Graphics g, Board board);
}
