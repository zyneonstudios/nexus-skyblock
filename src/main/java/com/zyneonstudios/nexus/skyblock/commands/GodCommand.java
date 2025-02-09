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

public class GodCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(@NotNull final CommandSender sender, UserStrings language) {
        String syntax = "/god (user)";
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
            if (sender instanceof Player player) {
                if (sender.hasPermission("zyneon.skyblock.commands.god")) {
                    if (!player.isInvulnerable()) {
                        player.setInvulnerable(true);
                        SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_god_activated));
                        return true;
                    } else {
                        player.setInvulnerable(false);
                        SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_god_deactivated));
                        return true;
                    }
                } else {
                    SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_noPermission));
                    return false;
                }
            } else {
                sendSyntax(sender, language);
            }
        } else if (args.length == 1) {
            if (sender.hasPermission("zyneon.skyblock.god.other")) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_playerNotFound));
                    return false;
                } else {
                    SkyUser user = SkyBlock.getUser(target);
                    if (!target.isInvulnerable()) {
                        target.setInvulnerable(true);
                        user.sendMessage(user.getUserStrings().get(UserStrings.KEY.commands_god_activated));
                        SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_god_activated_other));
                        return true;
                    } else {
                        target.setInvulnerable(false);
                        user.sendMessage(user.getUserStrings().get(UserStrings.KEY.commands_god_deactivated));
                        SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_god_deactivated_other));
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
