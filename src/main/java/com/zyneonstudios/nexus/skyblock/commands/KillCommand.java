package com.zyneonstudios.nexus.skyblock.commands;

import com.zyneonstudios.nexus.skyblock.SkyBlock;
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

public class KillCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(CommandSender sender, UserStrings language) {
        String syntax = "/kill (user)";
        if (!(sender instanceof Player player)) {
            syntax.replace("(user)", "<user>");
        }
        SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_syntax) + syntax);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        UserStrings language = SkyBlock.getStrings();
        if (sender instanceof Player player) {
            language = SkyBlock.getUser(player).getUserStrings();
        }

        if (!(sender instanceof Player player)) {
            if (sender.hasPermission("zyneon.skyblock.commands.kill.other")) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        target.setHealth(0);
                        SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_kill_killed_other).replace("%player%", target.getName()));
                        SkyLogger.sendMessage(target, SkyBlock.getUser(target).getUserStrings().get(UserStrings.KEY.commands_kill_killed));
                        return true;
                    } else {
                        SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_playerNotFound));
                        return false;
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
            if (args.length == 1) {
                if (player.hasPermission("zyneon.skyblock.commands.kill.other")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        target.setHealth(0);
                        SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_kill_killed_other).replace("%player%", target.getName()));
                        SkyLogger.sendMessage(target, SkyBlock.getUser(target).getUserStrings().get(UserStrings.KEY.commands_kill_killed));
                        return true;
                    } else {
                        SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_playerNotFound));
                        return false;
                    }
                } else {
                    SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_noPermission));
                    return false;
                }
            } else if (args.length == 0) {
                if (player.hasPermission("zyneon.skyblock.commands.kill")) {
                    player.setHealth(0);
                    SkyLogger.sendMessage(player, language.get(UserStrings.KEY.commands_kill_killed));
                    return true;
                } else {
                    SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_noPermission));
                    return false;
                }
            } else {
                sendSyntax(player, language);
                return false;
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        ArrayList<String> completions = new ArrayList<>();

        if(args.length == 1) {
            completions = new ArrayList<>();
            if(sender.hasPermission("zyneon.skyblock.commands.kill.other")) {
                for(Player all : Bukkit.getOnlinePlayers()) {
                    completions.add(all.getName());
                }
            }
        }
        return completions;

    }
}
