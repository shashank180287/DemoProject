package com.mobile.health.demo.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ExitButtonActionListener implements ActionListener {

    private JFrame frame;

    public ExitButtonActionListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.frame.dispose();
    }

}
