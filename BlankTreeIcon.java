import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;


public class BlankTreeIcon implements Icon {

	    private static int SIZE = 0;

	    public BlankTreeIcon() {
	    }

	    public int getIconWidth() {
	        return SIZE;
	    }

	    public int getIconHeight() {
	        return SIZE;
	    }

	    public void paintIcon(Component c, Graphics g, int x, int y) {
	        System.out.println(c.getWidth() + " " + c.getHeight() + " " + x + " " + y);
	    }
}
