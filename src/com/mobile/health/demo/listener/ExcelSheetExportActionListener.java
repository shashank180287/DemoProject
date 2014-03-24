package com.mobile.health.demo.listener;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mobile.health.demo.entity.PersonDetails;
import com.mobile.health.demo.manager.DBManger;

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
            FileTypeFilter fileFilter = (FileTypeFilter) fileDialog.getFileFilter();
            String extension = fileFilter.getExtension();
            String fileName = saveFile.getName();
            if (!fileName.endsWith(extension))
                saveFile = new File(saveFile.getAbsolutePath() + extension);
            writeExcelSheet(saveFile);
        }
    }

    private void writeExcelSheet(File saveFile) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Persons Data");
        List<PersonDetails> personDetails = DBManger.getPersonDetails();
        int rownum = 0;
        for (PersonDetails personDetail : personDetails) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = personDetail.toObjectArray();
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
            }
        }
        try {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(saveFile);
            workbook.write(out);
            out.close();
            showMessageDialog(frame, "File written successfully.");
        }
        catch (Exception e) {
            showMessageDialog(frame, "Some error occured. Please try after some time.");
        }
    }
}
