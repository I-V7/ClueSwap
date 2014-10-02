package clueGame;

public class WalkwayCell extends BoardCell {
	
	
	
	WalkwayCell(int x, int y) {
		this.row = x;
		this.column = y;
	}

	// default is true for a WalkwayCell
	public Boolean isWalkway() {
		return true;		
	}
	
	// TODO override the draw method, when we do the GUI
	
}
