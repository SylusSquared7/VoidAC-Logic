package com.sylus.voidaclogic;


import com.sylus.voidaclogic.managers.freezeManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


public class playerLeave implements Listener {

    public void playerLeaveHandeler(VoidACLogic plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private BanPlayerManager banPlayer;
    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event){
        // Checks if the player who left was frozen
        Player player = event.getPlayer();
        freezeManager frozen = new freezeManager();
        if (frozen.isPlayerFrozen(player.getUniqueId())){
            BanPlayerManager bans = new BanPlayerManager();
            bans.addToBan(player, "LEAVING WHILE FROZEN", 168);
        }
    }
}
