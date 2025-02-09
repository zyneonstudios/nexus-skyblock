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

public class PingCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(CommandSender sender, UserStrings language) {
        String syntax = "/ping (user)";
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

        if (args.length == 1) {
            if (sender.hasPermission("zyneon.skyblock.commands.ping.other")) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_ping_pinged_other).replace("%player%", target.getName()).replace("%ping%", Integer.toString(target.getPing())));
                    return true;
                } else {
                    SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_playerNotFound));
                    return false;
                }
            } else {
                SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_noPermission));
                return false;
            }
        } else if (sender instanceof Player player) {
            if (args.length == 0) {
                SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_ping_pinged).replace("%ping%", Integer.toString(player.getPing())));
                return true;
            } else {
                sendSyntax(sender, language);
                return false;
            }
        } else {
            sendSyntax(sender, language);
            return false;
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
