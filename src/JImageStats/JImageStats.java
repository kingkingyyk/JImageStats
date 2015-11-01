package JImageStats;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeModel;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.*;
import org.apache.sanselan.common.IImageMetadata.IImageMetadataItem;

public class JImageStats {
	
	public static String APP_TITLE = "JImageStats";
	public static String VERSION = "v2015.11.1.1";
	public static FileMutableTreeNode RootNode=new FileMutableTreeNode("This PC");
	public static MainUI MainMenu;
	public static ImageDatabase database=null;
	
	public static void InitializeFile () {
		File [] f=File.listRoots();
		for (int i=0;i<f.length;i++) {
			FileTree tr = new FileTree(f[i],0);
			tr.scan();
			RootNode.add(tr.guiNode);
		}
	}
	
	public static void InitializeGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		MainMenu = new MainUI();
		MainMenu.setTreeModel(new DefaultTreeModel(RootNode));
		MainMenu.setVisible(true);
	}
	
	private static void recurseAnalyze (File folder, ArrayList<File> pictureFilesList) {
		File [] files=folder.listFiles();
		for (int i=0;i<files.length;i++) {
			if (files[i].isFile()) {
				String s=files[i].getPath();
				if (s.substring(s.length()-4, s.length()).equalsIgnoreCase(".jpg") || s.substring(s.length()-5, s.length()).equalsIgnoreCase(".jpeg")) {
					pictureFilesList.add(files[i]);
				}
			} else {
				recurseAnalyze(files[i],pictureFilesList);
			}
		}
	}
	
	
	public static void Analyze() {
		if (MainMenu.treeGetSelectedFolder()!=null) {
			if (database!=null) {
				database.destroy();
			}
			database=new ImageDatabase();
			ArrayList<File> pictureFilesList=new ArrayList<>();
			recurseAnalyze(MainMenu.treeGetSelectedFolder(),pictureFilesList);
			MainMenu.setProgressBarCurr(0);
			MainMenu.setProgressBarMax(pictureFilesList.size());
			for (int i=0;i<pictureFilesList.size();i++) {
				MainMenu.setProgressBarCurr(i);
				try {
					File picture=pictureFilesList.get(i);
					IImageMetadata metadata = Sanselan.getMetadata(picture);
					if (metadata!=null) {
						@SuppressWarnings("unchecked")
						ArrayList<IImageMetadataItem> exifInfoList=metadata.getItems();
						Iterator<IImageMetadataItem> it=exifInfoList.iterator();
						String [] exifInfoAry=new String[exifInfoList.size()];
						int count=0;
						while (it.hasNext()) {
							exifInfoAry[count++]=it.next().toString();
						}
						Arrays.sort(exifInfoAry);

						String [][] exifInfoArySeperated=new String[2][exifInfoAry.length];
						for (int i2=0;i2<exifInfoAry.length;i2++) {
							String [] seperated=exifInfoAry[i2].split(": ");
							exifInfoArySeperated[0][i2]=seperated[0]+": ";
							exifInfoArySeperated[1][i2]=seperated[1];
						}
						
						ImageInfo info=new ImageInfo();
						info.filepath=picture.getPath();
						info.filename=picture.getName();
						info.body=searchTag(exifInfoArySeperated,"Model: ");
						if (info.body!=null) {
							info.body=info.body.substring(1,info.body.length()-1);
							info.lens=searchTag(exifInfoArySeperated,"Unknown Tag (0xa434): ");
							if (info.lens==null) {
								info.lens=ImageDatabase.AllKeyword;
							} else {
								info.lens=info.lens.substring(1,info.lens.length()-1);
							}
							
							String s=searchTag(exifInfoArySeperated,"FNumber: ");
							if (s!=null) {
								if (s.contains(" (")) {
									s=s.substring(s.indexOf(" (")+2, s.indexOf(')'));
								}
								try {
									info.aperture=Double.parseDouble(s);
								} catch (NumberFormatException e) {
									info.aperture=0;
								}
							}
							
							s=searchTag(exifInfoArySeperated,"ISO: ");
							if (s!=null) {
								info.ISO=Integer.parseInt(s);
							}
							
							s=searchTag(exifInfoArySeperated,"Exposure Time: ");
							if (s!=null) {
								if (s.contains(" (")) {
									s=s.substring(0, s.indexOf(" ("));
								}
								if (s.contains("/")) {
									String [] parts=s.split("/");
									int i1=Integer.parseInt(parts[0]);
									int i2=Integer.parseInt(parts[1]);
									int gcd=Utility.gcd(i1,i2);
									s=(i1/gcd)+"/"+(i2/gcd);
								}
								info.shutterSpeed=s;
							}
							
							s=searchTag(exifInfoArySeperated,"Focal Length In 3 5mm Format: ");
							if (s==null) {
								s=searchTag(exifInfoArySeperated,"Focal Length: ");
								if (s!=null && s.contains(" (")) {
									s=s.substring(s.indexOf(" (")+2, s.indexOf(')'));
								}
							}
							if (s!=null) {
								if (s.contains(" (")) {
									s=s.substring(s.indexOf(" (")+2, s.indexOf(')'));
								}
								info.focalLength=Double.parseDouble(s);
							}
							
							s=searchTag(exifInfoArySeperated,"Create Date: ");
							if (s!=null) {
								info.date=s.split(" ")[0].substring(1, s.split(" ")[0].length());
							}
							
							database.registerImage(info);
						}
					}
				} catch (ImageReadException | IOException e) {}
			}
			MainMenu.setProgressBarCurr(0);
		} else {
			JOptionPane.showMessageDialog(null, "Please select the desired folder before pressing Analyze button", "JImageStats", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static String searchTag (String [][] exifInfo, String tag) {
		int index=Arrays.binarySearch(exifInfo[0],tag);
		if (index>=0) {
			return exifInfo[1][index];
		} else {
			return null;
		}
	}
	
	public static void main(String[] args) {
		InitializeFile();
		InitializeGUI();
	}

}
