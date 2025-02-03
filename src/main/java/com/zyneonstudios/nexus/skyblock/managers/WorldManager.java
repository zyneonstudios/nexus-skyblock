package com.zyneonstudios.nexus.skyblock.managers;

import com.zyneonstudios.nexus.skyblock.storage.types.Config;
import com.zyneonstudios.nexus.skyblock.utilities.VoidGenerator;
import net.kyori.adventure.util.TriState;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.joml.Random;

import java.io.File;

public class WorldManager {

    public static boolean createWorld(org.bukkit.WorldCreator creator) {
        try {
            String name = creator.name();
            if(name.endsWith("/")||name.endsWith("\\")) {
                name = name.substring(0, name.length() - 1);
            }
            Config worldData = new Config(name + "/nexus-world.yml");
            worldData.set("data.environment", creator.environment().toString());
            worldData.set("data.seed", creator.seed());
            worldData.set("data.type", creator.type().toString());
            worldData.set("data.structures", creator.generateStructures());
            if (creator.generator() instanceof VoidGenerator) {
                worldData.set("data.generator", "void");
            }
            worldData.set("data.hardcore", creator.hardcore());
            worldData.set("data.keepSpawnLoaded", creator.keepSpawnLoaded().toString());
            Bukkit.createWorld(creator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean loadWorld(String name) {
        if(name.endsWith("/")||name.endsWith("\\")) {
            name = name.substring(0, name.length() - 1);
        }
        org.bukkit.WorldCreator creator = new org.bukkit.WorldCreator(name);
        File worldFile = new File(name+"/nexus-world.yml");
        if(worldFile.exists()) {
            Config worldData = new Config(worldFile.getAbsolutePath());
            if(worldData.getCFG().contains("data.environment")) {
                creator.environment(World.Environment.valueOf(worldData.getCFG().getString("data.environment")));
            }
            if(worldData.getCFG().contains("data.seed")) {
                creator.seed(worldData.getCFG().getLong("data.seed"));
            }
            if(worldData.getCFG().contains("data.type")) {
                creator.type(WorldType.valueOf(worldData.getCFG().getString("data.type")));
            }
            if(worldData.getCFG().contains("data.structures")) {
                creator.generateStructures(worldData.getCFG().getBoolean("data.structures"));
            }
            if(worldData.getCFG().contains("data.generator")) {
                String generator = worldData.get("data.generator").toString().toLowerCase();
                if(generator.equals("skyblock")||generator.equals("void")) {
                    creator.generator(new VoidGenerator());
                } else {
                    creator.generator(worldData.get("data.generator").toString());
                }
            }
            if(worldData.getCFG().contains("data.biomeProvider")) {
                creator.biomeProvider(worldData.get("data.generator").toString());
            }
            if(worldData.getCFG().contains("data.hardcore")) {
                creator.hardcore(worldData.getCFG().getBoolean("data.hardcore"));
            }
            if(worldData.getCFG().contains("data.keepSpawnLoaded")) {
                creator.keepSpawnLoaded(TriState.valueOf(worldData.getCFG().getString("data.keepSpawnLoaded")));
            }
        }
        try {
            Bukkit.createWorld(creator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static class WorldCreator {

        private final String name;
        private World.Environment environment = World.Environment.NORMAL;
        private WorldType worldType = WorldType.NORMAL;
        private ChunkGenerator generator = null;
        private BiomeProvider biomeProvider = null;
        private long seed = Random.newSeed();
        private boolean structures = true;
        private boolean hardcore = false;
        private TriState keepSpawnLoaded = TriState.NOT_SET;

        public WorldCreator(String name) {
            this.name = name;
        }

        public BiomeProvider getBiomeProvider() {
            return biomeProvider;
        }

        public ChunkGenerator getGenerator() {
            return generator;
        }

        public World.Environment getEnvironment() {
            return environment;
        }

        public WorldType getWorldType() {
            return worldType;
        }

        public long getSeed() {
            return seed;
        }

        public TriState keepSpawnLoaded() {
            return keepSpawnLoaded;
        }

        public boolean isHardcore() {
            return hardcore;
        }

        public boolean generateStructures() {
            return structures;
        }

        public void setBiomeProvider(BiomeProvider biomeProvider) {
            this.biomeProvider = biomeProvider;
        }

        public void setEnvironment(World.Environment environment) {
            this.environment = environment;
        }

        public void setGenerator(ChunkGenerator generator) {
            this.generator = generator;
        }

        public void setHardcore(boolean hardcore) {
            this.hardcore = hardcore;
        }

        public void setKeepSpawnLoaded(TriState keepSpawmLoaded) {
            this.keepSpawnLoaded = keepSpawmLoaded;
        }

        public void setSeed(long seed) {
            this.seed = seed;
        }

        public void setGenerateStructures(boolean structures) {
            this.structures = structures;
        }

        public void setWorldType(WorldType worldType) {
            this.worldType = worldType;
        }

        public String getName() {
            return name;
        }

        public void create() {
            createWorld(new org.bukkit.WorldCreator(name).generateStructures(structures).type(worldType).seed(seed).keepSpawnLoaded(keepSpawnLoaded).hardcore(hardcore).generator(generator).environment(environment).biomeProvider(biomeProvider));
        }
    }
}