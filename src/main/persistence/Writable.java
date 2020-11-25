package persistence;

import org.json.JSONObject;

/**
 This class was modeled after the persistence.Writable class in:
  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 Represents a Writable Interface; implemented in SundaySchoolClass
 */

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

