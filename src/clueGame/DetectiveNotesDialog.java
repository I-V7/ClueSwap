package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotesDialog extends JDialog{
	
	private JComboBox<String> personComboBox;
	private JComboBox<String> roomComboBox;
	private JComboBox<String> weaponComboBox;
	
	private ArrayList<JCheckBox> peopleBoxes;
	private ArrayList<JCheckBox> weaponBoxes;
	private ArrayList<JCheckBox> roomBoxes;
	
	private JPanel panel;
	private JPanel peoplePanel;
	private JPanel roomPanel;
	private JPanel weaponGuessPanel;
	private JPanel weaponPanel;
	private JPanel roomGuessPanel;
	
	private JPanel personGuessPanel;
	
	public DetectiveNotesDialog(){
		setTitle("Detective Notes");
		setSize(500,600);
		
		//initialize combo boxex
		personComboBox = new JComboBox<String>();	
		roomComboBox = new JComboBox<String>();
		weaponComboBox = new JComboBox<String>();
		
		//initalize array lists of all checkbox types
		peopleBoxes = new ArrayList<JCheckBox>();
		weaponBoxes = new ArrayList<JCheckBox>();
		roomBoxes = new ArrayList<JCheckBox>();
		
		panel= new JPanel();
		panel.setLayout(new GridLayout(3,2));
		
		peoplePanel= new JPanel();
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		peoplePanel.setLayout(new GridLayout(3,2));
		
		personGuessPanel= new JPanel();
		personGuessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		personGuessPanel.setLayout(new GridLayout(1,0));
		

		roomPanel= new JPanel();
		roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		roomPanel.setLayout(new GridLayout(3,2));
		
		roomGuessPanel= new JPanel();
		roomGuessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		roomGuessPanel.setLayout(new GridLayout(1,0));
		
		weaponPanel= new JPanel();
		weaponPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		weaponPanel.setLayout(new GridLayout(3,2));
		
		weaponGuessPanel= new JPanel();
		weaponGuessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		weaponGuessPanel.setLayout(new GridLayout(1,0));

		JPanel detectivePanel =detectivePanel();
		add(detectivePanel, BorderLayout.CENTER);
		
		
		
		
	}
	public JPanel detectivePanel(){
		
		//add all people cards to list
		peopleBoxes.add(new JCheckBox("Miss Scarlett"));
		peopleBoxes.add(new JCheckBox("Professor Plum"));
		peopleBoxes.add(new JCheckBox("Mrs Peacock"));
		peopleBoxes.add(new JCheckBox("Reverend Green"));
		peopleBoxes.add(new JCheckBox("Colonel Mustard"));
		peopleBoxes.add(new JCheckBox("Mrs White"));
		
		//add items to person combo box
		personComboBox.addItem("Miss Scarlett");
		personComboBox.addItem("Professor Plum");
		personComboBox.addItem("Mrs Peacock");
		personComboBox.addItem("Reverend Green");
		personComboBox.addItem("Colonel Mustard");
		personComboBox.addItem("Mrs White");
		
		//add listener to all checkboxes and add checkbox to panels
		for(JCheckBox box: peopleBoxes)
		{
			
			box.addItemListener(new PersonCheckBoxItemListener());
			peoplePanel.add(box);
		}
		personGuessPanel.add(personComboBox);
		
		
		
		roomBoxes.add(new JCheckBox("Kitchen"));
		roomBoxes.add(new JCheckBox("Ballroom"));
		roomBoxes.add(new JCheckBox("Conservatory"));
		roomBoxes.add(new JCheckBox("Billard Room"));
		roomBoxes.add(new JCheckBox("Library"));
		roomBoxes.add(new JCheckBox("Study"));
		roomBoxes.add(new JCheckBox("Hall"));
		roomBoxes.add(new JCheckBox("Lounge"));
		roomBoxes.add(new JCheckBox("Dinning Room"));
		
		roomComboBox.addItem("Kitchen");
		roomComboBox.addItem("Ballroom");
		roomComboBox.addItem("Conservatory");
		roomComboBox.addItem("Billard Room");
		roomComboBox.addItem("Library");
		roomComboBox.addItem("Study");
		roomComboBox.addItem("Hall");
		roomComboBox.addItem("Lounge");
		roomComboBox.addItem("Dinning Room");
		
		
		for(JCheckBox box: roomBoxes)
		{
			box.addItemListener(new RoomCheckBoxItemListener());
			roomPanel.add(box);
		}
		
		
		roomGuessPanel.add(roomComboBox);
	
		
		
		weaponBoxes.add(new JCheckBox("Candle Stick"));
		weaponBoxes.add(new JCheckBox("Knife"));
		weaponBoxes.add(new JCheckBox("Lead Pipe"));
		weaponBoxes.add(new JCheckBox("Revolver"));
		weaponBoxes.add(new JCheckBox("Rope"));
		weaponBoxes.add(new JCheckBox("Wrench"));
		
		weaponComboBox.addItem("Candle Stick");
		weaponComboBox.addItem("Knife");
		weaponComboBox.addItem("Lead Pipe");
		weaponComboBox.addItem("Revolver");
		weaponComboBox.addItem("Rope");
		weaponComboBox.addItem("Wrench");
		
		for(JCheckBox box: weaponBoxes)
		{
			box.addItemListener(new WeaponCheckBoxItemListener());
			weaponPanel.add(box);
		}
		
		
		
		weaponGuessPanel.add(weaponComboBox);
		
		
		panel.add(peoplePanel);
		panel.add(personGuessPanel);
		panel.add(roomPanel);
		panel.add(roomGuessPanel);
		panel.add(weaponPanel);
		panel.add(weaponGuessPanel);
		
		
		return panel;
	}
	
	private class PersonCheckBoxItemListener implements ItemListener
	{

		@Override
		public void itemStateChanged(ItemEvent event) {
			
			JCheckBox box = (JCheckBox)event.getItem();
			String boxName = box.getText();
			
			//change combo box based onif box is selected or unselected
			if(box.isSelected())
			{
				
				for(int i=0; i<personComboBox.getItemCount();i++)
				{
					if(boxName.equals(personComboBox.getItemAt(i)))
					{
						
						personComboBox.removeItemAt(i);
						break;
						
					}
					
				}
				
			}
			else if(!box.isSelected())
			{
				personComboBox.addItem(boxName);
			}
			
			
		}
		
	}
	
	private class RoomCheckBoxItemListener implements ItemListener
	{

		@Override
		public void itemStateChanged(ItemEvent event) {
			
			JCheckBox box = (JCheckBox)event.getItem();
			String boxName = box.getText();
			if(box.isSelected())
			{
				
				for(int i=0; i<roomComboBox.getItemCount();i++)
				{
					if(boxName.equals(roomComboBox.getItemAt(i)))
					{
						
						roomComboBox.removeItemAt(i);
						break;
						
					}
					
				}
				
			}
			else if(!box.isSelected())
			{
				roomComboBox.addItem(boxName);
			}
			
			
		}
		
	}
	
	private class WeaponCheckBoxItemListener implements ItemListener
	{

		@Override
		public void itemStateChanged(ItemEvent event) {
			
			JCheckBox box = (JCheckBox)event.getItem();
			String boxName = box.getText();
			if(box.isSelected())
			{
				
				for(int i=0; i<weaponComboBox.getItemCount();i++)
				{
					if(boxName.equals(weaponComboBox.getItemAt(i)))
					{
						
						weaponComboBox.removeItemAt(i);
						break;
						
					}
					
				}
				
			}
			else if(!box.isSelected())
			{
				weaponComboBox.addItem(boxName);
			}
			
			
		}
		
	}
	
	public void updateCheckBoxes(Player player)
	{
		//update the check boxes based on what cards the player has and has seen
		ArrayList<Card> cards = player.getCards();
		ArrayList<Card> seenCards = player.getShownCards();
		
		for(JCheckBox box: peopleBoxes)
		{
			
			for(Card card: seenCards)
			{
				
				if(card.getName().equals(box.getText()))
				{
					
					box.setSelected(true);
				}
			}
			for(Card card: cards)
			{
				if(card.getName().equals(box.getText()))
				{
					
					box.setSelected(true);
				}
			}
		}
		
		for(JCheckBox box: roomBoxes)
		{
			
			for(Card card: seenCards)
			{
			
				if(card.getName().equals(box.getText()))
				{
					box.setSelected(true);
				}
			}
			for(Card card: cards)
			{
				if(card.getName().equals(box.getText()))
				{
					box.setSelected(true);
				}
			}
		}
		for(JCheckBox box: weaponBoxes)
		{
			
			for(Card card: seenCards)
			{
				
				if(card.getName().equals(box.getText()))
				{
					
					box.setSelected(true);
				}
			}
			for(Card card: cards)
			{
				if(card.getName().equals(box.getText()))
				{
					
					box.setSelected(true);
				}
			}
		}
		
		//update combo boxes based on which check boxes are selected
		for(JCheckBox box: peopleBoxes)
		{
			if(box.isSelected())
			{
				for(int i=0; i<personComboBox.getItemCount();i++)
				{
					if(box.getText().equals(personComboBox.getItemAt(i)))
					{
						personComboBox.remove(i);
						break;
					}
				}
			}
		}
		
		for(JCheckBox box: weaponBoxes)
		{
			if(box.isSelected())
			{
				for(int i=0; i<weaponComboBox.getItemCount();i++)
				{
					if(box.getText().equals(weaponComboBox.getItemAt(i)))
					{
						weaponComboBox.remove(i);
						break;
					}
				}
			}
		}
		
		for(JCheckBox box: roomBoxes)
		{
			if(box.isSelected())
			{
				for(int i=0; i<roomComboBox.getItemCount();i++)
				{
					if(box.getText().equals(roomComboBox.getItemAt(i)))
					{
						roomComboBox.remove(i);
						break;
					}
				}
			}
		}
		
	}

}
