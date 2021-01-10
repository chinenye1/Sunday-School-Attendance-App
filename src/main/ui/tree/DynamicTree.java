package ui.tree;

import model.Person;
import model.SundaySchoolClass;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.List;

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


    // EFFECTS: creates new dynamic tree:
    //              with a JPanel of specified size
    //               sets JTree model with a default tree model (with a default tree node)
    //               made the tree editable
    //               a tree model listener
    //               visible root handles
    //               creates and adds scroll pane
    public DynamicTree(SundaySchoolClass myClass) {
        super(new GridLayout(1, 0));
        populateTree(myClass);
        treeModel.addTreeModelListener(new MyTreeModelListener(myClass));
        tree = new JTree(treeModel);
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }


    // EFFECTS: Populates the tree initially with example Teachers and Students in the Classroom
    public void populateTree(SundaySchoolClass myClass) {
        String teachersName = "Teachers";
        String studentsName = "Students";
        DefaultMutableTreeNode teachers = addObject(null, teachersName);
        DefaultMutableTreeNode students = addObject(null, studentsName);
        populateWithPeopleInClass(myClass, teachers, students);
    }

    // REQUIRES: teachersParentNode != null
    // EFFECTS: populates tree with people already in the class
    public void populateWithPeopleInClass(SundaySchoolClass myClass, DefaultMutableTreeNode teachersParentNode,
                                          DefaultMutableTreeNode studentsParentNode) {
        List<Person> teachers = myClass.getTeachers();
        List<Person> students = myClass.getStudents();
        for (Person teacher : teachers) {
            addObject(teachersParentNode, teacher.getName());
        }
        for (Person student : students) {
            addObject(studentsParentNode, student.getName());
        }
    }

    // EFFECTS: removes all nodes i.e., the listed teachers and students
    //          including the top-most node
    public void clear() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection
                    .getLastPathComponent());
            currentNode.removeAllChildren();
        }
    //rootNode.removeAllChildren();
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

    // EFFECTS: returns a new child node  that has been added to the parent node
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child) {
        return addObject(parent, child, false);
    }

    // EFFECTS: makes a new child node and inserts it to the tree model under the parent node ,
    //          if the parent node is null, then parent node becomes root node.
    //          if the panel should be visible, it makes the scroll panel visible
    //             returns the created childnode
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
