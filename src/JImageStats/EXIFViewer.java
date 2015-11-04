package JImageStats;

import javax.swing.JDialog;
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
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

public class EXIFViewer extends JFrame {
	private static final long serialVersionUID = -2151274913243300894L;

	public EXIFViewer(String filepath) {
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setBounds(100, 100, 450, 450);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		
		JTextArea textAreaEXIF = new JTextArea();
		textAreaEXIF.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		scrollPane.setViewportView(textAreaEXIF);
		getContentPane().setLayout(groupLayout);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		splitPane.setLeftComponent(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel lblImage = new JLabel();
		lblImage.setBackground(Color.WHITE);
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblImage);
		
		File f=new File(filepath);
		setTitle(f.getName());
		setIconImage(new ImageIcon(Toolkit.getDefaultToolkit().getClass().getResource("/icon.png")).getImage());
		
		try {
			IImageMetadata metadata = Sanselan.getMetadata(f);
			textAreaEXIF.setText(metadata.toString().replace("\t", " "));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error reading file "+filepath+"! The file may been moved.",JImageStats.APP_TITLE,JOptionPane.ERROR_MESSAGE);
		}
		
		setVisible(true);
	}
}
