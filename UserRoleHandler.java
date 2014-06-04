

import java.awt.Component;

import javax.swing.JTabbedPane;

public interface UserRoleHandler {

	public void addTabs(JTabbedPane tabbedPane, StockManagerDashboard stockManagerDashboard);
	
	public Component buildToolBar(Settings settings);
	
	public StockMenuBuilder getStockMenuBuilder();
	
}
