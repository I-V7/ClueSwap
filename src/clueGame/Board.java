package clueGame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.junit.Assert;

import clueGame.RoomCell.DoorDirection;


public class Board {
	private BoardCell[][] layout;
	private Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> cell_set;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private int numRows;
	private int numColumns;
	private String boardFile;
	private ClueGame game;
	
	// Board Constructor
	public Board(ClueGame game) {
		rooms = new HashMap<Character,String>();
		adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		setGame(game);
	}

	// load the board layout
	public void loadBoardConfig(Map<Character,String> roomsFromClue) throws BadConfigFormatException {
		Scanner scan = null; 
		String line;
		rooms = roomsFromClue;

		
		try {
			// try to read the layoutFile
			scan = new Scanner(new File(game.getLayoutFile()));
			ArrayList <String[]> temp = new ArrayList<String[]>();
			
			// Reading the data and storing it into a temporary ArrayList
			while(scan.hasNext()) {
				line = scan.next(); 	// entire line
				String[] letters = line.split(",");
				temp.add(letters);
			}
			
			// Create our 2d Array and fill it with BoardCells
			numRows = temp.size();
			numColumns = temp.get(0).length;
			layout = new BoardCell[numRows][numColumns];
			for (int i = 0; i < temp.size(); i++) {
				// test for a layout with bad Columns
				if(temp.get(i).length != numColumns) throw new BadConfigFormatException(game.getLayoutFile());
				String[] temp2 = temp.get(i);
				
				for (int j = 0; j < temp2.length; j++) {
					if (temp2[j].equals("W")) {
						layout[i][j] = new WalkwayCell(i, j);
					}
					else {
						layout[i][j] = new RoomCell(i, j, temp2[j]);
					}
					// test for a layout with bad rooms
					if(!rooms.containsKey(temp2[j].charAt(0))) {
						throw new BadConfigFormatException(game.getLayoutFile());
					}
					
			
				}
			
			}

		} catch (FileNotFoundException e) {
			System.out.println("ERROR: the file: '" + game.getLayoutFile() + "' was not found");
		}
		
	}
	
	
	// Calculate an adjacency list for each BoardCell on the Board
	public void calcAdjacencies() {

		for( int i = 0; i < numRows; i++ ) {
			
			for(int j = 0; j < numColumns; j++) {
				
				LinkedList<BoardCell> adj = new LinkedList<BoardCell>();
				
				BoardCell temp = getCellAt(i,j);
				
				// Is the current cell a Doorway?
				if(temp.isDoorway()) {
					RoomCell door = getRoomCellAt(i,j);
					if(door.getDoorDirection() == DoorDirection.UP) {
						adj.add(getCellAt(i-1,j));
					}
					else if(door.getDoorDirection() == DoorDirection.DOWN) {
						adj.add(getCellAt(i+1,j));
					}
					else if(door.getDoorDirection() == DoorDirection.RIGHT) {
						adj.add(getCellAt(i,j+1));
					}
					else if(door.getDoorDirection() == DoorDirection.LEFT) {
						adj.add(getCellAt(i,j-1));
					}
				}

				else  { // Is the current cell a walkway or room?
					// cell left of temp
					if((j-1) >= 0) {
						BoardCell tempU = getCellAt(i,j-1);
						if(tempU.isWalkway()) {
							if(!temp.isRoom()) { 
								adj.add(getCellAt(i,j-1));
							}
						}
						if(tempU.isRoom()) {
							RoomCell tempU2 = getRoomCellAt(i,j-1);
							if(tempU2.getDoorDirection() == DoorDirection.RIGHT) {
								adj.add(getCellAt(i,j-1));
							}
						}	
					
					}
					// cell right of temp
					if((j+1) < numColumns) {
						BoardCell tempD = getCellAt(i,j+1);
						if(tempD.isWalkway()) {
							if(!temp.isRoom()) {
								adj.add(getCellAt(i,j+1));
							}
						}
						if(tempD.isRoom()) {
							RoomCell tempD2 = getRoomCellAt(i,j+1);
							if(tempD2.getDoorDirection() == DoorDirection.LEFT) {
								adj.add(getCellAt(i,j+1));							
							}
						}	
					
					}
					// cell above temp
					if((i-1) >= 0) {
						BoardCell tempD = getCellAt(i-1,j);
						if(tempD.isWalkway()) {
							if(!temp.isRoom()) {
								adj.add(getCellAt(i-1,j));
							}
						}
						if(tempD.isRoom()) {
							RoomCell tempD2 = getRoomCellAt(i-1,j);
							if(tempD2.getDoorDirection() == DoorDirection.DOWN) {
								adj.add(getCellAt(i-1,j));
							}
						}	
					
					}
					// cell below temp
					if((i+1) < numRows) {
						BoardCell tempD = getCellAt(i+1,j);
						if(tempD.isWalkway()) {
							if(!temp.isRoom()) {
								adj.add(getCellAt(i+1,j));
							}
						}
						if(tempD.isRoom()) {
							RoomCell tempD2 = getRoomCellAt(i+1,j);
							if(tempD2.getDoorDirection() == DoorDirection.UP) {
								adj.add(getCellAt(i+1,j));
							}
						}	
					
					}
					
					
				}
				adjMtx.put(getCellAt(i,j), adj); // key: BoarcdCell at (x,y), value: adjacency list 
			}
		}
		
	}
	
	
	// Calculate the targets for a cell at (x,y) in the given number of moves
	public void calcTargets(int x, int y, int moves) {
		
		BoardCell cell = getCellAt(x,y);
		
		visited.add(cell);
		LinkedList<BoardCell> possMove = adjMtx.get(cell);
		// Base Case
		for(BoardCell b : possMove) {
			if(moves == 1 && !visited.contains(b)) {
				targets.add(b);
			}
		}

		if(moves > 1) {
			for(BoardCell b : possMove) {
				if(b.isDoorway()) {
					
					targets.add(b);
					System.out.println("adding b");
				}
				else if(!visited.contains(b)) {
					calcTargets(b.getRow(),b.getCol(), moves - 1);
				}
			}
		}
		
		visited.remove(cell);
	}

	
	// getter for the 2-D array
	public BoardCell[][] getLayout() {
		return layout;
	}

	// getter for the rooms Map
	public Map<Character, String> getRooms() {
		System.out.println("size" + rooms.size());
		return rooms;
	}

	// getter for rows
	public int getNumRows() {
		return numRows;
	}

	
	// getter for columns
	public int getNumColumns() {
		return numColumns;
	}
	

	// getter for RommCellAt
	public RoomCell getRoomCellAt(int x, int y) {
		return (RoomCell) layout[x][y];
	}

	// getter for cellAt
	public BoardCell getCellAt(int x, int y) {
		return layout[x][y];
	}
	
	// setter for ClueGame
	public void setGame(ClueGame game) {
		this.game = game;
	}
	

	
	
	// getter for the adjacency list
	public LinkedList<BoardCell> getAdjList(int x, int y) {
		return adjMtx.get(getCellAt(x,y));
	}
	
	
	
	// getter for the targets Set
	public Set<BoardCell> getTargets() {
		Set<BoardCell> copyTargets = new HashSet<BoardCell>();
		for(BoardCell b : targets) {
			copyTargets.add(b);
		}
		targets.clear();
		return copyTargets;
	}
	
	
}
