package me.diyar.ezar.utils;

import me.diyar.ezar.Main;
import org.bukkit.entity.Player;

import java.util.List;

public class PrintListMessages {

    public static void printListMessages(List<String> list, Player player){
        List<String> Message = list;

        for (String Messages : Message) {
            player.sendMessage(Messages.replace("&", "§"));
        }
    }

    public static String printMessage(String path){
        return Main.getInstance().getConfig().getString(path).replace("&", "§");
    }
}
