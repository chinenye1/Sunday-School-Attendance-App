package ui.tree;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;

import model.Person;
import model.Student;
import model.SundaySchoolClass;
import model.Teacher;

/**
 * This class listens to all the changes done to the mutable JTree and adjusts it.
 *
 * This class was modeled after the JTree example found on:
 * http://www.java2s.com/Tutorials/Java/Swing_How_to/JTree/Add_insert_and_update_JTree.htm
 */
class MyTreeModelListener implements TreeModelListener {
    private SundaySchoolClass myClass;
    private String oldNodeName;

    // EFFECTS: initializes global reference to a sunday school
    public MyTreeModelListener(SundaySchoolClass myClass) {
        this.myClass = myClass;
    }

    // EFFECTS: checks if and notifies that name of tree node was changed.
    @Override
    public void treeNodesChanged(TreeModelEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath()
                .getLastPathComponent());
        int index = e.getChildIndices()[0];
        node = (DefaultMutableTreeNode) (node.getChildAt(index));
        changeNameOfPersonInClass(node);
        System.out.println("New value NodesChanged: " + node.getUserObject());
    }

    // EFFECTS: Changes the name of the person in the appropriate sunday school class list of person
    private void changeNameOfPersonInClass(DefaultMutableTreeNode node) {
        String nodeName = node.getUserObject().toString();
        if (node.getParent().toString().equals("Teachers")) {
            for (Person teacher : myClass.getTeachers()) {
                if (teacher.getName().equals(oldNodeName)) {
                    teacher.setName(nodeName);
                }
            }
        } else if (node.getParent().toString().equals("Students")) {
            for (Person student : myClass.getStudents()) {
                if (student.getName().equals(oldNodeName)) {
                    student.setName(nodeName);
                }
            }
        }
    }

    // EFFECTS: checks if and notifies that a node was inserted
    @Override
    public void treeNodesInserted(TreeModelEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath()
                .getLastPathComponent());
        int index = e.getChildIndices()[0];
        node = (DefaultMutableTreeNode) (node.getChildAt(index));
        addPersonAtNodeToClass(node);
        System.out.println("New value NodesInserted : " + node.getUserObject().toString());
    }

    // EFFECTS: adds the inserted node/person into the appropriate sunday school class list of person
    //          also saves the new nodes old person to check if it is renamed
    private void addPersonAtNodeToClass(DefaultMutableTreeNode node) {
        String nodeName = node.getUserObject().toString();
        String parentNodeName = node.getParent().toString();
        if (parentNodeName.equals("Teachers")) {
            myClass.addTeacherToClass(new Teacher(nodeName, true));
        } else if (parentNodeName.equals("Students")) {
            myClass.addStudentToClass(new Student(nodeName, true));
        }
        oldNodeName = nodeName;
    }

    // EFFECTS: checks if, and notifies that a node was removed
    @Override
    public void treeNodesRemoved(TreeModelEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath()
                .getLastPathComponent());
        int index = e.getChildIndices()[0];
        node = (DefaultMutableTreeNode) (node.getChildAt(index));
        System.out.println("New value NodesRemoved : " + node.getUserObject());
    }

    // EFFECTS: checks if, and notifies that the tree structure has changed
    @Override
    public void treeStructureChanged(TreeModelEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath()
                .getLastPathComponent());
        int index = e.getChildIndices()[0];
        node = (DefaultMutableTreeNode) (node.getChildAt(index));
        System.out.println("New value StructureChanged : " + node.getUserObject());
    }
}

