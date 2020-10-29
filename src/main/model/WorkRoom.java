package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a workroom having a collection of thingies
public class WorkRoom implements Writable {
    private String name;
    private List<SundaySchoolClass> sundaySchoolClasses;

    // EFFECTS: constructs workroom with a name and empty list of classes
    public WorkRoom(String name) {
        this.name = name;
        sundaySchoolClasses = new ArrayList<SundaySchoolClass>();
    }

    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: adds a class to this workroom
    public void addClassroom(SundaySchoolClass classRoom) {
        sundaySchoolClasses.add(classRoom);
    }

    // EFFECTS: returns an unmodifiable list of thingies in this workroom
    public List<SundaySchoolClass> getSundaySchoolClasses() {
        return Collections.unmodifiableList(sundaySchoolClasses);
    }

    // EFFECTS: returns number of classes in this workroom
    public int numClassrooms() {
        return sundaySchoolClasses.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("classes", classesToJson());
        return json;
    }

    // EFFECTS: returns classes in this workroom as a JSON array
    private JSONArray classesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (SundaySchoolClass t : sundaySchoolClasses) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
