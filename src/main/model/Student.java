package model;

// Represents a student in a sunday school class
public class Student {
    private String sex;
    private int address;
    private int totalPoints;
    public String name;
    public int age;
    public int numOfWeeksAttended;
    public boolean saidVerse;
    public boolean broughtBible;
    public boolean broughtFriend = false;
    public boolean goodBehaviour;
    public boolean isPresent;
    private boolean givenReleaseConsent;

    // REQUIRES: age >=0, sex == "Male" or "Female", address >= 0
    // EFFECTS: creates a student with specified name, sex, age, address (only digits)
    //          and whether video and picture release consent has been given.
    //         Student starts out with no points, and zero track record for attendance.
    public Student(String name, String sex, int age, int address, boolean consent) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.address = address;
        this.givenReleaseConsent = consent;
        totalPoints = 0;
        numOfWeeksAttended = 1;
    }

    // REQUIRES: points >= 0
    // MODIFIES: this
    // EFFECTS: adds points to student's total points
    public void addToPoints(int points) {
        this.totalPoints += points;
    }

    public String getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public boolean isGivenReleaseConsent() {
        return givenReleaseConsent;
    }

    public void setGivenReleaseConsent(boolean givenReleaseConsent) {
        this.givenReleaseConsent = givenReleaseConsent;
    }
}
