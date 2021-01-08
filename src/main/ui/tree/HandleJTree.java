package ui.tree;

import model.SundaySchoolClass;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

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
    static String CLEAR_COMMAND = "clear";
    DynamicTree treePanel;

    // EFFECTS: Creates and adds a new treePanel with set dimensions and adds buttons
    public HandleJTree(SundaySchoolClass myClass) {
        super(new BorderLayout());
        treePanel = new DynamicTree(myClass);

        JButton addButton = createButton(ADD_COMMAND);
        JButton removeButton = createButton(REMOVE_COMMAND);
        JButton clearButton = createButton(CLEAR_COMMAND);
        treePanel.setPreferredSize(new Dimension(300, 150));
        add(treePanel, BorderLayout.CENTER);
        JPanel panel = new JPanel(new GridLayout(0, 3));
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(clearButton);
        add(panel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: creates and returns a button of any type, set button to ActionCommand and adds button to ActionListener
    public JButton createButton(String typeOfButton) {
        String nameOfButton = captitalizeFirstLetter(typeOfButton);
        JButton button = new JButton(nameOfButton);
        button.setActionCommand(typeOfButton);
        button.addActionListener(this);
        return button;
    }

    // EFFECTS: returns buttonName with it's first letter capitalized
    public String captitalizeFirstLetter(String buttonName) {
        String firstLetter = buttonName.substring(0, 1);
        return firstLetter + buttonName.substring(1);
    }

    // EFFECTS: listens to action performed
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

}


