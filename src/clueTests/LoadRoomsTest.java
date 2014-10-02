package clueTests;

import java.util.Map;

import org.junit.Test;

import junit.framework.Assert;
import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.ClueGame;


public class LoadRoomsTest {


	@SuppressWarnings("deprecation")
	@Test
	public void roomsMapTest() throws BadConfigFormatException {
		ClueGame game = new ClueGame("Clue Board.csv","Clue Legend.csv");
		game.loadConfigFiles();
		Map<Character,String> rooms = game.getRooms();

		Assert.assertEquals("Conservatory", rooms.get('C'));
		Assert.assertEquals("Kitchen", rooms.get('K'));
		Assert.assertEquals("Hall", rooms.get('H'));
		Assert.assertEquals("Closet", rooms.get('X'));
		Assert.assertEquals("Ballroom", rooms.get('B'));
		Assert.assertEquals("Study", rooms.get('S'));
		Assert.assertEquals("BilliardRoom", rooms.get('R'));
		Assert.assertEquals("Lounge", rooms.get('O'));
		Assert.assertEquals("Library", rooms.get('L'));
		Assert.assertEquals("Walkway", rooms.get('W'));
		Assert.assertEquals("DiningRoom", rooms.get('D'));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void roomsTest() throws BadConfigFormatException {
		ClueGame game = new ClueGame("Clue Board.csv","Clue Legend.csv");
		game.loadConfigFiles();
		Map<Character,String> rooms = game.getRooms();
		int numberOfRooms = 11;
		Assert.assertEquals(numberOfRooms,rooms.size());
	}

}

