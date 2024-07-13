package me.diyar.ezarevents;

import lombok.Getter;
import me.diyar.ezarevents.commands.SumoCommand;
import me.diyar.ezarevents.listeners.listener;
import me.diyar.ezarevents.utils.MatchState;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import static me.diyar.ezarevents.utils.MatchState.changeState;

@Getter
public final class Main extends JavaPlugin {

    public static HashMap<String, String> EventState = new HashMap<>();
    public static ArrayList<UUID> inGame = new ArrayList<>();
    public static ArrayList<UUID> inMatch = new ArrayList<>();
    public static ArrayList<UUID> fighting = new ArrayList<>();

    @Getter
    private static Main instance;

    private void registerConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onEnable(){
        instance = this;
        registerConfig();
        registerCommands();
        registerListeners();
        changeState(MatchState.state.END);
    }

    @Override
    public void onDisable(){
        instance = null;
    }

    public static Main getInstance(){
        return instance;
    }

    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new listener(), this);
    }

    private void registerCommands(){
        Arrays.asList(new SumoCommand()).forEach(command -> this.commandsReg(command, getName()));
    }

    public void commandsReg(Command cmd, String fallbackPrefix) {
        MinecraftServer.getServer().server.getCommandMap().register(cmd.getName(), fallbackPrefix, cmd);
    }

}