package me.diyar.ezar.handlers;

import me.diyar.ezar.Main;
import me.diyar.ezar.utils.CountdownTimer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

import static me.diyar.ezar.Main.inGame;
import static me.diyar.ezar.events.SumoStart.*;
import static me.diyar.ezar.handlers.SumoHandler.*;
import static me.diyar.ezar.handlers.SumoHandler.resetPlayerInMatch;
import static me.diyar.ezar.utils.MatchState.changeState;
import static me.diyar.ezar.utils.MatchState.state.IN_GAME;
import static me.diyar.ezar.utils.MessagesUtil.*;

public class SumoCountdowns {

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
                        broadcastMessage("not-enough-players");
                        cancelTournament();
                    }
                },
                (t) -> {
                    List<Integer> list = Main.getInstance().getConfig().getIntegerList("times");

                    if(list.contains(t.getSecondsLeft())){
                        broadcastMessageTime(t.getSecondsLeft());
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
                    sendMessageToInMatch(printMessage("match-started"));
                },
                (t) -> {
                    sendMessageToInMatch(printMessage("match-countdown-message").replace("%time%", String.valueOf(t.getSecondsLeft())));
                }

        );

        timer.scheduleTimer();
    }
}
