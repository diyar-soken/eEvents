package me.diyar.ezar.events;

import me.diyar.ezar.Main;
import me.diyar.ezar.utils.MatchState;
import me.diyar.ezar.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static me.diyar.ezar.Main.inGame;
import static me.diyar.ezar.handlers.SumoHandler.*;
import static me.diyar.ezar.handlers.SumoLocations.getLobbyLocation;
import static me.diyar.ezar.handlers.SumoLocations.getSpawnPointLocation;
import static me.diyar.ezar.utils.MatchState.changeState;
import static me.diyar.ezar.utils.MatchState.isTournamentStarted;
import static me.diyar.ezar.utils.MatchState.state.*;
import static me.diyar.ezar.utils.MessagesUtil.printMessage;

public class SumoStart {

    public static void startTournament(Player player){
        if(!isTournamentStarted()){
            changeState(LOBBY);
            addHostertoList(player);
            addPlayerInTournament(player);
            countdownMessage();
        }
        else{
            player.sendMessage(MessagesUtil.printMessage("already-on"));
        }
    }

    public static void startedTournament(){
        if(!inGame.isEmpty()){
            changeState(IN_GAME);
            for (UUID uuid : inGame) {
                Player player = Bukkit.getPlayer(uuid);
                Location loc = getLobbyLocation();
                player.teleport(loc);
            }

            Player randomPlayer1 = Bukkit.getPlayer(randomPlayer());
            Player randomPlayer2 = Bukkit.getPlayer(randomPlayer());
            Location position1 = getSpawnPointLocation(1);
            Location position2 = getSpawnPointLocation(2);
            randomPlayer1.teleport(position1);
            randomPlayer2.teleport(position2);

            for (UUID uuid : inGame) {
                Player players = Bukkit.getPlayer(uuid);
                matchStartedMessage("match-started",randomPlayer1,randomPlayer2,players);
            }
        }
    }

    public static void countdownMessage(){
        int time = Main.getInstance().getConfig().getInt("time");
        broadcastMessageTime(time);
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable()
        {
            int times = Main.getInstance().getConfig().getInt("time");
            @Override
            public void run(){
                if (times == 0 && getTournamentSize()>2) {
                    broadcastMessageTime(times);
                    startedTournament();
                }
                else if(times%5==0){
                    broadcastMessageTime(times);
                }
                else{
                    broadcastMessage("not-enough-players");
                    changeState(END);
                }
                times--;
            }
        }, 0L, 20L);
    }

    public static void broadcastMessageTime(int time){
        Bukkit.broadcastMessage(Main.getInstance().getConfig().getString("broadcast-time").replace("&", "§").replace("%host%", getHoster().getName()).replace("%time%", String.valueOf(time)));
    }

    public static String broadcastMessage(String path){
        return Main.getInstance().getConfig().getString(path).replace("&", "§");
    }

    public static void matchStartedMessage(String path, Player player1, Player player2, Player players){
        List<String> list = Main.getInstance().getConfig().getStringList(path);

        for (String Messages : list) {
            players.sendMessage(Messages.replace("&", "§").replace("%player1%", player1.getName()).replace("%player2%", player2.getName()));
        }
    }
}
