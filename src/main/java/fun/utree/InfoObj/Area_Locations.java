package fun.utree.InfoObj;

import org.bukkit.Location;

public class Area_Locations {
    private String areaName;

    private Location location_left;
    private Location location_right;

    public Area_Locations(String areaName, Location location_left, Location location_right) {
        this.areaName = areaName;
        this.location_left = location_left;
        this.location_right = location_right;
    }

    public String getAreaName() {
        return areaName;
    }

    public Location getLocation_left() {
        return location_left;
    }

    public Location getLocation_right() {
        return location_right;
    }
}
