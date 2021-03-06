package ui;

import java.io.FileNotFoundException;

/**
 * This class was modeled after ui.Main class in:
 * //github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 * This class calls the SundaySchoolApp constructor, initiating the app
 */
public class Main {

    // EFFECTS: Initiates the AttendanceApp
    public static void main(String[] args) {
        try {
            new AttendanceApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }



}
