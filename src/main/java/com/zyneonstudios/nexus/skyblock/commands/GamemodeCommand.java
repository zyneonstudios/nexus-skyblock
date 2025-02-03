package com.zyneonstudios.nexus.skyblock.commands;

import com.zyneonstudios.nexus.skyblock.SkyBlock;
import com.zyneonstudios.nexus.skyblock.users.SkyUser;
import com.zyneonstudios.nexus.skyblock.users.UserStrings;
import com.zyneonstudios.nexus.skyblock.utilities.SkyLogger;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GamemodeCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(@NotNull final CommandSender sender, UserStrings language) {
        String syntax = "/gamemode <gamemode> (user)";
        if(!(sender instanceof Player)) {
            syntax.replace("(user)","<user>");
        }
        SkyLogger.sendError(sender,language.get(UserStrings.KEY.errors_syntax)+syntax);
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String @NotNull [] args) {
        UserStrings language = SkyBlock.getStrings();
        if (sender instanceof Player player) {
            language = SkyBlock.getUser(player).getUserStrings();
        }

        if(args.length == 0) {
            sendSyntax(sender, language);
        } else if(args.length == 1) {
            if(sender instanceof Player player) {
                String gamemode = resolveGamemode(args[0]);
                try {
                    GameMode gameMode = GameMode.valueOf(gamemode.toUpperCase());
                    if (sender.hasPermission("zyneon.skyblock.commands.gamemode." + gamemode)) {
                        player.setGameMode(gameMode);
                        SkyLogger.sendMessage(sender,language.get(UserStrings.KEY.commands_gamemode_switched)+gamemode);
                    }
                } catch (Exception e) {
                    sendSyntax(sender, language);
                }
            } else {
                sendSyntax(sender, language);
            }
        } else {
            if(Bukkit.getPlayer(args[1])!=null) {
                Player target = Bukkit.getPlayer(args[1]);
                SkyUser targetUser = SkyBlock.getUser(target);
                String gamemode = resolveGamemode(args[0]);
                try {
                    GameMode gameMode = GameMode.valueOf(gamemode.toUpperCase());
                    if(sender.hasPermission("zyneon.skyblock.commands.gamemode."+gamemode+".other")) {
                        target.setGameMode(gameMode);
                        SkyLogger.sendMessage(sender,language.get(UserStrings.KEY.commands_gamemode_switched_other).replace("%player%",targetUser.getName()).replace("%gamemode%",gamemode));
                        targetUser.sendMessage(targetUser.getUserStrings().get(UserStrings.KEY.commands_gamemode_switched)+gamemode);
                    }
                } catch (Exception e) {
                    sendSyntax(sender, language);
                }
            } else {
                SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_playerNotFound));
            }
        }
        return false;
    }

    private String resolveGamemode(String input) {
        input = input.toLowerCase();
        switch (input) {
            case "0", "s", "survival" -> {
                return "survival";
            }
            case "1", "c", "creative" -> {
                return "creative";
            }
            case "2", "a", "adventure" -> {
                return "adventure";
            }
            case "3", "w", "spectator", "sp" -> {
                return "spectator";
            }
        }
        return "null";
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        ArrayList<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions = new ArrayList<>();
            if (sender.hasPermission("zyneon.skyblock.commands.gamemode.survival")) {
                completions.add("0");
                completions.add("s");
                completions.add("survival");
            }

            if (sender.hasPermission("zyneon.skyblock.commands.gamemode.creative")) {
                completions.add("1");
                completions.add("c");
                completions.add("creative");
            }

            if (sender.hasPermission("zyneon.skyblock.commands.gamemode.adventure")) {
                completions.add("2");
                completions.add("a");
                completions.add("adventure");
            }

            if (sender.hasPermission("zyneon.skyblock.commands.gamemode.spectator")) {
                completions.add("3");
                completions.add("sp");
                completions.add("w");
                completions.add("spectator");
            }
        } else if(args.length == 2) {
            completions = new ArrayList<>();
            if(sender.hasPermission("zyneon.skyblock.commands.gamemode.other")) {
                for(Player all : Bukkit.getOnlinePlayers()) {
                    completions.add(all.getName());
                }
            }
        }
        return completions;
    }
}