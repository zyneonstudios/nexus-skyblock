package com.zyneonstudios.nexus.skyblock.listeners;

import com.zyneonstudios.nexus.skyblock.SkyBlock;
import com.zyneonstudios.nexus.skyblock.commands.VanishCommand;
import com.zyneonstudios.nexus.skyblock.users.SkyUser;
import com.zyneonstudios.nexus.skyblock.users.UserStrings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class PlayerJoinListener implements Listener {

    private static ArrayList<Player> vanishedPlayers = VanishCommand.getvP();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        SkyUser user = SkyBlock.getUser(player);
        event.setJoinMessage("§8» §a"+user.getName());

        if (!player.hasPermission("zyneon.skyblock.commands.vanish.bypass")) {
            for (Player vanishedPlayer : vanishedPlayers) {
                player.hidePlayer(vanishedPlayer);
            }
        } else {
            if (!vanishedPlayers.isEmpty()) {
                StringBuilder message = new StringBuilder(user.getUserStrings().get(UserStrings.KEY.commands_vanish_bypass_joined));
                for (Player vanishedPlayer : vanishedPlayers) {
                    if (vanishedPlayer == vanishedPlayers.getLast()) {
                        message.append(vanishedPlayer.getName()).append("§8.");
                    }
                    message.append(vanishedPlayer.getName()).append("§8, §a");
                }
                user.sendMessage(message.toString());
            }
        }
    }
}