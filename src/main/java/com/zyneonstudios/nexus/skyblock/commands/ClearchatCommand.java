package com.zyneonstudios.nexus.skyblock.commands;

import com.zyneonstudios.nexus.skyblock.SkyBlock;
import com.zyneonstudios.nexus.skyblock.users.UserStrings;
import com.zyneonstudios.nexus.skyblock.utilities.SkyLogger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearchatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String @NotNull [] args) {

        UserStrings language = SkyBlock.getStrings();
        if (sender instanceof Player player) {
            language = SkyBlock.getUser(player).getUserStrings();
        }

        if (sender.hasPermission("zyneon.skyblock.commands.clearchat")) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (!all.hasPermission("zyneon.skyblock.commands.clearchat")) {
                    for (int i = 0; i < 1000; i++) {
                        SkyLogger.sendRaw(all, " §0 ");
                    }
                    SkyLogger.sendMessage(all, language.get(UserStrings.KEY.commands_clearchat_cleared));
                } else {
                    SkyLogger.sendMessage(all, language.get(UserStrings.KEY.commands_clearchat_cleared_permission));
                }
            }
            return true;
        } else {
            SkyLogger.sendError(language.get(UserStrings.KEY.errors_noPermission));
            return false;
        }
    }
}
