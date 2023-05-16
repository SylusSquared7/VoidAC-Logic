package com.sylus.voidaclogic.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class muteManager {
        private File muteFile;
        private YamlConfiguration muteConfig;
        private final Map<String, Long> playerBans;

        public muteManager(File configFile) {
            this.muteFile = muteFile;
            this.muteConfig = YamlConfiguration.loadConfiguration(muteFile);
            this.playerBans = loadPlayerBansFromConfig();
        }

       private Map<String, Long> loadPlayerBansFromConfig() {
         Map<String, Long> bans = new HashMap<>();
          if (muteConfig.contains("playerBans")) {
            muteConfig.getConfigurationSection("playerBans").getKeys(false).forEach(key -> {
                bans.put(key, muteConfig.getLong("playerBans." + key));
            });
        }
        return bans;
    }

    public void addToBan(Player target, Player sender, String reason, int banDurationInHours) {
        String targetUUID = String.valueOf(target.getUniqueId());
        if (banDurationInHours == -1) { // if ban duration is -1, then the ban is permanent
            long expirationTime = Long.MAX_VALUE;
            muteConfig.set(targetUUID + ".expirationTime", expirationTime);
            muteConfig.set(targetUUID + ".banned", true);
            muteConfig.set(targetUUID + ".reason", reason);
        } else {
            long banDurationSeconds = banDurationInHours * 3600L; // there are that many seconds in an hour
            long expirationTime = Instant.now().plus(banDurationSeconds, ChronoUnit.SECONDS).getEpochSecond();
            muteConfig.set(targetUUID + ".expirationTime", expirationTime);
            muteConfig.set(targetUUID + ".banned", true);
            muteConfig.set(targetUUID + ".reason", reason);
        }
        sender.sendMessage( target + " Has been banned");

        try {
            muteConfig.save(muteFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        target.kickPlayer(reason);
    }


    /*
//a  ban time of 999999999 means the ban is permienent
    public void addToBan(Player target, Player sender, String reason, long banDurationInDays){
            String targetUUID = String.valueOf(target.getUniqueId());
            if (banDurationInDays == 999999999){ // if ban durationInMins is 999999999 then the ban is perminent
                long expirationTime = (long) 999999999;
                muteConfig.set(targetUUID + ".expirationTime", expirationTime);
                muteConfig.set(targetUUID + ".banned", true);
                muteConfig.set(targetUUID + ".reason", reason);
            } else {
                long banDurationSeconds = banDurationInDays * 86400; //there are that many seconds in a day
                long expirationTime = Instant.now().plus(banDurationSeconds, ChronoUnit.SECONDS).getEpochSecond();
                muteConfig.set(targetUUID + ".expirationTime", expirationTime);
                muteConfig.set(targetUUID + ".banned", true);
                muteConfig.set(targetUUID + ".reason", reason);
            }

            try {
                muteConfig.save(muteFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            target.kickPlayer(reason);
        }

     */


    public long isPlayerBanned(Player player) {
        String playerName = String.valueOf(player.getUniqueId());
        if (playerBans.containsKey(playerName)) {
            long expirationTime = playerBans.get(playerName);
            if (expirationTime == Long.MAX_VALUE){
                return -1; // Long.MAX_VALUE means the ban is perminent
            }
            if (Instant.now().getEpochSecond() < expirationTime) {
                // Player is still banned
                long timeRemainingSeconds = expirationTime - Instant.now().getEpochSecond();
                    return timeRemainingSeconds / 3600; // Returns the ban time in hours
            } else {
                // Player's ban has expired, remove them from the bans file
                playerBans.remove(playerName);
                muteConfig.set("playerBans." + playerName, false);

                try {
                    muteConfig.save(muteFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0; // Player is not banned or ban has expired
    }

    public String banReason(UUID playerUUID){
        String banReason = muteConfig.getString(playerUUID.toString() + ".reason");
        return banReason;
    }

    public void removeFromBan(Player target){
        String targetUUID = String.valueOf(target.getUniqueId());
        if (muteConfig.contains(targetUUID)){
            muteConfig.set(targetUUID + ".expirationTime", false);
            muteConfig.set(targetUUID + ".banned", false);
            muteConfig.set(targetUUID + ".reason", false);
            try {
                muteConfig.save(muteFile);

            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}
