package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotesDialog extends JDialog{
	
	public DetectiveNotesDialog(){
		setTitle("Detective Notes");
		setSize(500,600);
		JPanel panel=detectivePanel();
		add(panel, BorderLayout.CENTER);
		
	}
	public JPanel detectivePanel(){
		JPanel panel= new JPanel();
		//setLayout(new GridLayout(3,2));
		
		JPanel peoplePanel= new JPanel();
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		
		JPanel personGuessPanel= new JPanel();
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "PersonGuess"));
		
		panel.add(peoplePanel);
		panel.add(personGuessPanel);
		
		
		return panel;
	}
	

}
