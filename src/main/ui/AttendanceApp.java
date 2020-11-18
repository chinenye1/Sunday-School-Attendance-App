package ui;

import model.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Category;
import model.SundaySchoolClass;
import model.WorkRoom;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import ui.tree.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * /* This class was modeled after ui.WorkRoomApp class in:
 * https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 * <p>
 * This is an app that enables one to take attendance of the entire class. And save who was in class
 * User is able to add and remove people in the class, as well as to view the people in the class.
 */
public class AttendanceApp {
    private SundaySchoolClass myClass;
    private Scanner input;
    private static final String JSON_STORE = "./data/workroom.json";
    private WorkRoom workRoom;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private boolean keepGoing = true;
    private boolean classLoaded = false;
    private HandleAudio playAudio = new HandleAudio();
    private JFrame frame;
    //private HandleJTree handleJTree;
    //private DynamicTree dynamicTree = new DynamicTree();

    public enum ClassOperation {
        ADD_TEACHER, REMOVE_TEACHER, ADD_STUDENT, REMOVE_STUDENT
    }

    // EFFECTS: Creates an AttendanceApp, with new Scanner, correct JSON Store, and initiates the program
    public AttendanceApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        workRoom = new WorkRoom("Chi's workroom");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        startSundaySchool();
    }

    // MODIFIES: this
    // EFFECTS: Initializes the entire use of the app.
    //          displays welcome message,continues to display option menus, and receive user input
    //          until quit option is chosen
    public void startSundaySchool() {
        welcomeMessage();
        input = new Scanner(System.in);
        askToLoadPreviousClasses();
        boolean showLoadOldOrCreateNewOrQuitMenu = true;
        while (keepGoing) {
            if (showLoadOldOrCreateNewOrQuitMenu) {
                loadOldOrCreateNewOrQuitMenu();
            }
            if (myClass != null) {
                displayClassRoomOperationMenu();
                showLoadOldOrCreateNewOrQuitMenu = false;
            }
        }
    }

    // EFFECTS: pop-up panel asks user if they would like to load the names of the previous classes.
    public void askToLoadPreviousClasses() {
        int answer = JOptionPane.showConfirmDialog(null, "Would you like to load a class?");
        if (answer == 0) {
            tryLoadingPreviousClass();
        }
    }


    // EFFECTS: Displays an introductory message to user to let them know of the app's functionality
    public void welcomeMessage() {
        System.out.println("Welcome to Sunday School! \nUse this application to load previous Sunday School Classes,"
                + "create a new Sunday School Class and save your current Sunday School Class."
                + " \nOnce you have a class, you can perform multiple operations on your class.");
    }

    // MODIFIES: this
    // EFFECTS: Stores user choice between loading a previous class or creating a new one or quitting the app
    public void loadOldOrCreateNewOrQuitMenu() {
        System.out.println("\nWhat would you like to do?"
                + "\n 1 -> Create a new class"
                + "\n 2 -> Print loaded classes"
                + "\n 3 -> Quit App");
        int answer = Integer.parseInt(input.next().toLowerCase());
        handleLoadOldOrCreateNewOrQuitChoice(answer, listOfValidOptions(3));
    }

    // REQUIRES: answer is a valid choice/option
    // MODIFIES: this
    // EFFECTS: Either loads a new class, creates a new class or quits the app
    //          based on user's choice
    public void handleLoadOldOrCreateNewOrQuitChoice(int answer, List<Integer> validOptions) {
        if (validChoice(answer, validOptions)) {
            switch (answer) {
                case 1:
                    addAClass();
                    classLoaded = true;
                    break;
                case 2:
                    printClasses();
                    break;
                case 3:
                    quitApp();
                    break;
            }
        } else {
            System.out.println("You must select a valid answer to continue.");
        }
    }

    // EFFECTS: if class has not been loaded, loads previous class
    //          else asks user to choose a different option
    public void tryLoadingPreviousClass() {
        if (!classLoaded) {
            loadPreviousClassRoom();
            classLoaded = true;

        } else {
            System.out.println("Your previous class has already been loaded."
                    + "\nPlease choose a different option");
        }
    }

    // EFFECTS: Displays class operations options and performs the chosen operation
    public void displayClassRoomOperationMenu() {
        System.out.println("\nWhat would you like to do with this class? "
                + "\n 1 -> Add/ Remove a Person "
                + "\n 2 -> Change Who's In Class"
                + "\n 3 -> Display People in Class"
                + "\n 4 -> Take Attendance"
                + "\n 5 -> Save Class Room"
                + "\n 6 -> Empty Current Class"
                + "\n 7 -> Quit App");
        int answer = Integer.parseInt(input.next().toLowerCase());
        operateClassRoom(answer, listOfValidOptions(7));
    }

    //  REQUIRES: lengthOfOptionSubstring > 0
    //  EFFECTS: returns a list of valid options a user can input at a certain stage in the program
    //           mergedOptions is a list of the concatenated valid string options
    //           lengthOfOptionSubstring is how length of a valid option in the mergedOptions string
    public List<Integer> listOfValidOptions(int numOfOptions) {
        List<Integer> validOptions = new ArrayList<>();
        for (int i = 1; i <= numOfOptions; i++) {
            validOptions.add(i);
        }
        return validOptions;
    }

    // EFFECTS: if user's choice is valid (one of offered choices)
    //              performs appropriate class operation based on user's choice.
    //          else asks user to enter a valid choice
    public void operateClassRoom(int choice, List<Integer> validOptions) {
        if (validChoice(choice, validOptions)) {
            doClassOperation(choice);
        } else {
            System.out.println("You must enter a valid option to proceed");
        }
    }

    // REQUIRES: choice is a valid option
    // EFFECTS: performs user's chosen operation on their current class
    public void doClassOperation(int choice) {
        switch (choice) {
            case 1:
            case 2:
                addOrRemovePeopleInClassMenu();
                break;
            case 3:
                displayPeopleInClass();
                break;
            case 4:
                takeAttendance();
                break;
            case 5:
                askToSaveCurrentClass();
                break;
            case 6:
                emptyClass();
                break;
            case 7:
                quitApp();
        }
    }

    // EFFECTS: creates a new SundaySchoolClass
    //          with user's chosen name and classCategory, and notifies user it was created
    public void addAClass() {
        String nameOfClass = askForClassName();
        Category classCategory = askForClassCategory();
        myClass = new SundaySchoolClass(nameOfClass, classCategory);
        workRoom.addClassroom(myClass);
        System.out.println("Your class has been created, and is currently empty.");
    }

    // MODIFIES: this
    // EFFECTS: Asks and returns user's chosen name of their current class
    public String askForClassName() {
        System.out.println("What is the name of the class? ");
        return input.next();
    }

    // MODIFIES: this
    // EFFECTS: Asks and returns user's chosen class category for their current class
    public Category askForClassCategory() {
        int choice;
        Category classCategory;
        while (true) {
            System.out.println("What category does this class fall under?"
                    + "\n 1 -> Pre School"
                    + "\n 2 -> Elementary"
                    + "\n 3 -> High School"
                    + "\n 4 -> Adult");
            choice = Integer.parseInt(input.next().toLowerCase());
            if (validChoice(choice, listOfValidOptions(4))) {
                classCategory = createClassCategory(choice);
                break;
            } else {
                System.out.println("You must select a valid answer to continue");
            }
        }
        return classCategory;
    }

    // REQUIRES: categoryEntry is a valid option for categories
    // EFFECTS: Maps user's category input to a corresponding category
    public Category createClassCategory(int categoryEntry) {
        switch (categoryEntry) {
            case 1:
                return Category.PRESCHOOL;
            case 2:
                return Category.ELEMENTARY;
            case 3:
                return Category.HIGHSCHOOL;
            case 4:
                return Category.ADULT;
            default:
                return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user if they would like to change who is in their class by adding/removing people to/from the class
    //          if they say yes, the people are added/removed as requested
    public void addOrRemovePeopleInClassMenu() {
        System.out.println("\nWhat would you like like to do?"
                + "\n 1 -> Add Teacher"
                + "\n 2 -> Remove Teacher"
                + "\n 3 -> Add Student"
                + "\n 4 -> Remove Student");
        int choice = Integer.parseInt(input.next().toLowerCase());
        processAddOrRemoveChoice(choice, listOfValidOptions(4));
    }

    // MODIFIES: this
    // EFFECTS: if user's choice is one of the valid options
    //              maps user choice to available ClassRoomOperation options
    //          else asks user to input a valid option
    public void processAddOrRemoveChoice(int choice, List<Integer> validOptions) {
        if (validChoice(choice, validOptions)) {
            switch (choice) {
                case 1:
                    this.addOrRemovePerson(ClassOperation.ADD_TEACHER);
                    System.out.println("Teacher added to current class");
                    break;
                case 2:
                    this.addOrRemovePerson(ClassOperation.REMOVE_TEACHER);
                    System.out.println("Person removed from current class");
                    break;
                case 3:
                    this.addOrRemovePerson(ClassOperation.ADD_STUDENT);
                    System.out.println("Student added to current class");
                    break;
                case 4:
                    this.addOrRemovePerson(ClassOperation.REMOVE_STUDENT);
                    System.out.println("Person removed from current class");
                    break;
            }
        } else {
            System.out.println("You must select a valid option");
        }
    }

    // MODIFIES: this
    // EFFECTS: either adds/removes a teacher or student depending of type of change requested by user
    private void addOrRemovePerson(ClassOperation change) {
        String personName;
        switch (change) {
            case ADD_STUDENT:
            case ADD_TEACHER:
                startJTree();
                break;
            case REMOVE_STUDENT:
                personName = getNameOfPerson();
                myClass.removeStudentFromClass(personName);
                break;
            case REMOVE_TEACHER:
                personName = getNameOfPerson();
                myClass.removeTeacherFromClass(personName);
                break;
        }
    }

    public void startJTree() {
        mainForJTree();
    }


    // REQUIRES: validInputs is not empty
    // EFFECTS: checks if user input one of the offered menu options
    public boolean validChoice(int choice, List<Integer> validInputs) {
        boolean valid = false;
        for (Integer validInput : validInputs) {
            if (choice == validInput) {
                valid = true;
                break;
            }
        }
        return valid;
    }

    // EFFECTS: displays the number of people present in the class (including teachers)
    public void takeAttendance() {
        myClass.takeAttendance();
        System.out.println("Attendance done! There are " + myClass.getDailyClassTotal() + " present today.");
    }

    // MODIFIES: this
    // EFFECTS: empty current class and notifies user.
    public void emptyClass() {
        myClass.emptyClass();
        System.out.println("Class is over! There are currently " + myClass.getDailyClassTotal() + " in this class!");
    }

    // MODIFIES: this
    // EFFECTS: shuts down program and notifies user
    public void quitApp() {
        System.out.println("Sunday School Attendance App is shutting down now. Goodbye!");
        keepGoing = false;
    }

    // EFFECTS: asks for the person's name
    public String getNameOfPerson() {
        System.out.println("What is the person's name? ");
        return input.next().toLowerCase();
    }

    // EFFECTS: displays the people in the class, if any. Teachers are listed first
    public void displayPeopleInClass() {
        if (myClass.getTeachers().size() == 0 && myClass.getStudents().size() == 0) {
            System.out.println("You have no people in your current class.");
        } else {
            System.out.println("These are the people in your current class:");
            for (Person teacher : myClass.getTeachers()) {
                System.out.println("Teacher: " + teacher.getName());
            }
            for (Person student : myClass.getStudents()) {
                System.out.println("Student: " + student.getName());
            }
        }
    }

    // EFFECTS: prints all the classes in the workroom
    private void printClasses() {
        List<SundaySchoolClass> classes = workRoom.getSundaySchoolClasses();
        System.out.println("These are your saved classes: ");
        for (SundaySchoolClass t : classes) {
            System.out.println(t.getClassName());
        }
    }

    // EFFECTS: pop-up panel asks user if they would like to load the names of the previous classes.
    public void askToSaveCurrentClass() {
        int answer = JOptionPane.showConfirmDialog(null, "Would you like to Save this class?");
        if (answer == 0) {
            saveClassRoom();
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the current class room to WorkRoom, plays audio after saving
    private void saveClassRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(workRoom);
            jsonWriter.close();
            playAudio.playAudio("C:\\Labs-210\\Week 1\\project_c1y2b\\data\\Music\\Missile_Launch Sound.wav");
            System.out.println("Saved " + workRoom.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads all classrooms from WorkRoom
    private void loadPreviousClassRoom() {
        try {
            workRoom = jsonReader.read();
            playAudio.playAudio("C:\\Labs-210\\Week 1\\project_c1y2b\\data\\Music\\Elevator Ding Sound.wav");
            System.out.println("Loaded " + workRoom.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    public void mainForJTree() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // TODO: take this line away
        HandleJTree newContentPane = new HandleJTree(myClass);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }

}
