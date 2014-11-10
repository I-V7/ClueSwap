package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class SuggestionDialog extends JFrame {

	private ArrayList<Card> suggestionCards;
	private final JFrame frame;
	private JPanel comboBoxPanel;
	private JPanel labelPanel;
	private JButton submitButton;
	private JButton cancelButton;
	private JLabel roomLabel;
	private JLabel weaponLabel;
	private JLabel personLabel;
	private JComboBox<String> personBox;
	private JComboBox<String> weaponBox;
	private String room;
	private JLabel currentRoom;
	private boolean alreadyAdded;

	public SuggestionDialog(ArrayList<Card> cards)
	{
		suggestionCards = new ArrayList<Card>();
		alreadyAdded =false;
		for(Card card: cards)
		{
			suggestionCards.add(card);
		}
		frame = new JFrame("Make a Guess");
		labelPanel = new JPanel();
		comboBoxPanel = new JPanel();
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		submitButton = new JButton("Submit");
		roomLabel = new JLabel("Room");
		weaponLabel = new JLabel("Weapon");
		personLabel = new JLabel("Person");
		personBox = new JComboBox<String>();
		weaponBox = new JComboBox<String>();
		for(Card card: suggestionCards)
		{
			if(card.getCardType() == CardType.PERSON)
			{
				personBox.addItem(card.getName());
			}
			else if(card.getCardType() == CardType.WEAPON)
			{
				weaponBox.addItem(card.getName());
			}

		}
		display("broke");

	}
	public void redisplay(String room)
	{
		currentRoom.setText(room);
		frame.setVisible(true);
	}
	public void display(String room)
	{
		this.room = room;

		labelPanel.setLayout(new GridLayout(3,2));
		labelPanel.setBorder(new EtchedBorder());
		if(!alreadyAdded)
		{
			currentRoom = new JLabel(this.room);
			labelPanel.add(currentRoom);
			alreadyAdded=true;
		}
		labelPanel.add(roomLabel);
		labelPanel.add(personLabel);
		labelPanel.add(personBox);
		labelPanel.add(weaponLabel);
		labelPanel.add(weaponBox);

		frame.setLayout(new GridLayout(2,1));
		frame.add(labelPanel);
		frame.add(submitButton);

	}
	public JComboBox<String> getPersonBox()
	{
		return personBox;
	}
	public JComboBox<String> getWeaponBox()
	{
		return weaponBox;
	}
	public String getRoom()
	{
		return room;
	}
	public JButton getSubmitButton()
	{
		return submitButton;
	}
	public void close() {
		frame.dispose();

	}
}