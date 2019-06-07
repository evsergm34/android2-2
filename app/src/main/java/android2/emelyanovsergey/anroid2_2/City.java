package android2.emelyanovsergey.anroid2_2;

public class City {
    private long ID;
    private String Name;
    private String Weather;
    private String UpToDate;

    public City(long ID, String name) {
        this.ID = ID;
        Name = name;
        Weather = "";
        UpToDate = "";
    }

    public City(long ID, String name, String weather) {
        this(ID, name);
        Weather = weather;
        UpToDate = "";
    }

    public City(long ID, String name, String weather, String upToDate) {
        this(ID, name, weather);
        UpToDate = upToDate;
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

    public String getWeather() {
        return Weather;
    }

    public void setWeather(String weather) {
        Weather = weather;
    }

    public String getUpToDate() {
        return UpToDate;
    }

    public void setUpToDate(String upToDate) {
        UpToDate = upToDate;
    }
}
