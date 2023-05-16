package com.sylus.voidaclogic.commands;

import com.sylus.voidaclogic.managers.freezeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;


public class freezeCommand implements CommandExecutor {
    int startArg;
    StringBuilder builder = new StringBuilder();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) {

            sender.sendMessage(ChatColor.RED + "Please specify a player");

        } else {

            freezeManager frozen = new freezeManager();
            Player target = Bukkit.getPlayerExact(args[0]);
            if(target == null){
                sender.sendMessage(ChatColor.RED + "Player not found or does not exist");
                return true;
            }
            UUID targetUUID = target.getUniqueId();


            if (target.isOnline()) {
                if (frozen.isPlayerFrozen(targetUUID)) {
                    frozen.unFreezePlayer(targetUUID);
                    return true;
                } else {

                    startArg = 1;
                    int endArg = args.length;

                    for(int i = startArg; i< endArg; i++){
                        builder.append(args[i]).append(args.length > (i + 1) ? " " : "");
                    }

                    String reason = builder.toString();
                    frozen.freezePlayer(targetUUID);
                    if (reason.equals("")){
                        reason = "not given";
                    }
                    target.playSound(target.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1f, 1f);
                    target.sendTitle("§cYou are frozen, DO NOT LEAVE", "§cReason: §f" + reason, 20 , 200, 20);
                    target.sendMessage(ChatColor.RED + "YOU WILL BE BANNED IF YOU LEAVE");
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Player is not online");
            }
        }
    return true;}
    }

