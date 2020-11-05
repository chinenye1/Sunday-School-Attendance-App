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
/* This class was modeled after ui.WorkRoomApp class in:
  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

 * This is an app that enables one to take attendance of the entire class. And save who was in class
 * User is able to add and remove people in the class, as well as to view the people in the class.
 */
public class AttendanceApp {
    //TODO: IMPROVE COHESIVENESS OF THIS CLASS BY CREATING OTHER CLASSES
    private SundaySchoolClass myClass;
    private Scanner input;
    private static final String JSON_STORE = "./data/workroom.json";
    private WorkRoom workRoom;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private boolean keepGoing = true;
    private boolean classLoaded = false;
    private List<String> validOptions;

    public enum ClassOperation {
        //TODO: TAKE AWAY ENUM CONSTANTS ARE AREN'T NEEDED
        ADD_PERSON, ADD_CLASS, REMOVE_PERSON, DISPLAY_PEOPLE_IN_CLASS, CHANGE_PEOPLE_IN_CLASS, EMPTY_CLASS,
        SAVE_WORKROOM, LOAD_WORKROOM, ADD_TEACHER, REMOVE_TEACHER, ADD_STUDENT, REMOVE_STUDENT;
    }

    //TODO: MAKE SURE ALL THE EFFECTS/REQUIRES/MODIFIES COMMENTS ARE UPDATED AND ACCURATE FOR ALL METHODS
    // EFFECTS: Creates an AttendanceApp, with new Scanner, correct JSON Store, and initiates the program
    public AttendanceApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        workRoom = new WorkRoom("Chi's workroom");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        startSundaySchool();
    }

    // MODIFIES: this
    // EFFECTS: initializes the entire use of the app.
    //          From the welcome message, all the way to the app termination.
    public void startSundaySchool() {
        welcomeMessage();
        input = new Scanner(System.in);
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

    // EFFECTS: displays an introductory message to user to let them know of the app's functionality
    public void welcomeMessage() {
        System.out.println("Welcome to Sunday School! Use this application to create a Sunday School Class.");
        System.out.println("Once you have a class, you can add/remove teachers, take attendance and to empty a class.");
    }

    // EFFECTS: prints all the classes in workroom to the console
    private void printClasses() {
        List<SundaySchoolClass> classes = workRoom.getSundaySchoolClasses();
        System.out.println("These are your classes: ");
        for (SundaySchoolClass t : classes) {
            System.out.println(t.getClassName());
        }
    }

    // EFFECTS: creates a new SundaySchoolClass at the request of the user.
    //          User will be asked twice to make a class before proceeding.
    //          If user refuses to make a class, the application will shut down.
    public void addAClass() {
        String nameOfClass = askForClassName();
        Category classCategory = askForClassCategory();
        myClass = new SundaySchoolClass(nameOfClass, classCategory);
        workRoom.addClassroom(myClass);
        System.out.println("Your class has been created, and is currently empty.");
    }

    // EFFECTS: Displays class operations options
    public void displayClassRoomOperationMenu() {
        System.out.println("What would you like to do with this class? "
                + "\n a -> Add/ Remove a Person "
                + "\n d -> Display People in Class"
                + "\n c -> Change Who's In Class"
                + "\n t -> Take Attendance"
                + "\n s -> Save Class Room"
                + "\n p -> Print previous classes"
                + "\n e -> Empty Current Class"
                + "\n q -> Quit App");
        List<String> validOptions = new ArrayList<>();
        validOptions.add("a");
        validOptions.add("d");
        validOptions.add("c");
        validOptions.add("t");
        validOptions.add("s");
        validOptions.add("p");
        validOptions.add("e");
        validOptions.add("q");
        operateClassRoom(input.next().toLowerCase(), validOptions);
    }

    // EFFECTS: checks if user input one of the offered menu options
    public boolean validChoice(String choice, List<String> validInputs) {
        boolean valid = false;
        for (String validInput : validInputs) {
            if (choice.equals(validInput)) {
                valid = true;
                break;
            }
        }
        return valid;
    }


    // EFFECTS: if user's choice is valid (one of offered choices)
    //           calls appropriate class operation based on user's choice.
    //          else user must enter a valid choice
    public void operateClassRoom(String choice, List<String> validOptions) {
        if (validChoice(choice, validOptions)) {
            currentClassOperations(choice);
            previousClassOperations(choice);
        } else {
            System.out.println("You must enter a valid option to proceed");
        }
    }

    // EFFECTS: completes operations concerning current class
    public void currentClassOperations(String choice) {
        switch (choice) {
            case "a":
            case "c":
                addOrRemovePeopleClassMenu();
                break;
            case "d":
                displayPeopleInClass();
                break;
            case "t":
                takeAttendance();
                break;
            case "s":
                saveWorkRoom();
                break;
            case "e":
                emptyClass();
                break;
            case "q":
                quitApp();
                break;
        }
    }

    // EFFECTS: completes operations concerning previous class
    public void previousClassOperations(String choice) {
        if (choice.equals("p")) {
            printClasses();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user if they would like to load a previous class or create a new one
    //          if so, the class will be loaded from the workroom
    public void loadOldOrCreateNewOrQuitMenu() {
        System.out.println("What would you like to do:"
                + "\n l -> Load a previous class?"
                + "\n c -> Create a new class"
                + "\n q -> Quit App");
        String answer = input.next().toLowerCase();
        handleChoice(answer);
    }

    // REQUIRES: answer is a valid choice/option
    // EFFECTS: either loads a new class, creates a new class or quits the app
    //          based on user's choice
    public void handleChoice(String answer) {
        validOptions = new ArrayList<>();
        validOptions.add("l");
        validOptions.add("c");
        validOptions.add("q");
        if (validChoice(answer, validOptions)) {
            switch (answer) {
                case "l":
                    tryLoadingPreviousClass();
                    break;
                case "c":
                    addAClass();
                    classLoaded = true;
                    break;
                case "q":
                    quitApp();
                    break;
            }
        } else {
            System.out.println("You must select a valid answer to continue.");
        }
    }

    // EFFECTS: loads previous class if class has not been loaded
    public void tryLoadingPreviousClass() {
        if (!classLoaded) {
            loadWorkRoom();
            classLoaded = true;
        } else {
            System.out.println("Your previous class has already been loaded."
                    + "\nPlease choose a different option");
        }
    }

    // MODIFIES: SundaySchoolClass instance
    // EFFECTS: Asks user for name of their class
    public String askForClassName() {
        System.out.println("What is the name of the class? ");
        return input.next();
    }

    // MODIFIES: SundaySchoolClass instance
    // EFFECTS: Asks user for name of the class category
    public Category askForClassCategory() {
        System.out.println("What category does this class fall under?"
                + "\n p -> PRESCHOOL"
                + "\n e -> ELEMENTARY"
                + "\n h -> HIGH SCHOOL"
                + "\n a -> ADULT");
        return createClassCategory(input.next().toLowerCase());
    }

    // EFFECTS: Maps user's input to corresponding category
    public Category createClassCategory(String categoryEntry) {
        switch (categoryEntry) {
            case "p":
                return Category.PRESCHOOL;
            case "e":
                return Category.ELEMENTARY;
            case "h":
                return Category.HIGHSCHOOL;
            case "a":
                return Category.ADULT;
            case "other":
                return Category.OTHER;
        }
        //TODO: call the validOptions method to make sure that only valid categories go through the cases.
        // then remove the return statement below
        return Category.OTHER;
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

    // MODIFIES: this
    // EFFECTS: asks user if they would like to change who is in their class by adding/removing people to/from the class
    //          if they say yes, the people are added/removed as requested
    public void addOrRemovePeopleClassMenu() {
        System.out.println("What would you like like to do?"
                + "\n at -> Add Teacher"
                + "\n rt -> Remove Teacher"
                + "\n as -> Add Student"
                + "\n rs -> Remove Student");
        validOptions = new ArrayList<>();
        // TODO: create a method that adds valid options by using substrings of a string of all the options and the num
        //  of characters each substring has.
        validOptions.add("at");
        validOptions.add("rt");
        validOptions.add("as");
        validOptions.add("rs");
        chooseOperation(input.next().toLowerCase(), validOptions);
    }

    // EFFECTS: maps user input to available options
    public void chooseOperation(String input, List<String> validOptions) {
        while (!validChoice(input, validOptions)) {
            System.out.println("You must select a valid option");
        }
        switch (input) {
            case "at":
                this.changeWhoIsInClass(ClassOperation.ADD_TEACHER);
                System.out.println("Person added to current class");
                break;
            case "rt":
                this.changeWhoIsInClass(ClassOperation.REMOVE_TEACHER);
                System.out.println("Person removed from current class");
                break;
            case "as":
                this.changeWhoIsInClass(ClassOperation.ADD_STUDENT);
                System.out.println("Person added to current class");
                break;
            case "rs":
                this.changeWhoIsInClass(ClassOperation.REMOVE_STUDENT);
                System.out.println("Person removed from current class");
                break;
        }
    }

    // EFFECTS: displays the number of people present in the class (including teachers)
    public void takeAttendance() {
        myClass.takeAttendance();
        System.out.println("Attendance done! There are " + myClass.getDailyClassTotal() + " present today.");
    }

    // MODIFIES: this
    // EFFECTS: empty current class, prints out message notifying it was done.
    public void emptyClass() {
        myClass.emptyClass();
        System.out.println("Class is over! There are currently " + myClass.getDailyClassTotal() + " in this class!");
    }

    // EFFECTS: user is notified that the application is shutting down.
    public void quitApp() {
        System.out.println("Sunday School Attendance App is shutting down now. Goodbye!");
        keepGoing = false;
    }


    // REQUIRES: operation is either "add" or "remove", typeOfPerson is either "teacher" or "student"
    // MODIFIES: this
    // EFFECTS: asks user who they would like to (add or remove) they would like to perform to people in the class.
    private void changeWhoIsInClass(ClassOperation change) {
        String personName = getNameOfPerson();
        switch (change) {
            case ADD_STUDENT:
                myClass.addStudentToClass(new Student(personName, true));
                break;
            case ADD_TEACHER:
                myClass.addTeacherToClass(new Student(personName, true));
                break;
            case REMOVE_STUDENT:
                myClass.removeStudentFromClass(personName);
                break;
            case REMOVE_TEACHER:
                myClass.removeTeacherFromClass(personName);
                break;
        }
    }

    // EFFECTS: asks for the person's name
    public String getNameOfPerson() {
        System.out.println("What is the person's name? ");
        return input.next().toLowerCase();
    }

    // EFFECTS: saves the workroom to file
    private void saveWorkRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(workRoom);
            jsonWriter.close();
            System.out.println("Saved " + workRoom.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWorkRoom() {
        try {
            workRoom = jsonReader.read();
            System.out.println("Loaded " + workRoom.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
