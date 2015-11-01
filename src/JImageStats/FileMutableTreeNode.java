package JImageStats;

import java.io.File;

import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;

public class FileMutableTreeNode extends DefaultMutableTreeNode implements Comparable<FileMutableTreeNode> {
	private static final long serialVersionUID = 3473072271026295740L;
	private File f;
	private int depth;
	
	public FileMutableTreeNode (File f, int depth) {
		super(FileSystemView.getFileSystemView().getSystemDisplayName(f));
		this.f=f;
		this.depth=depth;
	}
	
	public FileMutableTreeNode (String s) {
		super(s);
	}
	
	public File getFile () {
		return this.f;
	}
	
	public int compareTo(FileMutableTreeNode n) {
		return this.depth-n.depth;
	}
	
}
