package clueTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

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
import clueGame.ComputerPlayer;

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
	
	@Test
	public void disproveTest()
	{
		Solution solution = game.getSolution();
		solution.person = "";
		solution.room = "";
		solution.weapon = "";
		Player player = game.getPlayers().get(1);
		Card shownCard = game.handleSuggestions("", "", "", player);
		
		//only possible card
		Assert.assertTrue(shownCard.getName().equals(""));
		//randomly choose between 2
		Assert.assertTrue(shownCard.getName().equals("") || shownCard.getName().equals(""));
		
	}
	@Test
	//test that players are queried in order
	public void PlayerOrderTest()
	{
		Solution solution = game.getSolution();
		solution.person = "";
		solution.room = "";
		solution.weapon = "";
		ArrayList<Player> players = game.getPlayers();
		Player player = game.getPlayers().get(0);
		Card shownCard = game.handleSuggestions("", "", "", player);
		
		Assert.assertTrue(shownCard.getName().equals(""));
		
		
	}
	
	@Test
	//test involving human player
	public void humanDisproveTest()
	{
		
	}
	@Test
	//test that the players whose turn it is does not return a card
	public void cardReturnTest()
	{
		Solution solution = game.getSolution();
		ArrayList<Card> playerHand = new ArrayList<Card>();
		solution.person = "";
		solution.room = "";
		solution.weapon = "";
		ArrayList<Player> players = game.getPlayers();
		Player player = players.get(0);
		
		
		//set every players card hand
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		player.setCardHand(playerHand);
		playerHand.clear();
		
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		players.get(1).setCardHand(playerHand);
		playerHand.clear();
		
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		players.get(2).setCardHand(playerHand);
		playerHand.clear();
		
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		players.get(3).setCardHand(playerHand);
		playerHand.clear();
		
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		players.get(4).setCardHand(playerHand);
		playerHand.clear();
		
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		players.get(5).setCardHand(playerHand);
		playerHand.clear();
		
		Card shownCard = game.handleSuggestions("", "", "", player);
		
		//test that the player doesn't have the card that is shown 
		//(player doesn't show themselves their own card)
		Assert.assertFalse(player.getCards().contains(shownCard));

	}
	
	@Test
	//test a computer suggestion:
	//test where only one suggestion is possible
	public void compSuggestionTest()
	{
		Solution solution = game.getSolution();
		ArrayList<Card> playerHand = new ArrayList<Card>();
		solution.person = "";
		solution.room = "";
		solution.weapon = "";
		ArrayList<Player> players = game.getPlayers();
		Player player = players.get(0);
		
	    
	    playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		player.setCardHand(playerHand);
		playerHand.clear();
		
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		players.get(1).setCardHand(playerHand);
		playerHand.clear();
		
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		players.get(2).setCardHand(playerHand);
		playerHand.clear();
		
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		players.get(3).setCardHand(playerHand);
		playerHand.clear();
		
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		players.get(4).setCardHand(playerHand);
		playerHand.clear();
		
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		players.get(5).setCardHand(playerHand);
		playerHand.clear();
		
		
		String[] suggestion = ((ComputerPlayer)player).createSuggestion();
		Assert.assertEquals(3, suggestion.length);
        Assert.assertTrue(suggestion[0].equals(""));
        Assert.assertTrue(suggestion[1].equals(""));
        Assert.assertTrue(suggestion[2].equals(""));
	}
	
	@Test
	//random suggestion test
	public void compSuggestionTest1()
	{
		Solution solution = game.getSolution();
		ArrayList<Card> playerHand = new ArrayList<Card>();
		solution.person = "";
		solution.room = "";
		solution.weapon = "";
		ArrayList<Player> players = game.getPlayers();
		ArrayList<Card> testShownCards = new ArrayList<Card>();
		HashMap<String, Card> cards = new HashMap<String,Card>();
		
		Player player = players.get(0);
		
	    
	    playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		player.setCardHand(playerHand);
		playerHand.clear();
		
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		players.get(1).setCardHand(playerHand);
		playerHand.clear();
		
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		players.get(2).setCardHand(playerHand);
		playerHand.clear();
		
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		players.get(3).setCardHand(playerHand);
		playerHand.clear();
		
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		players.get(4).setCardHand(playerHand);
		playerHand.clear();
		
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		playerHand.add(new Card("", ""));
		players.get(5).setCardHand(playerHand);
		playerHand.clear();
		
		//set already shown cards
		testShownCards.add(kitchenCard);
		testShownCards.add(kitchenCard);
		testShownCards.add(kitchenCard);
		game.setShownCards(testShownCards);
		
		String[] suggestion = ((ComputerPlayer)player).createSuggestion();
		Assert.assertEquals(3, suggestion.length);
        

		Assert.assertFalse(testShownCards.contains(cards.get(suggestion[0])));
		Assert.assertFalse(testShownCards.contains(cards.get(suggestion[1])));
		Assert.assertFalse(testShownCards.contains(cards.get(suggestion[2])));
		
	}
	
	
}
