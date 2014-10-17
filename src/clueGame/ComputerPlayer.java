package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player{
	
	private char lastRoomVisited;
	
	public ComputerPlayer(){
		
	}
	public ComputerPlayer(String name, String color, int row, int col){
		super();
		this.name=name;
		setPlayerColor(color);
		this.colorString=color;
		this.row=row;
		this.col=col;
		//this.boardCell=
	}
	@Override
	public BoardCell pickLocation(Set<BoardCell> targets){
		return board.getCellAt(4,4);
	}
	public void createSuggestion(){
		
	}
	public void updateSeen(Card seen){
		
	}
	
}
