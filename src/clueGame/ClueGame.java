package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import clueGame.Board;


public class ClueGame {

	Map<Character,String> rooms;
	private String legendFile;
	private String layoutFile;
	private Board b;
	//New stuff from CluePlayer
	public final int MAX_PLAYERS=5;
	private ArrayList<Card> cards;
	private ArrayList<Player> players;
	private Solution solution;
	
	
	private ClueGame game;
	
	// Constructors
	public ClueGame(String board, String legend) {//throws BadConfigFormatException {
		b = new Board(this);
		rooms = new HashMap<Character,String>();
		legendFile = legend;
		setLayoutFile(board);
		try {
			loadConfigFiles();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
	}
	public ClueGame() {}

	// load the config files
	public void loadConfigFiles() throws BadConfigFormatException {
		loadRoomConfig();	
		b.loadBoardConfig(rooms);	
	}

	

	public void loadRoomConfig() throws BadConfigFormatException {
		// TODO load the room configuration files

		Scanner scan; 
		String line = "";
		String name = "";
		char letter;

		// try to read the legendFile
		try {
			scan = new Scanner(new File(legendFile));
			while(scan.hasNext()) {
				line = scan.nextLine(); 	// entire line
				// test for a bad line
				if(line.charAt(0) == ',') throw new BadConfigFormatException(legendFile);
				letter = line.charAt(0); 	// room initial
				name = line.substring(2); 	// room name
				name = name.replaceFirst(" ", "");
				// check for a bad name
				if(name.contains(","))	throw new BadConfigFormatException(legendFile);
				rooms.put(letter, name);
				
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: the file: '" + legendFile + "' was not found");

		}
		
	}
	
	// getter for Board
	public Board getBoard() {
		return b;
	}
	
	// getter for rooms
	public Map<Character,String> getRooms() {
		return rooms;
	}
	
	// getter for layout file
	public String getLayoutFile() {
		return layoutFile;
	}
	
	// setter for layout file
	public void setLayoutFile(String layoutFile) {
		this.layoutFile = layoutFile;
	}
	
	//New methods from cluePlayer
	public void deal(){
		
	}
	public void selectAnswer(){
		
	}
	public void handleSuggestions(String person, String room, String weapon, Player accusingPerson){
		
	}
	public boolean checkAccusation(Solution solution){
		return false;
	}
	
}

