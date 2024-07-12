package me.diyar.ezar.events;

import me.diyar.ezar.Main;
import me.diyar.ezar.utils.CountdownTimer;
import me.diyar.ezar.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.*;

import static me.diyar.ezar.Main.inGame;
import static me.diyar.ezar.handlers.SumoHandler.*;
import static me.diyar.ezar.handlers.SumoLocations.getLobbyLocation;
import static me.diyar.ezar.handlers.SumoLocations.getSpawnPointLocation;
import static me.diyar.ezar.utils.ClickableMessage.sendClickableCommand;
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
            countdown();
        }
        else{
            player.sendMessage(MessagesUtil.printMessage("already-on"));
        }
    }

    public static void startedTournament(){
        if(!inGame.isEmpty()){
            for (UUID uuid : inGame) {
                Player player = Bukkit.getPlayer(uuid);
                Location loc = getLobbyLocation();
                player.teleport(loc);
            }

            Player randomPlayer1 = Bukkit.getPlayer(randomPlayer());
            Player randomPlayer2 = Bukkit.getPlayer(randomPlayer());
            while(randomPlayer1 == randomPlayer2){
                randomPlayer2 = Bukkit.getPlayer(randomPlayer());
            }

            addPlayerInMatch(randomPlayer1,randomPlayer2);
            countdownMatch(randomPlayer1,randomPlayer2);

            for (UUID uuid : inGame) {
                Player players = Bukkit.getPlayer(uuid);
                matchStartedMessage("match-started",randomPlayer1,randomPlayer2,players);
            }
        }
    }

    public static void countdown(){
        CountdownTimer timer = new CountdownTimer(Main.getInstance(),
                Main.getInstance().getConfig().getInt("time"),
                () -> broadcastMessageTime(Main.getInstance().getConfig().getInt("time")),
                () -> {
                    if (getTournamentSize()>=2) {
                        changeState(IN_GAME);
                        startedTournament();
                    }
                    else{
                        broadcastMessage("not-enough-players");
                        cancelTournament();
                    }
                },
                (t) -> {
                    List<Integer> list = Main.getInstance().getConfig().getIntegerList("times");

                    if(list.contains(t.getSecondsLeft())){
                        broadcastMessageTime(t.getSecondsLeft());
                    }
                }

        );

        timer.scheduleTimer();
    }

    public static void countdownMatch(Player player1, Player player2){
        CountdownTimer timer = new CountdownTimer(Main.getInstance(), 3,
                () -> {
                    sendMessageToTournament("§eMatch starting..");
                },
                () -> {
                    addPlayerFighting(player1,player2);
                    sendMessageToTournament("§eMatch Started");
                },
                (t) -> {
                    broadcastMessageTime(t.getSecondsLeft());
                }

        );

        timer.scheduleTimer();
    }

    public static void broadcastMessageTime(int time){
        Bukkit.broadcastMessage(Main.getInstance().getConfig().getString("broadcast-time").replace("&", "§").replace("%host%", getHoster().getName()).replace("%time%", String.valueOf(time)).replace("%join%", "/sumo join"));
    }

    public static void broadcastMessage(String path){
        Bukkit.broadcastMessage(Main.getInstance().getConfig().getString(path).replace("&", "§"));
    }

    public static void matchStartedMessage(String path, Player player1, Player player2, Player players){
        List<String> list = Main.getInstance().getConfig().getStringList(path);

        for (String Messages : list) {
            players.sendMessage(Messages.replace("&", "§").replace("%player1%", player1.getName()).replace("%player2%", player2.getName()));
        }
    }
}
