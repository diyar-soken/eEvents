package me.diyar.ezar.listeners;

import me.diyar.ezar.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public class WaterListener implements Listener {

    /*@EventHandler
    public void waterTouch(PlayerMoveEvent event) {
        Material b = event.getPlayer().getLocation().getBlock().getType();
        if((b == Material.WATER || b == Material.STATIONARY_WATER) && Main.ingame.contains(event.getPlayer().getUniqueId()) && Main.sumo.containsValue(INGAME)){
            for (final UUID u : Main.ingame) {
                final Player p = Bukkit.getPlayer(u);
                p.sendMessage(event.getPlayer().getName() + " has been removed from the event.");
            }
            Main.ingame.remove(event.getPlayer().getUniqueId());
            if (Main.ingame.size() < 2) {
                Main.ingame.clear();
                Main.sumo.put("Sumo", String.valueOf(END));
            }
        }
    }

     */
}
