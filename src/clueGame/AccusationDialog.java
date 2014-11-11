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
	private JButton cancelButton;
	final JFrame frame;
	JComboBox<String> roomBox;
	JComboBox<String> personBox;
	JComboBox<String> weaponBox;

	public JButton getCancelButton()
	{
		return cancelButton;
	}
	public AccusationDialog(ArrayList<Card> cards)
	{
		frame = new JFrame("Make a Guess");
		JPanel labelPanel = new JPanel();
		JPanel comboBoxPanel = new JPanel();

		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Cancel");

		JLabel roomLabel = new JLabel("Room");
		JLabel weaponLabel = new JLabel("Weapon");
		JLabel personLabel = new JLabel("Person");

		personBox = new JComboBox<String>();
		weaponBox = new JComboBox<String>();
		roomBox = new JComboBox<String>();
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

		frame.setLayout(new GridLayout(1,2));
		frame.add(labelPanel);
		frame.add(comboBoxPanel);
		frame.setVisible(true);


	}
	public String getPerson()
	{
		return personBox.getSelectedItem().toString();
	}
	public String getRoom()
	{
		return roomBox.getSelectedItem().toString();
	}
	public String getWeapon()
	{
		return weaponBox.getSelectedItem().toString();
	}
	public void close()
	{
		frame.dispose();

	}
	public JButton getSubmitButton()
	{
		return submitButton;
	}
}
