package clueTests;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.ClueGame;
import clueGame.RoomCell;

@SuppressWarnings("deprecation")
public class LoadBoardTest {

	private static int ROWS = 29;
	private static int COLS = 20;
	static Board board;
	
	
	@BeforeClass
	public static void initialize() throws BadConfigFormatException {
		ClueGame game = new ClueGame("Clue Board.csv", "Clue Legend.csv");
		game.loadConfigFiles();
		board = game.getBoard();
	}

	
	// Check the columns and rows
	@Test
	public void rowsColumnstest() {
		Assert.assertEquals(ROWS,board.getNumRows());
		assertEquals(COLS, board.getNumColumns());
	}
	
	// Direction of Doorway at (3,3)
	@Test
	public void doorDirections() {
		RoomCell room = board.getRoomCellAt(3, 3);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
	}
	
	// Total Number of Cells
	@Test
	public void cellsTest() {
		int cells = 29 * 20;
		int totalCells = board.getNumColumns() * board.getNumRows();
		assertEquals(cells, totalCells);
	}
	

	// Check for correct Room Initial
	@Test
	public void roomInitials() {
		RoomCell library = board.getRoomCellAt(0, 0);
		Assert.assertEquals('L', library.getInitial());
		RoomCell billiard = board.getRoomCellAt(11, 2);
		Assert.assertEquals('R', billiard.getInitial());
		RoomCell ballroom = board.getRoomCellAt(10, 2);
		Assert.assertEquals('R', ballroom.getInitial());
		RoomCell closet = board.getRoomCellAt(10, 9);
		Assert.assertEquals('X', closet.getInitial());
	}
	
	// Test for bad configuration files
	@Test (expected = BadConfigFormatException.class)
	public void testRoomConfig() throws BadConfigFormatException {
		ClueGame game = new ClueGame("ClueBadBoard.csv","ClueBadLegend.csv");
		game.loadConfigFiles();
	}
	

}
