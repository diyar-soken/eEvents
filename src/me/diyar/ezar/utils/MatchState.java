package me.diyar.ezar.utils;

import static me.diyar.ezar.Main.EventState;
import static me.diyar.ezar.utils.MatchState.state.*;

public class MatchState {

    public enum state{
        LOBBY,IN_GAME,END
    }

    public static void changeState(state newState){
        EventState.put("Sumo", String.valueOf(newState));
    }

    public static boolean verifyState(state state){
        return getState().equalsIgnoreCase(String.valueOf(state));
    }

    public static String getState(){
        return EventState.get("Sumo");
    }

    public static boolean isTournamentStarted(){
        if(getState().equalsIgnoreCase(String.valueOf(LOBBY)) || getState().equalsIgnoreCase(String.valueOf(IN_GAME))){
            return true;
        }
        return false;
    }
}
