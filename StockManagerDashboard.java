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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.Options;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;

/**
 * Builds the main frame in the Simple Looks Demo. Demonstrates and tests
 * different multi-platform issues by showing a variety of Swing widgets in
 * different configurations. Also, this frame contains examples for Swing
 * misuse, that can be automatically corrected by ClearLook.
 * <p>
 * 
 * This class provides a couple of protected methods that create components or a
 * builder. The full JGoodies Looks Demo overrides these methods to vend
 * components or builders from the JGoodies UI framework that better handle
 * different platforms.
 * 
 * @author Karsten Lentzsch
 * @version $Revision: 1.17 $
 */
public class StockManagerDashboard extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5563196550611319791L;

	protected static final Dimension PREFERRED_SIZE = LookUtils.IS_LOW_RESOLUTION ? new Dimension(
			650, 510) : new Dimension(730, 560);

	private static final String COPYRIGHT = "\u00a9 2001-2005 JGoodies Karsten Lentzsch. All Rights Reserved.";

	/** Describes optional settings of the JGoodies Looks. */
	private final Settings settings;

	/**
	 * Constructs a <code>DemoFrame</code>, configures the UI, and builds the
	 * content.
	 */
	protected StockManagerDashboard(Settings settings) {
		this.settings = settings;
		configureUI();
		build();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		repaint();
	}

	public static void main(String[] args) {
		StockManagerDashboard instance = new StockManagerDashboard(createSettings());
		instance.setSize(PREFERRED_SIZE);
		instance.locateOnScreen(instance);
		instance.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		instance.setVisible(true);
	}

	private static Settings createSettings() {
		Settings settings = Settings.createDefault();
		// Configure the settings here.
		return settings;
	}

	/**
	 * Configures the user interface; requests Swing settings and jGoodies Looks
	 * options from the launcher.
	 */
	private void configureUI() {
		Options.setDefaultIconSize(new Dimension(18, 18));

		// Set font options
		UIManager.put(Options.USE_SYSTEM_FONTS_APP_KEY,
				settings.isUseSystemFonts());
		Icon empty = new BlankTreeIcon();
        UIManager.put("Tree.closedIcon", empty);
        UIManager.put("Tree.openIcon", empty);
        UIManager.put("Tree.collapsedIcon", empty);
        UIManager.put("Tree.expandedIcon", empty);
        UIManager.put("Tree.leafIcon", empty);
        
		Options.setGlobalFontSizeHints(settings.getFontSizeHints());
		Options.setUseNarrowButtons(settings.isUseNarrowButtons());

		// Global options
		Options.setTabIconsEnabled(settings.isTabIconsEnabled());
		UIManager.put(Options.POPUP_DROP_SHADOW_ENABLED_KEY,
				settings.isPopupDropShadowEnabled());

		// Swing Settings
		LookAndFeel selectedLaf = settings.getSelectedLookAndFeel();
		if (selectedLaf instanceof PlasticLookAndFeel) {
			PlasticLookAndFeel.setMyCurrentTheme(settings.getSelectedTheme());
			PlasticLookAndFeel.setTabStyle(settings.getPlasticTabStyle());
			PlasticLookAndFeel.setHighContrastFocusColorsEnabled(settings
					.isPlasticHighContrastFocusEnabled());
		} else if (selectedLaf.getClass() == MetalLookAndFeel.class) {
			MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
		}

		// Work around caching in MetalRadioButtonUI
		JRadioButton radio = new JRadioButton();
		radio.getUI().uninstallUI(radio);
		JCheckBox checkBox = new JCheckBox();
		checkBox.getUI().uninstallUI(checkBox);

		try {
			UIManager.setLookAndFeel(selectedLaf);
		} catch (Exception e) {
			System.out.println("Can't change L&F: " + e);
		}

	}

	/**
	 * Builds the <code>DemoFrame</code> using Options from the Launcher.
	 */
	private void build() {
		setContentPane(buildContentPane());
		setTitle(getWindowTitle());
		setJMenuBar(createMenuBuilder().buildMenuBar(settings,
				createHelpActionListener(), createAboutActionListener()));
		setIconImage(readImageIcon("eye_16x16.gif").getImage());
	}

	/**
	 * Creates and returns a builder that builds the menu. This method is
	 * overriden by the full JGoodies Looks Demo to use a more sophisticated
	 * menu builder that uses the JGoodies UI Framework.
	 * 
	 * @return the builder that builds the menu bar
	 */
	protected StockMenuBuilder createMenuBuilder() {
		return new StockMenuBuilder();
	}

	/**
	 * Builds and answers the content.
	 */
	private JComponent buildContentPane() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(buildToolBar(), BorderLayout.NORTH);
		panel.add(buildMainPanel(), BorderLayout.CENTER);
		return panel;
	}

	// Tool Bar *************************************************************

	/**
	 * Builds, configures and returns the toolbar. Requests HeaderStyle,
	 * look-specific BorderStyles, and Plastic 3D Hint from Launcher.
	 */
	private Component buildToolBar() {
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

	/**
	 * Creates and returns a <code>JToggleButton</code> configured for use in a
	 * JToolBar.
	 * <p>
	 * 
	 * This is a simplified method that is overriden by the Looks Demo. The full
	 * code uses the JGoodies UI framework's ToolBarButton that better handles
	 * platform differences.
	 */
	protected AbstractButton createToolBarRadioButton(String iconName) {
		JToggleButton button = new JToggleButton(readImageIcon(iconName));
		button.setFocusable(false);
		return button;
	}

	// Tabbed Pane **********************************************************

	/**
	 * Builds and answers the tabbed pane.
	 */
	private Component buildMainPanel() {
		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		// tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		addTabs(tabbedPane);
		tabbedPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		return tabbedPane;
	}

	private void addTabs(JTabbedPane tabbedPane) {
		tabbedPane.addTab("Stock Management", new StockManagementTab().build());
		tabbedPane.addTab("Dashboard", new DesktopTab().build());
	}

	protected String getWindowTitle() {
		return "Stock Management System";
	}

	// Helper Code
	// **********************************************************************

	/*
	 * Looks up and answers an icon for the specified filename suffix.<p>
	 */
	protected static ImageIcon readImageIcon(String filename) {
		URL url = StockManagerDashboard.class.getClassLoader().getResource(
				"images/" + filename);
		return new ImageIcon(url);
	}

	/**
	 * Locates the given component on the screen's center.
	 */
	protected void locateOnScreen(Component component) {
		Dimension paneSize = component.getSize();
		Dimension screenSize = component.getToolkit().getScreenSize();
		component.setLocation((screenSize.width - paneSize.width) / 2,
				(screenSize.height - paneSize.height) / 2);
	}

	/**
	 * Creates and answers an ActionListener that opens the help viewer.
	 */
	protected ActionListener createHelpActionListener() {
		return null;
	}

	/**
	 * Creates and answers an ActionListener that opens the about dialog.
	 */
	protected ActionListener createAboutActionListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(StockManagerDashboard.this,
						"The simple Looks Demo Application\n\n" + COPYRIGHT
								+ "\n\n");
			}
		};
	}

}
