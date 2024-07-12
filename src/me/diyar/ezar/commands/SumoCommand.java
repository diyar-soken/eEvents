package me.diyar.ezar.commands;

import me.diyar.ezar.Main;
import me.diyar.ezar.events.SumoStart;
import me.diyar.ezar.handlers.SumoLocations;
import me.diyar.ezar.utils.MatchState;
import me.diyar.ezar.utils.MessagesUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.diyar.ezar.events.SumoStart.startTournament;
import static me.diyar.ezar.handlers.SumoHandler.*;
import static me.diyar.ezar.handlers.SumoLocations.isLocationSet;
import static me.diyar.ezar.handlers.SumoLocations.lobbyPoint;
import static me.diyar.ezar.utils.MatchState.*;
import static me.diyar.ezar.utils.MatchState.state.IN_GAME;
import static me.diyar.ezar.utils.MatchState.state.LOBBY;
import static me.diyar.ezar.utils.MessagesUtil.printListMessages;
import static me.diyar.ezar.utils.MessagesUtil.printMessage;
import static me.diyar.ezar.utils.PermissionUtils.adminpermission;
import static me.diyar.ezar.utils.PermissionUtils.hostpermission;

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
                    player.sendMessage(printMessage("reload"));
                }
                else if(args[0].equalsIgnoreCase("join")) {
                    if(verifyState(LOBBY)){
                        if(!isInTournament(player)){
                            addPlayerInTournament(player);
                            player.sendMessage(printMessage("joining"));
                        }
                        else{
                            player.sendMessage(printMessage("alreadyin"));
                        }
                    }
                    else if(verifyState(IN_GAME)){
                        player.sendMessage(printMessage("in-game"));
                    }
                    else{
                        player.sendMessage(printMessage("noevent"));
                    }
                }
                else if(args[0].equalsIgnoreCase("host")) {
                    if(player.hasPermission(hostpermission)){
                        if(isLocationSet()){
                            startTournament(player);
                        }
                        else{
                            player.sendMessage(printMessage("nospawnpointset"));
                        }
                    }
                    else{
                        player.sendMessage(printMessage("host-permission-message"));
                    }
                }
                else if(args[0].equalsIgnoreCase("setlobby")) {
                    if(player.hasPermission(adminpermission)){
                        lobbyPoint(playerLocation);
                        player.sendMessage(printMessage("setlobby"));
                    }
                    else{
                        player.sendMessage(printMessage("noperms"));
                    }
                }
                else if(args[0].equalsIgnoreCase("test")){
                    player.sendMessage("Giocatori nel torneo: " + String.valueOf(getTournamentSize()));
                    player.sendMessage("Stato torneo: " + getState());
                    player.sendMessage("Torneo iniziato: " + String.valueOf(isTournamentStarted()));
                    player.sendMessage("In parita: " + String.valueOf(isInMatch(player)));
                    player.sendMessage("Combattendo: " + String.valueOf(isFighting(player)));
                }
                else{
                    if(player.hasPermission(adminpermission)){
                        printListMessages("admin-help-command", player);
                    }
                    else{
                        printListMessages("help-command", player);
                    }
                }
            }
            else if(args.length == 2){
                if(args[0].equalsIgnoreCase("setspawn")) {
                    if(player.hasPermission(adminpermission)){
                        if(args[1].equalsIgnoreCase("1")){
                            SumoLocations.spawnPoints(playerLocation, Integer.parseInt(args[1]));
                            player.sendMessage(printMessage("spawnpoint1"));
                        }
                        else if(args[1].equalsIgnoreCase("2")){
                            SumoLocations.spawnPoints(playerLocation, Integer.parseInt(args[1]));
                            player.sendMessage(printMessage("spawnpoint2"));
                        }
                        else{
                            printListMessages("admin-help-command", player);
                        }
                    }
                    else{
                        player.sendMessage(printMessage("noperms"));
                    }
                }
                else{
                    if(player.hasPermission(adminpermission)){
                        printListMessages("admin-help-command", player);
                    }
                    else{
                        player.sendMessage(printMessage("noperms"));
                    }
                }
            }
            else{
                if(player.hasPermission(adminpermission)){
                    printListMessages("admin-help-command", player);
                }
                else{
                    printListMessages("help-command", player);
                }
            }
        }
        return false;
    }
}
