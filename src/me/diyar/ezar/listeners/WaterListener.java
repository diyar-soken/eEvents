package me.diyar.ezar.listeners;

import org.bukkit.event.Listener;

import static me.diyar.ezar.utils.MatchState.changeState;
import static me.diyar.ezar.utils.MatchState.state.END;

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
