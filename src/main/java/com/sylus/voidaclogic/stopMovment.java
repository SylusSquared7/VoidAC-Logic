package com.sylus.voidaclogic;

import com.sylus.voidaclogic.managers.freezeManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.EventListener;

public class stopMovment implements EventListener {
    /*
public void stopMovementHandler(VoidACLogic plugin){
    Bukkit.getPluginManager().registerEvents(this, plugin);
}
*/

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent event){
        Player player = event.getPlayer();
        freezeManager frozen = new freezeManager();
        if (frozen.isPlayerFrozen(player.getUniqueId())){
            event.setCancelled(true);
        }
    }
}
