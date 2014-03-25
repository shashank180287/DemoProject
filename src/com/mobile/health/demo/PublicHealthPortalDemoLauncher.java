/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.mobile.health.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.mobile.health.demo.entity.PersonDetails;
import com.mobile.health.demo.entity.PersonDetailsTableModel;
import com.mobile.health.demo.listener.AddingNewPersonListener;
import com.mobile.health.demo.listener.EditingExistingPersonListener;
import com.mobile.health.demo.manager.DBManger;
import com.mobile.health.demo.manager.PortalMenuManager;

/**
 * This is exactly like PublicHealthPortalDemoLauncher, except that it uses a custom cell editor to
 * validate integer input.
 */
public class PublicHealthPortalDemoLauncher extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6846882782810412630L;

	private JButton previousButton;
	private JButton nextButton;
	
	private JButton addNewButton;
	private JButton editExistingButton;
	private JButton removeExistingButton;
	
	private static final int PAGE_SIZE=2;
	private static int PAGE_NUMBER=1;
	private List<PersonDetails> personDetailsList;
	
	public PublicHealthPortalDemoLauncher() {
		super(new GridLayout(1, 0));
		final JFrame frame = new JFrame("Public Health Portal Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		personDetailsList = DBManger.getPersonDetails();
		
		final PersonDetailsTableModel personDetailsTableModel = paginatePersonDetailsTableModel();
		final PublicHealthPortalTable table = new PublicHealthPortalTable(personDetailsTableModel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
		
		JPanel btnPnl = new JPanel(new BorderLayout());
        JPanel topBtnPnl = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JPanel bottombtnPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));

        topBtnPnl.add((personDetailsTableModel.getRowCount()==0?new JLabel("No Data Found..."):new JLabel()));
        previousButton = new JButton("Previous");
        if(PAGE_NUMBER==1){
        	previousButton.setEnabled(false);
        }else{
        	previousButton.setEnabled(true);
        }
        previousButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PAGE_NUMBER--;
				table.setModel(paginatePersonDetailsTableModel());
		        if(PAGE_NUMBER==1){
		        	previousButton.setEnabled(false);
		        }else{
		        	previousButton.setEnabled(true);
		        }
		        if(personDetailsList.size()<=PAGE_NUMBER*PAGE_SIZE){
		        	nextButton.setEnabled(false);
		        }else{
		        	nextButton.setEnabled(true);
		        }
				frame.repaint();
			}
		});
        topBtnPnl.add(previousButton);
        
        nextButton = new JButton("Next");
        if(personDetailsList.size()<=PAGE_NUMBER*PAGE_SIZE){
        	nextButton.setEnabled(false);
        }else{
        	nextButton.setEnabled(true);
        }
        nextButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PAGE_NUMBER++;
				table.setModel(paginatePersonDetailsTableModel());
		        if(PAGE_NUMBER==1){
		        	previousButton.setEnabled(false);
		        }else{
		        	previousButton.setEnabled(true);
		        }
		        if(personDetailsList.size()<=PAGE_NUMBER*PAGE_SIZE){
		        	nextButton.setEnabled(false);
		        }else{
		        	nextButton.setEnabled(true);
		        }
		        frame.repaint();
			}
		});
        topBtnPnl.add(nextButton);

        addNewButton = (new JButton("Add New"));
        addNewButton.addActionListener(new AddingNewPersonListener(frame));
        bottombtnPnl.add(addNewButton);
        
        editExistingButton  = new JButton("Edit Selected");
        editExistingButton.addActionListener(new EditingExistingPersonListener(frame, table));
        bottombtnPnl.add(editExistingButton);
        
        removeExistingButton = new JButton("Delete Selected");
        removeExistingButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				DBManger.removePersonDetailAtIndex(personDetailsTableModel.getPersonDetails().get(index));
				frame.repaint();				
			}
		});
        bottombtnPnl.add(removeExistingButton);

        btnPnl.add(topBtnPnl, BorderLayout.NORTH);
        btnPnl.add(bottombtnPnl, BorderLayout.CENTER);

        table.getTableHeader().setReorderingAllowed(false);

        frame.add(table.getTableHeader(), BorderLayout.NORTH);
        frame.add(table, BorderLayout.CENTER);
        frame.add(btnPnl, BorderLayout.SOUTH);

        PortalMenuManager.addPortalMenuBarToFrame(frame);
        
        frame.pack();
        frame.setVisible(true);
	}
	
	private PersonDetailsTableModel paginatePersonDetailsTableModel() {
		List<PersonDetails> paginatedPersonDetails = new ArrayList<PersonDetails>();
		for (int i = (PAGE_NUMBER-1)*PAGE_SIZE; i < (personDetailsList.size()>PAGE_NUMBER*PAGE_SIZE?PAGE_NUMBER*PAGE_SIZE:personDetailsList.size()); i++) {
			paginatedPersonDetails.add(personDetailsList.get(i));
		}
		PersonDetailsTableModel tableModel  = new PersonDetailsTableModel();
		tableModel.setPersonDetails(paginatedPersonDetails);
		return tableModel;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createAndShowGUI();
				}
			});
		} catch (UnsupportedLookAndFeelException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
	}
	
	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		new PublicHealthPortalDemoLauncher();
	}
}
