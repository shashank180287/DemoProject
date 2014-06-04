/*
 * Copyright (c) 2001-2005 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *     
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.looks.LookUtils;
import com.jgoodies.uif_lite.component.UIFSplitPane;

/**
 * Contains nested split panels and demonstrates how ClearLook removes obsolete
 * decorations.
 * 
 * @author Karsten Lentzsch
 * @version $Revision: 1.7 $
 * 
 * @see UIFSplitPane
 */
final class StockManagementTab {

	private UserRoleDefine userRoleDefine;
	private StockManagerDashboard stockManagerDashboard;
	/**
	 * Builds and returns the panel.
	 */

	public StockManagementTab(UserRoleDefine userRoleDefine) {
		this.userRoleDefine = userRoleDefine;
	}

	public StockManagementTab(UserRoleDefine userRoleDefine, StockManagerDashboard stockManagerDashboard) {
		this.userRoleDefine = userRoleDefine;
		this.stockManagerDashboard = stockManagerDashboard;
	}
	
	StockManagerDashboard getStockManagerDashboard() {
		return stockManagerDashboard;
	}

	JComponent build() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(Borders.DIALOG_BORDER);
		panel.add(buildHorizontalSplit(null));
		return panel;
	}

	JComponent build(JTable table) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(Borders.DIALOG_BORDER);
		panel.add(buildHorizontalSplit(table));
		return panel;
	}
	/**
	 * Builds and returns the horizontal split using stripped split panes.
	 * <p>
	 * 
	 * Nesting split panes often leads to duplicate borders. However, a
	 * look&feel should not remove borders completely - unless he has good
	 * knowledge about the context: the surrounding components in the component
	 * tree and the border states.
	 */
	private JComponent buildHorizontalSplit(JTable tabel) {
		JComponent left = new JScrollPane(buildTree());
		left.setPreferredSize(new Dimension(200, 100));

		JComponent upperRight = new JScrollPane(buildTextArea());
		upperRight.setPreferredSize(new Dimension(100, 100));
		
		if(tabel== null)
			tabel = buildTable();
		
		JComponent lowerRight = new JScrollPane(tabel);
		lowerRight.setPreferredSize(new Dimension(100, 100));

		JSplitPane verticalSplit = UIFSplitPane.createStrippedSplitPane(
				JSplitPane.VERTICAL_SPLIT, upperRight, lowerRight);
		
		return UIFSplitPane.createStrippedSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, left, verticalSplit);
	}

	/**
	 * Builds and returns a sample tree.
	 */
	private JTree buildTree() {
        JTree tree = new JTree(createSampleTreeModel());
        tree.expandRow(3);
        tree.expandRow(2);
        tree.expandRow(1);
        switch (userRoleDefine) {
		case ADMIN:
	        tree.addMouseListener(new AdminRoleMouseListener(tree, this));
			break;
		case CUSTOMER:
	        tree.addMouseListener(new CustomerRoleMouseListener(tree));
			break;
		case EMPLOYEE:
			tree.addMouseListener(new EmployeeRoleMouseListener(tree));
			break;
		}
        return tree;
    }

	/**
	 * Builds and returns a sample text area.
	 */
	private JTextArea buildTextArea() {
		JTextArea area = new JTextArea();
		area.setText("Place your comments here");
		return area;
	}

	/**
	 * Builds and returns a sample table.
	 */
	private JTable buildTable() {
		JTable table = new JTable(createSampleTableData(), new String[] {
				"Product Code", "Amount", "Customer		" });

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(300);
		table.setRowSelectionInterval(2, 2);
		int tableFontSize = table.getFont().getSize();
		int minimumRowHeight = tableFontSize + 6;
		int defaultRowHeight = LookUtils.IS_LOW_RESOLUTION ? 17 : 18;
		table.setRowHeight(Math.max(minimumRowHeight, defaultRowHeight));
		return table;
	}

	/**
	 * Creates and returns a sample tree model.
	 */
	private TreeModel createSampleTreeModel() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(
				"Stock Management");
		DefaultMutableTreeNode parent;

		switch (userRoleDefine) {
		case ADMIN: {
			parent = new DefaultMutableTreeNode("Accounts");
			root.add(parent);
			parent.add(new DefaultMutableTreeNode("Customer List"));
			parent.add(new DefaultMutableTreeNode("Supplier List"));
			parent.add(new DefaultMutableTreeNode("Manufacture List"));
			parent.add(new DefaultMutableTreeNode("Customer Order"));
			parent.add(new DefaultMutableTreeNode("Purchase Orders"));
			parent.add(new DefaultMutableTreeNode("Raw Item P.O"));
			parent.add(new DefaultMutableTreeNode("Manufactur Order"));
			//
			parent = new DefaultMutableTreeNode("Inventory");
			root.add(parent);
			parent.add(new DefaultMutableTreeNode("Inventory List"));
			parent.add(new DefaultMutableTreeNode("Raw Materials"));
			parent.add(new DefaultMutableTreeNode("Purchase Return"));
			parent.add(new DefaultMutableTreeNode("Sales return"));
			parent.add(new DefaultMutableTreeNode("Manage Categories"));
			parent.add(new DefaultMutableTreeNode("Destroy Items"));
			parent.add(new DefaultMutableTreeNode("Finished Products"));

			parent = new DefaultMutableTreeNode("Billing");
			root.add(parent);
			parent.add(new DefaultMutableTreeNode("Invoices"));
			parent.add(new DefaultMutableTreeNode("RawItem Invoices"));

		}
			break;
		case CUSTOMER: {
			parent = new DefaultMutableTreeNode("Customer Options");
			root.add(parent);
			parent.add(new DefaultMutableTreeNode("Book Deal"));
			parent.add(new DefaultMutableTreeNode("Check Deal History"));
			parent.add(new DefaultMutableTreeNode("Credit/Debit History"));
		}
			break;
		case EMPLOYEE: {
			parent = new DefaultMutableTreeNode("Employee Options");
			root.add(parent);
			parent.add(new DefaultMutableTreeNode("Edit Details"));
			parent.add(new DefaultMutableTreeNode("Book Deal"));
		}
			break;
		}
		//

		return new DefaultTreeModel(root);
	}

	/**
	 * Creates and returns sample table data.
	 */
	private String[][] createSampleTableData() {
		return new String[][] { { "CF0004", "25000", "ABC" },
				{ "CF0047", "15000", "ABC" }, { "ED0004", "55000", "ABC" },
				{ "AX2344", "5000", "ABC" }, { "AZ0876", "600", "ABC" },
				{ "CF0001", "15000", "ABC" }, { "CF0000", "85000", "ABC" } };
	}

	class AdminRoleMouseListener extends MouseAdapter {
		
		private JTree tree;
		private StockManagementTab stockManagementTab;
		
		public AdminRoleMouseListener() {
		}
		
		public AdminRoleMouseListener(JTree tree, StockManagementTab stockManagementTab) {
			super();
			this.tree = tree;
			this.stockManagementTab = stockManagementTab;
		}

		public void mouseClicked(MouseEvent me) {
			TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
			if("Customer List".equalsIgnoreCase(tp.getLastPathComponent().toString())) {
				JTable table = new JTable( new String[][]{{ "Customer1", "Bangalore", "ABC","012345678900" }}, new String[] {
					"Name", "City", "Account", "Phone" });
				this.stockManagementTab.build(table);
				this.stockManagementTab.getStockManagerDashboard().repaintDashboard();
			}
			
		}
	}
	
	class CustomerRoleMouseListener extends MouseAdapter {
		
		private JTree tree;
		
		public CustomerRoleMouseListener() {
		}
		
		public CustomerRoleMouseListener(JTree tree) {
			super();
			this.tree = tree;
		}
		
		public void mouseClicked(MouseEvent me) {
			if(me.getClickCount()==2){
				System.out.println("Mouse Clicked...");
			}
		}
	}
	
	class EmployeeRoleMouseListener extends MouseAdapter {
		
		private JTree tree;
		
		public EmployeeRoleMouseListener() {
		}
		
		public EmployeeRoleMouseListener(JTree tree) {
			super();
			this.tree = tree;
		}
		public void mouseClicked(MouseEvent me) {
			if(me.getClickCount()==2){
				System.out.println("Mouse Clicked...");
			}
		}
	}
}
