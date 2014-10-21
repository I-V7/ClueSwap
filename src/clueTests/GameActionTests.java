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
	static ArrayList<Card> player0sHand;
	static ArrayList<Card> player1sHand;
	static ArrayList<Card> player2sHand;
	static ArrayList<Card> player3sHand;
	static ArrayList<Card> player4sHand;
	static ArrayList<Card> player5sHand;
	
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
		player0sHand = new ArrayList<Card>();
		player1sHand = new ArrayList<Card>();
		player2sHand = new ArrayList<Card>();
		player3sHand = new ArrayList<Card>();
		player4sHand = new ArrayList<Card>();
		player5sHand = new ArrayList<Card>();
		
		player0sHand.add(new Card("Kitchen","Room"));
		player0sHand.add(new Card("Colonel Mustard","Person"));
		player0sHand.add(new Card("Revolver","Weapon"));
		player1sHand.add(new Card("Ballroom","Room"));
		player1sHand.add(new Card("Lounge","Room"));
		player1sHand.add(new Card("Reverend Green","Person"));
		player2sHand.add(new Card("Conservatory","Room"));
		player2sHand.add(new Card("Dining Room","Room"));
		player2sHand.add(new Card("Wrench","Weapon"));
		player3sHand.add(new Card("Billiard Room","Room"));
		player3sHand.add(new Card("Candle Stick","Weapon"));
		player3sHand.add(new Card("Miss Scarlett","Person"));
		player4sHand.add(new Card("Library","Room"));
		player4sHand.add(new Card("Knife","Weapon"));
		player4sHand.add(new Card("Professor Plum","Person"));
		player5sHand.add(new Card("Study","Room"));
		player5sHand.add(new Card("Lead Pipe","Weapon"));
		player5sHand.add(new Card("Mrs Peacock","Person"));
		
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
		for (int i=0; i< 100; i++) {
			//do this so player doesnt think room is last room visited
			player.setLastRoomVisited('X');
			board.calcTargets(player.getRow(), player.getCol(), 2);
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(5,17))
				roomLoc_6_16++;
			else
				otherLocations++;
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, roomLoc_6_16);
	}
	//test that the last room visited is not automatic
	@Test
	public void testLastRoomTargetSelection(){
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
		for (int i=0; i< 100; i++) {
			board.calcTargets(player.getRow(), player.getCol(), 2);
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(5,17))
				roomLoc_6_16++;
			else
				otherLocations++;
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100,otherLocations+roomLoc_6_16);
		Assert.assertNotEquals(100, roomLoc_6_16);
		//assure other locations is more than room
		assertTrue(otherLocations > roomLoc_6_16);
	}
	//Disprove test for a single player
	//test that one player returns only possible card and that that a player randomly chooses a card
	@Test
	public void disproveTest()
	{
		Solution solution = game.getSolution();
		ArrayList<Card> playerHand = new ArrayList<Card>();
		ArrayList<Player> players = game.getPlayers();
		ArrayList<Card> testShownCards = new ArrayList<Card>();
		solution.person = "Mrs White";
		solution.room = "Hall";
		solution.weapon = "Rope";
		Player player = game.getPlayers().get(0);
		ArrayList<Card> alreadyShown = new ArrayList<Card>();
		//set up card scenarios 
		players.get(0).setCardHand(player0sHand);
		players.get(1).setCardHand(player1sHand);
		players.get(2).setCardHand(player2sHand);
		players.get(3).setCardHand(player3sHand);
		players.get(4).setCardHand(player4sHand);
		players.get(5).setCardHand(player5sHand);
	
		//TESTS ASSERTIONS
		//disprove suggestions for different players
		//room
		assertTrue("Kitchen".equals(players.get(0).disproveSuggestion("Professor Plum", "Kitchen", "Wrench").getName()));
		//weapon
	    assertTrue("Wrench".equals(players.get(2).disproveSuggestion("Professor Plum", "Kitchen", "Wrench").getName()));
		//person
		assertTrue("Miss Scarlett".equals(players.get(3).disproveSuggestion("Miss Scarlett", "Kitchen", "Wrench").getName()));
		//null
		assertEquals(null,players.get(4).disproveSuggestion("Miss Scarlett",  "Kitchen" , "Wrench"));
		alreadyShown.remove(players.get(2).getCards().get(2));
		
		//randomly choose between 2 cards
		int kitchen=0;
		int cMustard=0;
		for(int i=0; i < 100; i++){
			String card=players.get(0).disproveSuggestion("Colonel Mustard", "Kitchen", "Wrench").getName();
			if("Kitchen".equals(card)){
				kitchen++;
			}else if("Colonel Mustard".equals(card)){
				cMustard++;
			}
		}
		assertEquals(100, kitchen + cMustard);
		assertTrue( kitchen >= 30 );
		assertTrue( cMustard >= 30);
	}
	@Test
	//test that players are queried in order 
	public void PlayerOrderTest()
	{
		Solution solution = game.getSolution();
		solution.person = "Mrs White";
		solution.room = "Hall";
		solution.weapon = "Rope";
		ArrayList<Player> players = game.getPlayers();
		Player player = game.getPlayers().get(0);
		Card shownCard = game.handleSuggestions("", "", "", player);
		players.get(0).setCardHand(player0sHand);
		players.get(1).setCardHand(player1sHand);
		players.get(2).setCardHand(player2sHand);
		players.get(3).setCardHand(player3sHand);
		players.get(4).setCardHand(player4sHand);
		players.get(5).setCardHand(player5sHand);
	
		//test that the last player is queried
		shownCard = game.handleSuggestions("Mrs White", "Hall", "Lead Pipe", player);
		Assert.assertTrue(players.get(5).getCards().contains(shownCard));
		
		//test that a middle player is queried
		shownCard = game.handleSuggestions("Miss Scarlett", "Hall", "Wrench", player);
		Assert.assertTrue(players.get(2).getCards().contains(shownCard));
		
	}
	@Test
	//test that current player does not return a card
	public void currentPlayerReturnTest()
	{
		Solution solution = game.getSolution();
		solution.person = "Mrs White";
		solution.room = "Hall";
		solution.weapon = "Rope";
		ArrayList<Player> players = game.getPlayers();
		Player player = game.getPlayers().get(0);
		Card shownCard = game.handleSuggestions("", "", "", player);
		players.get(0).setCardHand(player0sHand);
		players.get(1).setCardHand(player1sHand);
		players.get(2).setCardHand(player2sHand);
		players.get(3).setCardHand(player3sHand);
		players.get(4).setCardHand(player4sHand);
		players.get(5).setCardHand(player5sHand);
	
		//test that current player is not does not return a card
		shownCard = game.handleSuggestions("Colonel Mustard", "Kitchen", "Revolver", player);
		assertEquals(null, shownCard);
		
	}
	@Test
	//test involving human player
	//WHEN PROMPTED FOR CARD TYPE LOUNGE
	public void humanDisproveTest()
	{
		Solution solution = game.getSolution();
		solution.person = "Mrs White";
		solution.room = "Hall";
		solution.weapon = "Rope";
		ArrayList<Player> players = game.getPlayers();
		Player player = game.getPlayers().get(0);
		players.get(0).setCardHand(player0sHand);
		players.get(1).setCardHand(player1sHand);
		players.get(2).setCardHand(player2sHand);
		players.get(3).setCardHand(player3sHand);
		players.get(4).setCardHand(player4sHand);
		players.get(5).setCardHand(player5sHand);
	
		//With no GUI i wasn't sure how to prompt the user to select a card to show
		//so I printed out the list of possible cards the user could show
		//and then wait for them to enter the name of a card
		Card shownCard = game.handleSuggestions("Reverend Green", "Lounge", "Lead Pipe", player);
		//players.get(1).disproveSuggestion("Reverend Green", "Lounge", "Lead Pipe");
		
		assertTrue("Lounge".equals(shownCard.getName()));
	}
}
