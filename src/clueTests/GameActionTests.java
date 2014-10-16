package clueTests;

import java.util.ArrayList;

import org.junit.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
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
	
	/*@Test
	public void correctAccusation()
	{
		Solution solution = new Solution();
		solution.person = "Miss Scarlett";
		solution.room = "Kitchen";
		solution.weapon="Revolver";
		Assert.assertTrue(solution.person == "Miss Scarlett");
		Assert.assertTrue(solution.room == "Kitchen");
		Assert.assertTrue(solution.weapon == "Revolver");
		
		Assert.assertFalse(solution.person == "Professor Plum");
		Assert.assertTrue(solution.room == "Kitchen");
		Assert.assertTrue(solution.weapon == "Revolver");
		
		Assert.assertTrue(solution.person == "Miss Scarlett");
		Assert.assertFalse(solution.room == "Hall");
		Assert.assertTrue(solution.weapon == "Revolver");
		
		Assert.assertTrue(solution.person == "Miss Scarlett");
		Assert.assertTrue(solution.room == "Kitchen");
		Assert.assertFalse(solution.weapon == "Wrench");
		
	
		
	}*/
	

}
