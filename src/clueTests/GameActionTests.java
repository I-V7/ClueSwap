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
/*	
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
*/	
	@Test
	public void disproveTest()
	{
		Solution solution = game.getSolution();
		ArrayList<Card> playerHand = new ArrayList<Card>();
		ArrayList<Player> players = game.getPlayers();
		ArrayList<Card> testShownCards = new ArrayList<Card>();
		solution.person = "Miss Scarlet";
		solution.room = "Kitchen";
		solution.weapon = "Knife";
		Player player = game.getPlayers().get(0);
				System.out.println("before");
		playerHand.add(new Card("Conservatory", "Room"));
		playerHand.add(new Card("Billiard Room", "Room"));
		playerHand.add(new Card("Revolver", "Weapon"));
		players.get(2).setCardHand(playerHand);
		
		testShownCards.add(players.get(2).getCards().get(0));
		testShownCards.add(players.get(2).getCards().get(1));
		game.setShownCards(testShownCards);
		Card shownCard = game.handleSuggestions("Miss Scarlett", "Kitchen", "Revolver", player);
		System.out.println("after");
		//only possible card
		System.out.println(shownCard);
		Assert.assertTrue(shownCard.getName().equals("Revolver"));
		
		
		testShownCards.clear();
		testShownCards.add(players.get(2).getCards().get(0));
		
		shownCard = game.handleSuggestions("Miss Scarlett", "Billiard Room", "Revolver", player);
		
		//randomly choose between 2
		Assert.assertTrue(shownCard.getName().equals("Billiard Room") || shownCard.getName().equals("Revolver"));
		
	}
	
	@Test
	//HELP!!!!!
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
	//HELP!!!!!!!!!!
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
		solution.person = "Miss Scarlett";
		solution.room = "Kitchen";
		solution.weapon = "Revolver";
		ArrayList<Player> players = game.getPlayers();
		Player player = players.get(0);
		ArrayList<Card> shownCards = new ArrayList<Card>();
		
		players.get(0).setCardHand(player0sHand);
		players.get(1).setCardHand(player1sHand);
		players.get(2).setCardHand(player2sHand);
		players.get(3).setCardHand(player3sHand);
		players.get(4).setCardHand(player4sHand);
		players.get(5).setCardHand(player5sHand);
		
		System.out.println("after hand change");
		for(Card card: players.get(0).getCards())
		{
			
			System.out.println(card.getName());
		}
	    Card shownCard = game.handleSuggestions("Mrs White", "Kitchen", "Revolver", player);
	    //System.out.println(",,,,,,,,,'''''''''" + shownCard.getName());
		//test that the player doesn't have the card that is shown 
		//(player doesn't show themselves their own card)
	    Assert.assertNull(shownCard);
		
		

	}
	
	@Test
	//test a computer suggestion:
	//test where only one suggestion is possible
	public void compSuggestionTest()
	{
		Solution solution = game.getSolution();
		ArrayList<Card> alreadyShown = new ArrayList<Card>();
		solution.person = "";
		solution.room = "";
		solution.weapon = "";
		ArrayList<Player> players = game.getPlayers();
		Player player = players.get(0);
		
		players.get(0).setCardHand(player0sHand);
		players.get(1).setCardHand(player1sHand);
		players.get(2).setCardHand(player2sHand);
		players.get(3).setCardHand(player3sHand);
		players.get(4).setCardHand(player4sHand);
		players.get(5).setCardHand(player5sHand);
		
		for(Player p: players)
		{
			for(Card card: p.getCards())
			{
				alreadyShown.add(card);
			}
		}
		
		game.setShownCards(alreadyShown);
		player.setRow(28);
		player.setCol(0);
		
		String[] suggestion = ((ComputerPlayer) player).createSuggestion(game.getStringToCard(), game.getBoard());
		Assert.assertEquals(3, suggestion.length);
		System.out.println(suggestion[0] +"/////////");
		System.out.println(suggestion[1]);
        Assert.assertTrue(suggestion[0].equals("Mrs White"));
        Assert.assertTrue(suggestion[1].equals("Rope"));
        Assert.assertTrue(suggestion[2].equals("Hall"));
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
		
		players.get(0).setCardHand(player0sHand);
		players.get(1).setCardHand(player1sHand);
		players.get(2).setCardHand(player2sHand);
		players.get(3).setCardHand(player3sHand);
		players.get(4).setCardHand(player4sHand);
		players.get(5).setCardHand(player5sHand);
		
		
	    
		player.setRow(28);
		player.setCol(0);
		String[] suggestion = ((ComputerPlayer)player).createSuggestion(game.getStringToCard(), game.getBoard());
		Assert.assertEquals(3, suggestion.length);
        

		Assert.assertFalse(testShownCards.contains(cards.get(suggestion[0])));
		Assert.assertFalse(testShownCards.contains(cards.get(suggestion[1])));
		Assert.assertFalse(testShownCards.contains(cards.get(suggestion[2])));
		
	}
	
	
}
