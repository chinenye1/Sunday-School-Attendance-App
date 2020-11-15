package ui.tree;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This class listens to all the changes done to the mutable JTree and adjusts it.
 *
 * This class was modeled after the JTree example found on:
 * http://www.java2s.com/Tutorials/Java/Swing_How_to/JTree/Add_insert_and_update_JTree.htm
 *
 */
class MyTreeModelListener implements TreeModelListener {

    // EFFECTS: checks if and notifies that the tree node changed
    @Override
    public void treeNodesChanged(TreeModelEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath()
                .getLastPathComponent());
        int index = e.getChildIndices()[0];
        node = (DefaultMutableTreeNode) (node.getChildAt(index));
        System.out.println("New value NodesChanged: " + node.getUserObject());
    }

    // EFFECTS: checks if and notifies that a node was inserted
    @Override
    public void treeNodesInserted(TreeModelEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath()
                .getLastPathComponent());
        int index = e.getChildIndices()[0];
        node = (DefaultMutableTreeNode) (node.getChildAt(index));
        System.out.println("New value NodesInserted : " + node.getUserObject());
    }

    // EFFECTS: checks and notifies that if a node was removed
    @Override
    public void treeNodesRemoved(TreeModelEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath()
                .getLastPathComponent());
        int index = e.getChildIndices()[0];
        node = (DefaultMutableTreeNode) (node.getChildAt(index));
        System.out.println("New value NodesRemoved : " + node.getUserObject());
    }

    // EFFECTS: checks if and notifies that the tree structure has changed
    @Override
    public void treeStructureChanged(TreeModelEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath()
                .getLastPathComponent());
        int index = e.getChildIndices()[0];
        node = (DefaultMutableTreeNode) (node.getChildAt(index));
        System.out.println("New value StructureChanged : " + node.getUserObject());
    }
}

