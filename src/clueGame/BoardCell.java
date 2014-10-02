package clueGame;

public abstract class BoardCell {

	protected int row;
	protected int column;
	
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
	
	// TODO add an abstract method named draw
}
