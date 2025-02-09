package com.zyneonstudios.nexus.skyblock.listeners;

import com.zyneonstudios.nexus.skyblock.SkyBlock;
import com.zyneonstudios.nexus.skyblock.managers.InterfaceManager;
import com.zyneonstudios.nexus.skyblock.users.SkyUser;
import com.zyneonstudios.nexus.skyblock.users.UserStrings;
import com.zyneonstudios.nexus.skyblock.utilities.VoidGenerator;
import net.kyori.adventure.util.TriState;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class PlayerInventoryListener implements Listener {

    public static HashMap<UUID, Inventory> invs = new HashMap<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        SkyUser user = SkyBlock.getUser(player);
        UserStrings language = user.getUserStrings();
        if (e.getClickedInventory() != null) {
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().getItemMeta() != null) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§0")) {
                        e.setCancelled(true);
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(language.get(UserStrings.KEY.interface_worldCreator_generator))) {
                        e.setCancelled(true);
                        if (user.getWorldCreator() != null) {
                            if (user.getWorldCreator().getGenerator() == null) {
                                user.getWorldCreator().setGenerator(new VoidGenerator());
                            } else {
                                user.getWorldCreator().setGenerator(null);
                            }
                            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0F, 1.0F);
                            player.closeInventory();
                            player.openInventory(InterfaceManager.getWorldCreatorInterface(user.getWorldCreator(), user.getUserStrings()));
                        } else {
                            player.closeInventory();
                        }
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(language.get(UserStrings.KEY.interface_worldCreator_environment))) {
                        e.setCancelled(true);
                        if (user.getWorldCreator() != null) {
                            if (user.getWorldCreator().getEnvironment().equals(World.Environment.NORMAL)) {
                                user.getWorldCreator().setEnvironment(World.Environment.NETHER);
                            } else if (user.getWorldCreator().getEnvironment().equals(World.Environment.NETHER)) {
                                user.getWorldCreator().setEnvironment(World.Environment.THE_END);
                            } else if (user.getWorldCreator().getEnvironment().equals(World.Environment.THE_END)) {
                                user.getWorldCreator().setEnvironment(World.Environment.CUSTOM);
                            } else {
                                user.getWorldCreator().setEnvironment(World.Environment.NORMAL);
                            }
                            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0F, 1.0F);
                            player.closeInventory();
                            player.openInventory(InterfaceManager.getWorldCreatorInterface(user.getWorldCreator(), user.getUserStrings()));
                        } else {
                            player.closeInventory();
                        }
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(language.get(UserStrings.KEY.interface_worldCreator_type))) {
                        e.setCancelled(true);
                        if (user.getWorldCreator() != null) {
                            if (user.getWorldCreator().getWorldType().equals(WorldType.NORMAL)) {
                                user.getWorldCreator().setWorldType(WorldType.LARGE_BIOMES);
                            } else if (user.getWorldCreator().getWorldType().equals(WorldType.LARGE_BIOMES)) {
                                user.getWorldCreator().setWorldType(WorldType.AMPLIFIED);
                            } else if (user.getWorldCreator().getWorldType().equals(WorldType.AMPLIFIED)) {
                                user.getWorldCreator().setWorldType(WorldType.FLAT);
                            } else {
                                user.getWorldCreator().setWorldType(WorldType.NORMAL);
                            }
                            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0F, 1.0F);
                            player.closeInventory();
                            player.openInventory(InterfaceManager.getWorldCreatorInterface(user.getWorldCreator(), user.getUserStrings()));
                        } else {
                            player.closeInventory();
                        }
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(language.get(UserStrings.KEY.interface_worldCreator_biomeProvider))) {
                        e.setCancelled(true);
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(language.get(UserStrings.KEY.interface_worldCreator_structures))) {
                        e.setCancelled(true);
                        if (user.getWorldCreator() != null) {
                            user.getWorldCreator().setGenerateStructures(!user.getWorldCreator().generateStructures());
                            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0F, 1.0F);
                            player.closeInventory();
                            player.openInventory(InterfaceManager.getWorldCreatorInterface(user.getWorldCreator(), user.getUserStrings()));
                        } else {
                            player.closeInventory();
                        }
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(language.get(UserStrings.KEY.interface_worldCreator_hardcore))) {
                        e.setCancelled(true);
                        if (user.getWorldCreator() != null) {
                            user.getWorldCreator().setHardcore(!user.getWorldCreator().isHardcore());
                            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0F, 1.0F);
                            player.closeInventory();
                            player.openInventory(InterfaceManager.getWorldCreatorInterface(user.getWorldCreator(), user.getUserStrings()));
                        } else {
                            player.closeInventory();
                        }
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(language.get(UserStrings.KEY.interface_worldCreator_keepSpawnLoaded))) {
                        e.setCancelled(true);
                        if (user.getWorldCreator() != null) {
                            if (user.getWorldCreator().keepSpawnLoaded().equals(TriState.TRUE)) {
                                user.getWorldCreator().setKeepSpawnLoaded(TriState.FALSE);
                            } else if (user.getWorldCreator().keepSpawnLoaded().equals(TriState.FALSE)) {
                                user.getWorldCreator().setKeepSpawnLoaded(TriState.NOT_SET);
                            } else {
                                user.getWorldCreator().setKeepSpawnLoaded(TriState.TRUE);
                            }
                            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0F, 1.0F);
                            player.closeInventory();
                            player.openInventory(InterfaceManager.getWorldCreatorInterface(user.getWorldCreator(), user.getUserStrings()));
                        } else {
                            player.closeInventory();
                        }
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(language.get(UserStrings.KEY.interface_worldCreator_seed))) {
                        e.setCancelled(true);
                        user.setInputMode("world_creator_seed");
                        user.sendMessage(user.getUserStrings().get(UserStrings.KEY.commands_world_seed));
                        player.closeInventory();
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(language.get(UserStrings.KEY.interface_worldCreator_create))) {
                        e.setCancelled(true);
                        if (user.getWorldCreator() != null) {
                            player.closeInventory();
                            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
                            user.sendMessage(language.get(UserStrings.KEY.commands_world_creating));
                            user.getWorldCreator().create();
                            user.setWorldCreator(null);
                            user.sendMessage(language.get(UserStrings.KEY.commands_world_created));
                            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0F, 1.0F);
                        }
                    }
                }
            }
        }
    }
}