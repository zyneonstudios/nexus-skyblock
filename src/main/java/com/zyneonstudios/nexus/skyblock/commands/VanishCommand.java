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

public class VanishCommand implements CommandExecutor, TabCompleter {

    public static ArrayList<Player> vP = new ArrayList<>();

    public static ArrayList<Player> getvP() {
        return vP;
    }

    private void sendSyntax(@NotNull final CommandSender sender, UserStrings language) {
        String syntax = "/vanish (user)";
        if(!(sender instanceof Player)) {
            syntax = syntax.replace("(user)","<user>");
        }
        SkyLogger.sendError(sender,language.get(UserStrings.KEY.errors_syntax)+syntax);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String @NotNull [] args) {

        UserStrings language = SkyBlock.getStrings();
        if (sender instanceof Player player) {
            language = SkyBlock.getUser(player).getUserStrings();
        }

        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                sendSyntax(sender, language);
            } else {
                if (sender.hasPermission("zyneon.skyblock.commands.vanish")) {
                    if (vP.contains(player)) {
                        vP.remove(player);
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            SkyUser user = SkyBlock.getUser(all);
                            if (all.hasPermission("zyneon.skyblock.commands.vanish.bypass") && !all.getName().equals(sender.getName())) {
                                user.sendMessage(user.getUserStrings().get(UserStrings.KEY.commands_vanish_deactivated_bypass).replace("%player%", player.getName()));
                            } else if (all.getName().equals(player.getName())) {
                                SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_vanish_deactivated));
                            } else {
                                all.showEntity(SkyBlock.getInstance(), player);
                            }
                        }
                        return true;
                    } else {
                        vP.add(player);
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            SkyUser user = SkyBlock.getUser(all);
                            if (all.hasPermission("zyneon.skyblock.commands.vanish.bypass") && !all.getName().equals(sender.getName())) {
                                user.sendMessage(user.getUserStrings().get(UserStrings.KEY.commands_vanish_activated_bypass).replace("%player%", player.getName()));
                            } else if (all.getName().equals(player.getName())) {
                                SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_vanish_activated));
                            } else {
                                all.hideEntity(SkyBlock.getInstance(),player);
                            }
                        }
                        return true;
                    }
                } else {
                    SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_noPermission));
                    return false;
                }
            }
        } else if (args.length == 1) {
            if (sender.hasPermission("zyneon.skyblock.commands.vanish.other")) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_playerNotFound));
                    return false;
                } else {
                    SkyUser tuser = SkyBlock.getUser(target);
                    if (vP.contains(target)) {
                        vP.remove(target);
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            SkyUser user = SkyBlock.getUser(all);
                            if (all.hasPermission("zyneon.skyblock.commands.vanish.bypass") && !all.getName().equals(target.getName())) {
                                user.sendMessage(user.getUserStrings().get(UserStrings.KEY.commands_vanish_deactivated_bypass).replace("%player%", target.getName()));
                            } else if (all.getName().equals(target.getName())) {
                                tuser.sendMessage(tuser.getUserStrings().get(UserStrings.KEY.commands_vanish_deactivated));
                            } else {
                                all.showEntity(SkyBlock.getInstance(), target);
                            }
                        }
                        return true;
                    } else {
                        vP.add(target);
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            SkyUser user = SkyBlock.getUser(all);
                            if (all.hasPermission("zyneon.skyblock.commands.vanish.bypass") && !all.getName().equals(target.getName())) {
                                user.sendMessage(user.getUserStrings().get(UserStrings.KEY.commands_vanish_activated_bypass).replace("%player%", target.getName()));
                            } else if (all.getName().equals(target.getName())) {
                                tuser.sendMessage(tuser.getUserStrings().get(UserStrings.KEY.commands_vanish_activated));
                            } else {
                                all.hideEntity(SkyBlock.getInstance(),target);
                            }
                        }
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
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        ArrayList<String> completions = new ArrayList<>();

        if(args.length == 1) {
            completions = new ArrayList<>();
            if(sender.hasPermission("zyneon.skyblock.commands.fly.other")) {
                for(Player all : Bukkit.getOnlinePlayers()) {
                    completions.add(all.getName());
                }
            }
        }
        return completions;

    }

}
