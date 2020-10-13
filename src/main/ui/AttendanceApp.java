package ui;

import model.Person;
import model.Student;
import model.SundaySchoolClass;
import model.Teacher;

import java.util.Scanner;

public class AttendanceApp {
    private SundaySchoolClass someClass = null;
    private Scanner input;
    private boolean keepAsking;

    public AttendanceApp() {
        startSundaySchool();
    }

    public void startSundaySchool() {
        welcomeMessage();
        keepAsking = true;
        String response = null;

        input = new Scanner(System.in);
        createAClass();
        if (someClass == null) {
            System.out.println("Seems like you don't want a class. Goodbye!");
        } else {
            addPersonsToClass("teacher");
            addPersonsToClass("student");

            displayPeopleInClass();
            changeWhoIsInClass();
//        takeAttendance();
//        checkWhoIsInClass();
//        emptyClass();
        }

    }

    public void welcomeMessage() {
        System.out.println("Welcome to Sunday School! Use this application to create a Sunday School Class.");
        System.out.println("once you have a class, you can add/remove teachers, take attendance and to empty a class.");
    }

    public void createAClass() {
        //int loop = 1;
        for (int loop = 1; loop < 3; loop++) {
            System.out.println("Would you like to create a Sunday School Class? \n y -> yes \n n -> no");
            if (input.next().toLowerCase().equals("y")) {
                someClass = new SundaySchoolClass();
                System.out.println("Your class has been created, and is currently empty.");
                break;
            } else {
                System.out.println("You must first make a class.");
            }
        }
    }

    public void addPersonsToClass(String typeOfPerson) {
        doThisOperationWithPersons("add", typeOfPerson);
    }

    public void removePersonsFromClass(String typeOfPerson) {
        doThisOperationWithPersons("remove", typeOfPerson);
    }

    public void displayPeopleInClass() {
        System.out.println("These are the people in your class:");
        for (Person teacher : someClass.getTeachers()) {
            System.out.println("Teacher: " + teacher.getName());
        }
        for (Person student : someClass.getStudents()) {
            System.out.println("Student: " + student.getName());
        }
    }

    public void changeWhoIsInClass() {
        System.out.println("Would you like to change who is in your class?");
        if (input.next().toLowerCase().equals("y")) {
            System.out.println("would like like to add/remove? \n a -> add \n r -> remove");
            if (input.next().toLowerCase().equals("a")) {
                addPersonsToClass("teacher");
                addPersonsToClass("student");
            } else if (input.next().toLowerCase().equals("r")) {
                removePersonsFromClass("teacher");
                removePersonsFromClass("student");
            }
        }
    }

    // REQUIRES: operation is either "add" or "remove", typeOfPerson is either "teacher" or "student"
    // EFFECTS: asks user what operation(add or remove) they would like to perform to people in the class.
    public void doThisOperationWithPersons(String operation, String typeOfPerson) {
        System.out.println("Would you like to " + operation + " a " + typeOfPerson
                + " to the class? \n y -> yes \n n -> no");
        String wantsToAddPersonsToClass = input.next().toLowerCase();
        int countNumPeople = 1;

        if (wantsToAddPersonsToClass.equals("y")) {
            System.out.println("What is " + typeOfPerson + "#" + countNumPeople + "'s name?");
            doThisOperationWithPerson(operation, input.next().toLowerCase(), typeOfPerson);
            while (keepAsking) {
                System.out.println("Would you like " + operation + " another " + typeOfPerson
                        + " to the class? \n y -> yes \n n -> no");
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
    // EFFECTS: applies operation(adds or removes) person to or from the class
    //          checks if the person is a teacher or student then applies the operations it to class appropriately
    public void doThisOperationWithPerson(String operation, String name, String typeOfPerson) {
        if (operation.equals("add")) {
            if (typeOfPerson.equals("teacher")) {
                someClass.addTeacherToClass(new Teacher(name, true));
                new Teacher(typeOfPerson, true);
            } else if (typeOfPerson.equals("student")) {
                someClass.addStudentToClass(new Student(name, true));
            }
        } else {
            if (typeOfPerson.equals("teacher")) {
                someClass.removeTeacherFromClass(new Teacher(name, true));
                new Teacher(typeOfPerson, true);
            } else if (typeOfPerson.equals("student")) {
                someClass.removeStudentFromClass(new Student(name, true));
            }
        }

    }


}
