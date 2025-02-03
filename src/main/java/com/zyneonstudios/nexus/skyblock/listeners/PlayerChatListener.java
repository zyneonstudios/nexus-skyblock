package com.zyneonstudios.nexus.skyblock.listeners;

import com.zyneonstudios.nexus.skyblock.SkyBlock;
import com.zyneonstudios.nexus.skyblock.managers.InterfaceManager;
import com.zyneonstudios.nexus.skyblock.users.SkyUser;
import com.zyneonstudios.nexus.skyblock.users.UserStrings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void asyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        SkyUser user = SkyBlock.getUser(player);
        event.setCancelled(true);
        if(user.getInputMode()==null) {
            Bukkit.broadcastMessage("§b"+user.getName()+ " §8» §7"+ event.getMessage().replace("&&","%and%").replace("&","§").replace("%and%","&"));
        }
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        SkyUser user = SkyBlock.getUser(event.getPlayer());
        event.setCancelled(true);
        if(user.getInputMode()!=null) {
            resolveInputMode(user, event.getMessage());
        }
    }

    private void resolveInputMode(SkyUser user, String message) {
        if(user.getInputMode().equals("world_creator_seed")) {
            if(user.getWorldCreator()!=null) {
                try {
                    user.getWorldCreator().setSeed(Integer.parseInt(message));
                    user.getPlayer().openInventory(InterfaceManager.getWorldCreatorInterface(user.getWorldCreator(),user.getUserStrings()));
                    user.setInputMode(null);
                } catch (Exception e) {
                    e.printStackTrace();
                    user.sendError(user.getUserStrings().get(UserStrings.KEY.errors_notANumber));
                }
            } else {
                user.sendError(user.getUserStrings().get(UserStrings.KEY.commands_world_seedFailed));
                user.setInputMode(null);
            }
        }
    }
}