package me.diyar.ezarevents.handlers;

import me.diyar.ezarevents.Main;
import me.diyar.ezarevents.utils.CountdownTimer;
import org.bukkit.entity.Player;

import java.util.List;

import static me.diyar.ezarevents.events.SumoStart.*;
import static me.diyar.ezarevents.handlers.SumoHandler.*;
import static me.diyar.ezarevents.handlers.SumoHandler.resetPlayerInMatch;
import static me.diyar.ezarevents.utils.MatchState.changeState;
import static me.diyar.ezarevents.utils.MatchState.state.IN_GAME;
import static me.diyar.ezarevents.utils.MessagesUtil.*;

public class SumoCountdowns {

    public static void countdownTournament(){
        CountdownTimer timer = new CountdownTimer(Main.getInstance(),
                Main.getInstance().getConfig().getInt("TOURNAMENT-COUNTDOWN"),
                () -> broadcastMessageTime(Main.getInstance().getConfig().getInt("TOURNAMENT-COUNTDOWN")),
                () -> {
                    if (getTournamentSize()>=2) {
                        changeState(IN_GAME);
                        startMatch();
                    }
                    else{
                        sendMessageToTournament(printMessage(NO_ENOUGH_PLAYERS));
                        cancelTournament();
                    }
                },
                (t) -> {
                    List<Integer> list = Main.getInstance().getConfig().getIntegerList("TOURNAMENT-COUNTDOWN-FILTER");
                    if(list.contains(t.getSecondsLeft())){
                        if(t.getSecondsLeft()>=(Main.getInstance().getConfig().getInt("HOSTED-MESSAGE-DELAY")*2)){
                            broadcastMessageTime(t.getSecondsLeft());
                        }
                        sendMessageToTournament(printMessage(TOURNAMENT_COUNTDOWN_MESSAGE).replace("%time%", String.valueOf(t.getSecondsLeft())));
                    }
                }

        );

        timer.scheduleTimer();
    }

    public static void countdownPreMatch(Player player1, Player player2){
        CountdownTimer timer = new CountdownTimer(Main.getInstance(), Main.getInstance().getConfig().getInt("PREMATCH-COUNTDOWN"),
                () -> {
                    sendMessageToTournament(printMessage(PREMATCH_MESSAGE));
                },
                () -> {
                    matchStartedMessage(MATCH_INFO_MESSAGE,player1,player2);
                    addPlayerInMatch(player1,player2);
                    countdownMatch(player1,player2);
                },
                (t) -> {}

        );

        timer.scheduleTimer();
    }

    public static void countdownMatch(Player player1, Player player2){
        CountdownTimer timer = new CountdownTimer(Main.getInstance(), Main.getInstance().getConfig().getInt("MATCH-COUNTDOWN"),
                () -> {},
                () -> {
                    addPlayerFighting(player1,player2);
                    resetPlayerInMatch();
                    sendMessageToFighters(printMessage(MATCH_STARTED_MESSAGE));
                },
                (t) -> {
                    sendMessageToInMatch(printMessage(MATCH_COUNTDOWN_MESSAGE).replace("%time%", String.valueOf(t.getSecondsLeft())));
                }

        );

        timer.scheduleTimer();
    }
}
