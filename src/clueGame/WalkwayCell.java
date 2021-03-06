package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell {
	
	
	
	WalkwayCell(int x, int y) {
		this.row = x;
		this.column = y;
		target = false;
	}

	// default is true for a WalkwayCell
	public Boolean isWalkway() {
		return true;		
	}

	@Override
	public void draw(Graphics g, Board board) {
		g.setColor(Color.BLACK);
		g.drawRect(column*CELL_WIDTH, row*CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
		g.setColor(Color.YELLOW);
		if(target)
		{
			g.setColor(Color.CYAN);
		}
		g.fillRect(column*CELL_WIDTH+1, row*CELL_HEIGHT+1, CELL_WIDTH-1, CELL_HEIGHT-1);
		
	}
	
	// TODO override the draw method, when we do the GUI
	
}
