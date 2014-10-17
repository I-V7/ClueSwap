package clueGame;

import java.util.Set;

public class HumanPlayer extends Player {
	
	
	public HumanPlayer(){
		
	}
	public HumanPlayer(String name, String color, int row, int col){
		super();
		this.name=name;
		setPlayerColor(color);
		this.colorString=color;
		this.row=row;
		this.col=col;
		//this.boardCell=
	}
	@Override
	public boolean isHuman(){
		return true;
	}
	@Override
	public BoardCell pickLocation(Set<BoardCell> targets){
		return board.getCellAt(4,4);
	}
}
