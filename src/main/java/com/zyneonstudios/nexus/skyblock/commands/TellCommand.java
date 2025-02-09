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

public class TellCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(@NotNull final CommandSender sender, UserStrings language) {

        String syntax = "/msg <user> <message>";
        SkyLogger.sendError(sender,language.get(UserStrings.KEY.errors_syntax)+syntax);

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String @NotNull [] args) {

        UserStrings language = SkyBlock.getStrings();
        if (sender instanceof Player player) {
            language = SkyBlock.getUser(player).getUserStrings();
        }

        if (args.length > 1) {
            if (!(sender instanceof Player)) {
                SkyLogger.sendError(sender,language.get(UserStrings.KEY.errors_noConsoleCommand));
                return false;
            } else {
                if (Bukkit.getPlayer(args[0]) != null) {
                    SkyUser user = SkyBlock.getUser(Bukkit.getPlayer(args[0]));
                    StringBuilder m_recive = new StringBuilder(user.getUserStrings().get(UserStrings.KEY.commands_tell_message_from_other).replace("%player%", sender.getName()));
                    StringBuilder m_send = new StringBuilder(user.getUserStrings().get(UserStrings.KEY.commands_tell_message_to_other).replace("%player%", args[0]));
                    for (int i = 1; i < args.length; i++) {
                        m_recive.append(args[i].replace("&&", "%and%").replace("&", "§").replace("%and%", "&")).append(" ");
                        m_send.append(args[i].replace("&&", "%and%").replace("&", "§").replace("%and%", "&")).append(" ");
                    }
                    SkyLogger.sendMessage(sender, m_send.toString());
                    user.sendMessage(m_recive.toString());
                    return true;
                } else {
                    SkyLogger.sendError(sender,language.get(UserStrings.KEY.errors_playerNotFound));
                    return false;
                }
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
            for(Player all : Bukkit.getOnlinePlayers()) {
                completions.add(all.getName());
            }
        }
        return completions;

    }

}
