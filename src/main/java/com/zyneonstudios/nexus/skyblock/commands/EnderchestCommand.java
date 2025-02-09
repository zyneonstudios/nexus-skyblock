package com.zyneonstudios.nexus.skyblock.commands;

import com.zyneonstudios.nexus.skyblock.SkyBlock;
import com.zyneonstudios.nexus.skyblock.users.UserStrings;
import com.zyneonstudios.nexus.skyblock.utilities.SkyLogger;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EnderchestCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(@NotNull final CommandSender sender, UserStrings language) {
        String syntax = "/ec (user)";
        SkyLogger.sendError(sender,language.get(UserStrings.KEY.errors_syntax)+syntax);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String @NotNull [] args) {
        UserStrings language = SkyBlock.getStrings();
        if (sender instanceof Player player) {
            language = SkyBlock.getUser(player).getUserStrings();
        }

        if (sender instanceof Player player) {
            if (args.length == 1) {
                if (sender.hasPermission("zyneon.skyblock.commands.enderchest.other")) {
                     Player target = Bukkit.getPlayer(args[0]);
                     if (target != null) {
                         player.openInventory(target.getEnderChest());
                         SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_enderchest_ec_opened).replace("%player%", target.getName()));
                     } else {
                         SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_playerNotFound));
                     }
                } else {
                    SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_noPermission));
                }
            } else if (args.length == 0) {
                if (sender.hasPermission("zyneon.skyblock.commands.enderchest")) {
                    player.openInventory(player.getEnderChest());
                    player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);
                } else {
                    SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_noPermission));
                }
            } else {
                sendSyntax(sender, language);
            }
        } else {
            SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_noConsoleCommand));
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        ArrayList<String> completions = new ArrayList<>();

        if(args.length == 1) {
            completions = new ArrayList<>();
            if(sender.hasPermission("zyneon.skyblock.commands.enderchest.other")) {
                for(Player all : Bukkit.getOnlinePlayers()) {
                    completions.add(all.getName());
                }
            }
        }
        return completions;
    }
}
