package com.zyneonstudios.nexus.skyblock.commands;

import com.zyneonstudios.nexus.skyblock.SkyBlock;
import com.zyneonstudios.nexus.skyblock.managers.InterfaceManager;
import com.zyneonstudios.nexus.skyblock.managers.WorldManager;
import com.zyneonstudios.nexus.skyblock.users.SkyUser;
import com.zyneonstudios.nexus.skyblock.users.UserStrings;
import com.zyneonstudios.nexus.skyblock.utilities.SkyLogger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WorldCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(@NotNull final CommandSender sender, UserStrings language) {
        String syntax = "/world (teleport/create/load/info/unload/delete) (world)";
        if(sender instanceof Player) {
            syntax = "/world <load/info/unload/delete> <world>";
        }
        SkyLogger.sendError(sender,language.get(UserStrings.KEY.errors_syntax)+syntax);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String @NotNull [] args) {
        UserStrings language = SkyBlock.getStrings();
        if(sender instanceof Player player) {
            language = SkyBlock.getUser(player).getUserStrings();
        }

        if (args.length == 0) {
            if (sender instanceof Player player) {
                player.performCommand("world info "+player.getWorld().getName());
            } else {
                sendSyntax(sender,language);
            }
        } else if (args.length == 1) {
            Bukkit.dispatchCommand(sender,"world teleport "+args[0]);
        } else {
            String command = args[0];
            String worldName = args[1];
            if (command.equalsIgnoreCase("create")) {
                if (sender.hasPermission("zyneon.skyblock.commands.world.create")) {
                    if(sender instanceof Player player) {
                        SkyUser user = SkyBlock.getUser(player);
                        if(Bukkit.getWorld(worldName) == null) {
                            user.sendMessage(language.get(UserStrings.KEY.commands_world_create).replace("%world%", worldName));
                            user.setWorldCreator(new WorldManager.WorldCreator(worldName));
                            player.openInventory(InterfaceManager.getWorldCreatorInterface(user.getWorldCreator(),language));
                        } else {
                            user.sendError(language.get(UserStrings.KEY.errors_worldAlreadyLoaded));
                        }
                    } else {
                        sendSyntax(sender,language);
                    }
                } else {
                    SkyLogger.sendError(sender,language.get(UserStrings.KEY.errors_noPermission));
                }
            } else if (command.equalsIgnoreCase("teleport")||command.equalsIgnoreCase("tp")) {
                if (sender instanceof Player player) {
                    SkyUser user = SkyBlock.getUser(player);
                    if (player.hasPermission("zyneon.skyblock.commands.world.teleport")) {
                        if(Bukkit.getWorld(worldName) != null) {
                            player.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
                            user.sendMessage(language.get(UserStrings.KEY.commands_world_teleport).replace("%world%", worldName));
                        } else {
                            user.sendError(language.get(UserStrings.KEY.errors_worldNotFound));
                        }
                    } else {
                        user.sendError(language.get(UserStrings.KEY.errors_noPermission));
                    }
                } else {
                    Bukkit.dispatchCommand(sender, "world info "+worldName);
                }
            } else if (command.equalsIgnoreCase("load")) {
                if (sender.hasPermission("zyneon.skyblock.commands.world.load")) {
                    if(Bukkit.getWorld(worldName) == null) {
                        if(new File(worldName).exists()) {
                            WorldManager.loadWorld(worldName);
                            SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_world_load).replace("%world%", worldName));
                        } else {
                            SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_worldNotFound));
                        }
                    } else {
                        SkyLogger.sendError(sender,language.get(UserStrings.KEY.errors_worldAlreadyLoaded));
                    }
                } else {
                    SkyLogger.sendError(sender,language.get(UserStrings.KEY.errors_noPermission));
                }
            } else if (command.equalsIgnoreCase("info")) {
                if (sender.hasPermission("zyneon.skyblock.commands.world.info")) {
                    if(Bukkit.getWorld(worldName)==null) {
                        SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_worldNotFound));
                        return false;
                    }
                    World world = Bukkit.getWorld(worldName);
                    assert world != null;
                    Location worldSpawn = world.getSpawnLocation();

                    String name = language.get(UserStrings.KEY.commands_world_info_name) + world.getName();
                    String difficulty = language.get(UserStrings.KEY.commands_world_info_difficulty) + world.getDifficulty().toString().toLowerCase();
                    String generator = "";
                    try {
                        generator = language.get(UserStrings.KEY.commands_world_info_generator) + world.getGenerator().getClass().getSimpleName();
                    } catch (Exception ignore) {}
                    String spawn = language.get(UserStrings.KEY.commands_world_info_spawn) + "X"+worldSpawn.getX() + " Y"+worldSpawn.getY() + " Z"+worldSpawn.getZ() + " §8("+worldSpawn.getYaw()+", "+worldSpawn.getPitch()+")";
                    String structures = language.get(UserStrings.KEY.commands_world_info_structures) + world.canGenerateStructures();
                    String environment = language.get(UserStrings.KEY.commands_world_info_type) + world.getEnvironment().toString().toLowerCase() + " §8("+world.getWorldType().toString().toLowerCase()+")";

                    SkyLogger.sendMessage(sender,name,difficulty,generator,spawn,structures,environment);
                } else {
                    SkyLogger.sendError(sender,language.get(UserStrings.KEY.errors_noPermission));
                }
            } else if (command.equalsIgnoreCase("unload")) {
                if (sender.hasPermission("zyneon.skyblock.commands.world.unload")) {
                    if(Bukkit.getWorld(worldName)!=null) {
                        for(Player all : Bukkit.getWorld(worldName).getPlayers()) {
                            try {
                                all.teleport(all.getBedLocation());
                            } catch (Exception e) {
                                all.teleport(Bukkit.getWorlds().getFirst().getSpawnLocation());
                            }
                        }
                        Bukkit.getWorld(worldName).save();
                        Bukkit.unloadWorld(worldName, true);
                        SkyLogger.sendMessage(sender,language.get(UserStrings.KEY.commands_world_unload).replace("%world%", worldName));
                    } else {
                        SkyLogger.sendError(sender,language.get(UserStrings.KEY.errors_worldNotFound));
                    }
                } else {
                    SkyLogger.sendError(sender,language.get(UserStrings.KEY.errors_noPermission));
                }
            } else if (command.equalsIgnoreCase("delete")) {
                if (sender.hasPermission("zyneon.skyblock.commands.world.delete")) {
                    if(Bukkit.getWorld(worldName)!=null) {
                        Bukkit.unloadWorld(worldName, false);
                        File world = new File(worldName);
                        if(world.delete()) {
                            SkyLogger.sendError(sender,language.get(UserStrings.KEY.commands_world_delete));
                        } else {
                            SkyLogger.sendError(sender,language.get(UserStrings.KEY.commands_world_deleteOnExit).replace("%world%", worldName));
                            world.deleteOnExit();
                        }
                    } else {
                        SkyLogger.sendError(sender,language.get(UserStrings.KEY.errors_worldNotFound));
                    }
                } else {
                    SkyLogger.sendError(sender,language.get(UserStrings.KEY.errors_noPermission));
                }
            } else {
                sendSyntax(sender,language);
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        ArrayList<String> completions = new ArrayList<>();
        if(args.length == 1) {
            completions = new ArrayList<>();
            if (sender.hasPermission("zyneon.skyblock.commands.world.create")) {
                completions.add("create");
            }
            if (sender.hasPermission("zyneon.skyblock.commands.world.load")) {
                completions.add("load");
            }
            if (sender.hasPermission("zyneon.skyblock.commands.world.info")) {
                completions.add("info");
            }
            if (sender.hasPermission("zyneon.skyblock.commands.world.unload")) {
                completions.add("unload");
            }
            if (sender.hasPermission("zyneon.skyblock.commands.world.delete")) {
                completions.add("delete");
            }
            if (sender.hasPermission("zyneon.skyblock.commands.world.teleport")) {
                completions.add("teleport");
            }
        } else if (args.length == 2) {
            completions = new ArrayList<>();
            if (args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("unload") || args[0].equalsIgnoreCase("delete")) {
                String arg = args[1].toLowerCase();
                if (sender.hasPermission("zyneon.skyblock.commands.world." + arg)) {
                    for (World all : Bukkit.getWorlds()) {
                        completions.add(all.getName());
                    }
                }
            }
        }
        return completions;
    }
}