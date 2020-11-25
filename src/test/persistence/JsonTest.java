package persistence;

import model.Category;
import model.SundaySchoolClass;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 This class was modeled after the persistence.JsonTest class in: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 Abstracts away a repetitive method previously in JsonReaderTest and JsonReaderTest
 */
public class JsonTest {
    protected void checkClassroom(String name, Category category, SundaySchoolClass sundaySchoolClass) {
        assertEquals(name, sundaySchoolClass.getClassName());
        assertEquals(category, sundaySchoolClass.getClassCategory());
    }
}
