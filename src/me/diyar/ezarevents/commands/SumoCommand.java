package me.diyar.ezarevents.commands;

import me.diyar.ezarevents.Main;
import me.diyar.ezarevents.handlers.SumoLocationsHandler;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.diyar.ezarevents.events.SumoStart.startTournament;
import static me.diyar.ezarevents.handlers.SumoHandler.*;
import static me.diyar.ezarevents.handlers.SumoLocationsHandler.*;
import static me.diyar.ezarevents.utils.MatchState.*;
import static me.diyar.ezarevents.utils.MatchState.state.IN_GAME;
import static me.diyar.ezarevents.utils.MatchState.state.LOBBY;
import static me.diyar.ezarevents.utils.MessagesUtil.*;
import static me.diyar.ezarevents.utils.PermissionUtils.*;

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
                    Main.getInstance().saveDesign();
                    player.sendMessage(printMessage(RELOAD));
                }
                else if(args[0].equalsIgnoreCase("join")) {
                    if(player.hasPermission(joinpermission)){
                        if(verifyState(LOBBY)){
                            if(!isInTournament(player)){
                                if(getTournamentSize()<getTournamentMaxSize()){
                                    addPlayerInTournament(player);
                                    sendMessageToTournament(printMessage(TOURNAMENT_JOIN).replace("%player%", player.getName()).replace("%inTournament%", String.valueOf(getTournamentSize())).replace("%maxTournament%", printMessage("MAX-SLOT")));
                                }
                            }
                            else{
                                player.sendMessage(printMessage(INEVENT));
                            }
                        }
                        else if(verifyState(IN_GAME)){
                            player.sendMessage(printMessage(STARTED));
                        }
                        else{
                            player.sendMessage(printMessage(NO_HOSTED_EVENT));
                        }
                    }
                    else{
                        player.sendMessage(printMessage(NOPERMISSION));
                    }
                }
                else if(args[0].equalsIgnoreCase("host")) {
                    if(player.hasPermission(hostpermission)){
                        if(isLocationSet()){
                            startTournament(player);
                        }
                        else{
                            player.sendMessage(printMessage(NOSPAWNPOINTS));
                        }
                    }
                    else{
                        player.sendMessage(printMessage(HOST_NOPERMISSION));
                    }
                }
                else if(args[0].equalsIgnoreCase("spec") || args[0].equalsIgnoreCase("spect") || args[0].equalsIgnoreCase("spectate")) {
                    if(player.hasPermission(specpermission)){
                        if(isTournamentStarted()){
                            if(!isInTournament(player)){
                                addSpectatorExternal(player);
                                player.sendMessage(printMessage(SPEC_MESSAGE));
                            }
                            else{
                                player.sendMessage(printMessage(INEVENT));
                            }
                        }
                        else{
                            player.sendMessage(printMessage(NO_HOSTED_EVENT));
                        }
                    }
                    else{
                        player.sendMessage(printMessage(NOPERMISSION));
                    }
                }
                else if(args[0].equalsIgnoreCase("setlobby")) {
                    if(player.hasPermission(adminpermission)){
                        spawnPoints(playerLocation, "Lobby");
                        player.sendMessage(printMessage(SPAWNPOINT_1));
                    }
                    else{
                        player.sendMessage(printMessage(NOPERMISSION));
                    }
                }
                else if(args[0].equalsIgnoreCase("stop")){
                    if (player.hasPermission(adminpermission)){
                        if(isTournamentStarted()){
                            sendMessageToTournament(printMessage(TOURNAMENT_STOP_MESSAGE).replace("%player%", player.getName()));
                            cancelTournament();
                        }
                        else{
                            player.sendMessage("noevent");
                        }
                    }
                    else{
                        player.sendMessage(printMessage("noperms"));
                    }
                }
                else if(args[0].equalsIgnoreCase("test")){
                    player.sendMessage(String.valueOf(isInTournament(player)));
                    player.sendMessage(String.valueOf(isTournamentStarted()));
                    player.sendMessage(String.valueOf(getState()));
                    player.sendMessage(String.valueOf(getPlayerFighting()));
                    player.sendMessage(String.valueOf(getHoster()));
                    player.sendMessage(String.valueOf(areFighting()));
                }
                else{
                    if(player.hasPermission(adminpermission)){
                        printListMessages(ADMIN_HELP, player);
                    }
                    else{
                        printListMessages(HELP, player);
                    }
                }
            }
            else if(args.length == 2){
                if(args[0].equalsIgnoreCase("setspawn")) {
                    if(player.hasPermission(adminpermission)){
                        if(args[1].equalsIgnoreCase("1")){
                            SumoLocationsHandler.spawnPoints(playerLocation, String.valueOf(Integer.parseInt(args[1])));
                            player.sendMessage(printMessage(SPAWNPOINT_1));
                        }
                        else if(args[1].equalsIgnoreCase("2")){
                            SumoLocationsHandler.spawnPoints(playerLocation, String.valueOf(Integer.parseInt(args[1])));
                            player.sendMessage(printMessage(SPAWNPOINT_2));
                        }
                        else if(args[1].equalsIgnoreCase("spec")){
                            SumoLocationsHandler.spawnPoints(playerLocation, args[1]);
                            player.sendMessage(printMessage(SPAWNPOINT_SPEC));
                        }
                        else{
                            printListMessages(ADMIN_HELP, player);
                        }
                    }
                    else{
                        player.sendMessage(printMessage(NOPERMISSION));
                    }
                }
                else{
                    if(player.hasPermission(adminpermission)){
                        printListMessages(ADMIN_HELP, player);
                    }
                    else{
                        player.sendMessage(printMessage(NOPERMISSION));
                    }
                }
            }
            else{
                if(player.hasPermission(adminpermission)){
                    printListMessages(ADMIN_HELP, player);
                }
                else{
                    printListMessages(HELP, player);
                }
            }
        }
        return false;
    }
}
