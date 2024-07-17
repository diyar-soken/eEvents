package me.diyar.ezarevents;

import me.diyar.ezarevents.utils.assemble.Assemble;
import me.diyar.ezarevents.utils.assemble.AssembleStyle;
import lombok.Getter;
import me.diyar.ezarevents.commands.SumoCommand;
import me.diyar.ezarevents.handlers.SumoScoreboard;
import me.diyar.ezarevents.listeners.listener;
import me.diyar.ezarevents.utils.MatchState;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import static me.diyar.ezarevents.expansions.PlaceHolders.registerHook;
import static me.diyar.ezarevents.utils.MatchState.changeState;

@Getter
public final class Main extends JavaPlugin {

    public static File design;
    public static FileConfiguration designConfig;

    public static HashMap<String, String> EventState = new HashMap<>();
    public static ArrayList<UUID> inGame = new ArrayList<>();
    public static ArrayList<UUID> inMatch = new ArrayList<>();
    public static ArrayList<UUID> fighting = new ArrayList<>();
    public static ArrayList<UUID> spectating = new ArrayList<>();
    public static ArrayList<UUID> quitted = new ArrayList<>();

    @Getter
    private static Main instance;

    private void registerConfig() {
        saveResource("design.yml", false);
        design = new File(this.getDataFolder() + "/design.yml");
        designConfig = YamlConfiguration.loadConfiguration(design);
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void saveDesign(){
        try {
            Main.designConfig.save(Main.design);
            Main.designConfig.load(Main.design);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @EventHandler(priority= EventPriority.HIGH)
    private void registerScoreboard(){
        if(Main.getInstance().getConfig().getBoolean("SCOREBOARD")){
            Assemble assemble = new Assemble(this, new SumoScoreboard());
            assemble.setAssembleStyle(AssembleStyle.CUSTOM);
        }
    }

    @Override
    public void onEnable(){
        instance = this;
        registerHook();
        registerConfig();
        registerScoreboard();
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