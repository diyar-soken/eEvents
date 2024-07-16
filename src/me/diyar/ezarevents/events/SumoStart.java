package me.diyar.ezarevents.events;

import me.diyar.ezarevents.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static me.diyar.ezarevents.Main.inGame;
import static me.diyar.ezarevents.handlers.SumoCountdowns.*;
import static me.diyar.ezarevents.handlers.SumoHandler.*;
import static me.diyar.ezarevents.utils.MatchState.changeState;
import static me.diyar.ezarevents.utils.MatchState.state.*;

public class SumoStart {

    public static void startTournament(Player player){
        if(!isTournamentStarted()){
            changeState(LOBBY);
            addHostertoList(player);
            addPlayerInTournament(player);
            countdownTournament();
        }
        else{
            player.sendMessage(MessagesUtil.printMessage("already-on"));
        }
    }

    public static void startMatch(){
        if(!inGame.isEmpty()){
            Player randomPlayer1 = Bukkit.getPlayer(randomPlayer());
            Player randomPlayer2 = Bukkit.getPlayer(randomPlayer());
            while(randomPlayer1 == randomPlayer2){
                randomPlayer2 = Bukkit.getPlayer(randomPlayer());
            }

            countdownPreMatch(randomPlayer1,randomPlayer2);
        }
    }
}
