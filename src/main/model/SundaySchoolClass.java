package model;

import persistence.Writable;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents a sunday school class with a teacher and a list of students; the class starts empty.
 * Class behaviour entails typical activities in a sunday school class.
 */
public class SundaySchoolClass implements Writable {
    private static final boolean IS_PRESENT = true;

    private String className;
    private Category category;
    private int dailyClassTotal;
    private List<Person> students;
    private List<Person> teachers;

    // EFFECTS: creates a empty sunday school class
    public SundaySchoolClass(String name, Category category) {
        this.dailyClassTotal = 0;
        this.students = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.className = name;
        this.category = category;
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
        return findPersonThatMeetsRequirement(teachers, IS_PRESENT);
    }

    // EFFECTS: returns names of Teachers Absent in class today
    public List<String> teachersAbsentInClass() {
        return findPersonThatMeetsRequirement(teachers, !IS_PRESENT);
    }


    // EFFECTS: returns names of Students present in class today
    public List<String> studentsPresentInClass() {
        return findPersonThatMeetsRequirement(students, IS_PRESENT);
    }

    // EFFECTS: returns names of Teachers Absent in class today
    public List<String> studentsAbsentInClass() {
        return findPersonThatMeetsRequirement(students, !IS_PRESENT);
    }

    // MODIFIES: this
    // EFFECTS: adds teacher to list of teachers
    public void addTeacherToClass(Person teacher) {
        teachers.add(teacher);
    }

    // MODIFIES: this
    // EFFECTS: adds student to list of students
    public void addStudentToClass(Person student) {
        students.add(student);
    }

    // REQUIRES: teachers isn't empty
    // MODIFIES: this
    // EFFECTS: removes teacher from list of teachers
    public void removeTeacherFromClass(Person teacher) {
        teachers.remove(teacher);
    }

    // REQUIRES: students isn't empty
    // MODIFIES: this
    // EFFECTS: removes student from list of students
    public void removeStudentFromClass(Person student) {
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
    private List<String> findPersonThatMeetsRequirement(List<Person> persons, boolean isPresent) {
        List<String> namesThatMeetRequirement = new ArrayList<>();
        for (Person p : persons) {
            if (p.getIsPresent() == isPresent) {
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

    public String getClassName() {
        return className;
    }

    public Category getClassCategory() {
        return category;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", className);
        json.put("category", category);
        return json;
    }

}
