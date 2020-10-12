package model;

import java.util.ArrayList;
import java.util.List;

// Represents a sunday school class with a teacher and a list of students; the class starts empty.
// Class behaviour entails typical activities in a sunday school class.
public class SundaySchoolClass {
    private int dailyClassTotal;
    private List<Person> students;
    private List<Person> teachers;

    // EFFECTS: creates a empty sunday school class
    public SundaySchoolClass() {
        dailyClassTotal = 0;
        students = new ArrayList<>();
        teachers = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: returns number of students and teachers present today
    public void takeAttendance() {
        for (Person s : students) {
            if (s.getIsPresent()) {
                dailyClassTotal++;
            }
        }
        for (Person t : teachers) {
            if (t.getIsPresent()) {
                dailyClassTotal++;
            }
        }
    }

    // EFFECTS: returns names of Teachers present in class today
    public List<String> teachersPresentInClass() {
        return findPersonsPresent(teachers);
    }

    // EFFECTS: returns names of Teachers Absent in class today
    public List<String> teachersAbsentInClass() {
        return findPersonsAbsent(teachers);
    }


    // EFFECTS: returns names of Students present in class today
    public List<String> studentsPresentInClass() {
        return findPersonsPresent(students);
    }

    // EFFECTS: returns names of Teachers Absent in class today
    public List<String> studentsAbsentInClass() {
        return findPersonsAbsent(students);
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
    // EFFECTS: empties list of students and teachers,
    //          and resets daily class total of students
    public void emptyClass() {
        students.clear();
        teachers.clear();
        dailyClassTotal = 0;
    }

    // EFFECTS: returns names of Persons that are present
    private List<String> findPersonsPresent(List<Person> persons) {
        List<String> namesThatMeetRequirement = new ArrayList<>();
        for (Person p : persons) {
            if (p.getIsPresent() == true) {
                namesThatMeetRequirement.add(p.getName());
            }
        }
        return namesThatMeetRequirement;
    }

    // EFFECTS: returns names of names of Persons that are absent
    private List<String> findPersonsAbsent(List<Person> persons) {
        List<String> namesThatMeetRequirement = new ArrayList<>();
        for (Person p : persons) {
            if (p.getIsPresent() == false) {
                namesThatMeetRequirement.add(p.getName());
            }
        }
        return namesThatMeetRequirement;
    }

    public int getDailyClassTotal() {
        return dailyClassTotal;
    }

    public List<Person> getStudents() {
        return students;
    }

    public List<Person> getTeachers() {
        return teachers;
    }
}
