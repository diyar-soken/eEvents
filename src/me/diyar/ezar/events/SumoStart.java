package me.diyar.ezar.events;

import me.diyar.ezar.Main;
import me.diyar.ezar.utils.MatchState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

import static me.diyar.ezar.utils.MatchState.state.*;

public class SumoStart {
    public static int countdown;


    public static void start(UUID u){
        Player started = Bukkit.getPlayer(u);
        if ((Main.sumo.containsValue("IN_GAME") || Main.sumo.containsValue("LOBBY"))) {
            started.sendMessage(ChatColor.RED + "An event is already on!");
        } else {
            Main.sumo.put("Sumo", String.valueOf(LOBBY));
            countdown = Main.getInstance().getConfig().getInt("time");
            Bukkit.broadcastMessage(broadcastmessage(countdown,started));
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                public void run() {
                    Bukkit.broadcastMessage(broadcastmessage(15,started));
                }
            },  20L * 15);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                public void run() {
                    Bukkit.broadcastMessage(broadcastmessage(10,started));
                }
            },  20L * 20);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                public void run() {
                    Bukkit.broadcastMessage(broadcastmessage(5,started));
                }
            },  20L * 25);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                public void run() {
                    Bukkit.broadcastMessage(broadcastmessage(4,started));
                }
            },  20L * 26);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                public void run() {
                    Bukkit.broadcastMessage(broadcastmessage(3,started));
                }
            },  20L * 27);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                public void run() {
                    Bukkit.broadcastMessage(broadcastmessage(2,started));
                }
            },  20L * 28);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                public void run() {
                    Bukkit.broadcastMessage(broadcastmessage(1,started));
                    if (Main.ingame.size() < 2) {
                        Bukkit.broadcastMessage(Main.getInstance().getConfig().getString("notenoughplayers").replace("&", "§"));
                        Main.ingame.clear();
                        Main.sumo.put("Sumo", String.valueOf(END));
                    } else {
                        Main.sumo.put("Sumo", String.valueOf(INGAME));
                        SumoStarted.started();
                    }
                }
            },  20L * countdown);
        }
    }

    public static String broadcastmessage(int time, Player player){
        return Main.getInstance().getConfig().getString("broadcast-time").replace("&", "§").replace("%player%", player.getName()).replace("%time%", String.valueOf(time));
    }
}
