package JImageStats;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultTreeCellRenderer;

public class DataObjTreeRender extends DefaultTreeCellRenderer {
	private static final long serialVersionUID = -5319763091016088349L;

	public DataObjTreeRender() {
		super();
	}
    
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		ToolTipManager.sharedInstance().registerComponent(tree); //damn, to display the tooltip.
		
		this.selected = selected;
		this.hasFocus = hasFocus;           
		
		FileMutableTreeNode node=(FileMutableTreeNode) value;
		if (node.getFile()==null) {
			this.setText("This PC");
			this.setIcon(FileSystemView.getFileSystemView().getSystemIcon(null));
		} else {
			this.setIcon(FileSystemView.getFileSystemView().getSystemIcon(node.getFile()));
			this.setText(node.toString());
		}
		
		if (this.selected) {
		    super.setBackground(this.getBackgroundSelectionColor());
		    this.setForeground(this.getTextSelectionColor());
		} else {
		    super.setBackground(this.getBackgroundNonSelectionColor());
		    this.setForeground(this.getTextNonSelectionColor());
		}
		
		return this;
    }
}
