package clueTests;


import java.util.ArrayList;

import org.junit.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ClueGame;
import clueGame.Player;

public class GameSetupTests {
	static Board board;
	static ClueGame game;
	static Card kitchenCard;
	static Card libraryCard;
	static Card candleCard;
	static Card revolverCard;
	static Card scarlettCard;
	static Card greenCard;
	static ArrayList<Card> cards;
	
	@BeforeClass
	public static void setup() throws BadConfigFormatException {
		
		kitchenCard=new Card("Kitchen", "Room");
		libraryCard=new  Card("Library", "Room");
		candleCard=new Card("Candle Stick", "Weapon");
		revolverCard= new Card("Revolver", "Weapon");
		scarlettCard=new Card("Miss Scarlett", "Person");
		greenCard=new Card("Reverend Green", "Person");
		
	}
	@Before
	public void init() throws BadConfigFormatException {
		game = new ClueGame("Clue Board.csv", "Clue Legend.csv");
		board = game.getBoard();
		cards=game.getCards();
		
	}
	@Test
	public void loadPeopleTest(){
		//Test name, color, then location from input file
		ArrayList<Player> players=game.getPlayers();
		//test Human
		Assert.assertTrue(players.get(1).getName().equals("Professor Plum"));
		Assert.assertTrue(players.get(1).getColorString().equals("pink"));
		Assert.assertEquals(players.get(1).getRow(), 14);
		Assert.assertEquals(players.get(1).getCol(), 0);
		Assert.assertTrue(players.get(1).isHuman());

		
		//test CPU first and last cpu player
		Assert.assertTrue(players.get(0).getName().equals("Miss Scarlett"));
		Assert.assertTrue(players.get(0).getColorString().equals("red"));
		Assert.assertEquals(players.get(0).getRow(), 6);
		Assert.assertEquals(players.get(0).getCol(), 0);
		Assert.assertFalse(players.get(0).isHuman());
		
		Assert.assertTrue(players.get(5).getName().equals("Mrs White"));
		Assert.assertTrue(players.get(5).getColorString().equals("white"));
		Assert.assertEquals(players.get(5).getRow(), 7);
		Assert.assertEquals(players.get(5).getCol(), 19);
		Assert.assertFalse(players.get(5).isHuman());
	}
	
	@Test
	public void loadCardsTest(){
		
		//test size of deck
		Assert.assertEquals(21,cards.size());
		//test correct number of cards of types
		int weaponCount=0;
		int personCount=0;
		int roomCount=0;
		for(Card card:cards){
				if(card.getCardType()==CardType.WEAPON){
					weaponCount++;
				}else if(card.getCardType()==CardType.ROOM){
					roomCount++;
				}else if(card.getCardType()==CardType.PERSON){
					personCount++;
				}
		}
		Assert.assertEquals(weaponCount, 6);
		Assert.assertEquals(personCount, 6);
		Assert.assertEquals(roomCount, 9);
		//test selection (names of cards)
		//test room
		Assert.assertTrue(cards.get(0).name.equals(kitchenCard.name));
		Assert.assertTrue(cards.get(4).name.equals(libraryCard.name));

		//test weapon
		Assert.assertTrue(cards.get(9).name.equals(candleCard.name));
		Assert.assertTrue(cards.get(12).name.equals(revolverCard.name));
		//test person
		Assert.assertTrue(cards.get(15).name.equals(scarlettCard.name));
		Assert.assertTrue(cards.get(18).name.equals(greenCard.name));
		
		
		
	}
	@Test
	//Test the amount of cards before and after the deal
	public void dealCardsTest(){

		//before the deal
		Assert.assertEquals(21, cards.size());
		game.deal();
		Assert.assertEquals(0, cards.size());
		
	}
	
	@Test
	//Test each players hand to see if any duplicate were dealt
	public void dealCardsTest2()
	{
		ArrayList<Player> players = game.getPlayers();
		ArrayList<Card> player1Cards;
		ArrayList<Card> player2Cards;
		for(int i=0; i<players.size();i++)
		{
			player1Cards = players.get(i).getCards();
			for(int j = i + 1; j<players.size(); j++)
			{
				player2Cards = players.get(j).getCards();
				for(int k=0; k < player2Cards.size(); k++)
				{
					Assert.assertFalse(player1Cards.contains(player2Cards.get(k)));
				}
			}
		}
	}
	
	@Test
	//test that all the players have an even amount of cards
	public void dealCardsTest3()
	{
		ArrayList<Player> players = game.getPlayers();
		game.deal();
		Assert.assertEquals(3, players.get(0).getCards().size());
		Assert.assertEquals(3, players.get(1).getCards().size());
		Assert.assertEquals(3, players.get(2).getCards().size());
		
		Assert.assertEquals(3, players.get(3).getCards().size());
		Assert.assertEquals(3, players.get(4).getCards().size());
		Assert.assertEquals(3, players.get(5).getCards().size());
		
	}
}
