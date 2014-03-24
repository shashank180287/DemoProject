package com.mobile.health.demo.listener;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ExcelSheetExportActionListener implements ActionListener {

    private JFrame frame;
    private JTextArea tracker;
    private JFileChooser fileDialog;

    public ExcelSheetExportActionListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //create an instance of JFileChooser class
        fileDialog = new JFileChooser();//"C:\\Documents and Settings\\Owner\\My Documents"

        //Using a JTextArea to diplay feedback
        tracker = new JTextArea("File Tracker:");
        tracker.setVisible(true);
        frame.add(tracker, BorderLayout.NORTH);

        saveDialog();
    }

    //Show a save file dialog box
    private void saveDialog() {
        int saveChoice = fileDialog.showSaveDialog(this.frame);

        //display choice using tracker 
        logChoice(saveChoice, "Open Dialog");

        if (saveChoice == JFileChooser.APPROVE_OPTION) {
            //Put save file code in here
            File saveFile = fileDialog.getSelectedFile();
            tracker.append("\nThe file selected is " + saveFile.getName());
            tracker.append("\nThe file's path is " + saveFile.getPath());
        }
    }

    //append the button choice to the tracker JTextArea
    private void logChoice(int choice, String dialog) {
        switch (choice) {
        //The user pressed cancel button
        case JFileChooser.CANCEL_OPTION:
            tracker.append("\nCancel Option received from " + dialog);
            break;

        //The user pressed the open/save button
        case JFileChooser.APPROVE_OPTION:
            tracker.append("\nApprove Option received from " + dialog);
            break;

        //The user dismissed the dialog without pressing a button
        case JFileChooser.ERROR_OPTION:
            tracker.append("\nError Option received from " + dialog);
            break;
        }
    }
}
