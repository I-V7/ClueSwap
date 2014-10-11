package clueTests;


import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
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
		Assert.assertEquals(players.get(1).getLocation(), board.getCellAt(14, 0));
		
		//test CPU first and last cpu player
		Assert.assertTrue(players.get(0).getName().equals("Miss Scarlett"));
		Assert.assertTrue(players.get(0).getColorString().equals("red"));
		Assert.assertEquals(players.get(0).getLocation(), board.getCellAt(6, 0));
		
		Assert.assertTrue(players.get(5).getName().equals("Mrs White"));
		Assert.assertTrue(players.get(5).getColorString().equals("white"));
		Assert.assertEquals(players.get(5).getLocation(), board.getCellAt(7, 19));
	}
	
	public void loadCardsTest(){
		
	}
	
	public void dealCardsTest(){
		
	}
}
