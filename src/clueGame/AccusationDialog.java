package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class AccusationDialog extends JFrame {
    private JButton	submitButton;
	public AccusationDialog(ArrayList<Card> cards)
	{
		final JFrame  frame = new JFrame("Make a Guess");
		JPanel labelPanel = new JPanel();
		JPanel comboBoxPanel = new JPanel();
		
		frame.setSize(300, 300);
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");
		
		JLabel roomLabel = new JLabel("Room");
		JLabel weaponLabel = new JLabel("Weapon");
		JLabel personLabel = new JLabel("Person");
		
		JComboBox<String> personBox = new JComboBox<String>();
		JComboBox<String> weaponBox = new JComboBox<String>();
		JComboBox<String> roomBox = new JComboBox<String>();
		System.out.println(cards.size());
		for(Card card: cards)
		{
			if(card.getCardType() == CardType.PERSON)
			{
				personBox.addItem(card.getName());
			}
			else if(card.getCardType() == CardType.WEAPON)
			{
				weaponBox.addItem(card.getName());
			}
			else
			{
				roomBox.addItem(card.getName());
				System.out.println(card.getName());
			}
		}
		
		labelPanel.setLayout(new GridLayout(4,1));
		comboBoxPanel.setLayout(new GridLayout(4,1));
		labelPanel.setBorder(new EtchedBorder());
		comboBoxPanel.setBorder(new EtchedBorder());
		
		labelPanel.add(roomLabel);
		labelPanel.add(personLabel);
		labelPanel.add(weaponLabel);
		labelPanel.add(submitButton);
		
		comboBoxPanel.add(roomBox);
		comboBoxPanel.add(personBox);
		comboBoxPanel.add(weaponBox);
		comboBoxPanel.add(cancelButton);
		
		submitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				
			}
			
		});
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				
				
			}
			
		});
		
		frame.setLayout(new GridLayout(1,2));
		frame.add(labelPanel);
		frame.add(comboBoxPanel);
		frame.setVisible(true);
		
		
	}
public JButton getSubmitButton()
{
	return submitButton;
}
}
