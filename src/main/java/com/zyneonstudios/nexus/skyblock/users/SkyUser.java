package com.zyneonstudios.nexus.skyblock.users;

import com.zyneonstudios.nexus.skyblock.SkyBlock;
import com.zyneonstudios.nexus.skyblock.managers.WorldManager;
import com.zyneonstudios.nexus.skyblock.utilities.SkyLogger;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SkyUser {

    private Player player;
    private UserStrings userStrings;
    private String name;
    private UUID uuid;
    private WorldManager.WorldCreator creator = null;
    private String inputMode = null;
    private double skycoins;
    private UUID activeIsland = null;

    public SkyUser(Player player) {
        this.player = player;
        this.userStrings = new UserStrings();
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        SkyBlock.getStorage().ensure("users."+uuid+".balance", 0.0);
        skycoins = SkyBlock.getStorage().getDouble("users."+uuid+".balance");
        if(SkyBlock.getStorage().get("users."+uuid+".island") != null) {
            activeIsland = UUID.fromString(SkyBlock.getStorage().getString("users."+uuid+".island"));
        }
    }

    public Player getPlayer() {
        return player;
    }

    public UserStrings getUserStrings() {
        return userStrings;
    }

    public String getName() {
        return name;
    }

    public UUID getUUID() {
        return uuid;
    }

    @SuppressWarnings("all")
    public void setName(String name) {
        this.name = name;
        player.setDisplayName(name);
        player.setCustomName(name);
        player.setPlayerListName(name);
        player.setCustomNameVisible(true);
    }

    public void setUserStrings(UserStrings userStrings) {
        this.userStrings = userStrings;
    }

    public void sendRaw(String... message) {
        for (String msg : message) {
            if(!msg.isEmpty()) {
                player.sendMessage(message);
            }
        }
    }

    public void sendPrefix(String... message) {
        for (String msg : message) {
            if(!msg.isEmpty()) {
                sendRaw(SkyLogger.getPrefix() + msg);
            }
        }
    }

    public void sendMessage(String... message) {
        sendPrefix(message);
        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
    }

    public void sendError(String... message) {
        for(String msg:message) {
            if(!msg.isEmpty()) {
                sendRaw(userStrings.get(UserStrings.KEY.errors_prefix) + msg);
            }
        }
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
    }

    public void setWorldCreator(WorldManager.WorldCreator creator) {
        this.creator = creator;
    }

    public WorldManager.WorldCreator getWorldCreator() {
        return creator;
    }

    public String getInputMode() {
        return inputMode;
    }

    public void setInputMode(String inputMode) {
        this.inputMode = inputMode;
    }

    public Double getSkycoins() {
        return skycoins;
    }

    public void setSkycoins(double skycoins) {
        if(skycoins<0) {
            skycoins = 0;
        }
        SkyBlock.getStorage().ensure("users."+uuid+".balance", skycoins);
        this.skycoins = skycoins;
    }

    public void addSkycoins(double skycoins) {
        setSkycoins(this.skycoins + skycoins);
    }

    public void removeSkycoins(double skycoins) {
        setSkycoins(this.skycoins - skycoins);
    }

    private boolean disconnecting = false;
    public void disconnect() {
        if(!disconnecting) {
            disconnecting = true;
            quit();
        }
    }

    public UUID getActiveIsland() {
        return activeIsland;
    }

    public void setActiveIsland(UUID activeIsland) {
        SkyBlock.getStorage().set("users."+uuid+".island",activeIsland);
        this.activeIsland = activeIsland;
    }

    private void quit() {
        Location loc = player.getLocation();
        SkyBlock.getStorage().set("users."+uuid+".lastLoc.saved", false);
        SkyBlock.getStorage().set("users."+uuid+".lastLoc.X", loc.getX());
        SkyBlock.getStorage().set("users."+uuid+".lastLoc.Y", loc.getY());
        SkyBlock.getStorage().set("users."+uuid+".lastLoc.Z", loc.getZ());
        SkyBlock.getStorage().set("users."+uuid+".lastLoc.yaw", loc.getYaw());
        SkyBlock.getStorage().set("users."+uuid+".lastLoc.pitch", loc.getPitch());
        SkyBlock.getStorage().set("users."+uuid+".lastLoc.world", loc.getWorld().getName());
        SkyBlock.getStorage().set("users."+uuid+".lastLoc.saved", true);
        player = null;
        userStrings = null;
        name = null;
        SkyBlock.removeUser(uuid);
        uuid = null;
        System.gc();
    }
}