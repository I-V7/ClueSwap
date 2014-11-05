package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
public class GameControlPanel extends JPanel{
	JButton nextPlayerButton;
	JTextField nameLabel;
	JTextField rollNumber;

	public GameControlPanel() {
		setLayout(new GridLayout(2,1));
		JPanel firstPane = buttonPanel();
		JPanel secondPane = infoPanel();
		setSize(100,100);
		add(firstPane);
		add(secondPane);
	}
	// Create a panel to display the buttons in
	private JPanel buttonPanel() {
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(1,3));;
		JButton next = new JButton("Next player");
		nextPlayerButton = next;
		JButton accuse = new JButton("Make an accusation");
		JPanel temporary = new JPanel();
		temporary.setLayout(new GridLayout(2,2));
		
		nextPlayerButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
			
		});
		
		JLabel nameLabeler = new JLabel("Which player's turn?");
		nameLabel = new JTextField(20);
		nameLabel.setEditable(false);
		temporary.add(nameLabeler);
		temporary.add(nameLabel);
		temporary.add(new JLabel(""));
		temporary.setBorder(new EtchedBorder());
		
		temp.add(temporary);
		temp.add(nextPlayerButton);
		temp.add(accuse);
		return temp;
	}
	// Updates nameLabel
	public void updateNameLabel(String s)
	{
		nameLabel.setText(s);
	}
	// Returns the next player button
	public JButton getNextPlayerButton()
	{
		return nextPlayerButton;
	}
	// Create a panel to display roll, turn, guesses
	private JPanel infoPanel() 
	{
		
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,3));
		
		JPanel rollPanel = new JPanel();
		rollPanel.setLayout(new GridLayout(1,2));
		rollPanel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		/*JButton rollButton = new JButton("Roll the die!");
		temp.add(rollButton);*/
		
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(2,1));
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		//temporary = new JPanel();
		//temporary.setLayout(new GridLayout(3,2));
		JPanel resultPanel= new JPanel();
		resultPanel.setLayout(new GridLayout(1,2));
		resultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		
		
		JLabel rollResult = new JLabel("Roll");
		rollNumber = new JTextField(1);
		rollNumber.setEditable(false);
		JLabel guesser = new JLabel("Last Guess");
		JTextField guess = new JTextField(20);
		guess.setEditable(false);
		JLabel guesserResult = new JLabel("Guess Result");
		JTextField guessResult = new JTextField(20);
		guessResult.setEditable(false);
		rollPanel.add(rollResult);
		rollPanel.add(rollNumber);
		guessPanel.add(guesser);
		guessPanel.add(guess);
		resultPanel.add(guesserResult);
		resultPanel.add(guessResult);
		bottomPanel.add(rollPanel);
		bottomPanel.add(guessPanel);
		bottomPanel.add(resultPanel);
		
		return bottomPanel;
	}
	public void updateRollNumber(int i)
	{
		rollNumber.setText(Integer.toString(i));
	}
}
