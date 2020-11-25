package ui.tree;

import model.SundaySchoolClass;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This class deals with the user's events on the mutable JTree displayed
 *
 * This class was modeled after the JTree example found on:
 * http://www.java2s.com/Tutorials/Java/Swing_How_to/JTree/Add_insert_and_update_JTree.htm
 */
public class HandleJTree extends JPanel implements ActionListener {
    int newNodeSuffix = 1;
    static String ADD_COMMAND = "add";
    static String REMOVE_COMMAND = "remove";
    static String CLEAR_COMMAND = "clear";
    DynamicTree treePanel;

    // EFFECTS: Creates and adds a new treePanel with set dimensions and adds buttons
    public HandleJTree(SundaySchoolClass myClass) {
        super(new BorderLayout());
        treePanel = new DynamicTree(myClass);
        JButton addButton = new JButton("Add");
        addButton.setActionCommand(ADD_COMMAND);
        addButton.addActionListener(this);
        treePanel.setPreferredSize(new Dimension(300, 150));
        add(treePanel, BorderLayout.CENTER);
        JPanel panel = new JPanel(new GridLayout(0, 3));
        panel.add(addButton);
        add(panel, BorderLayout.SOUTH);
    }

    // EFFECTS: Checks if an action to add is performed
    //          and changes the tree accordingly
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (ADD_COMMAND.equals(command)) {
            treePanel.addObject("New Node " + newNodeSuffix++);
        }
    }

}


