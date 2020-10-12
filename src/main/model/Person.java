package model;

public abstract class Person {
    private String name;
    private boolean isPresent;


    // EFFECTS: creates a Person with specified name
    public Person(String name, boolean present) {
        this.name = name;
        this.isPresent = present;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsPresent() {
        return isPresent;
    }
}
