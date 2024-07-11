package me.diyar.ezar.listeners;

import me.diyar.ezar.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import static me.diyar.ezar.handlers.SumoHandler.*;
import static me.diyar.ezar.utils.MatchState.isTournamentStarted;
import static me.diyar.ezar.utils.MessagesUtil.printMessage;

public class listener implements Listener {

    @EventHandler
    public void onWaterTouch(PlayerMoveEvent event){
        final Player player = event.getPlayer();
        final Material material = event.getPlayer().getLocation().getBlock().getType();

        if(isTournamentStarted()){
            if(isInTournament(player)){
                if(material == Material.STATIONARY_WATER || material == Material.WATER){
                    removePlayerInTournament(player);
                    player.sendMessage(printMessage("eliminated"));
                    sendMessageToTournament(printMessage("broadcastElimination").replace("%player%", player.getName()));
                    if(getTournamentSize()<2){
                        Player winner = winner();
                        Bukkit.broadcastMessage(printMessage("winner").replace("%winner%", winner.getName()));
                        cancelTournament();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(isInTournament(player)){

        }
    }
}
