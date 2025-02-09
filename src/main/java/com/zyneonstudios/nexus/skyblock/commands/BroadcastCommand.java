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

public class BroadcastCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(CommandSender sender, UserStrings language) {
        String syntax = "/broadcast <info/warning/error> <Nachricht>";
        SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_syntax) + syntax);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String @NotNull [] args) {
        UserStrings language = SkyBlock.getStrings();
        if (sender instanceof Player player) {
            language = SkyBlock.getUser(player).getUserStrings();
        }

        if (sender.hasPermission("zyneon.skyblock.commands.broadcast")) {
            if (args.length >= 2) {

                String m = "";
                for (int i = 1; i < args.length; i++) {
                    m = m + args[i] + " ";
                }

                if (args[0].equalsIgnoreCase("info")) {
                    String message = language.get(UserStrings.KEY.commands_broadcast_information) + m.replace("&&", "%and%").replace("&", "§").replace("%and%", "&");
                    SkyLogger.sendRaw(message);
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        SkyLogger.sendRaw(all, message);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("warning")) {
                    String message = language.get(UserStrings.KEY.commands_broadcast_warning) + m.replace("&&", "%and%").replace("&", "§").replace("%and%", "&");
                    SkyLogger.sendRaw(message);
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        SkyLogger.sendRaw(all, message);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("error")) {
                    String message = language.get(UserStrings.KEY.commands_broadcast_error) + m.replace("&&", "%and%").replace("&", "§").replace("%and%", "&");
                    SkyLogger.sendRaw(message);
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        SkyLogger.sendRaw(all, message);
                    }
                    return true;
                } else {
                    sendSyntax(sender, language);
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
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        ArrayList<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("info");
            completions.add("warning");
            completions.add("error");
        }
        return completions;

    }
}
