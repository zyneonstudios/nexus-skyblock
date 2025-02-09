package com.zyneonstudios.nexus.skyblock.listeners;

import com.zyneonstudios.nexus.skyblock.SkyBlock;
import com.zyneonstudios.nexus.skyblock.commands.VanishCommand;
import com.zyneonstudios.nexus.skyblock.managers.WorldManager;
import com.zyneonstudios.nexus.skyblock.users.SkyUser;
import com.zyneonstudios.nexus.skyblock.users.UserStrings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private static ArrayList<Player> vanishedPlayers = VanishCommand.getvP();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        SkyUser user = SkyBlock.getUser(player);
        UUID uuid = user.getUUID();
        event.setJoinMessage("§8» §a"+user.getName());

        if (!player.hasPermission("zyneon.skyblock.commands.vanish.bypass")) {
            for (Player vanishedPlayer : vanishedPlayers) {
                player.hideEntity(SkyBlock.getInstance(),vanishedPlayer);
            }
        } else {
            if (!vanishedPlayers.isEmpty()) {
                StringBuilder message = new StringBuilder(user.getUserStrings().get(UserStrings.KEY.commands_vanish_bypass_joined));
                for (Player vanishedPlayer : vanishedPlayers) {
                    if (vanishedPlayers.size()>1 && vanishedPlayer != vanishedPlayers.getLast()) {
                        message.append(vanishedPlayer.getName()).append("§8, §a");
                    }
                }
                user.sendMessage(message.toString());
            }
        }

        if(SkyBlock.getStorage().get("users."+uuid+".lastLoc.saved")!=null) {
            if(SkyBlock.getStorage().get("users."+uuid+".lastLoc.saved").toString().equals("true")) {
                String world = SkyBlock.getStorage().get("users."+uuid+".lastLoc.world").toString();
                if(new File(world).exists()) {
                    if(Bukkit.getWorld(world)==null) {
                        WorldManager.loadWorld(world);
                    }
                    Location loc = new Location(Bukkit.getWorld(world),Double.parseDouble(SkyBlock.getStorage().get("users."+uuid+".lastLoc.X").toString()),Double.parseDouble(SkyBlock.getStorage().get("users."+uuid+".lastLoc.Y").toString()),Double.parseDouble(SkyBlock.getStorage().get("users."+uuid+".lastLoc.Z").toString()),Float.parseFloat(SkyBlock.getStorage().get("users."+uuid+".lastLoc.yaw").toString()),Float.parseFloat(SkyBlock.getStorage().get("users."+uuid+".lastLoc.pitch").toString()));
                    player.teleport(loc);
                }
            }
        }
    }
}