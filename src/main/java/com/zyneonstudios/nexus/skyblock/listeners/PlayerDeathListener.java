package com.zyneonstudios.nexus.skyblock.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getPlayer();
        if(player.getRespawnLocation()==null) {
            player.setRespawnLocation(player.getWorld().getSpawnLocation());
        }
    }
}