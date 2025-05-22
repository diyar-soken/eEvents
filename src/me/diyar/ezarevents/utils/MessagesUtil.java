package me.diyar.ezarevents.utils;

import me.diyar.ezarevents.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

import static me.diyar.ezarevents.Main.*;
import static me.diyar.ezarevents.handlers.SumoHandler.getHoster;

public class MessagesUtil {

    public static String ADMIN_HELP = "ADMIN-HELP";
    public static String HELP = "HELP";
    public static String RELOAD = "RELOAD";
    public static String NOPERMISSION = "NOPERMISSION";
    public static String JOIN_NOPERMISSION = "JOIN-NOPERMISSION";
    public static String HOST_NOPERMISSION = "HOST-NOPERMISSION";
    public static String NOSPAWNPOINTS = "NOSPAWNPOINTS";
    public static String SPAWNPOINT_LOBBY = "SPAWNPOINT-LOBBY";
    public static String SPAWNPOINT_SPEC = "SPAWNPOINT-SPEC";
    public static String SPAWNPOINT_1 = "SPAWNPOINT-1";
    public static String SPAWNPOINT_2 = "SPAWNPOINT-2";
    public static String NO_HOSTED_EVENT = "NO-HOSTED-EVENT";
    public static String HOSTED = "HOSTED";
    public static String NOT_INEVENT = "NOT-INEVENT";
    public static String TOURNAMENT_BROADCAST_MESSAGE = "TOURNAMENT-BROADCAST-MESSAGE";
    public static String TOURNAMENT_COUNTDOWN_MESSAGE = "TOURNAMENT-COUNTDOWN-MESSAGE";
    public static String TOURNAMENT_STOP_MESSAGE = "TOURNAMENT-STOP-MESSAGE";
    public static String TOURNAMENT_JOIN = "TOURNAMENT-JOIN";
    public static String TOURNAMENT_QUIT = "TOURNAMENT-QUIT";
    public static String INEVENT = "INEVENT";
    public static String STARTED = "STARTED";
    public static String NO_ENOUGH_PLAYERS = "NO-ENOUGH-PLAYERS";
    public static String ELIMINATED = "NO-ENOUGH-PLAYERS";
    public static String TOURNAMENT_ELIMINATION = "TOURNAMENT-ELIMINATION";
    public static String WINNER = "WINNER";
    public static String LEAVEITEM_DISPLAYNAME = "LEAVEITEM-DISPLAYNAME";
    public static String TOURNAMENT_LEAVE_MESSAGE = "TOURNAMENT-LEAVE-MESSAGE";
    public static String PREMATCH_MESSAGE = "PREMATCH-MESSAGE";
    public static String MATCH_INFO_MESSAGE = "MATCH-INFO-MESSAGE";
    public static String MATCH_COUNTDOWN_MESSAGE = "MATCH-COUNTDOWN-MESSAGE";
    public static String MATCH_STARTED_MESSAGE = "MATCH-STARTED-MESSAGE";
    public static String MATCH_QUIT_MESSAGE = "MATCH-QUIT-MESSAGE";
    public static String SPEC_MESSAGE = "SPEC-MESSAGE";


    public static void printListMessages(String path, Player player){
        List<String> list = Main.getInstance().getConfig().getStringList(path);

        for (String Messages : list) {
            player.sendMessage(Messages.replace("&", "§"));
        }
    }

    public static String printMessage(String path){
        return Main.getInstance().getConfig().getString(path).replace("&", "§");
    }

    public static void sendMessageToTournament(String message){
        for (UUID uuid : inGame) {
            Player player = Bukkit.getPlayer(uuid);
            player.sendMessage(message);
        }
        for (UUID uuid : spectating) {
            Player player = Bukkit.getPlayer(uuid);
            player.sendMessage(message);
        }
    }

    public static void sendMessageToInMatch(String message){
        for (UUID uuid : inMatch) {
            Player player = Bukkit.getPlayer(uuid);
            player.sendMessage(message);
        }
    }

    public static void sendMessageToFighters(String message){
        for (UUID uuid : fighting) {
            Player player = Bukkit.getPlayer(uuid);
            player.sendMessage(message);
        }
    }

    public static void broadcastMessageTime(int time){
        Bukkit.broadcastMessage(Main.getInstance().getConfig().getString(TOURNAMENT_BROADCAST_MESSAGE).replace("&", "§").replace("%host%", getHoster().getName()).replace("%time%", String.valueOf(time)).replace("%join%", "/sumo join"));
    }

    public static void broadcastMessage(String path){
        Bukkit.broadcastMessage(Main.getInstance().getConfig().getString(path).replace("&", "§"));
    }

    public static void matchStartedMessage(String path, Player player1, Player player2){
        List<String> list = Main.getInstance().getConfig().getStringList(path);

        for (String Messages : list) {
            sendMessageToTournament(Messages.replace("&", "§").replace("%player1%", player1.getName()).replace("%player2%", player2.getName()));
        }
    }
}
