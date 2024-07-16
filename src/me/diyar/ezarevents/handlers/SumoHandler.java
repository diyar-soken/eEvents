package me.diyar.ezarevents.handlers;

import me.diyar.ezarevents.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static me.diyar.ezarevents.Main.*;
import static me.diyar.ezarevents.handlers.SumoInventoryHandler.*;
import static me.diyar.ezarevents.handlers.SumoLocationsHandler.getSpawnPointLocation;
import static me.diyar.ezarevents.utils.MatchState.changeState;
import static me.diyar.ezarevents.utils.MatchState.state.END;
import static me.diyar.ezarevents.utils.MessagesUtil.printMessage;
import static me.diyar.ezarevents.utils.MessagesUtil.sendMessageToTournament;

public class SumoHandler {
    public static UUID hoster;

    ////////////////
    // TOURNAMENT //
    ////////////////

    public static boolean isInTournament(Player player){
        return inGame.contains(player.getUniqueId()) || spectating.contains(player.getUniqueId());
    }

    public static void addPlayerInTournament(Player player){
        inGame.add(player.getUniqueId());
        memorizeInventory(player);
        giveTournamentInventory(player);
        Location loc = SumoLocationsHandler.getSpawnPointLocation("spec");
        player.setFoodLevel(20);
        player.setHealth(20);
        player.teleport(loc);
    }

    public static void removePlayerInTournament(Player player){
        inGame.remove(player.getUniqueId());
        fighting.remove(player.getUniqueId());
        inMatch.remove(player.getUniqueId());
        spectating.remove(player.getUniqueId());
        restoreInventory(player);
        Location loc = getSpawnPointLocation("Lobby");
        player.setFoodLevel(20);
        player.setHealth(20);
        player.teleport(loc);
    }

    public static int getTournamentMaxSize(){
        return Main.getInstance().getConfig().getInt("max-slot");
    }

    public static int getTournamentSize(){
        return inGame.size();
    }

    public static void cancelTournament(){
        for (UUID uuid : inGame) {
            Player player = Bukkit.getPlayer(uuid);
            restoreInventory(player);
            player.teleport(getSpawnPointLocation("Lobby"));
        }
        for (UUID uuid : inMatch) {
            Player player = Bukkit.getPlayer(uuid);
            restoreInventory(player);
            player.teleport(getSpawnPointLocation("Lobby"));
        }
        for (UUID uuid : fighting) {
            Player player = Bukkit.getPlayer(uuid);
            restoreInventory(player);
            player.teleport(getSpawnPointLocation("Lobby"));
        }
        for (UUID uuid : spectating) {
            Player player = Bukkit.getPlayer(uuid);
            restoreInventory(player);
            player.teleport(getSpawnPointLocation("Lobby"));
        }
        inGame.clear();
        inMatch.clear();
        fighting.clear();
        spectating.clear();
        hoster = null;
        changeState(END);
    }

    public static void leaveTournament(Player player){
        player.sendMessage(printMessage("leave-message"));
        removePlayerInTournament(player);
        sendMessageToTournament(printMessage("leave-tournament").replace("%player%", player.getName()).replace("%inTournament%", String.valueOf(getTournamentSize())).replace("%maxTournament%", printMessage("max-slot")));
    }

    ///////////
    // MATCH //
    ///////////

    public static boolean isInMatch(Player player){
        if(inMatch.contains(player.getUniqueId())){
            return true;
        }
        return false;
    }

    public static void addPlayerInMatch(Player player, Player player2){
        inMatch.add(player.getUniqueId());
        inMatch.add(player2.getUniqueId());
        Location position1 = getSpawnPointLocation("1");
        Location position2 = getSpawnPointLocation("2");
        player.getInventory().clear();
        player2.getInventory().clear();
        player.teleport(position1);
        player2.teleport(position2);
    }

    public static void removePlayerInMatch(Player player){
        inMatch.remove(player.getUniqueId());
    }

    public static Player getPlayerInMatch(){
        Player player = null;
        for(UUID players : inMatch){
            player = Bukkit.getPlayer(players);
        }
        return player;
    }

    public static void resetPlayerInMatch(){
        inMatch.clear();
    }
     //////////////
    // FIGHTING //
   //////////////

    public static void addPlayerFighting(Player player, Player player1){
        fighting.add(player.getUniqueId());
        fighting.add(player1.getUniqueId());
    }

    public static boolean isFighting(Player player){
        return fighting.contains(player.getUniqueId());
    }

    public static void removePlayerInFight(Player player){
        fighting.remove(player.getUniqueId());
        Location loc = SumoLocationsHandler.getSpawnPointLocation("spec");
        player.teleport(loc);
    }

    public static Player getPlayerFighting(){
        Player player = null;
        for(UUID players : fighting){
            player = Bukkit.getPlayer(players);
        }
        return player;
    }

    public static Player getPlayerFightingByPos(int num){
        UUID player = inMatch.get(num);
        return Bukkit.getPlayer(player);
    }

    public static boolean areFighting(){
        return fighting.isEmpty();
    }

    public static void clearPlayerFighting(){
        fighting.clear();
    }

    ////////////
    // HOSTER //
    ////////////
    public static void addHostertoList(Player player){
        hoster = player.getUniqueId();
    }

    public static Player getHoster(){
        return Bukkit.getPlayer(hoster);
    }

    ////////////
    // SPECTATOR //
    ////////////
    public static void addSpectator(Player player){
        inGame.remove(player.getUniqueId());
        fighting.remove(player.getUniqueId());
        inMatch.remove(player.getUniqueId());
        spectating.add(player.getUniqueId());
        Location loc = SumoLocationsHandler.getSpawnPointLocation("spec");
        giveTournamentInventory(player);
        player.setFoodLevel(20);
        player.setHealth(20);
        player.teleport(loc);
    }

    public static void removeSpectator(Player player){
        inGame.remove(player.getUniqueId());
        fighting.remove(player.getUniqueId());
        inMatch.remove(player.getUniqueId());
        spectating.remove(player.getUniqueId());
        Location loc = SumoLocationsHandler.getSpawnPointLocation("Lobby");
        player.teleport(loc);
    }

    public static boolean isInSpectatorMode(Player player){
        return spectating.contains(player.getUniqueId());
    }

    // UTIL PLAYERS //

    public static UUID randomPlayer() {
        return inGame.get(ThreadLocalRandom.current().nextInt(0, getTournamentSize()));
    }

    public static Player winner(){
        Player player = null;
        for (UUID uuid : inGame) {
            player = Bukkit.getPlayer(uuid);
        }
        return player;
    }

}
