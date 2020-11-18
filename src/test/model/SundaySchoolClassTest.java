package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Unit tests for the SundaySchoolClass class
 */
class SundaySchoolClassTest {
    SundaySchoolClass aClass;
    List<Person> teachers;
    List<Person> students;
    boolean present = true;
    String name = "general";
    Category category = Category.PRESCHOOL;

    @BeforeEach
    public void setUp() {
        aClass = new SundaySchoolClass(name, category);
        teachers = aClass.getTeachers();
        students = aClass.getStudents();
    }

    @Test
    public void testTakeAttendanceOfWithNoStudentAndNoTeacherPresent() {
        students.add(new Student("student1", !present));
        teachers.add(new Teacher("teacher1", !present));
        aClass.takeAttendance();
        assertEquals(0, aClass.getDailyClassTotal());
    }

    @Test
    public void testTakeAttendanceOfOneStudentAndOneTeacherPresent() {
        students.add(new Student("student1", present));
        teachers.add(new Teacher("teacher1", present));

        aClass.takeAttendance();
        assertEquals(2, aClass.getDailyClassTotal());
    }

    @Test
    public void testTakeAttendanceOfSomeStudentsAndSomeTeachersPresent() {
        students.add(new Student("student1", present));
        students.add(new Student("student2", !present));
        students.add(new Student("student3", present));
        students.add(new Student("student4", !present));

        teachers.add(new Teacher("teacher1", present));
        teachers.add(new Teacher("teacher2", !present));
        teachers.add(new Teacher("teacher3", present));
        teachers.add(new Teacher("teacher4", !present));
        aClass.takeAttendance();
        assertEquals(4, aClass.getDailyClassTotal());
    }

    @Test
    public void testTakeAttendanceOfAllStudentsAndAllTeachersPresent() {
        students.add(new Student("student1", present));
        students.add(new Student("student2", present));
        students.add(new Student("student3", present));
        students.add(new Student("student4", present));

        teachers.add(new Teacher("teacher1", present));
        teachers.add(new Teacher("teacher2", present));
        teachers.add(new Teacher("teacher3", present));
        teachers.add(new Teacher("teacher4", present));
        aClass.takeAttendance();
        assertEquals(8, aClass.getDailyClassTotal());
    }

    @Test
    public void testTeachersPresentInClassWhenNonePresent() {
        teachers.add(new Teacher("teacher1", !present));
        teachers.add(new Teacher("teacher2", !present));

        List<String> teachersPresent = aClass.teachersPresentInClass();
        assertEquals(0, teachersPresent.size());
    }

    @Test
    public void testTeachersPresentInClassWhenOnePresent() {
        teachers.add(new Teacher("teacher1", present));
        teachers.add(new Teacher("teacher2", !present));
        teachers.add(new Teacher("teacher4", !present));

        List<String> teachersPresent = aClass.teachersPresentInClass();
        assertEquals("teacher1", teachersPresent.get(0));
        assertEquals(1, teachersPresent.size());
    }

    @Test
    public void testTeachersPresentInClassWhenManyPresent() {
        teachers.add(new Teacher("teacher1", present));
        teachers.add(new Teacher("teacher2", !present));
        teachers.add(new Teacher("teacher3", present));
        teachers.add(new Teacher("teacher4", !present));

        List<String> teachersPresent = aClass.teachersPresentInClass();
        assertEquals("teacher1", teachersPresent.get(0));
        assertEquals("teacher3", teachersPresent.get(1));
        assertEquals(2, teachersPresent.size());
    }

    @Test
    public void testTeachersAbsentInClassWhenNoneAbsent() {
        teachers.add(new Teacher("teacher1", present));
        teachers.add(new Teacher("teacher2", present));

        List<String> teachersAbsent = aClass.teachersAbsentInClass();
        assertEquals(0, teachersAbsent.size());
    }

    @Test
    public void testTeachersAbsentInClassWhenOneAbsent() {
        teachers.add(new Teacher("teacher1", present));
        teachers.add(new Teacher("teacher2", present));
        teachers.add(new Teacher("teacher4", !present));

        List<String> teachersAbsent = aClass.teachersAbsentInClass();
        assertEquals("teacher4", teachersAbsent.get(0));
        assertEquals(1, teachersAbsent.size());
    }

    @Test
    public void testTeachersAbsentInClassWhenManyAbsent() {
        teachers.add(new Teacher("teacher1", present));
        teachers.add(new Teacher("teacher2", !present));
        teachers.add(new Teacher("teacher3", present));
        teachers.add(new Teacher("teacher4", !present));

        List<String> teachersAbsent = aClass.teachersAbsentInClass();
        assertEquals("teacher2", teachersAbsent.get(0));
        assertEquals("teacher4", teachersAbsent.get(1));
        assertEquals(2, teachersAbsent.size());
    }

    @Test
    public void testStudentsPresentInClassWhenNonePresent() {
        students.add(new Student("student1", !present));
        students.add(new Student("student2", !present));

        List<String> studentPresent = aClass.studentsPresentInClass();
        assertEquals(0, studentPresent.size());
    }

    @Test
    public void testStudentsPresentInClassWhenOnePresent() {
        students.add(new Student("student1", present));
        students.add(new Student("student2", !present));
        students.add(new Student("student3", !present));

        List<String> studentPresent = aClass.studentsPresentInClass();
        assertEquals("student1", studentPresent.get(0));
        assertEquals(1, studentPresent.size());
    }

    @Test
    public void testStudentsPresentInClassWhenManyPresent() {
        students.add(new Student("student1", present));
        students.add(new Student("student2", !present));
        students.add(new Student("student3", present));
        students.add(new Student("student4", !present));

        List<String> studentPresent = aClass.studentsPresentInClass();
        assertEquals("student1", studentPresent.get(0));
        assertEquals("student3", studentPresent.get(1));
        assertEquals(2, studentPresent.size());
    }

    @Test
    public void testStudentsAbsentInClassWhenNoneAbsent() {
        students.add(new Student("student1", present));
        students.add(new Student("student2", present));

        List<String> studentAbsent = aClass.studentsAbsentInClass();
        assertEquals(0, studentAbsent.size());
    }

    @Test
    public void testStudentsAbsentInClassWhenOneAbsent() {
        students.add(new Student("student1", present));
        students.add(new Student("student2", present));
        students.add(new Student("student3", !present));

        List<String> studentAbsent = aClass.studentsAbsentInClass();
        assertEquals("student3", studentAbsent.get(0));
        assertEquals(1, studentAbsent.size());
    }

    @Test
    public void testStudentsAbsentInClassWhenManyAbsent() {
        students.add(new Student("student1", present));
        students.add(new Student("student2", !present));
        students.add(new Student("student3", present));
        students.add(new Student("student4", !present));

        List<String> studentPresent = aClass.studentsAbsentInClass();
        assertEquals("student2", studentPresent.get(0));
        assertEquals("student4", studentPresent.get(1));
        assertEquals(2, studentPresent.size());
    }

    @Test
    public void testAddOneTeacherToClass() {
        assertEquals(0, teachers.size());
        aClass.addTeacherToClass(new Teacher("teacher1", present));
        assertEquals(1, teachers.size());
    }

    @Test
    public void testAddManyTeachersToClass() {
        assertEquals(0, teachers.size());
        aClass.addTeacherToClass(new Teacher("teacher1", present));
        aClass.addTeacherToClass(new Teacher("teacher2", !present));
        aClass.addTeacherToClass(new Teacher("teacher3", present));
        aClass.addTeacherToClass(new Teacher("teacher4", !present));
        assertEquals(4, teachers.size());
    }

    @Test
    public void testRemoveOneTeacherFromClassWhenNoTeacherInClass() {
        Teacher teacher1 = new Teacher("teacher1", present);
        assertEquals(0, teachers.size());
        aClass.removeTeacherFromClass("teacher1");
        assertEquals(0, teachers.size());
    }

    @Test
    public void testRemoveOneTeacherFromClassWhenOneTeacherInClass() {
        Teacher teacher1 = new Teacher("teacher1", present);
        teachers.add(teacher1);
        assertEquals(1, teachers.size());
        aClass.removeTeacherFromClass("teacher1");
        assertEquals(0, teachers.size());
    }

    @Test
    public void testRemoveManyTeachersFromClassWhenManyTeachersInClass() {
        Teacher teacher1 = new Teacher("teacher1", present);
        Teacher teacher2 = new Teacher("teacher2", !present);
        Teacher teacher3 = new Teacher("teacher3", present);
        Teacher teacher4 = new Teacher("teacher4", !present);
        teachers.add(teacher1);
        teachers.add(teacher2);
        teachers.add(teacher3);
        teachers.add(teacher4);
        assertEquals(4, teachers.size());
        aClass.removeTeacherFromClass("teacher3");
        aClass.removeTeacherFromClass("teacher1");
        assertEquals(2, teachers.size());
    }

    @Test
    public void testAddOneStudentsToClass() {
        assertEquals(0, students.size());
        aClass.addStudentToClass(new Student("student1", present));
        assertEquals(1, students.size());
    }

    @Test
    public void testAddManyStudentsToClass() {
        assertEquals(0, students.size());
        aClass.addStudentToClass(new Student("student1", present));
        aClass.addStudentToClass(new Student("student2", !present));
        aClass.addStudentToClass(new Student("student3", present));
        aClass.addStudentToClass(new Student("student4", !present));
        assertEquals(4, students.size());
    }

    @Test
    public void testRemoveOneStudentFromClassWhenNoStudentInClass() {
        Student student1 = new Student("student1", present);
        assertEquals(0, students.size());
        aClass.removeStudentFromClass("student1");
        assertEquals(0, students.size());
    }

    @Test
    public void testRemoveOneStudentFromClassWhenOneStudentInClass() {
        Student student1 = new Student("student1", present);
        assertEquals(0, students.size());
        students.add(student1);
        aClass.removeStudentFromClass("student1");
        assertEquals(0, students.size());
    }

    @Test
    public void testRemoveWrongStudentFromClassWhenOneStudentInClass() {
        Student student1 = new Student("student1", present);
        assertEquals(0, students.size());
        students.add(student1);
        assertEquals(1, students.size());
        aClass.removeStudentFromClass("student2");
        assertEquals(1, students.size());
    }

    @Test
    public void testRemoveOneStudentFromClassWhenManyTeachersInClass() {
        Student student1 = new Student("student1", present);
        Student student2 = new Student("student2", !present);
        Student student3 = new Student("student3", present);
        Student student4 = new Student("student4", !present);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        assertEquals(4, students.size());
        aClass.removeStudentFromClass("student1");
        aClass.removeStudentFromClass("student2");
        aClass.removeStudentFromClass("student3");
        assertEquals(1, students.size());
    }

    @Test
    public void testEmptyClassWhenNoStudentOrTeacherListedInClass() {
        assertEquals(0, students.size());
        assertEquals(0, teachers.size());
        aClass.emptyClass();
        assertEquals(0, students.size());
        assertEquals(0, teachers.size());
        assertEquals(0, aClass.getDailyClassTotal());
    }

    @Test
    public void testEmptyClassWhenOneStudentAndOneTeacherListedInClass() {
        students.add(new Student("student1", !present));
        teachers.add(new Teacher("teacher1", present));
        assertEquals(1, students.size());
        assertEquals(1, teachers.size());
        aClass.emptyClass();
        assertEquals(0, students.size());
        assertEquals(0, teachers.size());
        assertEquals(0, aClass.getDailyClassTotal());
    }

    @Test
    public void testEmptyClassWhenManyStudentAndTeachersListedInClass() {
        students.add(new Student("student1", present));
        students.add(new Student("student2", !present));
        students.add(new Student("student3", present));
        students.add(new Student("student4", !present));

        teachers.add(new Teacher("teacher1", present));
        teachers.add(new Teacher("teacher2", !present));
        teachers.add(new Teacher("teacher3", present));
        teachers.add(new Teacher("teacher4", !present));

        aClass.takeAttendance();
        int totalInClass = 8;
        assertEquals(4, students.size());
        assertEquals(4, teachers.size());
        assertEquals(4, aClass.getDailyClassTotal());

        aClass.emptyClass();
        assertEquals(0, students.size());
        assertEquals(0, teachers.size());
        assertEquals(0, aClass.getDailyClassTotal());
    }

    @Test
    public void testSetName() {
        teachers.add(new Teacher("t", true));
        students.add(new Student("s", true));
        assertEquals("t", teachers.get(0).getName());
        assertEquals("s", students.get(0).getName());
        teachers.get(0).setName("nt");
        students.get(0).setName("ns");
        Student newStudent = new Student("ns", true);
        assertEquals("nt", teachers.get(0).getName());
        assertEquals("ns", students.get(0).getName());


    }
}