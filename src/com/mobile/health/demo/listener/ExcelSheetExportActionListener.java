package com.mobile.health.demo.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ExcelSheetExportActionListener implements ActionListener {

    private JFrame frame;
    private JFileChooser fileDialog;

    public ExcelSheetExportActionListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //create an instance of JFileChooser class
        fileDialog = new JFileChooser();//"C:\\Documents and Settings\\Owner\\My Documents"
        fileDialog.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileDialog.addChoosableFileFilter(new FileTypeFilter(".xls", "Microsoft Word Documents 2003"));
        fileDialog.addChoosableFileFilter(new FileTypeFilter(".xlsx", "Microsoft Excel Documents"));
        fileDialog.setAcceptAllFileFilterUsed(true);
        saveDialog();
    }

    //Show a save file dialog box
    private void saveDialog() {
        int saveChoice = fileDialog.showSaveDialog(this.frame);

        if (saveChoice == JFileChooser.APPROVE_OPTION) {
            //Put save file code in here
            File saveFile = fileDialog.getSelectedFile();
            // tracker.append("\nThe file selected is " + saveFile.getName());
            // tracker.append("\nThe file's path is " + saveFile.getPath());
        }
    }
}
