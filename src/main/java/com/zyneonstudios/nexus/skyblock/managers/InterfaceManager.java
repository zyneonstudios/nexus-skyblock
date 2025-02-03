package com.zyneonstudios.nexus.skyblock.managers;

import com.zyneonstudios.nexus.skyblock.users.UserStrings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InterfaceManager {

    public static ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

    public static Inventory getWorldCreatorInterface(WorldManager.WorldCreator creator, UserStrings language) {
        Inventory inventory = Bukkit.createInventory(null,27,language.get(UserStrings.KEY.interface_worldCreator_title)+" ("+creator.getName()+")");
        for(int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, placeholder);
        }
        inventory.setItem(9,ItemManager.getWorldCreatorGenerator(creator.getGenerator(), language));
        inventory.setItem(10,ItemManager.getWorldCreatorEnvironment(creator.getEnvironment(), language));
        inventory.setItem(11,ItemManager.getWorldCreatorType(creator.getWorldType(), language));
        inventory.setItem(12,ItemManager.getWorldCreatorBiomeProvider(creator.getBiomeProvider(), language));
        inventory.setItem(13,ItemManager.getWorldCreatorStructures(creator.generateStructures(), language));
        inventory.setItem(14,ItemManager.getWorldCreatorSeed(creator.getSeed(), language));
        inventory.setItem(15,ItemManager.getWorldCreatorHardcore(creator.isHardcore(), language));
        inventory.setItem(16,ItemManager.getWorldCreatorSpawnLoaded(creator.keepSpawnLoaded(), language));
        inventory.setItem(17,ItemManager.getWorldCreatorCreate(language));
        return inventory;
    }
}