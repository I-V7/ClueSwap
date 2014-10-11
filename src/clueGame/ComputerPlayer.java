package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player{
	
	private char lastRoomVisited;
	
	public ComputerPlayer(){
		
	}
	public ComputerPlayer(String name, String color, int row, int col){
		this.name=name;
		setPlayerColor(color);
		this.colorString=color;
		this.row=row;
		this.col=col;
	}
	public void pickLocation(Set<BoardCell> targets){
		
	}
	public void createSuggestion(){
		
	}
	public void updateSeen(Card seen){
		
	}
}
