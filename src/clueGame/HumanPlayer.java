package clueGame;

public class HumanPlayer extends Player {
	
	
	public HumanPlayer(){
		
	}
	public HumanPlayer(String name, String color, int row, int col){
		this.name=name;
		setPlayerColor(color);
		this.colorString=color;
		this.row=row;
		this.col=col;
	}
	@Override
	public boolean isHuman(){
		return true;
	}
}
