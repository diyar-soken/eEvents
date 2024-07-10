package me.diyar.ezar.utils;

import java.util.HashMap;

import static me.diyar.ezar.Main.EventState;
import static me.diyar.ezar.utils.MatchState.state.IN_GAME;

public class MatchState {

    public enum state{
        LOBBY,IN_GAME,END
    }

    public static void changeState(state newState){
        EventState.put("Sumo", newState.toString());
    }

    public static boolean verifyState(state state){
        return EventState.containsKey(state.toString());
    }

    public static String getState(){
        return EventState.get("Sumo");
    }

    public static boolean isTournamentStarted(){
        if (verifyState(state.LOBBY) || verifyState(IN_GAME)) {
            return true;
        }
        return false;
    }
}
