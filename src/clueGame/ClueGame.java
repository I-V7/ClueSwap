package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	public final int MAX_PLAYERS=6;
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
		loadPlayers();
		loadCards();
	
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
	public void loadPlayers(){
		players=new ArrayList<Player>();
		int humanPlayer=1;
		try{
			FileReader reader= new FileReader("players.txt");
			Scanner playersFile= new Scanner(reader);
			int i=0;
			while(playersFile.hasNextLine()){
				String[] line=playersFile.nextLine().split(",");
				String name = line[0];
				String color=  line[1].substring(1);
				int row = Integer.parseInt(line[2].substring(1));
				int col = Integer.parseInt(line[3].substring(1));
				if(i!=humanPlayer){
					players.add(new ComputerPlayer(name, color, row, col));
				}else{
					players.add(new HumanPlayer(name, color, row, col));
				}
				i++;
			}
		
		}catch(FileNotFoundException e){
			System.out.println(e.getLocalizedMessage());
		}
	}
	public void  loadCards(){
		cards=new ArrayList<Card>();
		try{
			FileReader reader= new FileReader("cards.txt");
			Scanner cardsFile= new Scanner(reader);
			int i=0;
			while(cardsFile.hasNextLine()){
				String[] line=cardsFile.nextLine().split(",");
				String name = line[0];
				String cardType=  line[1].substring(1);
				cards.add(new Card(name,cardType));
			}
		}catch(FileNotFoundException e){
			System.out.println(e.getLocalizedMessage());
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
	
	//GETTERS AND SETTER FOR CLUE PLAYERS
	public ArrayList<Player> getPlayers(){
		return this.players;
	}
	public ArrayList<Card> getCards(){
		return this.cards;
	}
	//FOR TESTING
	public static void main(String[] args){
		ClueGame game=new ClueGame("Clue Board.csv", "Clue Legend.csv");
	}
	
}

