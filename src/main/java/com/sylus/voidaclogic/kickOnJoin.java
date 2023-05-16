package com.sylus.voidaclogic;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;


public class kickOnJoin implements Listener {

    public void kickOnJoinHandeler(VoidACLogic plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        BanPlayerManager bans = new BanPlayerManager();
        Player player = event.getPlayer();
        ArrayList<String> keys = bans.getActiveBanKey(player.getUniqueId());
        long biggestTime = -1;
        String biggestKey = "";

        for (String banKey : keys) {
            long timeRemainingHours = bans.getRemainingBanTime(player.getUniqueId(), banKey);
            if (timeRemainingHours == -1) {
                biggestTime = -1;
                biggestKey = banKey;
                break; // Found permanent ban, exit the loop
            }
            if (timeRemainingHours > biggestTime) {
                biggestTime = timeRemainingHours;
                biggestKey = banKey;
            }
        }

        if (biggestTime == 0) { // player is not banned

        } else if (biggestTime == -1) { // Player is permanently banned
            player.kickPlayer("§cYou are permanently banned for: " + bans.banReason(player.getUniqueId(), biggestKey) + "\n§6Find out more: §3https://example.com/");
        } else if (biggestTime >= 24) { // The player has one or more days left on their ban
            player.kickPlayer("§cYou are temporarily banned from this server\n§7Duration: §f" + biggestTime / 24 + " §7days\n§7Reason: §f" + bans.banReason(player.getUniqueId(), biggestKey) + "\n§6Find out more: §3https://example.com/");
        } else { // The player has less than a day left on their ban
            player.kickPlayer("§cYou are temporarily banned from this server\n§7Duration: §f" + biggestTime + " §7hours\n§7Reason: §f" + bans.banReason(player.getUniqueId(), biggestKey) + "\n§6Find out more: §3https://example.com/");
        }
    }
}


