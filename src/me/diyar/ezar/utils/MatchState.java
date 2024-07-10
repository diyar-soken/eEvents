package me.diyar.ezar.utils;

import java.util.HashMap;

public class MatchState {

    public static HashMap<String, String> EventState = new HashMap<>();

    public enum state{
        LOBBY,IN_GAME,END
    }

    public static void changeState(state newState){
        EventState.clear();
        EventState.put("Sumo", newState.toString());
    }

    public static boolean verifyState(String state){
        return EventState.containsKey(state);
    }

    public static String getState(){
        return EventState.get("Sumo");
    }

    public static boolean isTournamentStarted(){
        if(!getState().equals("END")){
            return true;
        }
        return false;
    }
}
