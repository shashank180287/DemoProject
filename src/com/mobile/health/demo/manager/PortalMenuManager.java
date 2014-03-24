/**
 * 
 */
package com.mobile.health.demo.manager;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.mobile.health.demo.listener.ExcelSheetExportActionListener;
import com.mobile.health.demo.listener.ExitButtonActionListener;

public class PortalMenuManager {

    public static void addPortalMenuBarToFrame(JFrame frame) {
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);

        //a group of JMenuItems
        submenu = new JMenu("Save As");
        submenu.setMnemonic(KeyEvent.VK_T);

        menuItem = new JMenuItem("PDF");
        menuItem.addActionListener(new ExcelSheetExportActionListener(frame));
        submenu.add(menuItem);

        menuItem = new JMenuItem("Excel");
        menuItem.addActionListener(new ExcelSheetExportActionListener(frame));
        submenu.add(menuItem);

        menu.add(submenu);

        menuItem = new JMenuItem("Exit", KeyEvent.VK_B);
        menuItem.addActionListener(new ExitButtonActionListener(frame));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        menu.add(menuItem);


        menuBar.add(menu);

        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("About Us");
        menu.add(menuItem);
        menuBar.add(menu);

        frame.setJMenuBar(menuBar);
    }
}
