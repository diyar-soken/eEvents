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

    public static void countdownTournamentBroadcast(int time){
        CountdownTimer timer = new CountdownTimer(Main.getInstance(),Main.getInstance().getConfig().getInt("broadcast-hosted-time"),
                () -> broadcastMessageTime(time),
                () -> {},
                (t) -> {}

        );

        timer.scheduleTimer();
    }

    public static void countdownTournament(){
        CountdownTimer timer = new CountdownTimer(Main.getInstance(),
                Main.getInstance().getConfig().getInt("time"),
                () -> broadcastMessageTime(Main.getInstance().getConfig().getInt("time")),
                () -> {
                    if (getTournamentSize()>=2) {
                        changeState(IN_GAME);
                        startMatch();
                    }
                    else{
                        sendMessageToTournament(printMessage("not-enough-players"));
                        cancelTournament();
                    }
                },
                (t) -> {
                    List<Integer> list = Main.getInstance().getConfig().getIntegerList("times");
                    if(list.contains(t.getSecondsLeft())){
                        if(t.getSecondsLeft()>=(Main.getInstance().getConfig().getInt("broadcast-hosted-time")*2)){
                            countdownTournamentBroadcast(t.getSecondsLeft());
                        }
                        sendMessageToTournament(printMessage("countdown-message").replace("%time%", String.valueOf(t.getSecondsLeft())));
                    }
                }

        );

        timer.scheduleTimer();
    }

    public static void countdownPreMatch(Player player1, Player player2){
        CountdownTimer timer = new CountdownTimer(Main.getInstance(), Main.getInstance().getConfig().getInt("prematch-countdown"),
                () -> {
                    sendMessageToTournament(printMessage("prematch-message"));
                },
                () -> {
                    matchStartedMessage("match-info",player1,player2);
                    addPlayerInMatch(player1,player2);
                    countdownMatch(player1,player2);
                },
                (t) -> {}

        );

        timer.scheduleTimer();
    }

    public static void countdownMatch(Player player1, Player player2){
        CountdownTimer timer = new CountdownTimer(Main.getInstance(), Main.getInstance().getConfig().getInt("match-countdown"),
                () -> {},
                () -> {
                    addPlayerFighting(player1,player2);
                    resetPlayerInMatch();
                    sendMessageToFighters(printMessage("match-started"));
                },
                (t) -> {
                    sendMessageToInMatch(printMessage("match-countdown-message").replace("%time%", String.valueOf(t.getSecondsLeft())));
                }

        );

        timer.scheduleTimer();
    }
}
