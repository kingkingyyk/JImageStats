package JImageStats;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import java.io.File;
import javax.swing.JScrollPane;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class EXIFViewer extends JFrame {
	private static final long serialVersionUID = -2151274913243300894L;

	public EXIFViewer(String filepath) {
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setBounds(100, 100, 450, 450);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 412, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(12, Short.MAX_VALUE))
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 356, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JTextArea textAreaEXIF = new JTextArea();
		textAreaEXIF.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		textAreaEXIF.setEditable(false);
		scrollPane.setViewportView(textAreaEXIF);
		panel.setLayout(null);
		
		JLabel lblFilename = new JLabel("Filename");
		lblFilename.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblFilename.setBounds(10, 11, 414, 14);
		panel.add(lblFilename);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblBackground.setBounds(0, 0, 434, 38);
		panel.add(lblBackground);
		getContentPane().setLayout(groupLayout);
		
		File f=new File(filepath);
		setTitle(f.getName());
		setIconImage(new ImageIcon(Toolkit.getDefaultToolkit().getClass().getResource("/icon.png")).getImage());
		
		try {
			IImageMetadata metadata = Sanselan.getMetadata(f);
			textAreaEXIF.setText(metadata.toString());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error reading file "+filepath+"! The file may been moved.",JImageStats.APP_TITLE,JOptionPane.ERROR_MESSAGE);
		}
		
		setVisible(true);
	}
}
