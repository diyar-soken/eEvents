package me.diyar.ezarevents.handlers;

import me.diyar.ezarevents.utils.assemble.AssembleAdapter;
import me.diyar.ezarevents.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants.END;
import static me.diyar.ezarevents.expansions.PlaceHolders.*;
import static me.diyar.ezarevents.handlers.SumoHandler.*;
import static me.diyar.ezarevents.utils.MatchState.getState;

public class SumoScoreboard implements AssembleAdapter {
    @Override
    public String getTitle(Player player) {
        return Main.designConfig.getString("SCOREBOARD.TITLE").replace("§", "&");
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> strings = new ArrayList<>();
        if(isTournamentStarted()) {
            if(isInTournament(player)) {
                if (isFighting(player) || isInMatch(player)){
                    for(String sb : Main.designConfig.getStringList("SCOREBOARD.FIGHTING")){
                        String msg = sb;
                        msg = msg.replace("<players_inTournament>", String.valueOf(getTournamentSize()));
                        msg = msg.replace("<tournament_size>", String.valueOf(getTournamentMaxSize()));
                        msg = msg.replace("<sumo_state>", getStateMessage(player));
                        msg = msg.replace("<sumo_player_1>", String.valueOf(getPlayerFightingByPos(0).getName()));
                        msg = msg.replace("<sumo_player_2>", String.valueOf(getPlayerFightingByPos(1).getName()));
                        msg = msg.replace("<sumo_player_1_ping>", String.valueOf(playerPing(getPlayerFightingByPos(0))));
                        msg = msg.replace("<sumo_player_2_ping>", String.valueOf(playerPing(getPlayerFightingByPos(1))));
                        strings.add(msg);
                    }
                }
                else if(areFighting() && (getStateMessage(player).equalsIgnoreCase(IN_GAME) || isInSpectatorMode(player))){
                    for(String sb : Main.designConfig.getStringList("SCOREBOARD.FIGHTING")){
                        String msg = sb;
                        msg = msg.replace("<players_inTournament>", String.valueOf(getTournamentSize()));
                        msg = msg.replace("<tournament_size>", String.valueOf(getTournamentMaxSize()));
                        msg = msg.replace("<sumo_state>", getStateMessage(player));
                        msg = msg.replace("<sumo_player_1>", String.valueOf(getPlayerFightingByPos(0).getName()));
                        msg = msg.replace("<sumo_player_2>", String.valueOf(getPlayerFightingByPos(1).getName()));
                        msg = msg.replace("<sumo_player_1_ping>", String.valueOf(playerPing(getPlayerFightingByPos(0))));
                        msg = msg.replace("<sumo_player_2_ping>", String.valueOf(playerPing(getPlayerFightingByPos(1))));
                        strings.add(msg);
                    }
                }
                else{
                    for(String sb : Main.designConfig.getStringList("SCOREBOARD.LOBBY")){
                        String msg = sb;
                        msg = msg.replace("<players_inTournament>", String.valueOf(getTournamentSize()));
                        msg = msg.replace("<tournament_size>", String.valueOf(getTournamentMaxSize()));
                        msg = msg.replace("<sumo_state>", getStateMessage(player));
                        strings.add(msg);
                    }
                }
            }
        }
        return strings;
    }

    public String getStateMessage(Player player){
        String stato = null;
        if(getState().equalsIgnoreCase("LOBBY")){
            stato = LOBBY;
        }
        if(getState().equalsIgnoreCase("IN_GAME")){
            stato = IN_GAME;
        }
        if(getState().equalsIgnoreCase("END")){
            stato = END;
        }
        if(isInMatch((Player) player)){
            stato = STARTING;
        }
        if(isFighting((Player) player)){
            stato = FIGHTING;
        }
        if(isInSpectatorMode((Player) player)){
            stato = SPECTATING;
        }
        return stato;
    }
}
