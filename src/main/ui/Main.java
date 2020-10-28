package ui;

import java.io.FileNotFoundException;

/*
 * This class calls the SundaySchoolApp constructor, initiating the app
 */
public class Main {
    public static void main(String[] args) {
        try {
            new AttendanceApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
