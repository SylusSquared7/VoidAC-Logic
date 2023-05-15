package com.sylus.voidaclogic.commands;


import com.sylus.voidaclogic.BanPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class banCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, java.lang.String s, java.lang.String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage("Only players can use this command");
            return true;
        }

        BanPlayerManager bans = new BanPlayerManager();
        int startArg;
        int endArg = args.length;
        StringBuilder builder = new StringBuilder();

        Player target = Bukkit.getPlayerExact(java.lang.String.valueOf(args[0]));
        if (target == null){
            sender.sendMessage(ChatColor.RED + "Please specify a player");
            return true;
        }





        bans.addToBan(target, "TEST", Long.MAX_VALUE);
        Bukkit.broadcastMessage(target + " Has been banned");

        /*
        BanPlayerManager bansManager = new BanPlayerManager(new File("anticheatBans.yml"));
        StringBuilder builder = new StringBuilder();

        int startArg;
        int endArg = args.length;

        if (alias.equalsIgnoreCase("punish")) {
            if (args.length == 0 || args.length == 1) {
                sender.sendMessage(ChatColor.RED + "Please specify a player and a reason");
            }
            // /punish [-s for silent (optional) [player name] [reason]
            // silent means that a ban message will not be broadcast to the server
            if (args[0].equals("-s")) { // Make this make more sense soon
                startArg = 1;
                Player target = Bukkit.getPlayerExact(args[1]);
                for(int i = startArg; i< endArg; i++){
                    builder.append(args[i] + (args.length > (i+1) ? " " : ""));
                }

                String reason = builder.toString();
                if (target == null){
                    sender.sendMessage(ChatColor.RED + "The targeted player does not exist");
                    return true;
                }
                banPlayer.addToBan(target, reason, 5);
                return true;

            }else {
                startArg = 2;
                Player target = Bukkit.getPlayerExact(args[0]);

                for(int i = startArg; i< endArg; i++){
                    builder.append(args[i] + (args.length > (i+1) ? " " : ""));
                    sender.sendMessage(target + " §aHas been unbanned");
                }

                String reason = builder.toString();

                if (target == null){
                    sender.sendMessage(ChatColor.RED + "The targeted player does not exist");
                    return true;
                }

                banPlayer.addToBan(target, reason, 5);
                Bukkit.broadcastMessage(ChatColor.RED + "A player has been removed from your game for hacking or abuse. Report rule breakers with" + ChatColor.BLUE + " /report");
                return true;
            }
        }
        return true;
    }
         */

        return true;
    }


}
