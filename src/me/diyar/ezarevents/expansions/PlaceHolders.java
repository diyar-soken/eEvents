package me.diyar.ezarevents.expansions;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.diyar.ezarevents.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.diyar.ezarevents.handlers.SumoHandler.*;
import static me.diyar.ezarevents.utils.MatchState.getState;

public class PlaceHolders extends PlaceholderExpansion {

    public static final String LOBBY = Main.getInstance().getConfig().getString("PLACEHOLDERS.LOBBY");
    public static final String IN_GAME = Main.getInstance().getConfig().getString("PLACEHOLDERS.IN-GAME");
    public static final String STARTING = Main.getInstance().getConfig().getString("PLACEHOLDERS.STARTING");
    public static final String FIGHTING = Main.getInstance().getConfig().getString("PLACEHOLDERS.FIGHTING");
    public static final String SPECTATING = Main.getInstance().getConfig().getString("PLACEHOLDERS.SPECTATING");
    private static final String END = Main.getInstance().getConfig().getString("PLACEHOLDERS.END");

    @Override
    public @NotNull String getIdentifier() {
        return "sumo";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Diyar";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("isStarted")) {
            return String.valueOf(isTournamentStarted());
        }
        if(params.equalsIgnoreCase("isInTournament")) {
            return String.valueOf(isInTournament((Player) player));
        }
        if(params.equalsIgnoreCase("players")) {
            return String.valueOf(getTournamentSize());
        }
        if(params.equalsIgnoreCase("maxPlayers")) {
            return String.valueOf(getTournamentMaxSize());
        }
        if(params.equalsIgnoreCase("state")) {
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
        if(params.equalsIgnoreCase("player1")) {
            return String.valueOf(getPlayerFightingByPos(0).getName());
        }
        if(params.equalsIgnoreCase("player2")) {
            return String.valueOf(getPlayerFightingByPos(1).getName());
        }
        if(params.equalsIgnoreCase("fighting")) {
            return String.valueOf(areFighting());
        }
        if(params.equalsIgnoreCase("player1_ping")) {
            return String.valueOf(playerPing(getPlayerFightingByPos(0)));
        }
        if(params.equalsIgnoreCase("player2_ping")) {
            return String.valueOf(playerPing(getPlayerFightingByPos(1)));
        }

        return null;
    }

    public static void registerHook(){
        new PlaceHolders().register();
    }
}
