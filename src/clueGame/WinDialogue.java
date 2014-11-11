package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WinDialogue extends JFrame
{
	JFrame frame;
	public WinDialogue(String playa)
	{
		new JFrame("WINNER");
		setSize(300, 300);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		JLabel label = new JLabel(playa + " wins!");
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		panel.add(label);
		panel.add(exit);
		add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
