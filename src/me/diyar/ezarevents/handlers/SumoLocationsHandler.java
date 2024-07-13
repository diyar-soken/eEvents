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
        int x,y,z;
        String world = location.getWorld().getName();
        x = location.getBlockX();
        y = location.getBlockY();
        z = location.getBlockZ();
        Main.getInstance().getConfig().set("Sumo." + spawnLocation, "");
        Main.getInstance().getConfig().set("Sumo." + spawnLocation + ".world", world);
        Main.getInstance().getConfig().set("Sumo." + spawnLocation + ".x", Integer.valueOf(x));
        Main.getInstance().getConfig().set("Sumo." + spawnLocation + ".y", Integer.valueOf(y));
        Main.getInstance().getConfig().set("Sumo." + spawnLocation + ".z", Integer.valueOf(z));
        Main.getInstance().saveConfig();
    }

    public static void lobbyPoint(Location location){
        int x,y,z;
        String world = location.getWorld().getName();
        x = location.getBlockX();
        y = location.getBlockY();
        z = location.getBlockZ();
        Main.getInstance().getConfig().set("Lobby", "");
        Main.getInstance().getConfig().set("Lobby.world", world);
        Main.getInstance().getConfig().set("Lobby.x", Integer.valueOf(x));
        Main.getInstance().getConfig().set("Lobby.y", Integer.valueOf(y));
        Main.getInstance().getConfig().set("Lobby.z", Integer.valueOf(z));
        Main.getInstance().saveConfig();
    }

    public static Location getLobbyLocation(){
        String worldname = Main.getInstance().getConfig().getString("Lobby.world");
        int x = Main.getInstance().getConfig().getInt("Lobby.x");
        int y = Main.getInstance().getConfig().getInt("Lobby.y");
        int z = Main.getInstance().getConfig().getInt("Lobby.z");
        World world = Bukkit.getWorld(worldname);
        return new Location(world, x, y, z);
    }

    public static Location getSpawnPointLocation(String spawnLocation){
        String worldName;
        int x,y,z;

        worldName = Main.getInstance().getConfig().getString("Sumo." + spawnLocation + ".world");
        x = Main.getInstance().getConfig().getInt("Sumo." + spawnLocation + ".x");
        y = Main.getInstance().getConfig().getInt("Sumo." + spawnLocation + ".y");
        z = Main.getInstance().getConfig().getInt("Sumo." + spawnLocation + ".z");

        World world = Bukkit.getWorld(worldName);

        return new Location(world, x, y, z);
    }
}
