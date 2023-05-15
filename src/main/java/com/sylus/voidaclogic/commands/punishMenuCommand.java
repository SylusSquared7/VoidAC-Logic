package com.sylus.voidaclogic.commands;

import com.sylus.voidaclogic.VoidACLogic;
import com.sylus.voidaclogic.punishMenu;
import com.sylus.voidaclogic.punishMenuClick;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class punishMenuCommand implements CommandExecutor {
    punishMenu menu = new punishMenu();
    private final VoidACLogic plugin = VoidACLogic.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("Only players can use this command");
            return true;
        }
        StringBuilder builder = new StringBuilder();

        Player target = Bukkit.getPlayerExact(java.lang.String.valueOf(args[0]));
        if (target == null){
            sender.sendMessage(ChatColor.RED + "Please specify a player");
            return true;
        }

        Player player = ((Player) sender).getPlayer();
        punishMenuClick menuClick = new punishMenuClick(player, target);
        getServer().getPluginManager().registerEvents(new punishMenuClick(player, target), plugin);
        menu.showMenu((Player) sender, target);



        return true;
    }
}
