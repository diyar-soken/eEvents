package me.diyar.ezarevents.listeners;

import me.diyar.ezarevents.Main;
import me.diyar.ezarevents.handlers.SumoLocationsHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static me.diyar.ezarevents.events.SumoStart.startMatch;
import static me.diyar.ezarevents.handlers.SumoHandler.*;
import static me.diyar.ezarevents.handlers.SumoInventoryHandler.giveTournamentInventory;
import static me.diyar.ezarevents.handlers.SumoLocationsHandler.getSpawnPointLocation;
import static me.diyar.ezarevents.utils.MatchState.isTournamentStarted;
import static me.diyar.ezarevents.utils.MessagesUtil.printMessage;
import static me.diyar.ezarevents.utils.MessagesUtil.sendMessageToTournament;
import static me.diyar.ezarevents.utils.PermissionUtils.adminpermission;

public class listener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Location lobby = getSpawnPointLocation("Lobby");
        if(player.hasPlayedBefore()){
            player.teleport(lobby);
        }
        else{
            player.teleport(lobby);
        }
    }

    @EventHandler
    public void onWaterTouch(PlayerMoveEvent event){
        final Player player = event.getPlayer();
        final Material material = event.getPlayer().getLocation().getBlock().getType();

        if(isTournamentStarted()){
            if(isInTournament(player)){
                if(isFighting(player)){
                    if(material == Material.STATIONARY_WATER || material == Material.WATER){
                        Player playerMatchWinner = getPlayerFighting();
                        removePlayerInFight(playerMatchWinner);
                        clearPlayerFighting();
                        resetPlayerInMatch();
                        addSpectator(player);
                        if(getTournamentSize()<2){
                            Player winner = winner();
                            sendMessageToTournament(printMessage("broadcastElimination").replace("%looser%", player.getName()).replace("%winner%", winner.getName()));
                            Bukkit.broadcastMessage(printMessage("winner").replace("%winner%", winner.getName()));
                            Bukkit.broadcastMessage(printMessage("winner").replace("%winner%", winner.getName()));
                            Bukkit.broadcastMessage(printMessage("winner").replace("%winner%", winner.getName()));
                            cancelTournament();
                        }
                        else{
                            sendMessageToTournament(printMessage("broadcastElimination").replace("%looser%", player.getName()).replace("%winner%", playerMatchWinner.getName()));
                            startMatch();
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent event){
        Player player = (Player) event.getWhoClicked();
        if(isInTournament(player) || isInSpectatorMode(player)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(isTournamentStarted()){
            if(isInTournament(player) || isFighting(player) || isInMatch(player) || isInSpectatorMode(player)){
                if (!player.hasPermission(adminpermission) || player.getGameMode() != GameMode.CREATIVE){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if(isTournamentStarted()){
            if(isInTournament(player) || isFighting(player) || isInMatch(player) || isInSpectatorMode(player)){
                if (!player.hasPermission(adminpermission) || player.getGameMode() != GameMode.CREATIVE){
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
                else if(isInSpectatorMode(player)){
                    event.setCancelled(true);
                }
            }
            else{
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(isTournamentStarted()){
                if(isInTournament(player) || isInSpectatorMode(player)){
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

    @EventHandler(priority = EventPriority.MONITOR)
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();
        if(isTournamentStarted()){
            if(isInTournament(player) || isInSpectatorMode(player)){
                event.setFoodLevel(20);
                player.setHealth(20);
                Bukkit.getServer().getScheduler().runTaskLater(Main.getInstance(), () -> event.setCancelled(true), 1L);
            }
        }
    }

    @EventHandler
    public void onClickInv(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(isTournamentStarted()){
            if(isInTournament(player) || isInSpectatorMode(player)){
                if(!player.hasPermission(adminpermission) || !player.getGameMode().equals(GameMode.CREATIVE)){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onItemClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(isTournamentStarted()){
            if(isInTournament(player)){
                if(event.hasItem()){
                    if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(printMessage("leaveitem"))){
                        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)){
                            leaveTournament(player);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if(isTournamentStarted()){
            if(isInTournament(player) || isInSpectatorMode(player)){
                if(!player.hasPermission(adminpermission) || !player.getGameMode().equals(GameMode.CREATIVE)){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInMatchMoveEvent(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(isInTournament(player) || isInSpectatorMode(player)){
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
