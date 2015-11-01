package JImageStats;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.SwingConstants;

public class ImageTooltip extends JDialog {
	private static final long serialVersionUID = -4922269045082477427L;
	private static final int MaxSide=250;
	private JLabel lblImage;
	private ImageIcon displayedImage=null;

	public ImageTooltip() {
		getContentPane().setBackground(Color.WHITE);
		setUndecorated(true);
		setBounds(100, 100, 320, 234);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		lblImage = new JLabel();
		lblImage.setFont(UIManager.getFont("Menu.font"));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBackground(Color.WHITE);
		getContentPane().add(lblImage);

	}
    
	public void setImg (ImageIcon image) {
		lblImage.setText("Loading...");
		if (displayedImage!=null) {
			displayedImage.getImage().flush();
		}
		
		double height = image.getIconHeight();
		double width = image.getIconWidth();
		double ratio = height/width;

		int newWidth, newHeight;
		if (ratio>1.0) {
			newWidth=MaxSide;
			newHeight=(int)(newWidth*ratio);
		} else {
			newHeight=MaxSide;
			newWidth=(int)(newHeight/ratio);
		}

		setSize(newWidth, newHeight);
		displayedImage=Utility.resizeImageIcon(image,newWidth,newHeight);
		image.getImage().flush();
		lblImage.setText(null);
		lblImage.setIcon(displayedImage);
	}
}
