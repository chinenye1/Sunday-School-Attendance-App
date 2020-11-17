package ui.tree;

import model.SundaySchoolClass;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;


/**
 * This class represents a mutable JTree with a root node and children nodes
 *
 * This class was modeled after the JTree example found on:
 * http://www.java2s.com/Tutorials/Java/Swing_How_to/JTree/Add_insert_and_update_JTree.htm
 */
public class DynamicTree extends JPanel {
    DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Sunday School Class");
    DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
    JTree tree;

    /**
     * EFFECTS: creates new dynamic tree:
     *          with a JPanel of specified size
     *          sets JTree model with a default tree model (with a default tree node)
     *          made the tree editable
     *          a tree model listener
     *          visible root handles
     *          creates and adds scroll pane
     */
    public DynamicTree(SundaySchoolClass myclass) {
        super(new GridLayout(1, 0));
        // TODO: pass in teachers and students lists into the MyTreeModelListener constructor and the
        //  DynamicTree constructor found in AttendanceApp
        treeModel.addTreeModelListener(new MyTreeModelListener(myclass));
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

    // EFFECTS: returns a new childnode  that has been added below the parent node
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child) {
        return addObject(parent, child, false);
    }

    /** EFFECTS: makes a new child node and inserts it to the tree model under the parent node ,
     *          if the parent node is null, then parent node becomes root node.
     *          if the panel should be visible, it makes the scroll panel visible
     *          returns the created childnode
     */
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
