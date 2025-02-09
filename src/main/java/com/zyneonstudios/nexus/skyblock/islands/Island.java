package com.zyneonstudios.nexus.skyblock.islands;

import com.zyneonstudios.nexus.skyblock.managers.WorldManager;
import com.zyneonstudios.nexus.skyblock.users.SkyUser;
import com.zyneonstudios.nexus.skyblock.utilities.VoidGenerator;
import com.zyneonstudios.nexus.skyblock.utilities.WorldEditUtility;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class Island {

    public static Island getIsland(UUID uuid) {
        Island island = new Island(uuid);
        island.loadWorld(false);
        return island;
    }

    public static Island createIsland(SkyUser user) {
        Island island = new Island(UUID.randomUUID());
        island.setOwnerID(user.getUUID());
        island.loadWorld(true);
        user.setActiveIsland(island.getUUID());
        return island;
    }

    @Deprecated
    public static Island createIsland(Player player) {
        return createIsland(player.getUniqueId());
    }

    @Deprecated
    public static Island createIsland(UUID owner) {
        Island island = new Island(UUID.randomUUID());
        island.setOwnerID(owner);
        island.loadWorld(true);
        return island;
    }

    private final UUID uuid;
    private UUID ownerId;
    private World overworld;
    private World nether;
    boolean isLoaded = false;

    private Island(UUID uuid) {
        this.uuid = uuid;
        //this.ownerId = UUID.fromString(SkyBlock.getStorage().getString("islands."+uuid+".owner"));
    }

    public UUID getUUID() {
        return uuid;
    }

    public UUID getOwnerID() {
        return ownerId;
    }

    public void setOwnerID(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public void loadWorld(boolean create) {
        if (create) {
            WorldManager.createWorld(new WorldCreator("islands/" + uuid + "/overworld").generator(new VoidGenerator()).environment(World.Environment.NORMAL).type(WorldType.NORMAL).seed(1).generateStructures(false));
            WorldManager.createWorld(new WorldCreator("islands/" + uuid + "/nether").generator(new VoidGenerator()).environment(World.Environment.NETHER).type(WorldType.NORMAL).seed(1).generateStructures(false));
        } else {
            WorldManager.loadWorld("islands/" + uuid + "/overworld");
            WorldManager.loadWorld("islands/" + uuid + "/nether");
        }

        overworld = Bukkit.getWorld("islands/" + uuid + "/overworld");
        overworld.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN,true);
        overworld.setSpawnLocation(0,100,0);

        nether = Bukkit.getWorld("islands/" + uuid + "/nether");
        nether.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN,true);
        nether.setSpawnLocation(0,100,0);

        if(create) {
            WorldEditUtility.pasteSchematic(new File("plugins/SkyBlock/schematics/overworld.schem"),0,0,0,overworld.getSpawnLocation());
            WorldEditUtility.pasteSchematic(new File("plugins/SkyBlock/schematics/nether.schem"),0,0,0,nether.getSpawnLocation());
        }

        isLoaded = true;
    }

    public void unloadWorld() {
        isLoaded = false;
        for(Player all : overworld.getPlayers()) {
            try {
                all.teleport(all.getBedLocation());
            } catch (Exception e) {
                all.teleport(Bukkit.getWorlds().getFirst().getSpawnLocation());
            }
        }
        overworld.save();
        for(Player all : nether.getPlayers()) {
            try {
                all.teleport(all.getBedLocation());
            } catch (Exception e) {
                all.teleport(Bukkit.getWorlds().getFirst().getSpawnLocation());
            }
        }
        nether.save();
        Bukkit.unloadWorld(overworld,true);
        Bukkit.unloadWorld(nether,true);
    }

    public World getOverworld() {
        return overworld;
    }

    public World getNether() {
        return nether;
    }

    public boolean isLoaded() {
        return isLoaded;
    }
}
