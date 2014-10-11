package clueTests;


import java.util.ArrayList;

import junit.framework.Assert;

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
	@BeforeClass
	public static void setup() throws BadConfigFormatException {
		game = new ClueGame("Clue Board.csv", "Clue Legend.csv");
		game.loadConfigFiles();
		board = game.getBoard();
	}
	@Before
	public void beforeTest(){
		
	}
	@Test
	public void loadPeopleTest(){
		//Test name, color, then location from input file
		ArrayList<Player> players=game.getPlayers();
		//test Human
		Assert.assertTrue(players.get(1).getName().equals("Professor Plum"));
		Assert.assertTrue(players.get(1).getColorString().equals("purple"));
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
		ArrayList<Card> cards=game.getCards();
		//test size of deck
		Assert.assertEquals(cards.size(), 21);
		//test correct number of cards of types
		int weaponCount=0;
		int personCount=0;
		int roomCount=0;
		for(Card card:cards){
				if(card.getCardType()==CardType.WEAPON){
					weaponCount++;
				}else if(card.getCardType()==CardType.ROOM){
					personCount++;
				}else if(card.getCardType()==CardType.PERSON){
					roomCount++;
				}
		}
		Assert.assertEquals(weaponCount, 6);
		Assert.assertEquals(personCount, 6);
		Assert.assertEquals(roomCount, 9);
		//test selection (names of cards)
		//test person
		Assert.assertTrue(cards.get(0).name.equals("Reverend Green"));
		//test weapon
		Assert.assertTrue(cards.get(0).name.equals("Wrench"));
		//test room
		Assert.assertTrue(cards.get(0).name.equals("Conservatory"));
	}
	
	public void dealCardsTest(){
		
	}
}
