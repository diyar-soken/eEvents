package me.diyar.ezar.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static me.diyar.ezar.Main.inGame;
import static me.diyar.ezar.handlers.SumoLocations.getLobbyLocation;
import static me.diyar.ezar.utils.MatchState.changeState;
import static me.diyar.ezar.utils.MatchState.state.END;

public class SumoHandler {
    public static UUID hoster;

    public static boolean isInTournament(Player player){
        return inGame.contains(player.getUniqueId());
    }

    public static void addPlayerInTournament(Player player){
        inGame.add(player.getUniqueId());
        Location loc = SumoLocations.getLobbyLocation();
        player.teleport(loc);
    }

    public static void removePlayerInTournament(Player player){
        inGame.remove(player.getUniqueId());
        Location loc = SumoLocations.getLobbyLocation();
        player.teleport(loc);
    }

    public static void addHostertoList(Player player){
        hoster = player.getUniqueId();
    }

    public static Player getHoster(){
        return Bukkit.getPlayer(hoster);
    }

    public static int getTournamentSize(){
        return inGame.size();
    }

    public static UUID randomPlayer() {
        return inGame.get(ThreadLocalRandom.current().nextInt(0, getTournamentSize()));
    }

    public static void sendMessageToTournament(String message){
        for (UUID uuid : inGame) {
            Player player = Bukkit.getPlayer(uuid);
            player.sendMessage(message);
        }
    }

    public static void resetPlayerInTournament(){
        inGame.clear();
    }

    public static Player winner(){
        Player player = null;
        for (UUID uuid : inGame) {
            player = Bukkit.getPlayer(uuid);
        }
        return player;
    }

    public static void cancelTournament(){
        inGame.clear();
        hoster = null;
        changeState(END);
    }
}
