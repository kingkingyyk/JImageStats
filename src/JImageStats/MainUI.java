package JImageStats;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.JSplitPane;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.PriorityQueue;

import javax.swing.JProgressBar;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.JCheckBox;

public class MainUI extends JFrame {
	private static final long serialVersionUID = -3927883820726832777L;
	private JPanel contentPane;
	private JPanel panelISO;
	private JPanel panelShutterSpeed;
	private JPanel panelFocalLength;
	private JPanel panelAperture;
	private JPanel panelBody;
	private JPanel panelLens;
	private JButton btnAnalyze;
	private JProgressBar progressBar;
	private JTree treeFiles;
	private PriorityQueue<FileMutableTreeNode> expandedNodes;
	private JPanel panelFiles;
	private JPanel panelLens_panelGraph;
	private JComboBox<String> panelFocalLength_comboBoxLens;
	private JPanel panelFocalLength_panelGraph;
	private JComboBox<String> panelAperture_comboBoxBody;
	private JPanel panelAperture_panelGraph;
	private JPanel panelShutterSpeed_panelGraph;
	private JPanel panelISO_panelGraph;
	private JScrollPane scrollPane_1;
	private JTable panelFiles_tableFiles;
	private JPanel panelFiles_panel;
	private JComboBox<String> panelFiles_comboBoxBody;
	private JComboBox<String> panelFiles_comboBoxLens;
	private JPanel panelISO_panel;
	private JComboBox<String> panelISO_comboBoxBody;
	private JComboBox<String> panelISO_comboBoxLens;
	private JComboBox<String> panelLens_comboBoxBody;
	private JPanel panelShutterSpeed_panel;
	private JComboBox<String> panelShutterSpeed_comboBoxBody;
	private JComboBox<String> panelShutterSpeed_comboBoxLens;
	private JPanel panelFocalLength_panel;
	private JComboBox<String> panelFocalLength_comboBoxBody;
	private JComboBox<String> panelAperture_comboBoxLens;
	private JPanel panelDate;
	private JPanel panelDate_panel;
	private JComboBox<String> panelDate_comboBoxBody;
	private JComboBox<String> panelDate_comboBoxLens;
	private JPanel panelDate_panelGraph;
	private JComboBox<String> [] allComboBoxBody;
	private JComboBox<String> [] allComboBoxLens;
	private int [] allComboBoxBodySelectedIndex;
	private int [] allComboBoxLensSelectedIndex;
	private ImageTooltip imgTooltip;
	private JPanel panelAbout;
	private JPanel panelAbout_panelTitle;
	private JLabel pabelAbout_panelTitle_lblTitle;
	private JTextArea panelAbout_scrollPane_lblAbout;
	private JScrollPane panelAbout_scrollPane;
	private JCheckBox panelFiles_chckbxQuickPreview;

	@SuppressWarnings("unchecked")
	public MainUI() {
		expandedNodes=new PriorityQueue<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 694, 469);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setTitle(JImageStats.APP_TITLE);
		setIconImage(new ImageIcon(Toolkit.getDefaultToolkit().getClass().getResource("/icon.png")).getImage());
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBackground(Color.WHITE);
		splitPane.setContinuousLayout(true);
		splitPane.setResizeWeight(0.1);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
		);
		panel.setLayout(null);
		
		btnAnalyze = new JButton("Analyze");
		btnAnalyze.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnAnalyze.setBounds(8, 6, 113, 23);
		btnAnalyze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run () {
						btnAnalyze.setEnabled(false);
						Thread t=new Thread() {
							public void run () {
								JImageStats.Analyze();
								SwingUtilities.invokeLater(new Runnable() {
									public void run () {
										btnAnalyze.setEnabled(true);
										updateAll();
									}
								});
							}
						};
						t.start();
					}
				});


			}
		});
		panel.add(btnAnalyze);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(133, 6, 529, 23);
		panel.add(progressBar);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		treeFiles = new JTree();
		treeFiles.setFont(UIManager.getFont("Menu.font"));
		treeFiles.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		scrollPane.setViewportView(treeFiles);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(UIManager.getFont("Menu.font"));
		tabbedPane.setBackground(Color.WHITE);
		splitPane.setRightComponent(tabbedPane);
		
		panelFiles = new JPanel();
		panelFiles.setBackground(Color.WHITE);
		tabbedPane.addTab("Files", null, panelFiles, null);
		panelFiles.setLayout(new BorderLayout(0, 0));
		
		panelFiles_panel = new JPanel();
		panelFiles_panel.setBackground(Color.WHITE);
		panelFiles.add(panelFiles_panel, BorderLayout.NORTH);
		panelFiles_panel.setLayout(new BoxLayout(panelFiles_panel, BoxLayout.X_AXIS));
		
		panelFiles_comboBoxBody = new JComboBox<>();
		panelFiles_comboBoxBody.setFont(UIManager.getFont("Menu.font"));
		panelFiles_panel.add(panelFiles_comboBoxBody);
		
		panelFiles_comboBoxLens = new JComboBox<>();
		panelFiles_comboBoxLens.setFont(UIManager.getFont("Menu.font"));
		panelFiles_panel.add(panelFiles_comboBoxLens);
		
		scrollPane_1 = new JScrollPane();
		panelFiles.add(scrollPane_1, BorderLayout.CENTER);
		
		panelFiles_tableFiles = new JTable() {
			private static final long serialVersionUID = -9143046099485198944L;

			@Override
		    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		        Component c = super.prepareRenderer(renderer, row, col);
		        int [] selectedRows=this.getSelectedRows();
		        boolean selected=false;
		        for (int i=0;i<selectedRows.length && !selected;i++) {
		        	selected=(selectedRows[i]==row);
		        }
		        if (selected) {
		            c.setBackground(new Color(51,153,255,255));
		        } else if (row%2==0) {
		            c.setBackground(Color.WHITE);
		        } else {
		            c.setBackground(new Color(229,243,255,255));
		        }
		        return c;
		    }
		};
		panelFiles_tableFiles.setShowVerticalLines(false);
		panelFiles_tableFiles.setShowHorizontalLines(false);
		panelFiles_tableFiles.setShowGrid(false);
		panelFiles_tableFiles.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null},
			},
			new String[] {
				"Filename", "Camera", "Lens", "ISO", "Aperture", "Shutter Speed", "Focal Length", "Path"
			}
		));
		panelFiles_tableFiles.getColumnModel().getColumn(0).setPreferredWidth(67);
		panelFiles_tableFiles.getColumnModel().getColumn(1).setPreferredWidth(78);
		panelFiles_tableFiles.getColumnModel().getColumn(3).setPreferredWidth(45);
		panelFiles_tableFiles.getColumnModel().getColumn(4).setPreferredWidth(51);
		panelFiles_tableFiles.getColumnModel().getColumn(5).setPreferredWidth(64);
		panelFiles_tableFiles.getColumnModel().getColumn(6).setPreferredWidth(82);
		panelFiles_tableFiles.addMouseListener(new MouseListener() {
			private FileTableMenu ContextMenu=new FileTableMenu(panelFiles_tableFiles);
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton()==MouseEvent.BUTTON1) {
					ContextMenu.setVisible(false);
					int row=panelFiles_tableFiles.getSelectedRow();
					int col=panelFiles_tableFiles.getSelectedColumn();
					if (arg0.getClickCount()==1) {
						if (panelFiles_chckbxQuickPreview.isSelected() && row!=-1 && col!=-1) {
							panelFiles_tableFiles.setEnabled(false);
							Point l=MouseInfo.getPointerInfo().getLocation();
							int column=-1;
							for (int i=0;i<panelFiles_tableFiles.getColumnCount() && column==-1;i++) {
								if (panelFiles_tableFiles.getColumnName(i).equals("Path")) {
									column=i;
								}
							}
							String localPath=panelFiles_tableFiles.getValueAt(row, column).toString();
							imgTooltip.setLocation((int)l.getX()+15,(int)l.getY()+15);
							imgTooltip.setImg(new ImageIcon(localPath));
							imgTooltip.setVisible(true);
							panelFiles_tableFiles.setEnabled(true);
						}
					} else if (arg0.getClickCount()==2) {
						int column=-1;
						for (int i=0;i<panelFiles_tableFiles.getColumnCount() && column==-1;i++) {
							if (panelFiles_tableFiles.getColumnName(i).equals("Path")) {
								column=i;
							}
						}
						try {
							Desktop.getDesktop().open(new File(panelFiles_tableFiles.getValueAt(row, column).toString()));
						} catch (IOException e) {}
					}
				} else if ((arg0.getModifiers()==InputEvent.BUTTON3_MASK)) {
					int row=panelFiles_tableFiles.rowAtPoint(arg0.getPoint());
					if (row!=-1) {
						panelFiles_tableFiles.clearSelection();
						panelFiles_tableFiles.addRowSelectionInterval(row, row);
						int column=-1;
						for (int i=0;i<panelFiles_tableFiles.getColumnCount() && column==-1;i++) {
							if (panelFiles_tableFiles.getColumnName(i).equals("Path")) {
								column=i;
							}
						}
						ContextMenu.setFilePath(panelFiles_tableFiles.getValueAt(row, column).toString());
						ContextMenu.show(panelFiles_tableFiles,arg0.getX(),arg0.getY());
					}
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {
				if (imgTooltip.isVisible()) {
					imgTooltip.setVisible(false);
					System.gc ();
				}
			}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
		panelFiles_tableFiles.getTableHeader().setFont(UIManager.getFont("Menu.font"));
		panelFiles_tableFiles.setSurrendersFocusOnKeystroke(true);
		panelFiles_tableFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panelFiles_tableFiles.setFont(UIManager.getFont("Menu.font"));
		panelFiles_tableFiles.setFillsViewportHeight(true);
		panelFiles_tableFiles.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelFiles_tableFiles.setBackground(Color.WHITE);
		panelFiles_tableFiles.setAutoCreateRowSorter(true);
		scrollPane_1.setViewportView(panelFiles_tableFiles);
		
		
		panelBody = new JPanel();
		panelBody.setBackground(Color.WHITE);
		tabbedPane.addTab("Body", null, panelBody, null);
		panelBody.setLayout(new BorderLayout(0, 0));
		
		panelLens = new JPanel();
		panelLens.setBackground(Color.WHITE);
		tabbedPane.addTab("Lens", null, panelLens, null);
		panelLens.setLayout(new BorderLayout(0, 0));
		
		panelLens_comboBoxBody = new JComboBox<>();
		panelLens_comboBoxBody.setFont(UIManager.getFont("Menu.font"));
		panelLens.add(panelLens_comboBoxBody, BorderLayout.NORTH);
		
		panelLens_panelGraph = new JPanel();
		panelLens_panelGraph.setBackground(Color.WHITE);
		panelLens.add(panelLens_panelGraph);
		
		panelISO = new JPanel();
		panelISO.setBackground(Color.WHITE);
		tabbedPane.addTab("ISO", null, panelISO, null);
		panelISO.setLayout(new BorderLayout(0, 0));
		
		panelISO_panel = new JPanel();
		panelISO.add(panelISO_panel, BorderLayout.NORTH);
		panelISO_panel.setLayout(new BoxLayout(panelISO_panel, BoxLayout.X_AXIS));
		
		panelISO_comboBoxBody = new JComboBox<>();
		panelISO_comboBoxBody.setFont(UIManager.getFont("Menu.font"));
		panelISO_panel.add(panelISO_comboBoxBody);
		
		panelISO_comboBoxLens = new JComboBox<>();
		panelISO_comboBoxLens.setFont(UIManager.getFont("Menu.font"));
		panelISO_panel.add(panelISO_comboBoxLens);
		
		panelISO_panelGraph = new JPanel();
		panelISO_panelGraph.setBackground(Color.WHITE);
		panelISO.add(panelISO_panelGraph, BorderLayout.CENTER);
		
		panelShutterSpeed = new JPanel();
		panelShutterSpeed.setBackground(Color.WHITE);
		tabbedPane.addTab("Shutter Speed", null, panelShutterSpeed, null);
		panelShutterSpeed.setLayout(new BorderLayout(0, 0));
		
		panelShutterSpeed_panel = new JPanel();
		panelShutterSpeed.add(panelShutterSpeed_panel, BorderLayout.NORTH);
		panelShutterSpeed_panel.setLayout(new BoxLayout(panelShutterSpeed_panel, BoxLayout.X_AXIS));
		
		panelShutterSpeed_comboBoxBody = new JComboBox<>();
		panelShutterSpeed_comboBoxBody.setFont(UIManager.getFont("Menu.font"));
		panelShutterSpeed_panel.add(panelShutterSpeed_comboBoxBody);
		
		panelShutterSpeed_comboBoxLens = new JComboBox<>();
		panelShutterSpeed_comboBoxLens.setFont(UIManager.getFont("Menu.font"));
		panelShutterSpeed_panel.add(panelShutterSpeed_comboBoxLens);
		
		panelShutterSpeed_panelGraph = new JPanel();
		panelShutterSpeed_panelGraph.setBackground(Color.WHITE);
		panelShutterSpeed.add(panelShutterSpeed_panelGraph, BorderLayout.CENTER);
		
		panelFocalLength = new JPanel();
		panelFocalLength.setBackground(Color.WHITE);
		tabbedPane.addTab("Focal Length", null, panelFocalLength, null);
		panelFocalLength.setLayout(new BorderLayout(0, 0));
		
		panelFocalLength_panel = new JPanel();
		panelFocalLength.add(panelFocalLength_panel, BorderLayout.NORTH);
		panelFocalLength_panel.setLayout(new BoxLayout(panelFocalLength_panel, BoxLayout.X_AXIS));
		
		panelFocalLength_comboBoxBody = new JComboBox<>();
		panelFocalLength_comboBoxBody.setFont(UIManager.getFont("Menu.font"));
		panelFocalLength_panel.add(panelFocalLength_comboBoxBody);
		
		panelFocalLength_comboBoxLens = new JComboBox<>();
		panelFocalLength_panel.add(panelFocalLength_comboBoxLens);
		panelFocalLength_comboBoxLens.setFont(UIManager.getFont("Menu.font"));
		
		panelFocalLength_panelGraph = new JPanel();
		panelFocalLength_panelGraph.setBackground(Color.WHITE);
		panelFocalLength.add(panelFocalLength_panelGraph, BorderLayout.CENTER);
		
		panelAperture = new JPanel();
		panelAperture.setBackground(Color.WHITE);
		tabbedPane.addTab("Aperture", null, panelAperture, null);
		panelAperture.setLayout(new BorderLayout(0, 0));
		
		JPanel panelAperture_panel = new JPanel();
		panelAperture.add(panelAperture_panel, BorderLayout.NORTH);
		panelAperture_panel.setLayout(new BoxLayout(panelAperture_panel, BoxLayout.X_AXIS));
		
		panelAperture_comboBoxBody = new JComboBox<>();
		panelAperture_panel.add(panelAperture_comboBoxBody);
		panelAperture_comboBoxBody.setFont(UIManager.getFont("Menu.font"));
		
		panelAperture_comboBoxLens = new JComboBox<>();
		panelAperture_panel.add(panelAperture_comboBoxLens);
		panelAperture_comboBoxLens.setFont(UIManager.getFont("Menu.font"));
		
		panelAperture_panelGraph = new JPanel();
		panelAperture_panelGraph.setBackground(Color.WHITE);
		panelAperture.add(panelAperture_panelGraph, BorderLayout.CENTER);
		
		panelDate = new JPanel();
		panelDate.setBackground(Color.WHITE);
		tabbedPane.addTab("Date", null, panelDate, null);
		tabbedPane.setEnabledAt(7, false);
		panelDate.setLayout(new BorderLayout(0, 0));
		
		panelDate_panel = new JPanel();
		panelDate.add(panelDate_panel, BorderLayout.NORTH);
		panelDate_panel.setLayout(new BoxLayout(panelDate_panel, BoxLayout.X_AXIS));
		
		panelDate_comboBoxBody = new JComboBox<String>();
		panelDate_comboBoxBody.setFont(UIManager.getFont("Menu.font"));
		panelDate_panel.add(panelDate_comboBoxBody);
		
		panelDate_comboBoxLens = new JComboBox<String>();
		panelDate_comboBoxLens.setFont(UIManager.getFont("Menu.font"));
		panelDate_panel.add(panelDate_comboBoxLens);
		
		panelDate_panelGraph = new JPanel();
		panelDate_panelGraph.setBackground(Color.WHITE);
		panelDate.add(panelDate_panelGraph, BorderLayout.CENTER);
		contentPane.setLayout(gl_contentPane);
		
		treeFiles.addTreeExpansionListener(new TreeExpansionListener() {
			@Override
			public void treeCollapsed(TreeExpansionEvent arg0) {
				FileMutableTreeNode node=(FileMutableTreeNode)arg0.getPath().getLastPathComponent();
				expandedNodes.remove(node);
			}
			
			private int expandLevel=0;
			@Override
			public void treeExpanded(TreeExpansionEvent arg0) {
				FileMutableTreeNode node=(FileMutableTreeNode)arg0.getPath().getLastPathComponent();
				if (FileTree.treeNode2FileTree.get(node)!=null) {
					if (!expandedNodes.contains(node)) {
						expandedNodes.add(node);
					}
					if (FileTree.treeNode2FileTree.get(node).scan() && expandLevel<1) {
						SwingUtilities.invokeLater( new Runnable() {
							@Override
							public void run() {
								treeFiles.setEnabled(false);
								((DefaultTreeModel)treeFiles.getModel()).reload(node);
								SwingUtilities.invokeLater( new Runnable() {
									@Override
									public void run() {
										expandLevel++;
										Iterator<FileMutableTreeNode> it=expandedNodes.iterator();
										while (it.hasNext()) {
											FileMutableTreeNode n = it.next();
											boolean parentExpanded=true;
											FileMutableTreeNode tempNode=(FileMutableTreeNode) n.getParent();
											while (tempNode!=JImageStats.RootNode && parentExpanded) {
												parentExpanded=parentExpanded && expandedNodes.contains(tempNode);
												tempNode=(FileMutableTreeNode) tempNode.getParent();
											}
											if (parentExpanded) {
												treeFiles.expandPath(new TreePath(n.getPath()));
											}
										}
										expandLevel--;
									}
								});
								treeFiles.setEnabled(true);
							}
						});
					}
				}
			}
		});
		
		panelAbout = new JPanel();
		panelAbout.setBackground(Color.WHITE);
		tabbedPane.addTab("About", null, panelAbout, null);
		panelAbout.setLayout(new BorderLayout(0, 0));
		
		panelAbout_panelTitle = new JPanel();
		panelAbout_panelTitle.setBackground(Color.WHITE);
		panelAbout.add(panelAbout_panelTitle, BorderLayout.NORTH);
		panelAbout_panelTitle.setLayout(new BoxLayout(panelAbout_panelTitle, BoxLayout.X_AXIS));
		
		pabelAbout_panelTitle_lblTitle = new JLabel(JImageStats.APP_TITLE+" "+JImageStats.VERSION);
		pabelAbout_panelTitle_lblTitle.setIcon(Utility.resizeImageIcon(new ImageIcon(Toolkit.getDefaultToolkit().getClass().getResource("/icon.png")),64,64));
		pabelAbout_panelTitle_lblTitle.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
		pabelAbout_panelTitle_lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		panelAbout_panelTitle.add(pabelAbout_panelTitle_lblTitle);
		
		panelAbout_scrollPane = new JScrollPane();
		panelAbout.add(panelAbout_scrollPane, BorderLayout.CENTER);
		
		panelAbout_scrollPane_lblAbout = new JTextArea("");
		panelAbout_scrollPane_lblAbout.setEditable(false);
		panelAbout_scrollPane_lblAbout.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelAbout_scrollPane_lblAbout.setFont(UIManager.getFont("Menu.font"));
		panelAbout_scrollPane_lblAbout.setBackground(SystemColor.controlHighlight);
		panelAbout_scrollPane.setViewportView(panelAbout_scrollPane_lblAbout);
		try {
			BufferedReader br=new BufferedReader(new FileReader(Toolkit.getDefaultToolkit().getClass().getResource("/about.txt").getFile()));
			StringBuilder sb=new StringBuilder("");
			String s;
			while ((s=br.readLine())!=null) {
				sb.append(s);
				sb.append("\n");
			}
			panelAbout_scrollPane_lblAbout.setText(sb.toString());
			br.close();
		} catch (IOException e) {}
		
		allComboBoxBody=new JComboBox [7];
		allComboBoxLens=new JComboBox [6];
		allComboBoxBodySelectedIndex=new int [7];
		allComboBoxLensSelectedIndex=new int [6];
		
		allComboBoxBody[0]=panelAperture_comboBoxBody;
		allComboBoxBody[1]=panelFiles_comboBoxBody;
		allComboBoxBody[2]=panelISO_comboBoxBody;
		allComboBoxBody[3]=panelShutterSpeed_comboBoxBody;
		allComboBoxBody[4]=panelFocalLength_comboBoxBody;
		allComboBoxBody[5]=panelDate_comboBoxBody;
		allComboBoxBody[6]=panelLens_comboBoxBody;
		
		allComboBoxLens[0]=panelAperture_comboBoxLens;
		allComboBoxLens[1]=panelFiles_comboBoxLens;
		
		panelFiles_chckbxQuickPreview = new JCheckBox("Quick Preview");
		panelFiles_chckbxQuickPreview.setBackground(Color.WHITE);
		panelFiles_chckbxQuickPreview.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panelFiles_panel.add(panelFiles_chckbxQuickPreview);
		allComboBoxLens[2]=panelISO_comboBoxLens;
		allComboBoxLens[3]=panelShutterSpeed_comboBoxLens;
		allComboBoxLens[4]=panelFocalLength_comboBoxLens;
		allComboBoxLens[5]=panelDate_comboBoxLens;
		
		allComboBoxBody[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (allComboBoxBodySelectedIndex[1]!=allComboBoxBody[1].getSelectedIndex()) {
					updateFileTable();
					allComboBoxBodySelectedIndex[1]=allComboBoxBody[1].getSelectedIndex();
				}
			}
			
		});
		allComboBoxLens[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (allComboBoxLensSelectedIndex[1]!=allComboBoxLens[1].getSelectedIndex()) {
					updateFileTable();
					allComboBoxLensSelectedIndex[1]=allComboBoxLens[1].getSelectedIndex();
				}
			}
			
		});
		
		for (int i=0;i<allComboBoxBody.length;i++) {
			if (i!=1) {
				allComboBoxBody[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						int index=-1;
						int toUpdate=-1;
						for (int i=0;i<allComboBoxBody.length && index==-1;i++) {
							if (allComboBoxBody[i].equals(arg0.getSource())) {
								index=i;
							}
						}
						if (index!=-1) {
							if (allComboBoxBodySelectedIndex[index]!=allComboBoxBody[index].getSelectedIndex()) {
								allComboBoxBodySelectedIndex[index]=allComboBoxBody[index].getSelectedIndex();
								toUpdate=index;
							}
						}
						switch (toUpdate) {
							case 0 : {
								updateApertureGraph();
								break;
							}
							case 2 : {
								updateISOGraph();
								break;
							}
							case 3 : {
								updateShutterSpeedGraph();
								break;
							}
							case 4 : {
								updateFocalLengthGraph();
								break;
							}
							case 6 : {
								updateLensGraph();
								break;
							}
						}
					}
				});
			}
		}
		
		for (int i=0;i<allComboBoxLens.length;i++) {
			if (i!=1) {
				allComboBoxLens[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						int index=-1;
						int toUpdate=-1;
						for (int i=0;i<allComboBoxLens.length && index==-1;i++) {
							if (allComboBoxLens[i].equals(arg0.getSource())) {
								index=i;
							}
						}
						if (index!=-1) {
							if (allComboBoxLensSelectedIndex[index]!=allComboBoxLens[index].getSelectedIndex()) {
								allComboBoxLensSelectedIndex[index]=allComboBoxLens[index].getSelectedIndex();
								toUpdate=index;
							}
						}
						switch (toUpdate) {
							case 0 : {
								updateApertureGraph();
								break;
							}
							case 2 : {
								updateISOGraph();
								break;
							}
							case 3 : {
								updateShutterSpeedGraph();
								break;
							}
							case 4 : {
								updateFocalLengthGraph();
								break;
							}
							case 6 : {
								updateLensGraph();
								break;
							}
						}
					}
				});;
			}
		}
		
		imgTooltip=new ImageTooltip();
		treeFiles.setCellRenderer(new DataObjTreeRender());
	}
	
	public void setTreeModel (TreeModel model) {
		this.treeFiles.setModel(model);
	}
	
	public void treeSetNodeVisible (boolean flag) {
		this.treeFiles.setRootVisible(flag);
	}
	
	public File treeGetSelectedFolder () {
		if (this.treeFiles.getLastSelectedPathComponent()!=null) {
			return ((FileMutableTreeNode)this.treeFiles.getLastSelectedPathComponent()).getFile();
		} else {
			return null;
		}
	}
	
	public void setProgressBarCurr(int value) {
		this.progressBar.setValue(value);
	}
	
	public void setProgressBarMax(int value) {
		this.progressBar.setMaximum(value);
	}
	
	private void updateAll () {
		this.updateComboBoxBody();
		this.updateComboBoxLens();
		this.updateFileTable();
		this.updateBodyGraph();
		this.updateLensGraph();
		this.updateISOGraph();
		this.updateShutterSpeedGraph();
		this.updateFocalLengthGraph();
		this.updateApertureGraph();
	}
	
	private void updateComboBoxBody () {
		String [] camerainfo=JImageStats.database.cameraInfo();
		for (int i=0;i<allComboBoxBody.length;i++) {
			allComboBoxBody[i].setModel(new DefaultComboBoxModel<>(camerainfo));
			allComboBoxBodySelectedIndex[i]=0;
		}
	}
	
	private void updateComboBoxLens () {
		String [] lensinfo=JImageStats.database.lensInfo();
		ComboBoxModel<String> model=new DefaultComboBoxModel<>(lensinfo);
		for (int i=0;i<allComboBoxLens.length;i++) {
			allComboBoxLens[i].setModel(model);
			allComboBoxBodySelectedIndex[i]=0;
		}
	}
	
	private void updateFileTable() {
		ImageInfo [] infoAry=JImageStats.database.queryImageInfo(allComboBoxBody[1].getSelectedItem().toString(), allComboBoxLens[1].getSelectedItem().toString());
		String [][] parsedInfo=new String [infoAry.length][8];
		for (int i=0;i<infoAry.length;i++) {
			parsedInfo[i][0]=infoAry[i].filename;
			parsedInfo[i][1]=infoAry[i].body;
			parsedInfo[i][2]=infoAry[i].lens;
			parsedInfo[i][3]=infoAry[i].ISO+"";
			parsedInfo[i][4]=infoAry[i].aperture+"";
			parsedInfo[i][5]=infoAry[i].shutterSpeed;
			parsedInfo[i][6]=infoAry[i].focalLength+"";
			parsedInfo[i][7]=infoAry[i].filepath;
			
		}
		panelFiles_tableFiles.setModel(new DefaultTableModel(
				parsedInfo,
				new String[] {
					"Filename", "Camera", "Lens", "ISO", "Aperture", "Shutter Speed", "Focal Length", "Path"
				}
			) {
			private static final long serialVersionUID = -6968857184008220776L;
			
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
	}
	
	private ChartPanel drawChart (String [][] infoAry, String title, String xAxisLabel, String yAxisLabel, String categoryLabel) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	for (int i=0;i<infoAry.length;i++) {
    		if (infoAry[i][0].equals(ImageDatabase.AllKeyword)) {
    			infoAry[i][0]="Others";
    		}
    		dataset.addValue(Integer.parseInt(infoAry[i][1]), infoAry[i][0], categoryLabel);
		}
    	
    	JFreeChart barChart = ChartFactory.createBarChart3D(title, xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
    	barChart.getTitle().setFont(new Font("Segoe UI Light",Font.PLAIN,20));
    	barChart.getTitle().setPaint(Color.BLACK);
    	barChart.setBackgroundImage(new ImageIcon(Toolkit.getDefaultToolkit().getClass().getResource("/graph_bg.jpg")).getImage());
    	
    	barChart.getLegend().setItemFont(new Font("Segoe UI Light",Font.PLAIN,12));
    	barChart.getLegend().setBackgroundPaint(new Color(255,255,255,150));
    	
    	CategoryPlot plot=barChart.getCategoryPlot();
    	plot.setBackgroundPaint(new Color(0,0,0,150));
    	plot.setRangeCrosshairPaint(new Color(255,255,255,150));
    	plot.setDomainCrosshairPaint(new Color(255,255,255,150));
    	((BarRenderer)plot.getRenderer()).setBarPainter(new StandardBarPainter());

    	CategoryAxis xAxis = plot.getDomainAxis();
    	xAxis.setLabelFont(new Font("Segoe UI Light",Font.PLAIN,15));
    	xAxis.setLabelPaint(Color.BLACK);
    	xAxis.setTickLabelFont(new Font("Segoe UI Light",Font.PLAIN,12));
    	xAxis.setTickLabelPaint(Color.BLACK);
    	
    	NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
    	yAxis.setLabelFont(new Font("Segoe UI Light",Font.PLAIN,15));
    	yAxis.setLabelPaint(Color.BLACK);
    	yAxis.setTickLabelFont(new Font("Segoe UI Light",Font.PLAIN,12));
    	yAxis.setTickLabelPaint(Color.BLACK);
    	
    	ChartPanel chartPanel = new ChartPanel( barChart );
    	chartPanel.setPreferredSize(new Dimension(2, 2));
    	chartPanel.setAutoscrolls(true);
    	return chartPanel;
	}
	
	public void updateApertureGraph() {
    	panelAperture.remove(1);
    	panelAperture.add(drawChart(JImageStats.database.queryApertureUsage(allComboBoxBody[0].getSelectedItem().toString(),allComboBoxLens[0].getSelectedItem().toString()), "Aperture Usage", "F-Stop", "No. Of Photos",""));
    	panelAperture.revalidate();
    	panelAperture.repaint();
	}
	
	public void updateBodyGraph () {
		this.panelBody.removeAll();
    	this.panelBody.add(this.drawChart(JImageStats.database.queryBodyUsage(), "Camera Usage", "Camera", "No. Of Photos", ""));
	}
	
	public void updateLensGraph() {
    	panelLens.remove(1);
    	panelLens.add(drawChart(JImageStats.database.queryLensUsage(allComboBoxBody[6].getSelectedItem().toString()), "Lens Usage", "Lens", "No. Of Photos", ""));
    	panelLens.revalidate();
    	panelLens.repaint();
	}
	
	public void updateISOGraph() {
    	panelISO.remove(1);
		Integer [][] value=JImageStats.database.queryISOUsage(allComboBoxBody[2].getSelectedItem().toString(),allComboBoxLens[2].getSelectedItem().toString());
		String [][] valueStr=new String[value.length][2];
		for (int i=0;i<value.length;i++) {
			for (int i2=0;i2<value[i].length;i2++) {
				valueStr[i][i2]=String.valueOf(value[i][i2]);
			}
		}
    	panelISO.add(drawChart(valueStr, "ISO Usage", "ISO", "No. Of Photos", ""));
    	panelISO.revalidate();
    	panelISO.repaint();
	}
	
	public void updateShutterSpeedGraph() {
    	panelShutterSpeed.remove(1);
    	panelShutterSpeed.add(drawChart(JImageStats.database.queryShutterSpeedUsage(allComboBoxBody[3].getSelectedItem().toString(),allComboBoxLens[3].getSelectedItem().toString()), "Shutter Speed Usage", "Shutter Speed (sec)", "No. Of Photos", ""));
    	panelShutterSpeed.revalidate();
    	panelShutterSpeed.repaint();
	}
	
	public void updateFocalLengthGraph() {
    	panelFocalLength.remove(1);
    	panelFocalLength.add(drawChart(JImageStats.database.queryFocalLengthUsage(allComboBoxBody[4].getSelectedItem().toString(),allComboBoxLens[4].getSelectedItem().toString()), "Focal Length Usage", "Focal Length (mm)", "No. Of Photos", ""));
    	panelFocalLength.revalidate();
    	panelFocalLength.repaint();
	}
	
}
