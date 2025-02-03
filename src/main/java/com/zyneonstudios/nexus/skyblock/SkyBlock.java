package com.zyneonstudios.nexus.skyblock;

import com.zyneonstudios.nexus.Preloader;
import com.zyneonstudios.nexus.skyblock.commands.*;
import com.zyneonstudios.nexus.skyblock.islands.Island;
import com.zyneonstudios.nexus.skyblock.listeners.PlayerChatListener;
import com.zyneonstudios.nexus.skyblock.listeners.PlayerInventoryListener;
import com.zyneonstudios.nexus.skyblock.listeners.PlayerJoinListener;
import com.zyneonstudios.nexus.skyblock.listeners.PlayerQuitListener;
import com.zyneonstudios.nexus.skyblock.managers.InterfaceManager;
import com.zyneonstudios.nexus.skyblock.storage.Storage;
import com.zyneonstudios.nexus.skyblock.users.SkyUser;
import com.zyneonstudios.nexus.skyblock.users.UserStrings;
import com.zyneonstudios.nexus.skyblock.utilities.SkyLogger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class SkyBlock {

    private static Preloader instance = null;
    private static HashMap<UUID, SkyUser> users = new HashMap<>();
    private static HashMap<UUID, Island> islands = new HashMap<>();
    private static UserStrings userStrings = new UserStrings();
    private static Storage storage;

    public SkyBlock(Preloader preloader) {
        instance = preloader;
    }

    public void load() {
        SkyLogger.sendRaw("==========================================================================");
        sendBigFont();
        SkyLogger.sendRaw(" ");

        SkyLogger.sendRaw("  ● Initializing data and storage...");
        if(new File("plugins/SkyBlock/data/").exists()||new File("plugins/SkyBlock/data/").mkdirs()) {
            storage = new Storage("plugins/SkyBlock/data/sqlite.sql");
        } else {
            throw new RuntimeException("SkyBlock data directory doesn't exist");
        }
        SkyLogger.sendRaw(" ");

        SkyLogger.sendRaw("  ● Initializing InterfaceManager...");
        ItemMeta itemMeta = InterfaceManager.placeholder.getItemMeta();
        itemMeta.setDisplayName("§0"); InterfaceManager.placeholder.setItemMeta(itemMeta);
        SkyLogger.sendRaw(" ");

        SkyLogger.sendRaw("==========================================================================");
    }

    public void enable() {
        SkyLogger.sendRaw("==========================================================================");
        sendBigFont();
        SkyLogger.sendRaw(" ");
        SkyLogger.sendRaw("  ● Loading commands...");
        loadCommand(new BroadcastCommand());
        loadCommand(new ClearchatCommand());
        loadCommand(new FlyCommand());
        loadCommand(new GamemodeCommand());
        loadCommand(new GodCommand());
        loadCommand(new HealCommand());
        loadCommand(new InvseeCommand());
        loadCommand(new IslandCommand());
        loadCommand(new KillCommand());
        loadCommand(new MoneyCommand());
        loadCommand(new PingCommand());
        loadCommand(new SpeedCommand());
        loadCommand(new TellCommand());
        loadCommand(new VanishCommand());
        loadCommand(new WorldCommand());
        SkyLogger.sendRaw(" ");
        SkyLogger.sendRaw("  ● Loading listeners...");
        registerEvents(new PlayerChatListener());
        registerEvents(new PlayerInventoryListener());
        registerEvents(new PlayerJoinListener());
        registerEvents(new PlayerQuitListener());
        SkyLogger.sendRaw(" ");
        SkyLogger.sendRaw("==========================================================================");
        SkyLogger.sendRaw(" ");
        SkyLogger.sendRaw(" ");
        SkyLogger.sendRaw(" ");
    }

    private void loadCommand(CommandExecutor command) {
        String name = command.getClass().getSimpleName().replace("Command", "");
        SkyLogger.sendRaw("    • Loading command \""+name+"\"...");
        instance.getCommand(name).setExecutor(command);
        try {
            instance.getCommand(name).setTabCompleter((TabCompleter)command);
            SkyLogger.sendRaw("      · Loaded tab completer for command \""+name+"\"...");
        } catch (Exception ignore) {}
    }

    private void registerEvents(Listener listener) {
        String name = listener.getClass().getSimpleName().replace("Listener", "");
        SkyLogger.sendRaw("    • Loading listener \""+name+"\"...");
        Bukkit.getPluginManager().registerEvents(listener, instance);
    }

    public void disable() {
        SkyLogger.sendRaw("==========================================================================");
        sendBigFont();
        SkyLogger.sendRaw(" ");
        SkyLogger.sendRaw("==========================================================================");
    }

    private void sendBigFont() {
        SkyLogger.sendRaw("░██████╗██╗░░██╗██╗░░░██╗██████╗░██╗░░░░░░█████╗░░█████╗░██╗░░██╗");
        SkyLogger.sendRaw("██╔════╝██║░██╔╝╚██╗░██╔╝██╔══██╗██║░░░░░██╔══██╗██╔══██╗██║░██╔╝");
        SkyLogger.sendRaw("╚█████╗░█████═╝░░╚████╔╝░██████╦╝██║░░░░░██║░░██║██║░░╚═╝█████═╝░");
        SkyLogger.sendRaw("░╚═══██╗██╔═██╗░░░╚██╔╝░░██╔══██╗██║░░░░░██║░░██║██║░░██╗██╔═██╗░");
        SkyLogger.sendRaw("██████╔╝██║░╚██╗░░░██║░░░██████╦╝███████╗╚█████╔╝╚█████╔╝██║░╚██╗");
        SkyLogger.sendRaw("╚═════╝░╚═╝░░╚═╝░░░╚═╝░░░╚═════╝░╚══════╝░╚════╝░░╚════╝░╚═╝░░╚═╝");
        SkyLogger.sendRaw("Version: "+instance.getPluginMeta().getVersion());
    }

    public static Preloader getInstance() {
        return instance;
    }

    public static Collection<Island> getIslands() {
        return islands.values();
    }

    public static Island getIsland(UUID uuid) {
        if(islands.containsKey(uuid)) {
            return islands.get(uuid);
        }
        return null;
    }

    public static Collection<SkyUser> getUsers() {
        return users.values();
    }

    public static SkyUser getUser(UUID uuid) {
        if(users.containsKey(uuid)) {
            return users.get(uuid);
        }
        return null;
    }

    public static SkyUser getUser(Player player) {
        if(!users.containsKey(player.getUniqueId())) {
            users.put(player.getUniqueId(), new SkyUser(player));
        }
        return users.get(player.getUniqueId());
    }

    public static UserStrings getStrings() {
        return userStrings;
    }

    public static void removeUser(UUID uuid) {
        users.remove(uuid);
    }

    public static void removeUser(Player player) {
        users.remove(player.getUniqueId());
    }

    public static void removeUser(SkyUser user) {
        users.remove(user.getUUID());
    }

    public static Storage getStorage() {
        return storage;
    }
}
