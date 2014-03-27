package com.mobile.health.demo.listener;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.mobile.health.demo.PublicHealthPortalDemoLauncher;
import com.mobile.health.demo.entity.PersonDetails;
import com.mobile.health.demo.manager.DBManger;

public class AddingNewPersonListener extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6191510576296067659L;

	JLabel headline, firstNameLabel, lastNameLabel, genderLabel, ageLabel, addressLabel, panchayatLabel;
	JTextField firstNameText, lastNameText, genderText, ageText, addrText, panchayatText;
	JButton createButton, clearButton;
	WindowAdapter adapter;
	JFrame frame;
	PublicHealthPortalDemoLauncher launcher;
	
	public AddingNewPersonListener(JFrame frame, PublicHealthPortalDemoLauncher launcher) {
		this.frame = frame;
		this.launcher = launcher;
	}
	
	public void createAndShowGUI() {
		setVisible(true);
		setSize(700, 500);
		setLayout(null);
		setTitle("Public Health Portal Demo");

		headline = new JLabel("Register New Person:");
		headline.setForeground(Color.blue);
		headline.setFont(new Font("Serif", Font.BOLD, 20));

		firstNameLabel = new JLabel("First Name:");
		lastNameLabel = new JLabel("Last Name:");
		genderLabel = new JLabel("Gender:");
		ageLabel = new JLabel("Age:");
		addressLabel = new JLabel("Address:");
		panchayatLabel = new JLabel("Panchayat:");
		firstNameText = new JTextField();
		lastNameText = new JTextField();
		genderText = new JTextField();
		ageText = new JTextField();
		addrText = new JTextField();
		panchayatText = new JTextField();

		createButton = new JButton("Create");
		clearButton = new JButton("Clear");

		createButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PersonDetails personDetails = new PersonDetails(DBManger.getCurrentIdCursor()+1, firstNameText.getText(), lastNameText.getText(), genderText.getText(),
						Integer.parseInt(ageText.getText()), addrText.getText(), panchayatText.getText());
				DBManger.addPersonDetails(personDetails);
				frame.setEnabled(true);
				launcher.refreshTableInFrame(1);
				dispose();
			}
		});
		
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				firstNameText.setText("");
				lastNameText.setText("");
				genderText.setText("");
				ageText.setText("");
				addrText.setText("");
				panchayatText.setText("");
			}
		});

		headline.setBounds(100, 30, 400, 30);
		firstNameLabel.setBounds(80, 70, 200, 30);
		lastNameLabel.setBounds(80, 110, 200, 30);
		genderLabel.setBounds(80, 150, 200, 30);
		ageLabel.setBounds(80, 190, 200, 30);
		addressLabel.setBounds(80, 230, 200, 30);
		panchayatLabel.setBounds(80, 270, 200, 30);
		firstNameText.setBounds(300, 70, 200, 30);
		lastNameText.setBounds(300, 110, 200, 30);
		genderText.setBounds(300, 150, 200, 30);
		ageText.setBounds(300, 190, 200, 30);
		addrText.setBounds(300, 230, 200, 30);
		panchayatText.setBounds(300, 270, 200, 30);
		createButton.setBounds(50, 350, 100, 30);
		clearButton.setBounds(170, 350, 100, 30);

		add(headline);
		add(firstNameLabel);
		add(firstNameText);
		add(lastNameLabel);
		add(lastNameText);
		add(genderLabel);
		add(genderText);
		add(ageLabel);
		add(ageText);
		add(addressLabel);
		add(addrText);
		add(panchayatLabel);
		add(panchayatText);
		add(createButton);
		add(clearButton);
		
		adapter = new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				frame.setEnabled(true);
				super.windowClosing(e);
			}
			
		};
		addWindowListener(adapter);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.frame.setEnabled(false);
		createAndShowGUI();

	}
}
