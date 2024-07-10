package me.diyar.ezar.commands;

import me.diyar.ezar.Main;
import me.diyar.ezar.events.SumoStart;
import me.diyar.ezar.handlers.SumoLocations;
import me.diyar.ezar.utils.PrintListMessages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SumoCommand extends Command {

    public SumoCommand() {
        super("sumo");
    }

    @Override
    public boolean execute(CommandSender commandSender, String arg, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Location playerLocation = player.getLocation();
            if(args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    Main.getInstance().reloadConfig();
                    player.sendMessage(Main.getInstance().getConfig().getString("reload").replace("&", "§"));
                }
                else if(args[0].equalsIgnoreCase("join")) {
                    if(Main.sumo.containsValue("LOBBY")){
                        if(!Main.ingame.contains(player.getUniqueId())){
                            Location loc = SumoLocations.getLobbyLocation();
                            player.teleport(loc);
                            Main.ingame.add(player.getUniqueId());
                            player.sendMessage(PrintListMessages.printMessage("joining"));
                        }
                        else{
                            player.sendMessage(PrintListMessages.printMessage("alreadyin"));
                        }
                    }
                    else if(Main.sumo.containsValue("IN_GAMEE")){
                        player.sendMessage(PrintListMessages.printMessage("in-game"));
                    }
                    else{
                        player.sendMessage(PrintListMessages.printMessage("noevent"));
                    }
                }
                else if(args[0].equalsIgnoreCase("leave")) {

                }
                else if(args[0].equalsIgnoreCase("host")) {
                    if(player.hasPermission(Main.getInstance().getConfig().getString("host-permission"))){
                        SumoStart.start(player.getUniqueId());
                    }
                    else{
                        player.sendMessage(Main.getInstance().getConfig().getString("host-permission-message").replace("&", "§"));
                    }
                }
                else if(args[0].equalsIgnoreCase("setlobby")) {
                    if(player.hasPermission(Main.getInstance().getConfig().getString("admin-permission"))){
                        SumoLocations.lobbyPoint(playerLocation);
                        player.sendMessage(PrintListMessages.printMessage("setlobby"));
                    }
                    else{
                        player.sendMessage(PrintListMessages.printMessage("noperms"));
                    }
                }
                else{
                    if(player.hasPermission(Main.getInstance().getConfig().getString("admin-permission"))){
                        PrintListMessages.printListMessages(Main.getInstance().getConfig().getStringList("admin-help-command"), player);
                    }
                    else{
                        PrintListMessages.printListMessages(Main.getInstance().getConfig().getStringList("help-command"), player);
                    }
                }
            }
            else if(args.length == 2){
                if(args[0].equalsIgnoreCase("setspawn")) {
                    if(player.hasPermission(Main.getInstance().getConfig().getString("admin-permission"))){
                        if(args[1].equalsIgnoreCase("1")){
                            SumoLocations.spawnPoints(playerLocation, Integer.parseInt(args[1]));
                            player.sendMessage(PrintListMessages.printMessage("spawnpoint1"));
                        }
                        else if(args[1].equalsIgnoreCase("2")){
                            SumoLocations.spawnPoints(playerLocation, Integer.parseInt(args[1]));
                            player.sendMessage(PrintListMessages.printMessage("spawnpoint1"));
                        }
                        else{
                            PrintListMessages.printListMessages(Main.getInstance().getConfig().getStringList("admin-help-command"), player);
                        }
                    }
                    else{
                        player.sendMessage(PrintListMessages.printMessage("noperms"));
                    }
                }
                else{
                    if(player.hasPermission(Main.getInstance().getConfig().getString("admin-permission"))){
                        PrintListMessages.printListMessages(Main.getInstance().getConfig().getStringList("admin-help-command"), player);
                    }
                    else{
                        player.sendMessage(PrintListMessages.printMessage("noperms"));
                    }
                }
            }
            else{
                if(player.hasPermission(Main.getInstance().getConfig().getString("admin-permission"))){
                    PrintListMessages.printListMessages(Main.getInstance().getConfig().getStringList("admin-help-command"), player);
                }
                else{
                    PrintListMessages.printListMessages(Main.getInstance().getConfig().getStringList("help-command"), player);
                }
            }
        }
        return false;
    }
}
