package clueTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.Player;
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
		board.calcAdjacencies();
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
		
		//test correct solution
		Assert.assertTrue(game.checkAccusation(personCard, roomCard, weaponCard));
		//test incorrect solutions
		Assert.assertFalse(game.checkAccusation(wrongPersonCard, roomCard, weaponCard));
		Assert.assertFalse(game.checkAccusation(personCard, wrongRoomCard, weaponCard));
		Assert.assertFalse(game.checkAccusation(personCard, roomCard, wrongWeaponCard));
		
	}
	
	@Test 
	public void testRandomTargetSelection(){
		ArrayList<Player> players=game.getPlayers();
		//I know player 0 is computer since i assigned which player is human for the time being
		ComputerPlayer player=(ComputerPlayer)players.get(0); 
		player.setRow(8);
		player.setCol(18);
		//possible locations
		int loc_7_19Tot = 0;
		int loc_7_17Tot = 0;
		int loc_8_16Tot = 0;
		// Run the test 100 times
		for (int i=0; i< 100; i++) {
			board.calcTargets(player.getRow(), player.getCol(), 2);
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(7,19))
				loc_7_19Tot++;
			else if (selected == board.getCellAt(7, 17))
				loc_7_17Tot++;
			else if (selected == board.getCellAt(8, 16))
				loc_8_16Tot++;
			else
				fail("Invalid target selected");
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, loc_7_19Tot + loc_7_17Tot + loc_8_16Tot);
		// Ensure each target was selected more than once
		assertTrue(loc_7_19Tot > 10);
		assertTrue(loc_7_17Tot > 10);
		assertTrue(loc_8_16Tot > 10);
	}
	@Test
	public void testRoomTargetSelection(){
		ArrayList<Player> players=game.getPlayers();
		//I know player 0 is computer since i assigned which player is human for the time being
		ComputerPlayer player=(ComputerPlayer)players.get(0); 
		player.setRow(6);
		player.setCol(16);
		//possible locations
		//room location
		int roomLoc_6_16=0;
		//walkway locations
		int otherLocations = 0;
		// Run the test 100 times
		for (int i=0; i< 30; i++) {
			board.calcTargets(player.getRow(), player.getCol(), 2);
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(5,27))
				roomLoc_6_16++;
			else
				otherLocations++;
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(30, roomLoc_6_16);
	}

}
