package ui;

import model.*;

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
    private SundaySchoolClass myClass;
    private Scanner input;
    private boolean keepAsking = true;
    private static final String JSON_STORE = "./data/workroom.json";
    private WorkRoom workRoom;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private boolean wantsToStartOver = true;

    public AttendanceApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        workRoom = new WorkRoom("Chi's workroom");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        startSundaySchool();
    }

    // MODIFIES: this
    // EFFECTS: initializes the entire use of the app. From the welcome message, all the way to the app termination.
    //          Note: if near the end user wants to start over, the app will start again (near the beginning),
    //          and all class information will be preserved
    public void startSundaySchool() {
        welcomeMessage();
        wantsToStartOver = true;
        input = new Scanner(System.in);
        chooseLoadOrCreateClass();
        runWorkRoom();

        if (myClass == null) {
            System.out.println("Seems like you don't want a class. Goodbye!");
        } else {
            operateClassRoom();
        }
        stopUsingApp();
    }


    // MODIFIES: this
    // EFFECTS: processes user input
    private void runWorkRoom() {
        String command = null;
        input = new Scanner(System.in);

        while (true) {
            displayMenu();
            command = input.next().toLowerCase();
            if (command.equals("a")) {
                addAClass();
                break;
            } else if (command.equals("p")) {
                printClasses();
            } else if (command.equals("q")) {
                break;
            } else {
                System.out.println("Selection not valid...");
            }
        }
        operateClassRoom();

    }

    // EFFECTS: displays an introductory message to user to let them know of the app's functionality
    public void welcomeMessage() {
        System.out.println("Welcome to Sunday School! Use this application to create a Sunday School Class.");
        System.out.println("Once you have a class, you can add/remove teachers, take attendance and to empty a class.");
    }

    // EFFECTS: displays menu of options for adding and storing classes to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add a class");
        System.out.println("\tp -> print classes stored");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: prints all the classes in workroom to the console
    private void printClasses() {
        List<SundaySchoolClass> classes = workRoom.getSundaySchoolClasses();

        for (SundaySchoolClass t : classes) {
            System.out.println(t.getClassName());
        }
    }

    // EFFECTS: creates a new SundaySchoolClass at the request of the user.
    //          User will be asked twice to make a class before proceeding.
    //          If user refuses to make a class, the application will shut down.
    public void addAClass() {
        String nameOfClass;
        Category classCategory;
        for (int i = 0; i < 2; i++) {
            System.out.println("Would you like to add a Sunday School Class? \n y -> yes \n n -> no");
            if (input.next().toLowerCase().equals("y")) {
                nameOfClass = askForClassName();
                classCategory = askForClassCategory();
                myClass = new SundaySchoolClass(nameOfClass, classCategory);
                workRoom.addClassroom(myClass);
                System.out.println("Your class has been created, and is currently empty.");
                break;
            } else {
                System.out.println("You must first make a class.");
                stopUsingApp();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to interact with the current class
    public void operateClassRoom() {
        while (wantsToStartOver) {
            addPersonsToClass("teacher");
            addPersonsToClass("student");
            displayPeopleInClass();
            changeWhoIsInClass();
            takeAttendance();
            emptyClass();
            askToSaveWorkroom();
            if (!startOver()) {
                wantsToStartOver = false;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user if they would like to save the current class
    //          if so, the class will be save to the workroom
    public void askToSaveWorkroom() {
        System.out.println("Would you like to save this class? \n y -> yes \n n -> no");
        if (input.next().toLowerCase().equals("y")) {
            saveWorkRoom();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user if they would like to load a previous class
    //          if so, the class will be loaded from the workroom
    public void chooseLoadOrCreateClass() {
        System.out.println("Would you like to load a previous class? \n y -> yes \n n -> no");
        if (input.next().toLowerCase().equals("y")) {
            loadWorkRoom();
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
                + "\n h -> HIGHSCHOOL"
                + "\n a -> ADULT");
        return createClassCategory(input.next().toLowerCase());
    }

    // EFFECTS: Maps user's input to corresponding category
    public Category createClassCategory(String categoryEntry) {
        if (categoryEntry.equals("p")) {
            return Category.PRESCHOOL;
        } else if (categoryEntry.equals("e")) {
            return Category.ELEMENTARY;
        } else if (categoryEntry.equals("h")) {
            return Category.HIGHSCHOOL;
        } else if (categoryEntry.equals("a")) {
            return Category.ADULT;
        } else {
            return Category.OTHER;
        }
    }

    // REQUIRES: typeOfPerson must be "teacher" or "student"
    // MODIFIES: this
    // EFFECTS: adds specified type of person to the class
    public void addPersonsToClass(String typeOfPerson) {
        doThisOperationWithPersons("add", typeOfPerson);
    }

    // REQUIRES: typeOfPerson must be "teacher" or "student"
    // MODIFIES: this
    // EFFECTS: removes specified type of person from the class
    public void removePersonsFromClass(String typeOfPerson) {
        doThisOperationWithPersons("remove", typeOfPerson);
    }

    // EFFECTS: displays the people in the class, if any. Teachers are listed first
    public void displayPeopleInClass() {
        if (myClass.getTeachers().size() == 0 && myClass.getStudents().size() == 0) {
            System.out.println("You have no people in your class.");
        } else {
            System.out.println("These are the people in your class:");
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
    public void changeWhoIsInClass() {
        System.out.println("Would you like to change who is in your class? \n y -> yes \n n -> no");
        if (input.next().toLowerCase().equals("y")) {
            System.out.println("would like like to add/remove? \n a -> add \n r -> remove");
            String response = input.next().toLowerCase();
            if (response.equals("a")) {
                addPersonsToClass("teacher");
                addPersonsToClass("student");
            } else if (response.equals("r")) {
                removePersonsFromClass("teacher");
                removePersonsFromClass("student");
            }
        }
    }

    // EFFECTS: displays the number of people present in the class (including teachers)
    public void takeAttendance() {
        System.out.println("Would you like to take attendance and find out how many (including teachers) "
                + "are present in class? \n y -> yes \nn -> no");
        if (input.next().toLowerCase().equals("y")) {
            myClass.takeAttendance();
            System.out.println("Attendance done! There are " + myClass.getDailyClassTotal() + " present today.");
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user if they would like to clear their class by removing everyone in it
    //          if they say yes, everyone is removed, the results are displayed, and the class total is reset to 0.
    public void emptyClass() {
        System.out.println("Would you like to empty your classroom? \n y -> yes n -> no");
        if (input.next().toLowerCase().equals("y")) {
            myClass.emptyClass();
            System.out.println("Class is over! There are " + myClass.getDailyClassTotal() + " currently in class!");
        }
    }

    // MODIFIES: this
    // EFFECTS: asks and returns whether user would like to start from the beginning of the app
    public boolean startOver() {
        System.out.println("Would you like to start all over? \n y -> yes n -> no");
        if (input.next().toLowerCase().equals("y")) {
            return true;
        } else {
            System.out.println("You have no more options.");
            return false;
        }
    }

    // EFFECTS: user is notified that the application is shutting down.
    public void stopUsingApp() {
        System.out.println("Sunday School Attendance App is shutting down now. Goodbye!");
    }


    // REQUIRES: operation is either "add" or "remove", typeOfPerson is either "teacher" or "student"
    // MODIFIES: this
    // EFFECTS: asks user what operation(add or remove) they would like to perform to people in the class.
    private void doThisOperationWithPersons(String operation, String typeOfPerson) {
        System.out.println("Would you like to " + operation + " a " + typeOfPerson
                + "? \n y -> yes \n n -> no");
        String whatUserWantsToDo = input.next().toLowerCase();
        int countNumPeople = 1;

        if (whatUserWantsToDo.equals("y")) {
            System.out.println("What is " + typeOfPerson + "#" + countNumPeople + "'s name?");
            doThisOperationWithPerson(operation, input.next().toLowerCase(), typeOfPerson);
            while (keepAsking) {
                System.out.println("Would you like " + operation + " another " + typeOfPerson
                        + "? \n y -> yes \n n -> no");
                countNumPeople++;
                if (input.next().toLowerCase().equals("y")) {
                    System.out.println("What is " + typeOfPerson + "#" + countNumPeople + "'s name?");
                    doThisOperationWithPerson(operation, input.next().toLowerCase(), typeOfPerson);
                } else {
                    break;
                }
            }
        }
    }


    // REQUIRES: operation is either "add" or "remove", typeOfPerson is either "teacher" or "student"
    // MODIFIES: this
    // EFFECTS: applies operation(adds or removes) person to or from the class.
    //          checks if the person is a teacher or student then applies the operations appropriately
    private void doThisOperationWithPerson(String operation, String name, String typeOfPerson) {
        if (operation.equals("add")) {
            if (typeOfPerson.equals("teacher")) {
                myClass.addTeacherToClass(new Teacher(name, true));
                //new Teacher(typeOfPerson, true);
            } else if (typeOfPerson.equals("student")) {
                myClass.addStudentToClass(new Student(name, true));
            }
        } else if (operation.equals("remove")) {
            if (typeOfPerson.equals("teacher")) {
                for (Person teacher : myClass.getTeachers()) {
                    if (teacher.getName().equals(name)) {
                        myClass.removeTeacherFromClass(teacher);
                        break;
                    }
                }
            } else if (typeOfPerson.equals("student")) {
                for (Person student : myClass.getStudents()) {
                    if (student.getName().equals(name)) {
                        myClass.removeStudentFromClass(student);
                        break;
                    }
                }
            }
        }
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
