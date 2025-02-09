package com.zyneonstudios.nexus.skyblock.commands;

import com.zyneonstudios.nexus.skyblock.SkyBlock;
import com.zyneonstudios.nexus.skyblock.users.SkyUser;
import com.zyneonstudios.nexus.skyblock.users.UserStrings;
import com.zyneonstudios.nexus.skyblock.utilities.SkyLogger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SpeedCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(CommandSender sender, UserStrings language) {
        String syntax = "/speed <0-9/d/default> (user)";
        if (!(sender instanceof Player player)) {
            syntax = syntax.replace("(user)", "<user>");
        }
        SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_syntax) + syntax);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String @NotNull [] args) {

        UserStrings language = SkyBlock.getStrings();
        if (sender instanceof Player player) {
            language = SkyBlock.getUser(player).getUserStrings();
        }

        if (!(sender instanceof Player player)) {
            if (sender.hasPermission("zyneon.skyblock.commands.speed.others")) {
                if (args.length == 2) {
                    if ((args[0].equalsIgnoreCase("d") || args[0].equalsIgnoreCase("default"))||(Integer.parseInt(args[0]) < 0 || Integer.parseInt(args[0]) > 9)) {
                        if (args[0].equalsIgnoreCase("d") || args[0].equalsIgnoreCase("default")) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (target == null) {
                                SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_playerNotFound));
                                return false;
                            } else {
                                SkyUser user = SkyBlock.getUser(target);
                                if (target.isFlying()) {
                                    target.setFlySpeed(Float.parseFloat("0.1"));
                                    SkyLogger.sendMessage(target, user.getUserStrings().get(UserStrings.KEY.commands_speed_switched_flying).replace("%speed%", "default"));
                                    SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_speed_switched_flying_other).replace("%player%", args[1]).replace("%speed%", "default"));
                                    return true;
                                } else {
                                    target.setWalkSpeed(Float.parseFloat("0.2"));
                                    SkyLogger.sendMessage(target, user.getUserStrings().get(UserStrings.KEY.commands_speed_switched_walking).replace("%speed%", "default"));
                                    SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_speed_switched_walking_other).replace("%player%", args[1]).replace("%speed%", "default"));
                                    return true;
                                }
                            }
                        } else {
                            sendSyntax(sender, language);
                            return false;
                        }
                    } else {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_playerNotFound));
                            return false;
                        } else {
                            SkyUser user = SkyBlock.getUser(target);
                            if (target.isFlying()) {
                                target.setFlySpeed(Float.parseFloat("0." + args[0]));
                                SkyLogger.sendMessage(target, user.getUserStrings().get(UserStrings.KEY.commands_speed_switched_flying).replace("%speed%", args[0]));
                                SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_speed_switched_flying_other).replace("%player%", args[1]).replace("%speed%", args[0]));
                                return true;
                            } else {
                                target.setWalkSpeed(Float.parseFloat("0." + args[0]));
                                SkyLogger.sendMessage(target, user.getUserStrings().get(UserStrings.KEY.commands_speed_switched_walking).replace("%speed%", args[0]));
                                SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_speed_switched_walking_other).replace("%player%", args[1]).replace("%speed%", args[0]));
                                return true;
                            }
                        }
                    }
                } else {
                    sendSyntax(sender, language);
                    return false;
                }
            } else {
                SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_noPermission));
                return false;
            }
        } else {
            if (args.length == 2) {
                if (player.hasPermission("zyneon.skyblock.commands.speed.others")) {
                    if ((args[0].equalsIgnoreCase("d") || args[0].equalsIgnoreCase("default"))||(Integer.parseInt(args[0]) < 0 || Integer.parseInt(args[0]) > 9)) {
                        if (args[0].equalsIgnoreCase("d") || args[0].equalsIgnoreCase("default")) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (target == null) {
                                SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_playerNotFound));
                                return false;
                            } else {
                                if (target.isFlying()) {
                                    target.setFlySpeed(Float.parseFloat("0.1"));
                                    SkyLogger.sendMessage(target, language.get(UserStrings.KEY.commands_speed_switched_flying).replace("%speed%", "default"));
                                    SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_speed_switched_flying_other).replace("%player%", args[1]).replace("%speed%", "default"));
                                    return true;
                                } else {
                                    target.setWalkSpeed(Float.parseFloat("0.2"));
                                    SkyLogger.sendMessage(target, language.get(UserStrings.KEY.commands_speed_switched_walking).replace("%speed%", "default"));
                                    SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_speed_switched_walking_other).replace("%player%", args[1]).replace("%speed%", "default"));
                                    return true;
                                }
                            }
                        } else {
                            sendSyntax(sender, language);
                            return false;
                        }
                    } else {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            SkyLogger.sendError(player, language.get(UserStrings.KEY.errors_playerNotFound));
                            return false;
                        } else {
                            if (target.isFlying()) {
                                target.setFlySpeed(Float.parseFloat("0." + args[0]));
                                SkyLogger.sendMessage(target, language.get(UserStrings.KEY.commands_speed_switched_flying).replace("%speed%", args[0]));
                                SkyLogger.sendMessage(player, language.get(UserStrings.KEY.commands_speed_switched_flying_other).replace("%player%", args[1]).replace("%speed%", args[0]));
                                return true;
                            } else {
                                target.setWalkSpeed(Float.parseFloat("0." + args[0]));
                                SkyLogger.sendMessage(target, language.get(UserStrings.KEY.commands_speed_switched_walking).replace("%speed%", args[0]));
                                SkyLogger.sendMessage(player, language.get(UserStrings.KEY.commands_speed_switched_walking_other).replace("%player%", args[1]).replace("%speed%", args[0]));
                                return true;
                            }
                        }
                    }
                } else {
                    SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_noPermission));
                    return false;
                }
            } else if (args.length == 1) {
                if (player.hasPermission("zyneon.skyblock.commands.speed")) {
                    if ((args[0].equalsIgnoreCase("d") || args[0].equalsIgnoreCase("default"))||(Integer.parseInt(args[0]) < 0 || Integer.parseInt(args[0]) > 9)) {
                        if (args[0].equalsIgnoreCase("d") || args[0].equalsIgnoreCase("default")) {
                            if (player.isFlying()) {
                                player.setFlySpeed(Float.parseFloat("0.1"));
                                SkyLogger.sendMessage(player, language.get(UserStrings.KEY.commands_speed_switched_flying).replace("%speed%", "default"));
                                return true;
                            } else {
                                player.setWalkSpeed(Float.parseFloat("0.2"));
                                SkyLogger.sendMessage(player, language.get(UserStrings.KEY.commands_speed_switched_walking).replace("%speed%", "default"));
                                return true;
                            }
                        } else {
                            sendSyntax(sender, language);
                            return false;
                        }
                    } else {
                        if (player.isFlying()) {
                            player.setFlySpeed(Float.parseFloat("0." + args[0]));
                            SkyLogger.sendMessage(player, language.get(UserStrings.KEY.commands_speed_switched_flying).replace("%speed%", args[0]));
                            return true;
                        } else {
                            player.setWalkSpeed(Float.parseFloat("0." + args[0]));
                            SkyLogger.sendMessage(player, language.get(UserStrings.KEY.commands_speed_switched_walking).replace("%speed%", args[0]));
                            return true;
                        }
                    }
                } else {
                    SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_noPermission));
                    return false;
                }
            } else {
                sendSyntax(sender, language);
                return false;
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String @NotNull [] args) {

        ArrayList<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions = new ArrayList<>();
            if (sender.hasPermission("zyneon.skyblock.commands.speed")) {
                completions.add("0");
                completions.add("1");
                completions.add("2");
                completions.add("3");
                completions.add("4");
                completions.add("5");
                completions.add("6");
                completions.add("7");
                completions.add("8");
                completions.add("9");
                completions.add("d");
                completions.add("default");
            }
        } else if(args.length == 2) {
            completions = new ArrayList<>();
            if(sender.hasPermission("zyneon.skyblock.commands.speed.other")) {
                for(Player all : Bukkit.getOnlinePlayers()) {
                    completions.add(all.getName());
                }
            }
        }
        return completions;
    }
}
