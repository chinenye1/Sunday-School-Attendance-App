package ui.tree;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;

class MyTreeModelListener implements TreeModelListener {
    @Override
    public void treeNodesChanged(TreeModelEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath()
                .getLastPathComponent());
        int index = e.getChildIndices()[0];
        node = (DefaultMutableTreeNode) (node.getChildAt(index));
        System.out.println("New value NodesChanged: " + node.getUserObject());
    }

    @Override
    public void treeNodesInserted(TreeModelEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath()
                .getLastPathComponent());
        int index = e.getChildIndices()[0];
        node = (DefaultMutableTreeNode) (node.getChildAt(index));
        System.out.println("New value NodesInserted : " + node.getUserObject());
    }

    @Override
    public void treeNodesRemoved(TreeModelEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath()
                .getLastPathComponent());
        int index = e.getChildIndices()[0];
        node = (DefaultMutableTreeNode) (node.getChildAt(index));
        System.out.println("New value NodesRemoved : " + node.getUserObject());
    }

    @Override
    public void treeStructureChanged(TreeModelEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath()
                .getLastPathComponent());
        int index = e.getChildIndices()[0];
        node = (DefaultMutableTreeNode) (node.getChildAt(index));
        System.out.println("New value StructureChanged : " + node.getUserObject());
    }
}

