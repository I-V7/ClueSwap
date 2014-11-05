package clueGame;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class MyCardPanel extends JPanel {

	JTextField messageBoard;
	
	public JTextField getMessageBoard()
	{
		return messageBoard;
	}
	
	public MyCardPanel(ArrayList<Card> myCards)
	{
		
	    ArrayList<String> rooms = new ArrayList<String>();
	    ArrayList<String> people = new ArrayList<String>();
	    ArrayList<String> weapons = new ArrayList<String>();
	    
	    setLayout(new GridLayout(2,1));
	    
		for(Card card: myCards)
		{
			if(card.getCardType() == CardType.ROOM)
			{
				rooms.add(card.getName());
			}
			else if(card.getCardType() == CardType.PERSON)
			{
				people.add(card.getName());
			}
			else if(card.getCardType() == CardType.WEAPON)
			{
				weapons.add(card.getName());
			}
			
		}
		
		JPanel cardPanel = new JPanel();
		cardPanel.setLayout(new GridLayout(4,1));
		JLabel myCardsLabel = new JLabel("My Cards");
		cardPanel.add(myCardsLabel);
		
		JPanel peoplePanel = new JPanel();
		peoplePanel.setLayout(new GridLayout(people.size(), 1));
		peoplePanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		for(String person: people)
		{
			JLabel personLabel = new JLabel(person);
			peoplePanel.add(personLabel);
		}
		
		JPanel weaponPanel = new JPanel();
		weaponPanel.setLayout(new GridLayout(weapons.size(), 1));
		weaponPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		for(String weapon: weapons)
		{
			JLabel weaponLabel = new JLabel(weapon);
			weaponPanel.add(weaponLabel);
		}
		
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(rooms.size(), 1));
		roomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		for(String room: rooms)
		{
			JLabel roomLabel = new JLabel(room);
			roomPanel.add(roomLabel);
		}
		
		messageBoard = new JTextField(12);
		
		cardPanel.add(peoplePanel);
		cardPanel.add(weaponPanel);
		cardPanel.add(roomPanel);
		add(cardPanel);
		add(messageBoard);
	}
}
