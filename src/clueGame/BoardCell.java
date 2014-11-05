package clueGame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class BoardCell {
	
	protected int row;
	protected int column;
	//gui instance variables
	public final int CELL_WIDTH=34;
	public final int CELL_HEIGHT=24;
	protected Boolean target;
	public Boolean isWalkway() {
		return false;
	}
	public Boolean containsClick(int x, int y)
	{
		// Hard coded values 10 and 58 adjust the board. It's being weird and pretending the board is higher than it should be.
		Rectangle rect = new Rectangle(column*CELL_WIDTH + 10, row*CELL_HEIGHT + 58, CELL_WIDTH, CELL_HEIGHT);

		if(rect.contains(new Point(x,y)) && target)
		{
			return true;
		}
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
