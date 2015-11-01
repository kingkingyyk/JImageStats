package JImageStats;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.tree.DefaultMutableTreeNode;

public class FileTree {
	public static HashMap<DefaultMutableTreeNode, FileTree> treeNode2FileTree=new HashMap<>();
	private File f;
	private int depth=0;
	private ArrayList<FileTree> child;
	private HashMap<String,FileTree> contains;
	private boolean scanned=false;
	public FileMutableTreeNode guiNode;
	
	public FileTree (File f, int depth) {
		this.f=f;
		this.depth=depth;
		this.contains=new HashMap<>();
		this.guiNode=new FileMutableTreeNode(this.f,depth);
		this.child=new ArrayList<>();
		treeNode2FileTree.put(this.guiNode, this);
	}
	
	private void scanHelper (int level) {
		if (this.f!=null && level<2 && this.f.isDirectory()) {
			File [] childs=this.f.listFiles();
			if (childs!=null) {
				for (int i=0;i<childs.length;i++) {
					if (childs[i].isDirectory() && !childs[i].isHidden()) {
						if (!this.contains.containsKey(childs[i].getPath())) {
							FileTree newNode=new FileTree(childs[i],this.depth+1);
							this.child.add(newNode);
							this.contains.put(childs[i].getPath(), newNode);
							this.guiNode.add(newNode.guiNode);
							newNode.scanHelper(level+1);
						} else {
							this.contains.get(childs[i].getPath()).scanHelper(level+1);
						}
					}
				}
			}
		}
	}
	
	public boolean scan () {
		if (!this.scanned) {
			this.scanned=true;
			this.scanHelper(0);
			return true;
		} else {
			return false;
		}
	}

}
