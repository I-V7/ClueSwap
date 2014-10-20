package ClueGUI;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameControlGUI extends JFrame{

	private JButton nextPlayerButton;
	private JButton makeAccusationButton;
	private JTextField rollTextField;
	private JTextField guessTextField;
	private JTextField responseTextField;
	private JTextField whoseTurnTextField;
	private JPanel bottomPanel;
	private JPanel topPanel;
	
	
	public GameControlGUI()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game Controls");
		setSize(250,250);
		nextPlayerButton = new JButton("Next Player");
		makeAccusationButton = new JButton("Make an Accusation");
		bottomPanel = new JPanel();
		topPanel = new JPanel();
		rollTextField = new JTextField(2);
		guessTextField = new JTextField(20);
		responseTextField = new JTextField(10);
		whoseTurnTextField = new JTextField(10);
		bottomPanel.add(rollTextField);
		bottomPanel.add(guessTextField);
		bottomPanel.add(responseTextField);
		topPanel.add(whoseTurnTextField);
		topPanel.add(nextPlayerButton);
		topPanel.add(makeAccusationButton);
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel,BorderLayout.SOUTH);
		
		
		
		
	}
	public static void main(String[] args)
	{
		GameControlGUI gui = new GameControlGUI();
		
		
		
		
		gui.setVisible(true);
	}
}
