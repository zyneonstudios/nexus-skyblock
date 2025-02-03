package com.zyneonstudios.nexus.skyblock.managers;

import com.zyneonstudios.nexus.skyblock.users.UserStrings;
import com.zyneonstudios.nexus.skyblock.utilities.VoidGenerator;
import net.kyori.adventure.util.TriState;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemManager {

    public static ItemStack getWorldCreatorGenerator(ChunkGenerator generator, UserStrings language) {
        ItemStack item;
        String name = "default";
        if(generator instanceof VoidGenerator) {
            item = new ItemStack(Material.GLASS);
            name = "void";
        } else {
            item = new ItemStack(Material.STONE);
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(language.get(UserStrings.KEY.interface_worldCreator_generator));
        itemMeta.setLore(List.of("§e"+name));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getWorldCreatorEnvironment(World.Environment environment, UserStrings language) {
        ItemStack item;
        if(environment == World.Environment.NORMAL) {
            item = new ItemStack(Material.GRASS_BLOCK);
        } else if(environment == World.Environment.NETHER) {
            item = new ItemStack(Material.CRIMSON_NYLIUM);
        } else if(environment == World.Environment.THE_END) {
            item = new ItemStack(Material.END_STONE);
        } else {
            item = new ItemStack(Material.COMMAND_BLOCK);
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(language.get(UserStrings.KEY.interface_worldCreator_environment));
        itemMeta.setLore(List.of("§e"+environment.toString().toLowerCase()));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getWorldCreatorType(WorldType type, UserStrings language) {
        ItemStack item = new ItemStack(Material.BARRIER);
        if(type == WorldType.NORMAL) {
            item = new ItemStack(Material.MOSS_BLOCK);
        } else if(type == WorldType.LARGE_BIOMES) {
            item = new ItemStack(Material.MAP);
        } else if(type == WorldType.AMPLIFIED) {
            item = new ItemStack(Material.FILLED_MAP);
        } else if(type == WorldType.FLAT) {
            item = new ItemStack(Material.MOSS_CARPET);
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(language.get(UserStrings.KEY.interface_worldCreator_type));
        itemMeta.setLore(List.of("§e"+type.toString().toLowerCase()));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getWorldCreatorBiomeProvider(BiomeProvider biomeProvider, UserStrings language) {
        ItemStack item = new ItemStack(Material.OAK_LEAVES);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(language.get(UserStrings.KEY.interface_worldCreator_biomeProvider));
        itemMeta.setLore(List.of("§edefault"));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getWorldCreatorStructures(boolean generateStructures, UserStrings language) {
        ItemStack item = new ItemStack(Material.RED_DYE);
        if(generateStructures) {
            item = new ItemStack(Material.LIME_DYE);
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(language.get(UserStrings.KEY.interface_worldCreator_structures));
        itemMeta.setLore(List.of("§e"+generateStructures));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getWorldCreatorSeed(Long seed, UserStrings language) {
        ItemStack item = new ItemStack(Material.WHEAT_SEEDS);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(language.get(UserStrings.KEY.interface_worldCreator_seed));
        itemMeta.setLore(List.of("§e"+seed));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getWorldCreatorHardcore(boolean hardcore, UserStrings language) {
        ItemStack item = new ItemStack(Material.RED_DYE);
        if(hardcore) {
            item = new ItemStack(Material.LIME_DYE);
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(language.get(UserStrings.KEY.interface_worldCreator_hardcore));
        itemMeta.setLore(List.of("§e"+hardcore));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getWorldCreatorSpawnLoaded(TriState keepSpawnLoaded, UserStrings language) {
        ItemStack item = new ItemStack(Material.YELLOW_DYE);
        if(keepSpawnLoaded.equals(TriState.TRUE)) {
            item = new ItemStack(Material.LIME_DYE);
        } else if(keepSpawnLoaded.equals(TriState.FALSE)) {
            item = new ItemStack(Material.RED_DYE);
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(language.get(UserStrings.KEY.interface_worldCreator_keepSpawnLoaded));
        itemMeta.setLore(List.of("§e"+keepSpawnLoaded.toString().toLowerCase()));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getWorldCreatorCreate(UserStrings language) {
        ItemStack item = new ItemStack(Material.REPEATING_COMMAND_BLOCK);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(language.get(UserStrings.KEY.interface_worldCreator_create));
        item.setItemMeta(itemMeta);
        return item;
    }
}