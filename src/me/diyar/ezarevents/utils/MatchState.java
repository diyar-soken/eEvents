package me.diyar.ezarevents.utils;

import static me.diyar.ezarevents.Main.EventState;
import static me.diyar.ezarevents.utils.MatchState.state.*;

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

}
