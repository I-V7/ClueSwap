package clueTests;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;

public class AdjTargetTest {
	private static Board board;
	@BeforeClass
	public static void setUp() throws BadConfigFormatException {
		ClueGame game = new ClueGame("Clue Board.csv","Clue Legend.csv");
		game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();
	}
	

		// Tests a walkway tile with 4 walkway adjacencies
		// Orange tile on the board xlsx file
		@Test
		public void testAdjacenciesOnlyWalkways()
		{
			LinkedList<BoardCell> testList = board.getAdjList(6, 15);
			Assert.assertEquals(4, testList.size());
			Assert.assertTrue(testList.contains(board.getCellAt(5, 15)));
			Assert.assertTrue(testList.contains(board.getCellAt(7, 15)));
			Assert.assertTrue(testList.contains(board.getCellAt(6, 14)));
			Assert.assertTrue(testList.contains(board.getCellAt(6, 16)));
		}
		
		// Tests walkway tiles that are on the edge of the board
		// Orange tiles on the board xlsx file
		@Test
		public void testEdgeWalkways()
		{
			// Test a top walkway
			LinkedList<BoardCell> testList = board.getAdjList(0, 5);
			Assert.assertEquals(2, testList.size());
			Assert.assertTrue(testList.contains(board.getCellAt(0, 4)));
			Assert.assertTrue(testList.contains(board.getCellAt(1, 5)));
			// Test a left walkway
			testList = board.getAdjList(14, 0);
			Assert.assertEquals(3, testList.size());
			Assert.assertTrue(testList.contains(board.getCellAt(13, 0)));
			Assert.assertTrue(testList.contains(board.getCellAt(15, 0)));
			Assert.assertTrue(testList.contains(board.getCellAt(14, 1)));
			// Test a bottom walkway
			testList = board.getAdjList(28, 3);
			Assert.assertEquals(2, testList.size());
			Assert.assertTrue(testList.contains(board.getCellAt(27, 3)));
			Assert.assertTrue(testList.contains(board.getCellAt(28, 4)));
			// Test a right walkway
			testList = board.getAdjList(7, 19);
			Assert.assertEquals(2, testList.size());
			Assert.assertTrue(testList.contains(board.getCellAt(7, 18)));
			Assert.assertTrue(testList.contains(board.getCellAt(8, 19)));
		}
		
		// Tests walkway tiles that are next to non-door room tiles
		// Orange tiles on the board xlsx file
		@Test
		public void testNonDoorWalkwayTiles() {
			// Test one place
			LinkedList<BoardCell> testList = board.getAdjList(22, 4);
			Assert.assertEquals(3, testList.size());
			Assert.assertTrue(testList.contains(board.getCellAt(21, 4)));
			Assert.assertTrue(testList.contains(board.getCellAt(23, 4)));
			Assert.assertTrue(testList.contains(board.getCellAt(22, 3)));
			// Test a second place
			testList = board.getAdjList(9, 12);
			Assert.assertEquals(3, testList.size());
			Assert.assertTrue(testList.contains(board.getCellAt(8, 12)));
			Assert.assertTrue(testList.contains(board.getCellAt(10, 12)));
			Assert.assertTrue(testList.contains(board.getCellAt(9, 13)));
		}
		
		// Tests walkway tiles that are next to door room tiles
		// Green tiles on the board xlsx file
		@Test
		public void testDoorWalkwayTiles() {
			// Test one place
			LinkedList<BoardCell> testList = board.getAdjList(18, 8);
			Assert.assertEquals(4, testList.size());
			Assert.assertTrue(testList.contains(board.getCellAt(17, 8)));
			Assert.assertTrue(testList.contains(board.getCellAt(19, 8)));
			Assert.assertTrue(testList.contains(board.getCellAt(18, 7)));
			Assert.assertTrue(testList.contains(board.getCellAt(18, 9)));
			// Test a second place
			testList = board.getAdjList(12, 15);
			Assert.assertEquals(3, testList.size());
			Assert.assertTrue(testList.contains(board.getCellAt(12, 14)));
			Assert.assertTrue(testList.contains(board.getCellAt(12, 16)));
			Assert.assertTrue(testList.contains(board.getCellAt(13, 15)));
		}
		
		// Tests walkway tiles that are next to door room tiles
		// Black tiles on the board xlsx file
		@Test
		public void testDoorTiles() {
			// Test one door
			LinkedList<BoardCell> testList = board.getAdjList(19, 5);
			Assert.assertEquals(1, testList.size());
			Assert.assertTrue(testList.contains(board.getCellAt(18, 5)));
			
			// Test a second door
			testList = board.getAdjList(23, 13);
			Assert.assertEquals(1, testList.size());
			Assert.assertTrue(testList.contains(board.getCellAt(22, 13)));
		}
		
		//Tests of just walkways, moving 1, 2, 3, or 4 spaces
		//Brown on xlsx file
		@Test
		public void testTargetsWalkways() {
			//1 Step
			board.calcTargets(16, 11, 1);
			
			Set<BoardCell> targets= board.getTargets();
			
			Assert.assertEquals(4, targets.size());
			Assert.assertTrue(targets.contains(board.getCellAt(15, 11)));
			Assert.assertTrue(targets.contains(board.getCellAt(17, 11)));	
			Assert.assertTrue(targets.contains(board.getCellAt(16, 12)));
			Assert.assertTrue(targets.contains(board.getCellAt(16, 10)));
			
			//2 Steps
			board.calcTargets(1, 13, 2);
			targets= board.getTargets();
	
			Assert.assertEquals(5, targets.size());
			Assert.assertTrue(targets.contains(board.getCellAt(0, 12)));
			Assert.assertTrue(targets.contains(board.getCellAt(0, 14)));	
			Assert.assertTrue(targets.contains(board.getCellAt(2, 12)));
			Assert.assertTrue(targets.contains(board.getCellAt(2, 14)));
			Assert.assertTrue(targets.contains(board.getCellAt(3, 13)));
			
			//3 Steps
			board.calcTargets(28, 9, 3);
			targets= board.getTargets();
			for(BoardCell b : targets) {
				System.out.println(b.getRow() + " " + b.getCol());
			}
			Assert.assertEquals(4, targets.size());
			Assert.assertTrue(targets.contains(board.getCellAt(25, 9)));
			Assert.assertTrue(targets.contains(board.getCellAt(26, 10)));
			Assert.assertTrue(targets.contains(board.getCellAt(27, 9)));
			Assert.assertTrue(targets.contains(board.getCellAt(28, 10)));
			
			//4 Steps
			board.calcTargets(20, 1, 4);
			targets= board.getTargets();
			Assert.assertEquals(10, targets.size());
			Assert.assertTrue(targets.contains(board.getCellAt(17, 0)));
			Assert.assertTrue(targets.contains(board.getCellAt(16, 1)));
			Assert.assertTrue(targets.contains(board.getCellAt(17, 2)));
			Assert.assertTrue(targets.contains(board.getCellAt(18, 3)));
			Assert.assertTrue(targets.contains(board.getCellAt(19, 4)));
			Assert.assertTrue(targets.contains(board.getCellAt(21, 4)));
			Assert.assertTrue(targets.contains(board.getCellAt(22, 3)));
			Assert.assertTrue(targets.contains(board.getCellAt(18, 1)));
			Assert.assertTrue(targets.contains(board.getCellAt(19, 2)));
			Assert.assertTrue(targets.contains(board.getCellAt(20, 3)));
		}
		
		//Tests of rolls that allow you to enter a room
		//Brown on xlsx file
		@Test
		public void testEnteringRooms() {
			
			//2 Steps
			board.calcTargets(17, 17, 2);
			Set<BoardCell> targets= board.getTargets();
			System.out.println("17,17 -------");
			for(BoardCell b : targets) {
				System.out.println(b.getRow() + " " + b.getCol());
			}
			Assert.assertEquals(7, targets.size());
			Assert.assertTrue(targets.contains(board.getCellAt(17, 15)));
			Assert.assertTrue(targets.contains(board.getCellAt(17, 19)));	
			Assert.assertTrue(targets.contains(board.getCellAt(18, 16)));
			Assert.assertTrue(targets.contains(board.getCellAt(18, 18)));
			Assert.assertTrue(targets.contains(board.getCellAt(16, 17)));
			Assert.assertTrue(targets.contains(board.getCellAt(16, 18)));
			Assert.assertTrue(targets.contains(board.getCellAt(19, 17)));
			
			//3 Steps
			board.calcTargets(3, 16, 3);
			targets= board.getTargets();
			
			Assert.assertEquals(9, targets.size());
			Assert.assertTrue(targets.contains(board.getCellAt(2, 14)));
			Assert.assertTrue(targets.contains(board.getCellAt(3, 13)));
			Assert.assertTrue(targets.contains(board.getCellAt(4, 14)));
			Assert.assertTrue(targets.contains(board.getCellAt(5, 15)));
			Assert.assertTrue(targets.contains(board.getCellAt(6, 16)));
			Assert.assertTrue(targets.contains(board.getCellAt(4, 17)));
			Assert.assertTrue(targets.contains(board.getCellAt(5, 17)));
			Assert.assertTrue(targets.contains(board.getCellAt(4, 16)));
			Assert.assertTrue(targets.contains(board.getCellAt(3, 15)));
		}
		
		//Tests of rolls when you leave a room
		//Purple on xlsx file
		@Test
		public void testLeavingRooms() {
			
			//2 Steps
			board.calcTargets(1, 9, 2);
			Set<BoardCell> targets= board.getTargets();
			System.out.println("1,9------");
			for(BoardCell b : targets) {
				System.out.println(b.getRow() + " " + b.getCol());
			}
			Assert.assertEquals(2, targets.size());
			Assert.assertTrue(targets.contains(board.getCellAt(2, 8)));
			Assert.assertTrue(targets.contains(board.getCellAt(3, 9)));	
			
			//3 Steps
			board.calcTargets(19, 17, 3);
			targets= board.getTargets();
			Assert.assertEquals(7, targets.size());
			Assert.assertTrue(targets.contains(board.getCellAt(18, 15)));
			Assert.assertTrue(targets.contains(board.getCellAt(17, 16)));
			Assert.assertTrue(targets.contains(board.getCellAt(16, 17)));
			Assert.assertTrue(targets.contains(board.getCellAt(17, 18)));
			Assert.assertTrue(targets.contains(board.getCellAt(18, 19)));
			Assert.assertTrue(targets.contains(board.getCellAt(19, 18)));
		}
}