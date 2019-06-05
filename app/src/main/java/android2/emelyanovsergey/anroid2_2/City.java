package android2.emelyanovsergey.anroid2_2;

public class City {
    private long ID;
    private String Name;

    public City() {
    }

    public City(long ID, String name) {
        this.ID = ID;
        Name = name;
    }

    public long getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        Name = name;
    }
}
