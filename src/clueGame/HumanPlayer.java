package clueGame;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class HumanPlayer extends Player {
	
	ArrayList<Card> allCards;
	
	public HumanPlayer(){
		
	}
	public HumanPlayer(String name, String color, int row, int col, ArrayList<Card> cards){
		super();
		this.name=name;
		setPlayerColor(color);
		this.colorString=color;
		this.row=row;
		this.col=col;
		allCards = cards;
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
		Card disprovenCard=null;
		for(Card card: myCards)
		{
			if((card.getName().equals(person) || card.getName().equals(room) || card.getName().equals(weapon)) && !shownCards.contains(card))
			{
				disproveCards.add(card);
			}
		}
		if(!disproveCards.isEmpty()){
			System.out.println("Possible cards to disprove: ");
			for(Card card: disproveCards)
			{
				System.out.println(card.getName());
			}
			System.out.println("Choose one to show: ");
			Scanner scanner = new Scanner(System.in);
			String cardChoosen = scanner.nextLine();
			boolean invalidCard=true;
			while(invalidCard){
				for(Card card:myCards){
					if(cardChoosen.equals(card.getName())){
						disprovenCard=card;
						invalidCard=false;
					}
				}
				if(invalidCard){
					System.out.println("Choose a valid card: ");
					cardChoosen = scanner.nextLine();
				}
			}
		}
		
		return disprovenCard;
	}
}
