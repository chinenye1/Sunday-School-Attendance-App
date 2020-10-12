package model;

import java.util.ArrayList;
import java.util.List;

// Represents a sunday school class with a teacher and a list of students; the class starts empty.
// Class behaviour entails typical activities in a sunday school class.
public class SundaySchoolClass {
    public int dailyClassTotal;
    public List<Person> students;
    public List<Person> teachers;

    // EFFECTS: creates a empty sunday school class
    public SundaySchoolClass() {
        dailyClassTotal = 0;
        students = new ArrayList<>();
        teachers = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: returns number of students present today
    public void takeAttendance() {
        for (Person s : students) {
            if (s.getIsPresent()) {
                dailyClassTotal++;
            }
        }
        System.out.println("dailyClassTotal is:" + dailyClassTotal);
    }

    // EFFECTS: returns names of Teachers present in class today
    public List<String> teachersPresentInClass(boolean present) {
        return findPersonsPresent(teachers, present);
    }

    // EFFECTS: returns names of Teachers Absent in class today
    public List<String> teachersAbsentInClass(boolean absent) {
        return findPersonsAbsent(teachers, absent);
    }


    // EFFECTS: returns names of Students present in class today
    public List<String> studentsPresentInClass(boolean present) {
        return findPersonsPresent(students, present);
    }

    // EFFECTS: returns names of Teachers Absent in class today
    public List<String> studentsAbsentInClass(boolean absent) {
        return findPersonsAbsent(students, absent);
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
        dailyClassTotal = 0;
    }

    // EFFECTS: returns names of Persons that are present
    public List<String> findPersonsPresent(List<Person> persons, boolean meetsRequirement) {
        List<String> namesThatMeetRequirement = new ArrayList<>();
        for (Person p : persons) {
            if (p.getIsPresent()) {
                namesThatMeetRequirement.add(p.getName());
            }
        }
        return namesThatMeetRequirement;
    }

    // EFFECTS: returns names of names of Persons that are absent
    public List<String> findPersonsAbsent(List<Person> persons, boolean meetsRequirement) {
        List<String> namesThatMeetRequirement = new ArrayList<>();
        for (Person p : persons) {
            if (p.getIsPresent()) {
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
