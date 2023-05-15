package com.sylus.voidaclogic.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class freezeManager {

    private final List<UUID> frozenPlayers = new ArrayList<>();

    public void freezePlayer(UUID playerUUID) {
        if (!frozenPlayers.contains(playerUUID)) {
            frozenPlayers.add(playerUUID);
        }
    }

    public void unFreezePlayer(UUID playerUUID) {
        frozenPlayers.remove(playerUUID);
        Player player = Bukkit.getPlayer(playerUUID);
        try {
            if (player != null && player.isOnline()) {
                player.sendTitle("§aYou have been unfrozen, thanks for your patience", "§You may now continue", 1, 5, 1);
                player.sendMessage(ChatColor.GREEN + "Thanks for your patience :)");
            }
        } catch (NullPointerException e) {

        }
    }

    public boolean isPlayerFrozen(UUID playerUUID) {
            System.out.println(frozenPlayers);
        return frozenPlayers.contains(playerUUID);
    }



    /*
    private final HashMap<UUID, Boolean> frozenPlayers = new HashMap<>();

    public void freezePlayer(UUID playerUUID) {
        frozenPlayers.put(playerUUID, true);
        Player player = Bukkit.getPlayer(playerUUID);
        player.sendMessage("You have been added to the thingy");
    }
    public void unFreezePlayer(UUID playerUUID) {
        frozenPlayers.remove(playerUUID, true);
        try {
        Player player = Bukkit.getPlayer(playerUUID);
        player.sendMessage("test");

            if (player.isOnline()) {
                player.sendTitle("§aYou have been unfrozen, thanks for your patience", "§You may now continue", 1, 5, 1);
                player.sendMessage(ChatColor.GREEN + "Thanks for your patience :)");
            }
        }catch (NullPointerException e){

        }
    }

    public boolean isPlayerFrozen(UUID playerUUID) {
        return frozenPlayers.containsKey(playerUUID);
    }

     */
}
