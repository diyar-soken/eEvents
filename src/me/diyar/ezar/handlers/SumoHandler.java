package me.diyar.ezar.handlers;

import me.diyar.ezar.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static me.diyar.ezar.Main.inGame;

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
}
