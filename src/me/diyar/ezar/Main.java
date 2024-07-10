package me.diyar.ezar;

import lombok.Getter;
import me.diyar.ezar.commands.SumoCommand;
import me.diyar.ezar.listeners.WaterListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

@Getter
public final class Main extends JavaPlugin {

    public static HashMap<String, String> EventState = new HashMap<>();
    public static ArrayList<UUID> inGame = new ArrayList<>();

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
    }

    @Override
    public void onDisable(){
        instance = null;
    }

    public static Main getInstance(){
        return instance;
    }

    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new WaterListener(), this);
    }

    private void registerCommands(){
        Arrays.asList(new SumoCommand()).forEach(command -> this.commandsReg(command, getName()));
    }

    public void commandsReg(Command cmd, String fallbackPrefix) {
        MinecraftServer.getServer().server.getCommandMap().register(cmd.getName(), fallbackPrefix, cmd);
    }

}