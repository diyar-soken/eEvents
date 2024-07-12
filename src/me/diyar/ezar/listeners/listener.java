package me.diyar.ezar.listeners;

import me.diyar.ezar.Main;
import me.diyar.ezar.utils.CountdownTimer;
import me.diyar.ezar.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import static me.diyar.ezar.events.SumoStart.countdownMatch;
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
                if(isInMatch(player)){
                    if(material == Material.STATIONARY_WATER || material == Material.WATER){
                        removePlayerInTournament(player);
                        player.sendMessage(printMessage("eliminated"));
                        removePlayerInMatch(getPlayerInMatch());
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
    }

    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent event){
        Player player = (Player) event.getWhoClicked();
        if(isInTournament(player)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInMatchMoveEvent(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(isInTournament(player)){
            while(isInMatch(player)){
                Location to = event.getTo();
                Location from = event.getFrom();
                if ((to.getX() != from.getX() || to.getZ() != from.getZ())) {
                    player.teleport(from);
                }
            }
        }
    }
}
