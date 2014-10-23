package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class RoomCell extends BoardCell {

	
	private DoorDirection doorDirection;
	private char roomInitial;
	private int doorways = 0;
	public enum DoorDirection {UP("U"), DOWN("D"), LEFT("L"), RIGHT("R"), NONE("N");
		
		private String door;
		
		DoorDirection(String letter) {
			door = letter;
		}
		
		public String toString() {
			return door;
		}
	}
	
	// RoomCell constructor
	RoomCell(int row, int col, String letter) {
		this.row = row;
		this.column = col;
		
		roomInitial = letter.charAt(0); // first character
		// check if the RoomCell has a door
		if(letter.length() == 2) {
			switch(letter.charAt(1)) {
			case 'U':
				doorDirection = DoorDirection.UP;
				break;
			case 'D':
				doorDirection = DoorDirection.DOWN;
				break;
			case 'L':
				doorDirection = DoorDirection.LEFT;
				break;
			case 'R':
				doorDirection = DoorDirection.RIGHT;
				break;
			}
		}
		else {
			doorDirection = DoorDirection.NONE;
		}
		// set the door direction
		setDoorDirection(doorDirection);
	}
	
	// default is true for a RoomCell
	public Boolean isRoom() {
		return true;
	}
	
	// check if the RoomCell is a doorway
	public Boolean isDoorway() {
		// TODO return true if this cell is a room
		if(doorDirection == DoorDirection.NONE) {
			return false;
		}
		
		doorways++;
		return true;
	}
	
	// getter for initial
	public char getInitial() {
		return roomInitial;
	}
	
	
	// getter for doorDirection
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	// setter for doorDirection
	public void setDoorDirection(DoorDirection direction) {
		doorDirection = direction;
	}

	@Override
	public void draw(Graphics g, Board board) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(column*CELL_WIDTH, row*CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
		
		if(isDoorway()){
			g.setColor(Color.BLUE);
			switch(this.doorDirection){
				case UP:
					for(int i=0; i < 5; i++){
						g.drawLine(column*CELL_WIDTH, row*CELL_HEIGHT+i, column*CELL_WIDTH+CELL_WIDTH, row*CELL_HEIGHT+i);
					}
					break;
				case DOWN:
					//System.out.println("heyy");
					for(int i=0; i < 5; i++){
						g.drawLine(column*CELL_WIDTH, row*CELL_HEIGHT+CELL_HEIGHT-i, column*CELL_WIDTH+CELL_WIDTH, row*CELL_HEIGHT+CELL_HEIGHT-i);
					}
					break;
				case LEFT:
					for(int i=0; i < 5; i++){
						g.drawLine(column*CELL_WIDTH+i, row*CELL_HEIGHT, column*CELL_WIDTH+i, row*CELL_HEIGHT+CELL_HEIGHT);
					}
					break;
				case RIGHT:
					g.drawLine(column*CELL_WIDTH+CELL_WIDTH, row*CELL_HEIGHT, column*CELL_WIDTH+CELL_WIDTH, row*CELL_HEIGHT+CELL_HEIGHT);
					//g.drawLine(column*CELL_WIDTH+CELL_WIDTH+1, row*CELL_HEIGHT, column*CELL_WIDTH+CELL_WIDTH+1, row*CELL_HEIGHT+CELL_HEIGHT);
					for(int i=0; i < 5; i++){
						g.drawLine(column*CELL_WIDTH+CELL_WIDTH-i, row*CELL_HEIGHT, column*CELL_WIDTH+CELL_WIDTH-i, row*CELL_HEIGHT+CELL_HEIGHT);
					}
					break;
				case NONE:
					break;
				default:
					break;
			}
		}
		

	}

	
	// TODO override the draw method, when we do the GUI
}

