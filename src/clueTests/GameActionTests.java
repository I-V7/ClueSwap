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
import clueGame.Solution;

public class GameActionTests {
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
	public void correctAccusation()
	{
		Solution solution = game.getSolution();
		solution.person = "Miss Scarlett";
		solution.room = "Kitchen";
		solution.weapon="Revolver";
		Card personCard = new Card("Miss Scarlett", "Person");
		Card roomCard = new Card("Kitchen", "Room");
		Card weaponCard = new Card("Revolver", "Weapon");
		Card wrongPersonCard = new Card("Mrs White", "Person");
		Card wrongWeaponCard = new Card("Hall", "Room");
		Card wrongRoomCard = new Card("Rope", "Weapon");
		
		
		Assert.assertTrue(game.checkAccusation(personCard, roomCard, weaponCard));
		
		Assert.assertFalse(game.checkAccusation(wrongPersonCard, roomCard, weaponCard));
	
		Assert.assertFalse(game.checkAccusation(personCard, wrongRoomCard, weaponCard));
		Assert.assertFalse(game.checkAccusation(personCard, roomCard, wrongWeaponCard));
		
	
		
	}
	

}
