package clueGame;

import java.util.ArrayList;
import java.util.Scanner;
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
	@Override
	public Card disproveSuggestion(String person, String room, String weapon)
	{
		ArrayList<Card> disproveCards = new ArrayList<Card>();
		for(Card card: myCards)
		{
			if((card.getName().equals(person) || card.getName().equals(room) || card.getName().equals(weapon)) && !shownCards.contains(card))
			{
				disproveCards.add(card);
			}
		}
		System.out.println("Possible cards to disprove: ");
		for(Card card: disproveCards)
		{
			System.out.println(card.getName());
		}
		System.out.println("Choose one to show: ");
		Scanner scanner = new Scanner(System.in);
		String cardChoosen = scanner.next();
		
		
		return ClueGame.cardStringToCard.get(cardChoosen);
	}
}
