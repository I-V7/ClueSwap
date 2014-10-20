package ClueGUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameControlGUI extends JFrame{

	private JButton nextPlayerButton;
	private JButton makeAccusationButton;
	public GameControlGUI()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game Controls");
		nextPlayerButton = new JButton("Next Player");
		makeAccusationButton = new JButton("Make an Accusation");
		
	}
	public static void main(String[] args)
	{
		GameControlGUI gui = new GameControlGUI();
		
		
		
		
		gui.setVisible(true);
	}
}
