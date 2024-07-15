package me.diyar.ezarevents.handlers;

import me.diyar.ezarevents.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SumoLocationsHandler {

    public static boolean isLocationSet(){
        if(Main.getInstance().getConfig().getString("Sumo") == null){
            return false;
        }

        return true;
    }

    public static void spawnPoints(Location location, String spawnLocation){
        double x,y,z;
        float yaw,pitch;
        String world = location.getWorld().getName();
        x = location.getX();
        y = location.getY();
        z = location.getZ();
        yaw = location.getYaw();
        pitch = location.getPitch();
        Main.getInstance().getConfig().set("Sumo." + spawnLocation, "");
        Main.getInstance().getConfig().set("Sumo." + spawnLocation + ".world", world);
        Main.getInstance().getConfig().set("Sumo." + spawnLocation + ".x", x);
        Main.getInstance().getConfig().set("Sumo." + spawnLocation + ".y", y);
        Main.getInstance().getConfig().set("Sumo." + spawnLocation + ".z", z);
        Main.getInstance().getConfig().set("Sumo." + spawnLocation + ".yaw", yaw);
        Main.getInstance().getConfig().set("Sumo." + spawnLocation + ".pitch", pitch);
        Main.getInstance().saveConfig();
    }

    public static Location getSpawnPointLocation(String spawnLocation){
        String worldName;
        double x,y,z;
        float yaw,pitch;

        worldName = Main.getInstance().getConfig().getString("Sumo." + spawnLocation + ".world");
        x = Main.getInstance().getConfig().getDouble("Sumo." + spawnLocation + ".x");
        y = Main.getInstance().getConfig().getDouble("Sumo." + spawnLocation + ".y");
        z = Main.getInstance().getConfig().getDouble("Sumo." + spawnLocation + ".z");
        yaw = Main.getInstance().getConfig().getInt("Sumo." + spawnLocation + ".yaw");
        pitch = Main.getInstance().getConfig().getInt("Sumo." + spawnLocation + ".pitch");

        World world = Bukkit.getWorld(worldName);

        return new Location(world, x, y, z,yaw,pitch);
    }
}
