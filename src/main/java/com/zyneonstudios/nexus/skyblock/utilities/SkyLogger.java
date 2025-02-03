package com.zyneonstudios.nexus.skyblock.utilities;

import com.zyneonstudios.nexus.skyblock.SkyBlock;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkyLogger {

    private static final String prefix = "§9SkyBlock §8» §7";

    public static void sendRaw(String... message) {
        for(String msg : message) {
            Bukkit.getConsoleSender().sendMessage(msg);
        }
    }

    public static void sendRaw(CommandSender receiver, String... message) {
        if(receiver instanceof Player player) {
            SkyBlock.getUser(player).sendRaw(message);
            return;
        }
        sendRaw(message);
    }

    public static void sendMessage(String... message) {
        for(String msg : message) {
            Bukkit.getConsoleSender().sendMessage(prefix+msg);
        }
    }

    public static void sendMessage(CommandSender receiver, String... message) {
        if(receiver instanceof Player player) {
            SkyBlock.getUser(player).sendMessage(message);
            return;
        }
        sendMessage(receiver, message);
    }

    public static void sendError(String... messages) {
        for(String message : messages) {
            Bukkit.getConsoleSender().sendMessage("§4Error§8: §c"+message);
        }
    }

    public static void sendError(CommandSender receiver, String... message) {
        if(receiver instanceof Player player) {
            SkyBlock.getUser(player).sendError(message);
            return;
        }
        sendError(message);
    }

    public static String getPrefix() {
        return prefix;
    }
}
