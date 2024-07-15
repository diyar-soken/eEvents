package me.diyar.ezarevents.handlers;

import me.diyar.ezarevents.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SumoLocationsHandler {

    public static boolean isLocationSet(){
        if(Main.getInstance().getConfig().getString("Sumo") == null || Main.getInstance().getConfig().getString("Lobby") == null){
            return false;
        }

        return true;
    }

    public static void spawnPoints(Location location, String spawnLocation){
        double x,y,z;
        float yaw,pitch;
        String world = location.getWorld().getName();
        x = location.getBlockX();
        y = location.getBlockY();
        z = location.getBlockZ();
        yaw = location.getYaw();
        pitch = location.getPitch();
        Main.getInstance().getConfig().set("Sumo." + spawnLocation, "");
        Main.getInstance().getConfig().set("Sumo." + spawnLocation + ".world", world);
        Main.getInstance().getConfig().set("Sumo." + spawnLocation + ".x", Double.valueOf(x));
        Main.getInstance().getConfig().set("Sumo." + spawnLocation + ".y", Double.valueOf(y));
        Main.getInstance().getConfig().set("Sumo." + spawnLocation + ".z", Double.valueOf(z));
        Main.getInstance().getConfig().set("Sumo." + spawnLocation + ".yaw", Float.valueOf(yaw));
        Main.getInstance().getConfig().set("Sumo." + spawnLocation + ".pitch", Float.valueOf(pitch));
        Main.getInstance().saveConfig();
    }

    public static void lobbyPoint(Location location){
        double x,y,z;
        float yaw,pitch;
        String world = location.getWorld().getName();
        x = location.getBlockX();
        y = location.getBlockY();
        z = location.getBlockZ();
        yaw = location.getYaw();
        pitch = location.getPitch();
        Main.getInstance().getConfig().set("Lobby", "");
        Main.getInstance().getConfig().set("Lobby.world", world);
        Main.getInstance().getConfig().set("Lobby.x", Double.valueOf(x));
        Main.getInstance().getConfig().set("Lobby.y", Double.valueOf(y));
        Main.getInstance().getConfig().set("Lobby.z", Double.valueOf(z));
        Main.getInstance().getConfig().set("Lobby.yaw", Float.valueOf(yaw));
        Main.getInstance().getConfig().set("Lobby.pitch", Float.valueOf(pitch));
        Main.getInstance().saveConfig();
    }

    public static Location getLobbyLocation(){
        String worldname = Main.getInstance().getConfig().getString("Lobby.world");
        double x = Main.getInstance().getConfig().getDouble("Lobby.x");
        double y = Main.getInstance().getConfig().getDouble("Lobby.y");
        double z = Main.getInstance().getConfig().getDouble("Lobby.z");
        float yaw = Main.getInstance().getConfig().getInt("Lobby.yaw");
        float pitch = Main.getInstance().getConfig().getInt("Lobby.pitch");
        World world = Bukkit.getWorld(worldname);
        return new Location(world, x, y, z,yaw,pitch);
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
