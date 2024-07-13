package me.diyar.ezar.listeners;

import me.diyar.ezar.Main;
import me.diyar.ezar.utils.CountdownTimer;
import me.diyar.ezar.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static me.diyar.ezar.events.SumoStart.countdownMatch;
import static me.diyar.ezar.events.SumoStart.startedTournament;
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
                if(isFighting(player)){
                    if(material == Material.STATIONARY_WATER || material == Material.WATER){
                        removePlayerInTournament(player);
                        player.sendMessage(printMessage("eliminated"));
                        resetPlayerInMatch();
                        clearPlayerFighting();
                        sendMessageToTournament(printMessage("broadcastElimination").replace("%player%", player.getName()));
                        if(getTournamentSize()<2){
                            Player winner = winner();
                            Bukkit.broadcastMessage(printMessage("winner").replace("%winner%", winner.getName()));
                            cancelTournament();
                        }
                        else{
                            startedTournament();
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
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(isTournamentStarted()){
            if(isInTournament(player) || isFighting(player) || isInMatch(player)){
                if (!player.hasPermission(printMessage("admin-permission")) || player.getGameMode() != GameMode.CREATIVE){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if(isTournamentStarted()){
            if(isInTournament(player) || isFighting(player) || isInMatch(player)){
                if (!player.hasPermission(printMessage("admin-permission")) || player.getGameMode() != GameMode.CREATIVE){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            if(isTournamentStarted()){
                if(isInTournament(player) && isInTournament(damager)){
                    if(isFighting(player) && isFighting(damager)){
                        event.setCancelled(false);
                        new BukkitRunnable() {
                            public void run() {
                                damager.setHealth(20L);
                                player.setHealth(20L);
                            }
                        }.run();
                    }
                    else{
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(isTournamentStarted()){
                if(isInTournament(player)){
                    if(isFighting(player)){
                        event.setCancelled(false);
                    }
                    else if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                        event.setCancelled(true);
                    }
                    else{
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onFoodLevel(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();
        if(isTournamentStarted()){
            if(isInTournament(player) || isInMatch(player) || isFighting(player)){
                event.setCancelled(true);
            }
        }
        event.setCancelled(false);
    }

    @EventHandler
    public void onClickInv(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(isTournamentStarted()){
            if(isInTournament(player)){
                if(!player.hasPermission(printMessage("admin-permission")) || !player.getGameMode().equals(GameMode.CREATIVE)){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if(isTournamentStarted()){
            if(isInTournament(player)){
                if(!player.hasPermission(printMessage("admin-permission")) || !player.getGameMode().equals(GameMode.CREATIVE)){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInMatchMoveEvent(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(isInTournament(player)){
            if(isInMatch(player)){
                Location to = event.getTo();
                Location from = event.getFrom();
                if ((to.getX() != from.getX() || to.getZ() != from.getZ())) {
                    player.teleport(from);
                }
            }
        }
    }
}
