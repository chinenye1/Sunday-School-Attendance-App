package ui.tree;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;
import java.awt.*;

public class DynamicTree extends JPanel {
    DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Sunday School Class");
    DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
    JTree tree;

    /**
     * EFFECTS: creates new dynamic tree:
     * with a JPanel of specified size
     * sets JTree model with a default tree model (with a default tree node)
     * made the tree editable
     * a tree model listener
     * visible root handles
     * creates and adds scroll pane
     */
    public DynamicTree() {
        super(new GridLayout(1, 0));
        treeModel.addTreeModelListener(new MyTreeModelListener());
        tree = new JTree(treeModel);
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }

    // EFFECTS: removes all nodes i.e., the listed teachers and students
    //          including the top-most node
    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }

    // EFFECTS: removes the current node mouse is selecting
    public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection
                    .getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        }
    }

    // EFFECTS: adds a Person to the selected node
    // TODO: change signature to add a person and allow a person to be addable
    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();
        if (parentPath == null) {
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
        }
        return addObject(parentNode, child, true);
    }

    // EFFECTS: continues to add an
    // TODO: Figure out what this method does
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child) {
        return addObject(parent, child, false);
    }

    // EFFECTS: continues to add an
    // TODO: Figure out what this method does
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child, boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
        if (parent == null) {
            parent = rootNode;
        }
        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }
}
