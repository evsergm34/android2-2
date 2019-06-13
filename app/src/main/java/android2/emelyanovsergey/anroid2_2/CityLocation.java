package android2.emelyanovsergey.anroid2_2;

import android.location.Location;

public class CityLocation {
    String cityName;
    Location locationCenter;
    Location locationCorner;

    public CityLocation(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Location getLocationCenter() {
        return locationCenter;
    }

    public void setLocationCenter(Location locationCenter) {
        this.locationCenter = locationCenter;
    }

    public Location getLocationCorner() {
        return locationCorner;
    }

    public void setLocationCorner(Location locationCorner) {
        this.locationCorner = locationCorner;
    }
}
