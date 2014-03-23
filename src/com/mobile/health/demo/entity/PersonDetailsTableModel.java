package com.mobile.health.demo.entity;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class PersonDetailsTableModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5837886206580656013L;
	private List<PersonDetails> personDetails;
	private int editableFieldRow;
	private List<String> columnNames = Arrays.asList("First Name", "Last Name", "Gender", "Age", "Address", "Panchayat");
	private List<String> columns = Arrays.asList("firstName", "lastName", "gender", "age", "address", "panchayat");
	
	@Override
	public int getColumnCount() {
		return columnNames.size();
	}

	@Override
	public int getRowCount() {
		return personDetails!=null?personDetails.size():0;
	}

	@Override
	public String getColumnName(int col) {
		return columnNames.get(col);
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(personDetails!=null){
			try {
				return personDetails.get(rowIndex).getClass().getField(columns.get(columnIndex)).get(personDetails.get(rowIndex));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/*
	 * JTable uses this method to determine the default renderer/ editor for
	 * each cell. If we didn't implement this method, then the last column
	 * would contain text ("true"/"false"), rather than a check box.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public List<PersonDetails> getPersonDetails() {
		return personDetails;
	}

	public void setPersonDetails(List<PersonDetails> personDetails) {
		this.personDetails = personDetails;
	}

	public int getEditableFieldRow() {
		return editableFieldRow;
	}

	public void setEditableFieldRow(int editableFieldRow) {
		this.editableFieldRow = editableFieldRow;
	}
	
}
