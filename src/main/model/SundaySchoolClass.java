package model;

import java.util.ArrayList;
import java.util.List;

// Represents a sunday school class with a teacher and a list of students; the class starts empty.
// Class behaviour entails typical activities in a sunday school class.
public class SundaySchoolClass {
    private static final boolean PRESENT = true; // used as a true or false flag

    public int dailyClassTotal;
    public List<Student> students;
    public List<Teacher> teachers;

    // EFFECTS: creates a empty sunday school class
    public SundaySchoolClass(String grade) {
        dailyClassTotal = 0;
        students = new ArrayList<>();
        teachers = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: returns number of students present today
    public void takeAttendance() {
        for (Student s : students) {
            if (s.getIsPresent()) {
                dailyClassTotal++;
            }
        }
    }

    // EFFECTS: returns names of Teachers present in class today
    public List<String> teachersPresentInClass() {
        List<String> namesPresent = findTeachersThatMeetRequirement(PRESENT);
        return namesPresent;
    }

    // EFFECTS: returns names of Teachers Absent in class today
    public List<String> teachersAbsentInClass() {
        List<String> namesAbsent = findTeachersThatMeetRequirement(!PRESENT);
        return namesAbsent;
    }


    // EFFECTS: returns names of Students present in class today
    public List<String> studentsPresentInClass() {
        List<String> namesPresent = findStudentsThatMeetRequirement(PRESENT);
        return namesPresent;
    }

    // EFFECTS: returns names of Teachers Absent in class today
    public List<String> studentsAbsentInClass() {
        List<String> namesAbsent = findStudentsThatMeetRequirement(!PRESENT);
        return namesAbsent;
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

    // EFFECTS: returns names of Teachers that meets the requirement
    public List<String> findTeachersThatMeetRequirement(boolean meetsRequirement) {
        List<String> namesThatMeetRequirement = new ArrayList<>();
        for (Teacher t : teachers) {
            if (meetsRequirement) {
                namesThatMeetRequirement.add(t.getName());
            }
        }
        return namesThatMeetRequirement;
    }

    // EFFECTS: returns names of Students that meets the requirement
    public List<String> findStudentsThatMeetRequirement(boolean meetsRequirement) {
        List<String> namesThatMeetRequirement = new ArrayList<>();
        for (Student s : students) {
            if (meetsRequirement) {
                namesThatMeetRequirement.add(s.getName());
            }
        }
        return namesThatMeetRequirement;
    }

    public int getDailyClassTotal() {
        return dailyClassTotal;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

}
