package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SundaySchoolClassTest {
    SundaySchoolClass aClass;
    List<Person> teachers;
    List<Person> students;
    boolean present = true;

//        students.add(new Student("student1", present));
//        students.add(new Student("student2", !present));
//        students.add(new Student("student3", present));
//        students.add(new Student("student4", !present));
//
//        teachers.add(new Teacher("teacher1", present));
//        teachers.add(new Teacher("teacher2", !present));
//        teachers.add(new Teacher("teacher3", present));
//        teachers.add(new Teacher("teacher4", !present));

    @BeforeEach
    public void setUp() {
        aClass = new SundaySchoolClass();
        teachers = aClass.teachers;
        students = aClass.students;


    }

    @Test
    public void testTakeAttendanceOfNoStudent() {
        aClass.takeAttendance();
        assertEquals(0, aClass.dailyClassTotal);
    }

    @Test
    public void testTakeAttendanceOfOneStudent() {
        students.add(new Student("student1", present));
        aClass.takeAttendance();
        assertEquals(1, aClass.dailyClassTotal);
    }

    @Test
    public void testTakeAttendanceOfManyStudents() {
        students.add(new Student("student1", present));
        students.add(new Student("student2", !present));
        students.add(new Student("student3", present));
        students.add(new Student("student4", !present));
        aClass.takeAttendance();
        assertEquals(2, aClass.dailyClassTotal);
    }

    @Test
    public void testManyTeachersPresentInClassWhenNonePresent() {
        teachers.add(new Teacher("teacher2", !present));
        teachers.add(new Teacher("teacher4", !present));

        List<String> teachersPresent = aClass.teachersPresentInClass(present);
        assertEquals(0, teachersPresent.size());
    }

    @Test
    public void testManyTeachersPresentInClassWhenOnePresent() {
        teachers.add(new Teacher("teacher1", present));
        teachers.add(new Teacher("teacher2", !present));
        teachers.add(new Teacher("teacher4", !present));

        List<String> teachersPresent = aClass.teachersPresentInClass(present);
        assertEquals("teacher1", teachersPresent.get(0));
        assertEquals(1, teachersPresent.size());
    }

    @Test
    public void testTeachersPresentInClassWhenManyPresent() {
        teachers.add(new Teacher("teacher1", present));
        teachers.add(new Teacher("teacher2", !present));
        teachers.add(new Teacher("teacher3", present));
        teachers.add(new Teacher("teacher4", !present));

        List<String> teachersPresent = aClass.teachersPresentInClass(present);
        assertEquals("teacher1", teachersPresent.get(0));
        assertEquals("teacher3", teachersPresent.get(1));
        assertEquals(2, teachersPresent.size());
    }

}