package com.mobile.health.demo.listener;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.mobile.health.demo.entity.PersonDetails;
import com.mobile.health.demo.manager.DBManger;

public class FlatFileExportActionListener implements ActionListener {

    private JFrame frame;
    private JFileChooser fileDialog;

    public FlatFileExportActionListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //create an instance of JFileChooser class
        fileDialog = new JFileChooser();//"C:\\Documents and Settings\\Owner\\My Documents"
        fileDialog.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileDialog.addChoosableFileFilter(new FileTypeFilter(".csv", "Comma Seperated Value"));
        fileDialog.setAcceptAllFileFilterUsed(true);
        saveDialog();
    }

    //Show a save file dialog box
    private void saveDialog() {
        int saveChoice = fileDialog.showSaveDialog(this.frame);

        if (saveChoice == JFileChooser.APPROVE_OPTION) {
            //Put save file code in here
            File saveFile = fileDialog.getSelectedFile();
            FileTypeFilter fileFilter = (FileTypeFilter) fileDialog.getFileFilter();
            String extension = fileFilter.getExtension();
            String fileName = saveFile.getName();
            if (!fileName.endsWith(extension))
                saveFile = new File(saveFile.getAbsolutePath() + extension);
            writeExcelSheet(saveFile);
        }
    }

    private void writeExcelSheet(File saveFile) {
        List<PersonDetails> personDetails = DBManger.getPersonDetails();
        StringBuilder fileContent = new StringBuilder();
        for (PersonDetails personDetail : personDetails) {
            StringBuilder lineContent = new StringBuilder();
            Object[] objArr = personDetail.toObjectArray();
            for (Object obj : objArr) {
                lineContent.append(obj.toString());
                lineContent.append(",");
            }
            fileContent.append(lineContent.substring(0, lineContent.length() - 1));
            fileContent.append(System.getProperty("line.separator"));
        }
        try {
            //Write the workbook in file system
            FileWriter out = new FileWriter(saveFile);
            out.write(fileContent.toString().toCharArray());
            out.close();
            showMessageDialog(frame, "File written successfully.");
        }
        catch (Exception e) {
            showMessageDialog(frame, "Some error occured. Please try after some time.");
        }
    }
}
