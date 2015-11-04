package JImageStats;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class FileTableMenu extends JPopupMenu {
	private static final long serialVersionUID = 32681421964042318L;
	private String filePath;
	
	public FileTableMenu (Component invoker) {
		JMenuItem itemOpen=new JMenuItem("<html><b>Open</b></html>",Utility.resizeImageIcon(new ImageIcon(Toolkit.getDefaultToolkit().getClass().getResource("/open.png")),20,18));
		JMenuItem itemViewEXIF=new JMenuItem("View Raw EXIF Info",Utility.resizeImageIcon(new ImageIcon(Toolkit.getDefaultToolkit().getClass().getResource("/document.png")),20,20));
		
		this.add(itemOpen);
		this.add(itemViewEXIF);
		
		itemOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().open(new File(filePath));
				} catch (IOException e) {}
			}
			
		});
		
		itemViewEXIF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread() {
					@Override
					public void run() {
						new EXIFViewer(filePath);
					}
				}.start();
			}
			
		});
	}
	
	public void setFilePath (String s) {
		this.filePath=s;
	}
	
}
