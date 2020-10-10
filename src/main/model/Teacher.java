package model;

// Represents a sunday school teacher
public class Teacher {

    private String name;
    private int address;
    private boolean canDrive;

    public Teacher(String name, int address, boolean canDrive) {
        this.name = name;
        this.address = address;
        this.canDrive = canDrive;
    }

    public String getName() {
        return name;
    }

    public int getAddress() {
        return address;
    }

    public boolean getCanDrive() {
        return canDrive;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public void setCanDrive(boolean canDrive) {
        this.canDrive = canDrive;
    }


}
