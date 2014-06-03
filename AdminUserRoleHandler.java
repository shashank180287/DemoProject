

import java.awt.Component;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import com.jgoodies.looks.Options;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;

public class AdminUserRoleHandler implements UserRoleHandler{

	@Override
	public void addTabs(JTabbedPane tabbedPane) {
		tabbedPane.addTab("Stock Management", new StockManagementTab(UserRoleDefine.ADMIN).build());
		tabbedPane.addTab("Dashboard", new StockDashboardTab().build());
	}

	@Override
	public Component buildToolBar(Settings settings) {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.putClientProperty("JToolBar.isRollover", Boolean.TRUE);
		// Swing
		toolBar.putClientProperty(Options.HEADER_STYLE_KEY,
				settings.getToolBarHeaderStyle());
		toolBar.putClientProperty(PlasticLookAndFeel.BORDER_STYLE_KEY,
				settings.getToolBarPlasticBorderStyle());
		toolBar.putClientProperty(PlasticLookAndFeel.BORDER_STYLE_KEY,
				settings.getToolBarWindowsBorderStyle());
		toolBar.putClientProperty(PlasticLookAndFeel.IS_3D_KEY,
				settings.getToolBar3DHint());

		AbstractButton button;
		toolBar.add(createToolBarButton("home.gif"));
		toolBar.addSeparator();
		toolBar.add(createToolBarButton("print.gif"));
		toolBar.add(createToolBarButton("refresh.gif"));
		toolBar.addSeparator();

		button = createToolBarButton("help.gif");
		button.addActionListener(createHelpActionListener());
		toolBar.add(button);

		toolBar.add(Box.createGlue());
		return toolBar;
	}

	@Override
	public StockMenuBuilder getStockMenuBuilder() {
		return new StockMenuBuilder();
	}

	/**
	 * Creates and returns a <code>JButton</code> configured for use in a
	 * JToolBar.
	 * <p>
	 * 
	 * This is a simplified method that is overriden by the Looks Demo. The full
	 * code uses the JGoodies UI framework's ToolBarButton that better handles
	 * platform differences.
	 */
	protected AbstractButton createToolBarButton(String iconName) {
		JButton button = new JButton(readImageIcon(iconName));
		button.setToolTipText(iconName);
		button.setFocusable(false);
		return button;
	}
	
	/*
	 * Looks up and answers an icon for the specified filename suffix.<p>
	 */
	protected static ImageIcon readImageIcon(String filename) {
		URL url = StockManagerDashboard.class.getClassLoader().getResource(
				"images/" + filename);
		return new ImageIcon(url);
	}
	
	/**
	 * Creates and answers an ActionListener that opens the help viewer.
	 */
	protected ActionListener createHelpActionListener() {
		return null;
	}
}
