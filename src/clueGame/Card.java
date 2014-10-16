package clueGame;

public class Card 
{
	public String name;
	private CardType card;
	
	//Constructor
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
	@Override
	public boolean equals(Object card)
	{
		//if the object passed in is a card and the 2 cards are the same type and name the return true else it's false
		if(card instanceof Card && ((Card)card).getCardType() == this.getCardType() && ((Card)card).getName() == this.getName())
		{
			return true;
		}
		return false;
		
	}
	
	//GETTERS AND SETTERS
	public CardType getCardType(){
		return this.card;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCard(CardType card) {
		this.card = card;
	}

}
