package me.diyar.ezar.events;

import me.diyar.ezar.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class SumoStarted {

    public static void started(){
        int x = Main.getInstance().getConfig().getInt("Lobby.x");
        int y = Main.getInstance().getConfig().getInt("Lobby.y");
        int z = Main.getInstance().getConfig().getInt("Lobby.z");
        for (UUID u : Main.ingame) {
            Player p = Bukkit.getPlayer(u);
            Location loc = new Location(p.getWorld(), x, y, z);
            p.teleport(loc);
        }
        if (Main.ingame.isEmpty())
            return;
        UUID randPlr1 = Main.ingame.get(ThreadLocalRandom.current().nextInt(0, Main.ingame.size()));
        Main.ingame.remove(randPlr1);
        UUID randPlr2 = Main.ingame.get(ThreadLocalRandom.current().nextInt(0, Main.ingame.size()));
        Main.ingame.add(randPlr1);
        Player p1 = Bukkit.getPlayer(randPlr1);
        Player p2 = Bukkit.getPlayer(randPlr2);
        int x1 = Main.getInstance().getConfig().getInt("Sumo.1.x");
        int y1 = Main.getInstance().getConfig().getInt("Sumo.1.y");
        int z1 = Main.getInstance().getConfig().getInt("Sumo.1.z");
        Location loc1 = new Location(p1.getWorld(), x1, y1, z1);
        int x2 = Main.getInstance().getConfig().getInt("Sumo.2.x");
        int y2 = Main.getInstance().getConfig().getInt("Sumo.2.y");
        int z2 = Main.getInstance().getConfig().getInt("Sumo.2.z");
        Location loc2 = new Location(p1.getWorld(), x2, y2, z2);
        p1.teleport(loc1);
        p2.teleport(loc2);
        for (UUID u : Main.ingame) {
            Player p = Bukkit.getPlayer(u);
            p.sendMessage("First match: " + p1.getName() + " vs " + p2.getName());
        }
    }
}
