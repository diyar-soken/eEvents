package me.diyar.ezar.utils;

import me.diyar.ezar.Main;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class MessagesUtil {

    public static void printListMessages(String path, Player player){
        List<String> list = Main.getInstance().getConfig().getStringList(path);

        for (String Messages : list) {
            player.sendMessage(Messages.replace("&", "§"));
        }
    }

    @NotNull
    public static String printMessage(String path){
        return Main.getInstance().getConfig().getString(path).replace("&", "§");
    }
}
