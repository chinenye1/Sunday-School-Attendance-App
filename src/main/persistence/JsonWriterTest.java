//package persistence;
//
//import model.Category;
//import model.SundaySchoolClass;
//import model.WorkRoom;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class JsonWriterTest extends persistence.JsonTest {
//    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
//    //write data to a file and then use the reader to read it back in and check that we
//    //read in a copy of what was written out.
//
//    @Test
//    void testWriterInvalidFile() {
//        try {
//            WorkRoom wr = new WorkRoom("My work room");
//            persistence.JsonWriter writer = new persistence.JsonWriter("./data/my\0illegal:fileName.json");
//            writer.open();
//            fail("IOException was expected");
//        } catch (IOException e) {
//            // pass
//        }
//    }
//
//    @Test
//    void testWriterEmptyWorkroom() {
//        try {
//            WorkRoom wr = new WorkRoom("My work room");
//            persistence.JsonWriter writer = new persistence.JsonWriter("./data/testWriterEmptyWorkroom.json");
//            writer.open();
//            writer.write(wr);
//            writer.close();
//
//            persistence.JsonReader reader = new persistence.JsonReader("./data/testWriterEmptyWorkroom.json");
//            wr = reader.read();
//            assertEquals("My work room", wr.getName());
//            assertEquals(0, wr.numClassrooms());
//        } catch (IOException e) {
//            fail("Exception should not have been thrown");
//        }
//    }
//
//    @Test
//    void testWriterGeneralWorkroom() {
//        try {
//            WorkRoom wr = new WorkRoom("My work room");
//            wr.addClassroom(new SundaySchoolClass("gradeOne", Category.ELEMENTARY));
//            wr.addClassroom(new SundaySchoolClass("gradeTen", Category.HIGHSCHOOL));
//            persistence.JsonWriter writer = new persistence.JsonWriter("./data/testWriterGeneralWorkroom.json");
//            writer.open();
//            writer.write(wr);
//            writer.close();
//
//            persistence.JsonReader reader = new persistence.JsonReader("./data/testWriterGeneralWorkroom.json");
//            wr = reader.read();
//            assertEquals("My work room", wr.getName());
//            List<SundaySchoolClass> classRooms = wr.getSundaySchoolClasses();
//            assertEquals(2, classRooms.size()
//            );
//            checkClassroom("gradeOne", Category.ELEMENTARY, classRooms.get(0));
//            checkClassroom("gradeTen", Category.HIGHSCHOOL, classRooms.get(1));
//
//        } catch (IOException e) {
//            fail("Exception should not have been thrown");
//        }
//    }
//}