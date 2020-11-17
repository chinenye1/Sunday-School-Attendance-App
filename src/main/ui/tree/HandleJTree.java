package ui.tree;

import model.Person;
import model.SundaySchoolClass;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This class deals with the user's events on the mutable JTree displayed
 * <p>
 * This class was modeled after the JTree example found on:
 * http://www.java2s.com/Tutorials/Java/Swing_How_to/JTree/Add_insert_and_update_JTree.htm
 */
public class HandleJTree extends JPanel implements ActionListener {
    int newNodeSuffix = 1;
    static String ADD_COMMAND = "add";
    static String REMOVE_COMMAND = "remove";
    //    static String CLEAR_COMMAND = "clear";
    DynamicTree treePanel;
    private SundaySchoolClass myClass;

    public HandleJTree(SundaySchoolClass myClass) {
        super(new BorderLayout());
        this.myClass = myClass;
        treePanel = new DynamicTree(myClass);
        populateTree(treePanel);
        JButton addButton = new JButton("Add");
        addButton.setActionCommand(ADD_COMMAND);
        addButton.addActionListener(this);
//        JButton removeButton = new JButton("Remove");
//        removeButton.setActionCommand(REMOVE_COMMAND);
//        removeButton.addActionListener(this);
//        JButton clearButton = new JButton("Clear");
//        clearButton.setActionCommand(CLEAR_COMMAND);
//        clearButton.addActionListener(this);
        treePanel.setPreferredSize(new Dimension(300, 150));
        add(treePanel, BorderLayout.CENTER);
        JPanel panel = new JPanel(new GridLayout(0, 3));
        panel.add(addButton);
        //panel.add(removeButton);
        // panel.add(clearButton);
        add(panel, BorderLayout.SOUTH);
    }

    // EFFECTS: Populates the tree initially with example Teachers and Students in the Classroom
    //TODO: Populate tree with people already in the class
    public void populateTree(DynamicTree treePanel) {
        String teachersName = "Teachers";
        String studentsName = "Students";
        DefaultMutableTreeNode teachers = treePanel.addObject(null, teachersName);
        DefaultMutableTreeNode students = treePanel.addObject(null, studentsName);
        populateWithPeopleInClass(treePanel,teachers,students);
        //treePanel.addObject(null, studentsName);  // this worked

    }

    // EFFECTS: populates tree with people already in the class
    public void populateWithPeopleInClass(DynamicTree treePanel, DefaultMutableTreeNode teachersParentNode,
                                          DefaultMutableTreeNode studentsParentNode) {
        List<Person> teachers = myClass.getTeachers();
        List<Person> students = myClass.getStudents();
        for (Person teacher : teachers) {
            treePanel.addObject(teachersParentNode, teacher.getName());
        }
        for (Person student : students) {
            treePanel.addObject(studentsParentNode, student.getName());
        }
    }

    // EFFECTS: Checks if an action (add/remove/clear) is performed
    //          and changes the tree accordingly
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (ADD_COMMAND.equals(command)) {
            treePanel.addObject("New Node " + newNodeSuffix++);
        }
    }

}


