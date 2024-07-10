package me.diyar.ezar.utils;

import me.diyar.ezar.Main;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class MessagesUtil {

    public static void printListMessages(String path, Player player){
        List<String> list = Collections.singletonList(Main.getInstance().getConfig().getString(path));

        for (String Messages : list) {
            player.sendMessage(Messages.replace("&", "§"));
        }
    }

    public static String printMessage(String path){
        return Main.getInstance().getConfig().getString(path).replace("&", "§");
    }
}
