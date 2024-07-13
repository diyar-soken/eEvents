package me.diyar.ezar.utils;

import me.diyar.ezar.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

import static me.diyar.ezar.Main.inGame;
import static me.diyar.ezar.Main.inMatch;
import static me.diyar.ezar.handlers.SumoHandler.getHoster;

public class MessagesUtil {

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
    }

    public static void sendMessageToInMatch(String message){
        for (UUID uuid : inMatch) {
            Player player = Bukkit.getPlayer(uuid);
            player.sendMessage(message);
        }
    }

    public static void broadcastMessageTime(int time){
        Bukkit.broadcastMessage(Main.getInstance().getConfig().getString("broadcast-time").replace("&", "§").replace("%host%", getHoster().getName()).replace("%time%", String.valueOf(time)).replace("%join%", "/sumo join"));
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
