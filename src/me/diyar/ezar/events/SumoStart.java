package me.diyar.ezar.events;

import me.diyar.ezar.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

import static me.diyar.ezar.Main.inGame;
import static me.diyar.ezar.handlers.SumoCountdowns.countdown;
import static me.diyar.ezar.handlers.SumoCountdowns.countdownMatch;
import static me.diyar.ezar.handlers.SumoHandler.*;
import static me.diyar.ezar.utils.MatchState.changeState;
import static me.diyar.ezar.utils.MatchState.isTournamentStarted;
import static me.diyar.ezar.utils.MatchState.state.*;
import static me.diyar.ezar.utils.MessagesUtil.*;

public class SumoStart {

    public static void startTournament(Player player){
        if(!isTournamentStarted()){
            changeState(LOBBY);
            addHostertoList(player);
            addPlayerInTournament(player);
            countdown();
        }
        else{
            player.sendMessage(MessagesUtil.printMessage("already-on"));
        }
    }

    public static void startedTournament(){
        if(!inGame.isEmpty()){
            Player randomPlayer1 = Bukkit.getPlayer(randomPlayer());
            Player randomPlayer2 = Bukkit.getPlayer(randomPlayer());
            while(randomPlayer1 == randomPlayer2){
                randomPlayer2 = Bukkit.getPlayer(randomPlayer());
            }

            addPlayerInMatch(randomPlayer1,randomPlayer2);
            countdownMatch(randomPlayer1,randomPlayer2);

            for (UUID uuid : inGame) {
                Player players = Bukkit.getPlayer(uuid);
                matchStartedMessage("match-info",randomPlayer1,randomPlayer2,players);
            }
        }
    }
}
