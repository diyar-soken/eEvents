package me.diyar.ezar.handlers;

import me.diyar.ezar.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static me.diyar.ezar.Main.fighting;
import static me.diyar.ezar.Main.inGame;
import static me.diyar.ezar.handlers.SumoLocations.getLobbyLocation;
import static me.diyar.ezar.handlers.SumoLocations.getSpawnPointLocation;
import static me.diyar.ezar.utils.MatchState.changeState;
import static me.diyar.ezar.utils.MatchState.state.END;
import static me.diyar.ezar.utils.MessagesUtil.printMessage;

public class SumoHandler {
    public static UUID hoster;
    public static HashMap<Player, ItemStack[]> itemhash = new HashMap<Player, ItemStack[]>();

    public static boolean isInTournament(Player player){
        return inGame.contains(player.getUniqueId());
    }

    public static void addPlayerInTournament(Player player){
        inGame.add(player.getUniqueId());
        memorizeInventory(player);
        giveTournamentInventory(player);
        Location loc = SumoLocations.getLobbyLocation();
        player.teleport(loc);
    }

    public static void removePlayerInTournament(Player player){
        inGame.remove(player.getUniqueId());
        restoreInventory(player);
        Location loc = SumoLocations.getLobbyLocation();
        player.teleport(loc);
    }

    public static boolean isInMatch(Player player){
        if(fighting.contains(player.getUniqueId())){
            return true;
        }
        return false;
    }

    public static void addPlayerInMatch(Player player, Player player2){
        fighting.add(player.getUniqueId());
        fighting.add(player2.getUniqueId());
        Location position1 = getSpawnPointLocation(1);
        Location position2 = getSpawnPointLocation(2);
        player.getInventory().clear();
        player2.getInventory().clear();
        player.teleport(position1);
        player2.teleport(position2);
    }

    public static void removePlayerInMatch(Player player){
        fighting.remove(player.getUniqueId());
        Location loc = SumoLocations.getLobbyLocation();
        player.teleport(loc);
        giveTournamentInventory(player);
    }

    public static Player getPlayerInMatch(){
        Player player = null;
        for(UUID players : fighting){
            player = Bukkit.getPlayer(players);
        }
        return player;
    }

    public static void resetPlayerInMatch(){
        fighting.clear();
    }

    public static void addHostertoList(Player player){
        hoster = player.getUniqueId();
    }

    public static Player getHoster(){
        return Bukkit.getPlayer(hoster);
    }

    public static int getTournamentSize(){
        return inGame.size();
    }

    public static UUID randomPlayer() {
        return inGame.get(ThreadLocalRandom.current().nextInt(0, getTournamentSize()));
    }

    public static void giveTournamentInventory(Player player){
        PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();
        ItemStack leaveEvent = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta leaveEventMeta = leaveEvent.getItemMeta();
        leaveEventMeta.setDisplayName(printMessage("leaveitem"));
        playerInventory.setItem(Main.getInstance().getConfig().getInt("leaveitem-position"),leaveEvent);
    }

    public static void memorizeInventory(Player player){
        ItemStack[] playerinv = player.getInventory().getContents();
        itemhash.put(player, playerinv);
    }

    public static void restoreInventory(Player player){
        if(itemhash.containsKey(player)){
            ItemStack[] items = itemhash.get(player);
            PlayerInventory inv = player.getInventory();
            for(ItemStack item : items){
                if(item != null) {
                    player.getInventory().addItem(item);
                }
            }
        }
    }

    public static void sendMessageToTournament(String message){
        for (UUID uuid : inGame) {
            Player player = Bukkit.getPlayer(uuid);
            player.sendMessage(message);
        }
    }

    public static void resetPlayerInTournament(){
        inGame.clear();
    }

    public static Player winner(){
        Player player = null;
        for (UUID uuid : inGame) {
            player = Bukkit.getPlayer(uuid);
        }
        return player;
    }

    public static void cancelTournament(){
        for (UUID uuid : inGame) {
            Player player = Bukkit.getPlayer(uuid);
            restoreInventory(player);
            player.teleport(getLobbyLocation());
        }
        inGame.clear();
        resetPlayerInMatch();
        hoster = null;
        changeState(END);
    }
}
