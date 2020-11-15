package ui.tree;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*ww  w.j ava2  s .co  m*/
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This class deals with the
 * This class was modeled after the JTree example found on:
 * http://www.java2s.com/Tutorials/Java/Swing_How_to/JTree/Add_insert_and_update_JTree.htm
 *
 */
public class HandleJTree extends JPanel implements ActionListener {
    int newNodeSuffix = 1;
    static String ADD_COMMAND = "add";
    static String REMOVE_COMMAND = "remove";
    static String CLEAR_COMMAND = "clear";
    DynamicTree treePanel = new DynamicTree();

    public HandleJTree() {
        super(new BorderLayout());
        populateTree(treePanel);
        JButton addButton = new JButton("Add");
        addButton.setActionCommand(ADD_COMMAND);
        addButton.addActionListener(this);
        JButton removeButton = new JButton("Remove");
        removeButton.setActionCommand(REMOVE_COMMAND);
        removeButton.addActionListener(this);
        JButton clearButton = new JButton("Clear");
        clearButton.setActionCommand(CLEAR_COMMAND);
        clearButton.addActionListener(this);
        treePanel.setPreferredSize(new Dimension(300, 150));
        add(treePanel, BorderLayout.CENTER);
        JPanel panel = new JPanel(new GridLayout(0, 3));
        panel.add(addButton);
        panel.add(removeButton);
        // panel.add(clearButton);
        add(panel, BorderLayout.SOUTH);
    }

    // EFFECTS: Populates the tree initially with example Teachers and Students in the Classrom
    public void populateTree(DynamicTree treePanel) {
        String teachersName = "Teachers";
        String studentsName = "Students";
        String person1 = "Person1";
        String person2 = "Person2";
        DefaultMutableTreeNode teachers = treePanel.addObject(null, teachersName);
        DefaultMutableTreeNode students = treePanel.addObject(null, studentsName);
        treePanel.addObject(teachers, person1);
        treePanel.addObject(teachers, person2);
        treePanel.addObject(students, person1);
        treePanel.addObject(students, person2);
    }

    // EFFECTS: Checks if an action (add/remove/clear) is performed
    //          and changes the tree accordingly

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (ADD_COMMAND.equals(command)) {
            treePanel.addObject("New Node " + newNodeSuffix++);
        } else if (REMOVE_COMMAND.equals(command)) {
            treePanel.removeCurrentNode();
        } else if (CLEAR_COMMAND.equals(command)) {
            treePanel.clear();
        }
    }

    // EFFECTS:
    // TODO: change main method to helper method and call in constructor
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        HandleJTree newContentPane = new HandleJTree();
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }
}


