package ui;

import model.Person;
import model.Student;
import model.SundaySchoolClass;
import model.Teacher;

import java.util.Scanner;

public class AttendanceApp {
    private SundaySchoolClass myClass = null;
    private Scanner input;
    private boolean keepAsking;

    public AttendanceApp() {
        startSundaySchool();
    }

    public void startSundaySchool() {
        welcomeMessage();
        keepAsking = true;

        input = new Scanner(System.in);
        createAClass();
        if (myClass == null) {
            System.out.println("Seems like you don't want a class. Goodbye!");
        } else {
            addPersonsToClass("teacher");
            addPersonsToClass("student");

            displayPeopleInClass();
            changeWhoIsInClass();
            takeAttendance();
            displayPeopleInClass();
            emptyClass();
            startOver();
            stopUsingApp();
        }

    }

    public void welcomeMessage() {
        System.out.println("Welcome to Sunday School! Use this application to create a Sunday School Class.");
        System.out.println("Once you have a class, you can add/remove teachers, take attendance and to empty a class.");
    }

    public void createAClass() {
        //int loop = 1;
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

    public void addPersonsToClass(String typeOfPerson) {
        doThisOperationWithPersons("add", typeOfPerson);
    }

    public void removePersonsFromClass(String typeOfPerson) {
        doThisOperationWithPersons("remove", typeOfPerson);
    }

    public void displayPeopleInClass() {
        System.out.println("These are the people in your class:");
        for (Person teacher : myClass.getTeachers()) {
            System.out.println("Teacher: " + teacher.getName());
        }
        for (Person student : myClass.getStudents()) {
            System.out.println("Student: " + student.getName());
        }
    }

    public void changeWhoIsInClass() {
        System.out.println("Would you like to change who is in your class? \n y -> yes n -> no");
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

    public void takeAttendance() {
        System.out.println("would you like to take attendance and find out how many (including teachers) "
                + "are present in class? \n y -> yes \nn -> no");
        if (input.next().toLowerCase().equals("y")) {
            myClass.takeAttendance();
            System.out.println("Attendance done! There are " + myClass.getDailyClassTotal() + " present today.");
        }
    }

    public void emptyClass() {
        System.out.println("Would you like to empty your classroom? \n y -> yes n -> no");
        if (input.next().toLowerCase().equals("y")) {
            myClass.emptyClass();
            System.out.println("Class is over! There are " + myClass.getDailyClassTotal() + " currently in class!");
        }
    }

    public void startOver() {
        System.out.println("Would you like to start all over? \n y -> yes n -> no");
        if (input.next().toLowerCase().equals("y")) {
            startSundaySchool();
        }  else {
            System.out.println("You have no more options.");
        }
    }

    public void stopUsingApp() {
        System.out.println("Sunday School Attendance App is shutting down now. Goodbye!");
    }


    // REQUIRES: operation is either "add" or "remove", typeOfPerson is either "teacher" or "student"
    // EFFECTS: asks user what operation(add or remove) they would like to perform to people in the class.
    public void doThisOperationWithPersons(String operation, String typeOfPerson) {
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
    // EFFECTS: applies operation(adds or removes) person to or from the class
    //          checks if the person is a teacher or student then applies the operations appropriately
    public void doThisOperationWithPerson(String operation, String name, String typeOfPerson) {
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
