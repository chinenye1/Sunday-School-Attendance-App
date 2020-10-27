package ui;

import model.Person;
import model.Student;
import model.SundaySchoolClass;
import model.Teacher;

import java.util.Scanner;

/*
 * This is an app that enables one to take attendance of the entire class.
 * User is able to add and remove people in the class, as well as to view the people in the class.
 */
public class AttendanceApp {
    private SundaySchoolClass myClass = null;
    private Scanner input;
    private boolean keepAsking = true;

    public AttendanceApp() {
        startSundaySchool();
    }

    // MODIFIES: this
    // EFFECTS: initializes the entire use of the app. From the welcome message, all the way to the app termination.
    //          Note: if near the end user wants to start over, the app will start again (near the beginning),
    //          and all class information will be preserved
    public void startSundaySchool() {
        welcomeMessage();
        boolean wantsToStartOver = true;
        input = new Scanner(System.in);
        createAClass();

        if (myClass == null) {
            System.out.println("Seems like you don't want a class. Goodbye!");
        } else {
            while (wantsToStartOver) {
                addPersonsToClass("teacher");
                addPersonsToClass("student");
                displayPeopleInClass();
                changeWhoIsInClass();
                takeAttendance();
                displayPeopleInClass();
                emptyClass();
                if (startOver()) {
                    wantsToStartOver = true;
                } else {
                    wantsToStartOver = false;
                }
            }
        }
        stopUsingApp();
    }

    // EFFECTS: displays an introductory message to user to let them know of the app's functionality
    public void welcomeMessage() {
        System.out.println("Welcome to Sunday School! Use this application to create a Sunday School Class.");
        System.out.println("Once you have a class, you can add/remove teachers, take attendance and to empty a class.");
    }

    // EFFECTS: creates a new SundaySchoolClass at the request of the user.
    //          User will be asked twice to make a class before proceeding.
    //          If user refuses to make a class, the application will shut down.
    public void createAClass() {
        for (int loop = 1; loop < 3; loop++) {
            System.out.println("Would you like to create a Sunday School Class? \n y -> yes \n n -> no");
            if (input.next().toLowerCase().equals("y")) {
                myClass = new SundaySchoolClass();
                System.out.println("Your class has been created, and is currently empty.");
                break;
            } else {
                System.out.println("You must first make a class.");
            }
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

}
