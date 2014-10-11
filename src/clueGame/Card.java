package clueGame;

public class Card {
	public String name;
	private CardType card;
	public Card(String name, String cardType){
		this.name=name;
		if(cardType.equals("Room")){
			this.card=CardType.ROOM;
		}else if(cardType.equals("Weapon")){
			this.card=CardType.WEAPON;
		}else if(cardType.equals("Person")){
			this.card=CardType.PERSON;
		}
	}
	
	
	//GETTERS AND SETTERS
	public CardType getCardType(){
		return this.card;
	}
}
