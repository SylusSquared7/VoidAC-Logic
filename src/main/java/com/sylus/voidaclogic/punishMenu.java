package com.sylus.voidaclogic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Objects;

public class punishMenu implements EventListener {

    private Player target;
    private Inventory punishMenu;
    private Inventory historyInv;
    private Inventory toolsInv;
    public void showMenu(Player player, Player target) {
        if (target == null){
            player.sendMessage(ChatColor.RED + "TARGET NOT PROVIDED");
            System.out.println("TARGET IS NULL");
            return;
        }
        this.target = target;
        firstPunishMenu(player, target);

    }
    private void firstPunishMenu(Player player, Player target) {
        String targetName = target.getName();
        String invName = "§6Punish: §c" + targetName;
        punishMenu = Bukkit.createInventory(player, 9 * 4, invName);

        // Creates the lore for the players skull
        List <String> skullLore = new ArrayList<>();

        skullLore.add("§eUUID: §f" + target.getUniqueId());
        if (target.getPing() > 500){
            skullLore.add("§ePing: §4" + target.getPing());
        } else if (target.getPing() > 200) {
            skullLore.add("§ePing: " + target.getPing());
        } else {
            skullLore.add("§ePing: §a" + target.getPing());
        }

        if (target.getAllowFlight()){
            skullLore.add("§eAllowed flight: §a" + target.getAllowFlight());
        } else {
            skullLore.add("§eAllowed flight: §4" + target.getAllowFlight());
        }


        final ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(target);
        meta.setDisplayName(ChatColor.RED + targetName);
        meta.setLore(skullLore);
        skull.setItemMeta(meta);



        // creates the punishment menu
        punishMenu.setItem(10, getItem(new ItemStack(Material.BOOK), "&cPunishment History", "&6Veiw the punishment history for: &c" + targetName));
        punishMenu.setItem(12, getItem(new ItemStack(Material.IRON_AXE), "&cNew punishment", "&eCreate a new punishment for: &c" + targetName));
        punishMenu.setItem(14, skull);
        punishMenu.setItem(16, getItem(new ItemStack(Material.IRON_SHOVEL), "&cTools", "&eTools to use on the player"));
        punishMenu.setItem(22, getItem(new ItemStack(Material.BARRIER), "&Close", "&eClose the menu"));
        player.openInventory(punishMenu);
    }

    private void historyMenu(Player player, Player target){

    }


        private ItemStack getItem(ItemStack item, String name, String ... lore){
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

            List<String> lores = new ArrayList<>();
            for(String s : lore){
                lores.add(ChatColor.translateAlternateColorCodes('&', s));
            }
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.setLore(lores);
            item.setItemMeta(meta);
            return item;
        }

        public void history(Player target, Player sender){
        target.sendMessage(ChatColor.GOLD + "You clicked the book");
            String targetName = target.getName();
            String invName = "§6History for: §c" + targetName;
            historyInv = Bukkit.createInventory(sender, 9 * 4, invName);

            historyInv.setItem(10, getItem(new ItemStack(Material.BARRIER), "Test", "[Expired]"));
            sender.openInventory(historyInv);

        }


        public void newPunishment (Player target){
        target.sendMessage("TEST");
            String[] punishments = {"Malicious hacks", "Non malicious hacks", "Xray or similar", "Inappropriate Skin and or cape", "Advertising", "IRL trading", "Encouraging suicide / Death Threats", "Hate speech",
                    "Command spam", "Illegal links in chat"};
            Inventory inv = Bukkit.createInventory(target, 9 * 4, "§cSelect a punishment");
            for (int i = 0; i < 10; i++){
                inv.setItem(i, getItem(new ItemStack(Material.CHEST), "§8" + punishments[i], ""));
            }
            inv.setItem(10, getItem(new ItemStack(Material.ENDER_CHEST), "This is an echest", ""));
            inv.setItem(11, getItem(new ItemStack(Material.ENDER_CHEST), "Look an echest", ""));
            target.openInventory(inv);
        }
        public void tools (Player target){

        }
        public Player getTarget(){
        return target;
        }

        public Inventory getInventory(String invName){
        if (Objects.equals(invName, "punishMenu")){
            return punishMenu;
        } else if (Objects.equals(invName, "historyMenu")) {
            return historyInv;
        }
        return null;
        }



    }

