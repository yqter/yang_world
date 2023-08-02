package fun.utree.InfoObj;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class FileToLocation {
    private Location location;

    public FileToLocation(Location location) {
        this.location = location;
    }

    public FileToLocation(String dataFromFile) {
        String[] strings = dataFromFile.split(" ");
        location = new Location(
                Bukkit.getWorld("world"),
                Double.parseDouble(strings[0]),
                Double.parseDouble(strings[1]),
                Double.parseDouble(strings[2]));
    }

    @Override
    public String toString() {
        return "%.2f %.2f %.2f".formatted(location.getX(), location.getY(), location.getZ());
    }

    public Location getLocation() {
        return location;
    }
}
