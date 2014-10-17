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
		BoardCell nextLocation=null;
		//pick random target number
		int randomCardNum = (int)(Math.random()*targets.size());
		int i=0;
		//look for random target number
		for(BoardCell cell: targets){
			if(i == randomCardNum){
				nextLocation = cell;
				break;
			}
			i++;
		}
		
		return nextLocation;
	}
	public void createSuggestion(){
		
	}
	public void updateSeen(Card seen){
		
	}
	
}
