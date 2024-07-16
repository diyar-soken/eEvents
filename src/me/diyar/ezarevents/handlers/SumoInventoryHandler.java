package me.diyar.ezarevents.handlers;

import me.diyar.ezarevents.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

import static me.diyar.ezarevents.utils.MessagesUtil.printMessage;

public class SumoInventoryHandler {
    public static HashMap<Player, ItemStack[]> itemhash = new HashMap<Player, ItemStack[]>();
    public static HashMap<Player, ItemStack[]> armorhash = new HashMap<Player, ItemStack[]>();
    // UTIL INVENTORY MANAGER

    public static void giveTournamentInventory(Player player){
        PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();
        playerInventory.setArmorContents(null);
        ItemStack leaveEvent = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta leaveEventMeta = leaveEvent.getItemMeta();
        leaveEventMeta.setDisplayName(printMessage("leaveitem"));
        leaveEvent.setItemMeta(leaveEventMeta);
        playerInventory.setItem(Main.getInstance().getConfig().getInt("leaveitem-position")-1,leaveEvent);
    }

    public static void memorizeInventory(Player player){
        ItemStack[] playerinv = player.getInventory().getContents();
        ItemStack[] playerInvArmor = player.getInventory().getArmorContents();
        itemhash.put(player, playerinv);
        armorhash.put(player, playerInvArmor);
    }

    public static void restoreInventory(Player player){
        if(itemhash.containsKey(player)){
            ItemStack[] items = itemhash.get(player);
            player.getInventory().clear();
            for(ItemStack item : items){
                if(item != null) {
                    player.getInventory().addItem(item);
                }
            }
        }
        if(armorhash.containsKey(player)){
            ItemStack[] armor = armorhash.get(player);
            player.getInventory().setArmorContents(armor);
        }

    }
}
