package com.sylus.voidaclogic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class punishMenuClick extends punishMenu implements Listener {

    public void punishMenuClickHandeler(VoidACLogic plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private final Player sender;
    private final Player target;


    public punishMenuClick(Player sender, Player target){
        this.sender = sender;
        this.target = target;
    }

    String[] punishments ={"malicious hacks", "7", "14", "20", "-1", "non malicious hacks", "4", "10", "-1", "Xray or similar", "w", "2", "7", "15", "-1", "Inappropriate Skin and or cape", "1", "5",
            "-1", "advertising", "m1", "m5", "m15", "7", "15", "-1", "IRL trading", "15", "-1", "Encouraging suicide / Death Threats", "m7", "7", "15", "-1", "hate speech", "m7", "m15", "m20", "7", "-1",
            "command spam", "1", "5", "20", "illegal links in chat", "m20", "m30", "7", "15", "-1"};


    @EventHandler
    public void firstMenuClick(InventoryClickEvent event){
        Player target = (Player) event.getWhoClicked();
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();
        if (inv.equals(getInventory("punishMenu"))){    //event.getView().getTitle().contains("Punish:")) {
            event.setCancelled(true);
            try{
                switch (event.getCurrentItem().getType()) {
                    case BOOK:
                        history(target, target);
                        break;
                    case IRON_AXE:
                        newPunishment(target);
                        break;
                    case IRON_SHOVEL:
                        tools(target);
                        break;
                    case BARRIER:
                        player.closeInventory();
                        break;
                }
            } catch (NullPointerException e){

            }
        } else if (event.getView().getTitle().equals("§cSelect a punishment")) {
            try {
                switch (event.getCurrentItem().getItemMeta().getDisplayName()){
                    case "test":
                }
            }catch (NullPointerException e){

            }
        }


    }
}
