package clueGame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.junit.Assert;

import clueGame.RoomCell.DoorDirection;


public class Board extends JPanel{
	//logic
	private BoardCell[][] layout;
	private Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private ArrayList<Player> players;
	private int numRows;
	private int numColumns;
	private ClueGame game;
	private JLabel kitchenLabel;
	private JLabel ballroomLabel;
	private JLabel conservatoryLabel;
	private JLabel billardLabel;
	private JLabel libraryLabel;
	private JLabel studyLabel;
	private JLabel hallLabel;
	private JLabel loungeLabel;
	private JLabel diningLabel;
	private JLabel closetLabel;
	private Color labelColor;
	
	
	//GUI instance variables
	public final int BOARD_WIDTH=700;
	public final int BOARD_HEIGHT=900;
	// Board Constructor
	public Board(ClueGame game) {
		rooms = new HashMap<Character,String>();
		adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		kitchenLabel = new JLabel("Kitchen");
		ballroomLabel = new JLabel("Ballroom");
		conservatoryLabel = new JLabel("Conservatory");
		billardLabel = new JLabel("Billard Room");
		libraryLabel = new JLabel("Library");
		studyLabel = new JLabel("Study");
		hallLabel = new JLabel("Hall");
		loungeLabel = new JLabel("Lounge");
		diningLabel = new JLabel("Dining Room");
		closetLabel= new JLabel("Closet");
		labelColor = new Color(255,0,0);
		setLayout(null);
	    
		displayLabels();
		setGame(game);
	}
	
	private void displayLabels()
	{
		kitchenLabel.setForeground(labelColor);
		ballroomLabel.setForeground(labelColor);
		conservatoryLabel.setForeground(labelColor);
		billardLabel.setForeground(labelColor);
		libraryLabel.setForeground(labelColor);
		studyLabel.setForeground(labelColor);
		hallLabel.setForeground(labelColor);
		loungeLabel.setForeground(labelColor);
		diningLabel.setForeground(labelColor);
		closetLabel.setForeground(labelColor);
		
		
		
		kitchenLabel.setBounds(285, 5, 50, 20);
		ballroomLabel.setBounds(210, 600, 100, 20);
		conservatoryLabel.setBounds(575, 550, 100, 20);
		billardLabel.setBounds(30, 240, 100, 20);
		libraryLabel.setBounds(50, 50, 50, 20);
		studyLabel.setBounds(410, 630, 50, 20);
		hallLabel.setBounds(40, 600, 50, 20);
		loungeLabel.setBounds(575, 300, 50, 20);
		diningLabel.setBounds(575, 5, 100, 20);
		closetLabel.setBounds(275,250,100,20);
		
		
		add(kitchenLabel);
		add(ballroomLabel);
		add(conservatoryLabel);
		add(billardLabel);
		add(libraryLabel);
		add(studyLabel);
		add(hallLabel);
		add(loungeLabel);
		add(diningLabel);
		add(closetLabel);
	}
	//GUI methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i=0; i < numRows; i++){
			for(int j=0; j < numColumns; j++){
				layout[i][j].draw(g,this);
			}
		}
		players=game.getPlayers();
		for(Player player:players){
			player.draw(g, this);
		}
		
	}
	//LOGIC methods
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
		//close scanner
		scan.close();
	}
	
	// Calculate an adjacency list for each BoardCell on the Board
	public void calcAdjacencies() {

		for( int i = 0; i < numRows; i++ ) {
			
			for(int j = 0; j < numColumns; j++) {
				
				LinkedList<BoardCell> adj = new LinkedList<BoardCell>();
				
				BoardCell temp = getCellAt(i,j);
				
				int a = i-1;
				int b = i+1;
				int c = j+1;
				int d = j-1;
				
				// Is the current cell a Doorway?
				if(temp.isDoorway()) {
					RoomCell door = getRoomCellAt(i,j);
					if(door.getDoorDirection() == DoorDirection.UP) {
						adj.add(getCellAt(a,j));
					}
					else if(door.getDoorDirection() == DoorDirection.DOWN) {
						adj.add(getCellAt(b,j));
					}
					else if(door.getDoorDirection() == DoorDirection.RIGHT) {
						adj.add(getCellAt(i,c));
					}
					else if(door.getDoorDirection() == DoorDirection.LEFT) {
						adj.add(getCellAt(i,d));
					}
				}

				else  { // Is the current cell a walkway or room?
					// cell left of temp
					if((d) >= 0) {
						BoardCell tempU = getCellAt(i,d);
						if(tempU.isWalkway()) {
							if(!temp.isRoom()) { 
								adj.add(getCellAt(i,d));
							}
						}
						if(tempU.isRoom()) {
							RoomCell tempU2 = getRoomCellAt(i,d);
							if(tempU2.getDoorDirection() == DoorDirection.RIGHT) {
								adj.add(getCellAt(i,d));
							}
						}	
					
					}
					// cell right of temp
					if((c) < numColumns) {
						BoardCell tempD = getCellAt(i,c);
						if(tempD.isWalkway()) {
							if(!temp.isRoom()) {
								adj.add(getCellAt(i,c));
							}
						}
						if(tempD.isRoom()) {
							RoomCell tempD2 = getRoomCellAt(i,c);
							if(tempD2.getDoorDirection() == DoorDirection.LEFT) {
								adj.add(getCellAt(i,c));							
							}
						}	
					
					}
					// cell above temp
					if((a) >= 0) {
						BoardCell tempD = getCellAt(a,j);
						if(tempD.isWalkway()) {
							if(!temp.isRoom()) {
								adj.add(getCellAt(a,j));
							}
						}
						if(tempD.isRoom()) {
							RoomCell tempD2 = getRoomCellAt(a,j);
							if(tempD2.getDoorDirection() == DoorDirection.DOWN) {
								adj.add(getCellAt(a,j));
							}
						}	
					
					}
					// cell below temp
					if((b) < numRows) {
						BoardCell tempD = getCellAt(b,j);
						if(tempD.isWalkway()) {
							if(!temp.isRoom()) {
								adj.add(getCellAt(b,j));
							}
						}
						if(tempD.isRoom()) {
							RoomCell tempD2 = getRoomCellAt(b,j);
							if(tempD2.getDoorDirection() == DoorDirection.UP) {
								adj.add(getCellAt(b,j));
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
	public BoardCell[][] getLayout1() {
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
