package persistence;

import model.Category;
import model.SundaySchoolClass;
import model.WorkRoom;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
 This class was modeled after the persistence.JsonReaderTest class in:
  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 Tests the JsonReader class
 */
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        persistence.JsonReader reader = new persistence.JsonReader("./data/noSuchFile.json");
        try {
            WorkRoom wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        persistence.JsonReader reader = new persistence.JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            WorkRoom wr = reader.read();
            assertEquals("My Classrooms", wr.getName());
            assertEquals(0, wr.numClassrooms());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            WorkRoom wr = reader.read();
            assertEquals("My Classrooms", wr.getName());
            List<SundaySchoolClass> classrooms = wr.getSundaySchoolClasses();
            assertEquals(2, classrooms.size());
            checkClassroom("preschoolGirls", Category.PRESCHOOL, classrooms.get(0));
            checkClassroom("grade10", Category.HIGHSCHOOL, classrooms.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }
}
