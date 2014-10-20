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
			if(cell.isRoom()){
				RoomCell room=(RoomCell)cell;
				if(room.getInitial()!=lastRoomVisited){
					nextLocation=cell;
					this.lastRoomVisited=room.getInitial();
					break;
				}
				if(i == randomCardNum){
					nextLocation = cell;
				}
			}else if(i == randomCardNum){
				nextLocation = cell;
			}
			i++;
		}
		
		return nextLocation;
	}
	public String[] createSuggestion(){
		
		
		return null;
		
	}
	public void setLastRoomVisited(char room){
		lastRoomVisited=room;
	}
}
