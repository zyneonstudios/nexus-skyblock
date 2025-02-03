package com.zyneonstudios.nexus.skyblock.listeners;

import com.zyneonstudios.nexus.skyblock.SkyBlock;
import com.zyneonstudios.nexus.skyblock.users.SkyUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        SkyUser user = SkyBlock.getUser(player);
        event.setJoinMessage("§8» §a"+user.getName());
    }
}