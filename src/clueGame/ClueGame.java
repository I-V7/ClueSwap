package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import clueGame.Board;


public class ClueGame extends JFrame {
	//functionality
	Map<Character,String> rooms;
	private String legendFile;
	private String layoutFile;
	private Board board;
	//New stuff from CluePlayer
	public final int MAX_PLAYERS=6;
	private ArrayList<Card> cards;
	private ArrayList<Player> players;
	private ArrayList<Card> shownCards;
	//static Map<String,Card> cardStringToCard;
	private Solution solution;
	private boolean winner;
	private boolean playersTurnIsOver;
	private int currentTurn;
	GameControlPanel panel;
	JTextField messageBoard;
	int currentRoll;
	private boolean neverDone;
	private ArrayList<Card> copyCards;

	//GUI Stuff
	private DetectiveNotesDialog detectiveNotes;
	private SuggestionDialog suggestion;
	// Constructors
	public ClueGame(String board, String legend) {//throws BadConfigFormatException {
		//Logic
		this.board = new Board(this);
		rooms = new HashMap<Character,String>();
		shownCards = new ArrayList<Card>();
		//cardStringToCard = new HashMap<String, Card>();
		currentTurn = 0;
		playersTurnIsOver = true;
		legendFile = legend;
		neverDone = true;
		setLayoutFile(board);
		try {
			loadConfigFiles();
		} catch (BadConfigFormatException e) {
			e.getMessage();
		}

		solution= new Solution();

		//GUI stuff
		gui();


	}
	public ClueGame() {
		try {
			loadConfigFiles();
		} catch (BadConfigFormatException e) {
			e.getMessage();
		}
	}
	//GUI FUNCTIONS
	private void gui(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("ClueGame");
		setSize(board.BOARD_WIDTH-700,board.BOARD_HEIGHT);
		add(board, BorderLayout.CENTER);
		panel = new GameControlPanel(cards);
		add(panel, BorderLayout.SOUTH);
		panel.getNextPlayerButton().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				if(playersTurnIsOver)
				{
					nextPlayersTurn();
				}
			}
		});

		addMouseListener( new MouseListener(){

			@Override
			public void mousePressed(MouseEvent e) 
			{
				messageBoard.setText(null);
				BoardCell whichCell = null;
				Boolean triggerWarning = true;
				BoardCell selectedCell = null;
				for (int i = 0; i < board.getNumRows();i++)
				{
					for (int ii = 0; ii < board.getNumColumns(); ii++)
					{
						BoardCell temp = board.getCellAt(i, ii);

						if (temp.containsClick(e.getX(),e.getY()))
						{


							if(!playersTurnIsOver)
							{
								players.get(1).setCol(temp.getCol());
								players.get(1).setRow(temp.getRow());
								playersTurnIsOver = true;
								for (int j = 0; j < board.getNumRows();j++)
								{
									for (int jj = 0; jj < board.getNumColumns(); jj++)
									{
										board.getCellAt(j, jj).setAsNotTarget();
									}
								}
								repaint();
								if(temp.isRoom())
								{
									makeSuggestion();
								}
							}
							triggerWarning = false;
						}
					}
				}
				if (triggerWarning)
				{
					messageBoard.setText("Invalid Move");
				}



			}

			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}

		}
				);

		//File Menu
		JMenuBar fileMenu= new JMenuBar();
		setJMenuBar(fileMenu);
		fileMenu.add(createFileMenu());

	}
	private void makeSuggestion()
	{
		char initial = board.getRoomCellAt(players.get(currentTurn).getRow(), players.get(currentTurn).getCol()).getInitial();
		if(players.get(currentTurn).isHuman())
		{
			String roomName ="";
			switch(initial)
			{
			case 'L':
				roomName = "Library";
				break;
			case 'R':
				roomName = "Billiard Room";
				break;
			case 'S':
				roomName = "Study";
				break;
			case 'K':
				roomName = "Kitchen";
				break;
			case 'D':
				roomName = "Dining Room";
				break;
			case 'O':
				roomName = "Lounge";
				break;
			case 'B':
				roomName = "Ballroom";
				break;
			case 'C':
				roomName = "Conservatory";
				break;
			case 'H':
				roomName = "Hall";
				break;
			}
			suggestion.display(roomName);
			suggestion.redisplay(roomName);
			JButton makeSugg = suggestion.getSubmitButton();
			makeSugg.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					String person = (String)suggestion.getPersonBox().getSelectedItem();
					String weapon = (String)suggestion.getWeaponBox().getSelectedItem();
					String room = suggestion.getRoom();
					Boolean disprover = false;
					for(Player player: players)
					{
						if(!player.isHuman())
						{
							Card disproveCard = player.disproveSuggestion(person, room, weapon);
							if(disproveCard != null && disprover == false)
							{
								panel.setGuessResult(disproveCard.getName());
								disprover = true;

							}
							else if (disprover == false)
							{
								panel.setGuessResult("Not disproved");
							}
						}
					}
					suggestion.close();
				}

			});
		}
		else
		{
			Boolean disprover = false;
			ComputerPlayer playa = (ComputerPlayer)players.get(currentTurn);
			HashMap<String,Card> allCards = new HashMap<String,Card>();
			System.out.println(copyCards.size());
			for(Card a: copyCards)
			{
				allCards.put(a.getName(),a);
			}
			String[] carderon = playa.createSuggestion(allCards,board);
			for(Player player: players)
			{
				if(!player.isHuman())
				{
					Card disproveCard = player.disproveSuggestion(carderon[0], carderon[2], carderon[1]);
					if(disproveCard != null && disprover == false)
					{
						panel.setGuessResult(disproveCard.getName());
						disprover = true;

					}
					else if (disprover == false)
					{
						panel.setGuessResult("Not disproved");
					}
				}
			}
		}

	}
	//Advances a player's turn
	private void nextPlayersTurn()
	{
		currentTurn = (currentTurn + 1 )% players.size();
		if (currentTurn == 1)
		{
			playersTurnIsOver = false;
		}
		panel.updateNameLabel(players.get(currentTurn).getName());
		Random rand = new Random();
		currentRoll = rand.nextInt(players.size()) + 1;
		panel.updateRollNumber(currentRoll);
		if (currentTurn != 1)
		{
			makeMove();
			for (int i = 0; i < board.getNumRows();i++)
			{
				for (int ii = 0; ii < board.getNumColumns(); ii++)
				{
					board.getCellAt(i, ii).setAsNotTarget();
				}
			}
		}
		else
		{
			board.calcTargets(players.get(currentTurn).getRow(), players.get(currentTurn).getCol(), currentRoll);
			for (int i = 0; i < board.getNumRows()-1;i++)
			{
				for (int ii = 0; ii < board.getNumColumns()-1; ii++)
				{
					board.calcTargets(players.get(currentTurn).getRow(), players.get(currentTurn).getCol(), currentRoll);
					BoardCell temp = board.getCellAt(i, ii);
					if(board.getTargets().contains(temp))
					{
						temp.setAsTarget();
					}
					else
					{
						temp.setAsNotTarget();
					}
				}
			}
		}
		repaint();
	}
	// Computer player makes a move
	private void makeMove()
	{
		board.calcTargets(players.get(currentTurn).getRow(), players.get(currentTurn).getCol(), currentRoll);
		BoardCell temp = players.get(currentTurn).pickLocation(board.getTargets());
		players.get(currentTurn).setCol(temp.getCol());
		players.get(currentTurn).setRow(temp.getRow());
		if(temp.isRoom())
		{
			RoomCell temp1 = (RoomCell) temp;
			players.get(currentTurn).setLastRoomVisited( temp1.getInitial() );
			makeSuggestion();
		}
	}
	private JMenu createFileMenu(){
		JMenu menu = new JMenu("File");
		menu.add(createShowNotesItem());
		menu.add(createFileExitItem());
		return menu;
	}
	private JMenuItem createShowNotesItem(){
		JMenuItem item = new JMenuItem("Show Notes");
		detectiveNotes=new DetectiveNotesDialog(cards);
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e){		
				detectiveNotes.updateCheckBoxes(players.get(1));
				detectiveNotes.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}

	private JMenuItem createFileExitItem(){
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}

	//LOGIC FUNCTIONS
	// load the config files
	public void loadConfigFiles() throws BadConfigFormatException {
		loadRoomConfig();
		board.loadBoardConfig(rooms);	
		loadCards();
		loadPlayers();
		board.giveLabels(rooms);
	}

	public void loadRoomConfig() throws BadConfigFormatException {

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
					players.add(new ComputerPlayer(name, color, row, col, cards));

				}else{
					players.add(new HumanPlayer(name, color, row, col, cards));

				}
				i++;
			}
		}catch(FileNotFoundException e){
			System.out.println(e.getLocalizedMessage());
		}
	}
	public void  loadCards(){

		cards=new ArrayList<Card>();
		Card currentCard;
		try{
			FileReader reader= new FileReader("cards.txt");
			Scanner cardsFile= new Scanner(reader);
			//int i=0;
			while(cardsFile.hasNextLine()){
				String[] line=cardsFile.nextLine().split(",");
				String name = line[0];
				String cardType=  line[1].substring(1);
				currentCard = new Card(name,cardType);
				cards.add(currentCard);
				//cardStringToCard.put(name, currentCard);
			}
			cardsFile.close();
		}catch(FileNotFoundException e){
			System.out.println(e.getLocalizedMessage());
		}
		copyCards = new ArrayList<Card>(cards);
		suggestion = new SuggestionDialog(cards);

	}

	// getter for Board
	public Board getBoard() {
		return board;
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

	//New methods from cluePlayer, new load configs are placed with load functions
	public void deal(){	
		int playerNumber =0;
		selectAnswer();
		int i =cards.size()-1;
		while(!cards.isEmpty())
		{
			players.get(playerNumber%MAX_PLAYERS).addCard(cards.get(i));
			cards.remove(i);
			playerNumber++;

			i--;
		}
		detectiveNotes.updateCheckBoxes(players.get(1));
		board.calcAdjacencies();
		MyCardPanel paneler = new MyCardPanel(players.get(1).getCards());
		add(paneler, BorderLayout.LINE_END);
		messageBoard = paneler.getMessageBoard();
	}

	public void selectAnswer(){

		ArrayList<Card> personCards = new ArrayList<Card>();
		ArrayList<Card> weaponCards = new ArrayList<Card>();
		ArrayList<Card> roomCards = new ArrayList<Card>();

		for(Card card: cards)
		{
			if(card.getCardType() == CardType.PERSON)
			{
				personCards.add(card);
			}
			else if(card.getCardType() == CardType.WEAPON)
			{
				weaponCards.add(card);
			}
			else
			{
				roomCards.add(card);
			}
		}
		int randomCardNum = (int)(Math.random()*personCards.size());
		solution.person = personCards.get(randomCardNum).getName();
		cards.remove(personCards.get(randomCardNum));

		randomCardNum = (int)(Math.random()*weaponCards.size());
		solution.weapon = weaponCards.get(randomCardNum).getName();
		cards.remove(weaponCards.get(randomCardNum));

		randomCardNum = (int)(Math.random()*roomCards.size());
		solution.room = roomCards.get(randomCardNum).getName();
		cards.remove(roomCards.get(randomCardNum));

	}
	public Card handleSuggestions(String person, String room, String weapon, Player accusingPerson){

		Card suggestedCardResult=null;

		int indexOfPlayer=players.indexOf(accusingPerson);
		int curPlayer=(indexOfPlayer+1)%(players.size());
		while(curPlayer!=indexOfPlayer){
			suggestedCardResult=players.get(curPlayer).disproveSuggestion(person, room, weapon);
			if(suggestedCardResult!=null){
				//shownCards.add(suggestedCardResult);
				break;
			}
			curPlayer=(curPlayer+1)%(players.size());
		}
		return suggestedCardResult;

	}
	public boolean checkAccusation(Card person, Card room, Card weapon){

		if(solution.person.equals(person.getName()) && solution.weapon.equals(weapon.getName()) && solution.room.equals(room.getName()))
		{
			return true;
		}
		else
			return false;
	}

	//GETTERS AND SETTER FOR CLUE PLAYERS
	public ArrayList<Player> getPlayers(){
		return this.players;
	}
	public ArrayList<Card> getCards(){
		return this.cards;
	}
	public Solution getSolution()
	{
		return solution;
	}
	public boolean isWinner()
	{
		return winner;
	}

	//set/get already shown cards for test purposes only
	public void setShownCards(ArrayList<Card> testShownCards)
	{
		shownCards = testShownCards;
		//only need to set for one since shown cards is static
		players.get(0).setShownCards(testShownCards);
	}
	public void clearShown(){
		players.get(0).clearShownCards();
	}
	public ArrayList<Card> getShownCards()
	{
		return shownCards;
	}
	public HashMap<String, Card> getStringToCard()
	{
		return null;//(HashMap<String, Card>) cardStringToCard;
	}
	public static void main(String[] args){
		ClueGame game=new ClueGame("Clue Board.csv", "Clue Legend.csv");
		game.deal();
		ArrayList<Card> cards = game.getPlayers().get(1).getCards();
		game.setVisible(true);	
		NewGameDialog newGameDialog = new NewGameDialog(game.getPlayers().get(1).getName());

	}	


}

