package com.zyneonstudios.nexus.skyblock.listeners;

import com.zyneonstudios.nexus.skyblock.SkyBlock;
import com.zyneonstudios.nexus.skyblock.users.SkyUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        Player player = event.getPlayer();
        SkyUser user = SkyBlock.getUser(player);
        event.setQuitMessage("§8« §c"+user.getName());
        user.disconnect();
    }
}