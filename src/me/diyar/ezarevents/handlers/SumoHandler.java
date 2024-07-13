package me.diyar.ezarevents.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static me.diyar.ezarevents.Main.*;
import static me.diyar.ezarevents.handlers.SumoInventoryHandler.*;
import static me.diyar.ezarevents.handlers.SumoLocationsHandler.getLobbyLocation;
import static me.diyar.ezarevents.handlers.SumoLocationsHandler.getSpawnPointLocation;
import static me.diyar.ezarevents.utils.MatchState.changeState;
import static me.diyar.ezarevents.utils.MatchState.state.END;

public class SumoHandler {
    public static UUID hoster;

    ////////////////
    // TOURNAMENT //
    ////////////////

    public static boolean isInTournament(Player player){
        return inGame.contains(player.getUniqueId());
    }

    public static void addPlayerInTournament(Player player){
        inGame.add(player.getUniqueId());
        memorizeInventory(player);
        giveTournamentInventory(player);
        Location loc = SumoLocationsHandler.getSpawnPointLocation("spec");
        player.teleport(loc);
    }

    public static void removePlayerInTournament(Player player){
        inGame.remove(player.getUniqueId());
        restoreInventory(player);
        Location loc = SumoLocationsHandler.getLobbyLocation();
        player.teleport(loc);
    }

    public static int getTournamentSize(){
        return inGame.size();
    }

    public static void cancelTournament(){
        for (UUID uuid : inGame) {
            Player player = Bukkit.getPlayer(uuid);
            restoreInventory(player);
            player.teleport(getLobbyLocation());
        }
        inGame.clear();
        resetPlayerInMatch();
        hoster = null;
        changeState(END);
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
