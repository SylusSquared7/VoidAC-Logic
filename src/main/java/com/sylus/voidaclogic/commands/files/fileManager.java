package com.sylus.voidaclogic.commands.files;


import com.sylus.voidaclogic.VoidACLogic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;


public class fileManager {
    private VoidACLogic plugin;
    private FileConfiguration dataConfig = null;
    private File bansFile = null;

    public fileManager(VoidACLogic plugin){ // Constructor
        this.plugin = plugin;
    }

    public VoidACLogic getMain(){
        return plugin;
    }

    public void reloadConfig(){
        if (this.bansFile == null){ // Add more files here as needed
            this.bansFile = new File(this.plugin.getDataFolder(), "anticheatBans.yml");
            saveDefultConfig();
        }
        this.dataConfig = YamlConfiguration.loadConfiguration(this.bansFile);
        InputStream defultStream = this.plugin.getResource("anticheatBans.yml");
        if (defultStream != null){
            YamlConfiguration defultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defultStream));
            this.dataConfig.setDefaults(defultConfig);
        }
    }

    public FileConfiguration getConfig(){
        if (this.dataConfig == null){
            reloadConfig();
        }
        return this.dataConfig;
    }

    public void  saveConfig() {
        if (this.dataConfig == null || this.bansFile == null) {
            return;
        }
        try {
            this.getConfig().save(bansFile);
        }catch (IOException e){
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + bansFile, e);
        }
    }

    public void saveDefultConfig(){
        if (this.bansFile == null){
            this.bansFile = new File(this.plugin.getDataFolder(), "anticheatBans.yml");
        }
        if (!this.bansFile.exists()){
            this.plugin.saveResource("anticheatBans.yml", false);
        }
    }
}
