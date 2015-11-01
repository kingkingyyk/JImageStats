package JImageStats;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Utility {

	public static int gcd(int i1, int i2) { 
		if (i2 == 0) {
			return i1;
		}
		return gcd(i2, i1%i2);
	}
	
    public static ImageIcon resizeImageIcon (ImageIcon ic, int width, int height) {
    	return new ImageIcon(ic.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }
}
