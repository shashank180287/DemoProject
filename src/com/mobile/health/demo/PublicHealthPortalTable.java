package com.mobile.health.demo;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class PublicHealthPortalTable extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4986221693409246695L;

	public PublicHealthPortalTable(TableModel tableModel) {
		super(tableModel);
	}
	
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row,
			int column) {
        Component stamp = super.prepareRenderer(renderer, row, column);
        if(!isRowSelected(row)){
        	if (row % 2 == 0)
        		stamp.setBackground(Color.white);
        	else
        		stamp.setBackground(Color.LIGHT_GRAY);
        }
        return stamp;
	}
}
