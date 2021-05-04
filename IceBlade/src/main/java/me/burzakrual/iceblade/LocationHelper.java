package me.burzakrual.iceblade;

import java.util.Random;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LocationHelper {
    public static Location offsetLocation(Location startLocation, Vector offset) {
        return new Location(
                startLocation.getWorld(),
                startLocation.getX() + offset.getX(),
                startLocation.getY() + offset.getY(),
                startLocation.getZ() + offset.getZ());
    }

    public static Location validateLocation(Location location) {
        Location validatedLocation = location;
        while (validatedLocation.getBlock().getType().isSolid())
            validatedLocation.setY(validatedLocation.getY() + 1.0D);
        while (!offsetLocation(validatedLocation, new Vector(0, -1, 0)).getBlock().getType().isSolid())
            validatedLocation.setY(validatedLocation.getY() - 1.0D);
        return validatedLocation;
    }

    public static Location getRandomNearbyPosition(Location startLocation, int range) {
        Random rdm = new Random();
        Location randomLocation = startLocation;
        Location validatedRandomLocation = null;
        while (validatedRandomLocation == null ||
                validatedRandomLocation.distance(startLocation) > (range * 2) ||
                validatedRandomLocation.getBlock().getType().isSolid()) {
            Vector offset = new Vector(
                    rdm.nextInt(range * 2) - range,
                    rdm.nextInt(range * 2) - range,
                    rdm.nextInt(range * 2) - range);
            randomLocation = offsetLocation(startLocation, offset);
            validatedRandomLocation = validateLocation(randomLocation);
        }
        return validatedRandomLocation;
    }
}

