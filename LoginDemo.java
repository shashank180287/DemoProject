import java.awt.Dimension;

import javax.swing.JFrame;

import com.jgoodies.looks.LookUtils;


public class LoginDemo {

	public static void main(String[] args) {
		StockManagerDashboard instance = new StockManagerDashboard(StockManagerDashboard.createSettings(), UserRoleDefine.CUSTOMER);
		instance.setSize(LookUtils.IS_LOW_RESOLUTION ? new Dimension(
				650, 510) : new Dimension(730, 560));
		instance.locateOnScreen(instance);
		instance.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		instance.setVisible(true);
	}
}
