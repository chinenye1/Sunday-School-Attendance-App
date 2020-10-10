package model;

import java.util.ArrayList;
import java.util.List;

// Represents a sunday school class with a teacher and a list of students; the class starts empty.
// Class behaviour entails typical activities in a sunday school class.
public class SundaySchoolClass {
    private static final int POINTS_NEEDED_FOR_AWARD = 150;
    private static final int NUM_OF_POINTS_FOR_BRINGING_A_FRIEND = 5;
    private static final int NUM_OF_POINTS_FOR_COMING = 1;
    private static final int NUM_OF_POINTS_FOR_SAYING_VERSE = 2;
    private static final int NUM_OF_POINTS_FOR_BRINGING_BIBLE = 1;
    private static final int NUM_OF_POINTS_FOR_SAYING_GOOD_BEHAVIOUR = 1;

    public String grade;
    public int dailyClassTotal;
    public List<Student> studentGettingAwards;
    public List<Student> students;
    public List<Teacher> teachers;

    // REQUIRES: 0 <= grade <= 13, where 0 means preschool age,
    //          and 13 means a level of college or higher
    // EFFECTS: creates a empty sunday school class with for a certain grade level
    public SundaySchoolClass(String grade) {
        this.grade = grade;
        dailyClassTotal = 0;
        students = new ArrayList<>();
        teachers = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: makes a list of student qualifying for a year-end award
    public void studentGettingAnAward() {
        for (Student s : students) {
            if (s.getTotalPoints() >= POINTS_NEEDED_FOR_AWARD) {
                studentGettingAwards.add(s);
            }
        }
    }

    // MODIFIES: student
    // EFFECTS: returns number of students present and stores that each student was present
    public void takeAttendance() {
        for (Student s : students) {
            if (s.isPresent) {
                dailyClassTotal++;
                s.numOfWeeksAttended++;
            }
        }
    }

    // MODIFIES: student
    // EFFECTS: gives student appropriate amount of points for
    //              coming
    //              bringing a friend
    //              bringing a Bible
    //              showing good behaviour in class
    //              saying their verse
    //          if they didn't do either of the above, they won't get the points for it
    public void recordDailyPoints() {
        for (Student s : students) {
            givePoints(s.isPresent, NUM_OF_POINTS_FOR_COMING);
            givePoints(s.broughtFriend, NUM_OF_POINTS_FOR_BRINGING_A_FRIEND);
            givePoints(s.broughtBible, NUM_OF_POINTS_FOR_BRINGING_BIBLE);
            givePoints(s.goodBehaviour, NUM_OF_POINTS_FOR_SAYING_GOOD_BEHAVIOUR);
            givePoints(s.saidVerse, NUM_OF_POINTS_FOR_SAYING_VERSE);
        }
    }

    // MODIFIES: student
    // EFFECTS: assigns appropriate points to student if they meet the criteria or reason for points
    public void givePoints(boolean reasonForPoints, int pointsGiven) {
        for (Student s : students) {
            if (reasonForPoints) {
                s.addToPoints(pointsGiven);
            }
        }
    }

    // MODIFIES: student
    // EFFECTS: student is given points for coming to sunday school
    public void storesWhoSaidVerse() {
        //stub
    }

    // MODIFIES: this
    // EFFECTS: adds teacher to list of teachers
    public void addTeacherToClass(Teacher teacher) {
        teachers.add(teacher);
    }

    // MODIFIES: this
    // EFFECTS: adds student to list of students
    public void addStudentToClass(Student student) {
        students.add(student);
    }

    // MODIFIES: this
    // EFFECTS: removes teacher from list of teachers
    public void removeTeacherFromClass(Teacher teacher) {
        teachers.remove(teacher);
    }

    // MODIFIES: this
    // EFFECTS: removes student from list of students
    public void removeStudentFromClass(Student student) {
        students.remove(student);
    }

    // MODIFIES: this
    // EFFECTS: empties list of students and teachers
    public void emptyClass() {
        students.clear();
        teachers.clear();
    }


}
