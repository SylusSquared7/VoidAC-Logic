package com.sylus.voidaclogic;

import com.sylus.voidaclogic.commands.banCommand;
import com.sylus.voidaclogic.commands.files.fileManager;
import com.sylus.voidaclogic.commands.freezeCommand;
import com.sylus.voidaclogic.commands.punishMenuCommand;
import org.bukkit.plugin.java.JavaPlugin;


public final class VoidACLogic extends JavaPlugin {


    public fileManager data;
    private static VoidACLogic instance;
    private fileManager fileManagers;

    public VoidACLogic(){
        instance = this;
        this.fileManagers = new fileManager(this);
    }

    public static VoidACLogic getInstance(){
        return instance;
    }
    public fileManager getFileManager(){
        return fileManagers;
    }

    @Override
    public void onEnable() {


        this.getCommand("punish").setExecutor(new banCommand());
        this.getCommand("freeze").setExecutor(new freezeCommand());
        this.getCommand("punishMenu").setExecutor(new punishMenuCommand());

        new kickOnJoin().kickOnJoinHandeler(this);
       // new stopMovment().stopMovementHandler(this);
        new playerLeave().playerLeaveHandeler(this);
        // Plugin startup logic
        System.out.println("[ANTICHEAT LOGIC] Logic plugin started");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
