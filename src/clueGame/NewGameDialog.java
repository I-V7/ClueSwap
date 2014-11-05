package clueGame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class NewGameDialog extends JPanel {

	
	public NewGameDialog(String player)
	{
		JFrame frame = new JFrame("DialogDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JOptionPane.showMessageDialog(frame, "You are " + player + ", press Next Player to begin play");
		
	}
}
