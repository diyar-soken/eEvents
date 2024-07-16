package me.diyar.ezarevents.expansions;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.diyar.ezarevents.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.diyar.ezarevents.handlers.SumoHandler.*;
import static me.diyar.ezarevents.utils.MatchState.getState;
import static me.diyar.ezarevents.utils.MatchState.isTournamentStarted;

public class PlaceHolders extends PlaceholderExpansion {
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
                stato = Main.getInstance().getConfig().getString("placeholders.lobby");
            }
            if(getState().equalsIgnoreCase("IN_GAME")){
                stato = Main.getInstance().getConfig().getString("placeholders.in-game");
            }
            if(getState().equalsIgnoreCase("END")){
                stato = Main.getInstance().getConfig().getString("placeholders.end");
            }
            if(isInMatch((Player) player)){
                stato = Main.getInstance().getConfig().getString("placeholders.starting");
            }
            if(isFighting((Player) player)){
                stato = Main.getInstance().getConfig().getString("placeholders.fighting");
            }
            if(isInSpectatorMode((Player) player)){
                stato = Main.getInstance().getConfig().getString("placeholders.spectating");
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

        return null;
    }

    public static void registerHook(){
        new PlaceHolders().register();
    }
}
