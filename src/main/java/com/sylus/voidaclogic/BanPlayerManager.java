package com.sylus.voidaclogic;

import com.sylus.voidaclogic.commands.files.fileManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.*;


public class BanPlayerManager {
    fileManager files = new fileManager(VoidACLogic.getInstance());


    public void addToBan(Player target, String reason, long banDurationInHours) {
        String targetUUID = String.valueOf(target.getUniqueId());
        if (banDurationInHours == -1) { // if ban duration is -1, then the ban is permanent
            // Retrieve ban list
            String playerUUID = targetUUID.toString();
            ConfigurationSection playerSection = files.getConfig().getConfigurationSection("playersUUID." + playerUUID);
            if (playerSection == null) {
                playerSection = files.getConfig().createSection(playerUUID);
            }
            ConfigurationSection banSection = playerSection.getConfigurationSection("bans");
            if (banSection == null) {
                banSection = playerSection.createSection("bans");
            }

            int banCount = banSection.getKeys(false).size() + 1;
            String banKey = "ban" + banCount;

            ConfigurationSection newBanSection = banSection.createSection(banKey);
            newBanSection.set("expirationTime", banDurationInHours);
            newBanSection.set("bannedAt", System.currentTimeMillis()); // Instant.now().getEpochSecond()
            newBanSection.set("banned", true);
            newBanSection.set("reason", reason);
            newBanSection.set("type", reason);
            newBanSection.set("level", reason);

            target.kickPlayer("§cYou have been permanently banned from this server\n§7Reason: §f" + reason + "\n§6Find out more: §3Link to the punishments on the wiki");

        } else { // Not a perm ban
            String playerUUID = targetUUID.toString();
            ConfigurationSection playerSection = files.getConfig().getConfigurationSection("playersUUID." + playerUUID);
            if (playerSection == null) {
                playerSection = files.getConfig().createSection("playersUUID." + playerUUID);
            }
            ConfigurationSection banSection = playerSection.getConfigurationSection("bans");
            if (banSection == null) {
                banSection = playerSection.createSection("bans");
            }

            long banTime = System.currentTimeMillis(); // / 1000L; // Converts to seconds,s Don't need it in seconds anymore
           // long expirationTime = banDurationInHours // banTime + (banDurationInHours * 3600L);

            int banCount = banSection.getKeys(false).size() + 1;
            String banKey = "ban" + banCount;
            int level = getBanLevel(targetUUID, String.valueOf(banCount));

            ConfigurationSection newBanSection = banSection.createSection(banKey);
            newBanSection.set("expirationTime", banDurationInHours); // expirationTime);
            newBanSection.set("bannedAt", System.currentTimeMillis()); //Instant.now().getEpochSecond()
            newBanSection.set("banned", true);
            newBanSection.set("reason", reason);
            newBanSection.set("expired", false);
            newBanSection.set("level", level);

            if (banDurationInHours > 24){
                target.kickPlayer("§cYou have been temporarily banned from this server\n§7Duration: §f" + banDurationInHours / 24 + " §7days!\n§7Reason: §f" + reason + "\n§6Find out more: §3Link to the punishments on the wiki");
            } else {
                target.kickPlayer("§cYou have been temporarily banned from this server\n§7Duration: §f" + banDurationInHours + " §7hours!\n§7Reason: §f" + reason + "\n§6Find out more: §3Link to the punishments on the wiki");
            }

        }
        files.saveConfig();
    }


    public long getRemainingBanTime(UUID playerUUID, String banKey) {
        ConfigurationSection playerSection = files.getConfig().getConfigurationSection("playersUUID." + playerUUID.toString());
        if (playerSection != null) {
            ConfigurationSection banSection = playerSection.getConfigurationSection("bans");
            if (banSection != null) {
                ConfigurationSection banEntry = banSection.getConfigurationSection(banKey);
                if (banEntry != null) {
                    if (banEntry.getBoolean("banned")) {
                        long expirationTime = banEntry.getLong("expirationTime");
                        long currentTime = System.currentTimeMillis(); // / 1000L; don't need it in seconds yet
                        if (expirationTime == -1) {
                            return -1;
                        }
                        if (expirationTime - currentTime > 0) {
                            // Ban is not expired
                            return currentTime - expirationTime;
                        } else {
                            // Ban has expired, update the "expired" field
                            banEntry.set("expired", true);
                            playerSection.set("banned", false); // Set the player as unbanned
                            files.saveConfig();
                            return 0;
                        }
                    } else {
                        return 0;
                    }
                }
            }
        }
        return 0; // Return 0 if ban information is not found or ban has expired
    }


    public ArrayList<String> getActiveBanKey(UUID playerUUID) {
        ConfigurationSection playerSection = files.getConfig().getConfigurationSection("playersUUID." + playerUUID.toString());
        if (playerSection != null) {
            ConfigurationSection banSection = playerSection.getConfigurationSection("bans");
            if (banSection != null) {
              ArrayList<String >  bansKey = new ArrayList<String>();
                for (String banKey : banSection.getKeys(false)) {
                    ConfigurationSection banEntry = banSection.getConfigurationSection(banKey);
                    if (banEntry != null && banEntry.getBoolean("banned")) {
                        bansKey.add(banKey);
                    }
                }
                return bansKey;
            }
        }
        return null; // Return null if no active ban is found
    }
/*
    public long isPlayerBanned(Player player) {
        String playerUUID = player.getUniqueId().toString();
        ConfigurationSection playerSection = files.getConfig().getConfigurationSection("playersUUID." + playerUUID);
        if (playerSection != null && playerSection.contains("bans")) {
            ConfigurationSection banSection = playerSection.getConfigurationSection("bans");
            if (banSection != null) {
                for (String banKey : banSection.getKeys(false)) {
                    ConfigurationSection banDataSection = banSection.getConfigurationSection(banKey);
                    boolean banned = banDataSection.getBoolean("banned", false);
                    long expirationTime = banDataSection.getLong("expirationTime", -1L);
                    if (expirationTime == -1){
                        return -1; // -1 means the punishment is permanent
                    }
                    if (banned && Instant.now().getEpochSecond() < expirationTime) {
                        long timeRemainingSeconds = expirationTime - Instant.now().getEpochSecond();
                        return timeRemainingSeconds / 3600; // returns it in hours not seconds
                    }
                }
            }
        }
        return 0; // the player is not in the bans file or not currently banned
    }
 */

    public int getLevel(Player player){
        return files.getConfig().getInt(player.getUniqueId() + ".level");
    }

    public String banReason(UUID playerUUID, String key) {
        ConfigurationSection playerSection = files.getConfig().getConfigurationSection(playerUUID.toString());
        if (playerSection != null) {
            ConfigurationSection bansSection = playerSection.getConfigurationSection("bans");
            if (bansSection != null && bansSection.contains(key)) {
                ConfigurationSection banSection = bansSection.getConfigurationSection(key);
                if (banSection != null) {
                    String banReason = banSection.getString("reason");
                    if (banReason != null) {
                        return banReason;
                    }
                }
            }
        }
        return "REASON NOT FOUND"; // Ban reason not found
    }

    public int getBanLevel(String playerUUID, String banKey) {
        ConfigurationSection playerSection = files.getConfig().getConfigurationSection("playersUUID." + playerUUID);
        if (playerSection != null) {
            ConfigurationSection banSection = playerSection.getConfigurationSection("bans");
            if (banSection != null && banSection.contains(banKey)) {
                ConfigurationSection banEntry = banSection.getConfigurationSection(banKey);
                try {
                    banEntry.getInt("level");
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                return banEntry.getInt("level");
            }
        }
        return 0; // Default to level of 0
    }
}
