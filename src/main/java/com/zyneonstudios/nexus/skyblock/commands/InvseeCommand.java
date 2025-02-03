package com.zyneonstudios.nexus.skyblock.commands;

import com.zyneonstudios.nexus.skyblock.SkyBlock;
import com.zyneonstudios.nexus.skyblock.users.UserStrings;
import com.zyneonstudios.nexus.skyblock.utilities.SkyLogger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class InvseeCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(@NotNull final CommandSender sender, UserStrings language) {
        String syntax = "/invsee (user)";
        SkyLogger.sendError(sender,language.get(UserStrings.KEY.errors_syntax)+syntax);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        UserStrings language = SkyBlock.getStrings();
        if (sender instanceof Player player) {
            language = SkyBlock.getUser(player).getUserStrings();
        }

        if (args.length == 1) {
            if (sender.hasPermission("zyneon.skyblock.commands.invsee")) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.errors_playerNotFound));
                    return false;
                } else {
                    Player player  = Bukkit.getPlayer(sender.getName());
                    Inventory inventory = Bukkit.createInventory(null, 45, language.get(UserStrings.KEY.interface_invsee_title).replace("%player%", target.getName()));
                    inventory.setContents(target.getInventory().getContents());

                    ItemStack hotbarslot = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                    ItemMeta hotbarslot_m =  hotbarslot.getItemMeta();
                    hotbarslot_m.setDisplayName(language.get(UserStrings.KEY.interface_invsee_hotbarslot));
                    hotbarslot.setItemMeta(hotbarslot_m);

                    ItemStack invslot = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                    ItemMeta invslot_m =  hotbarslot.getItemMeta();
                    invslot_m.setDisplayName(language.get(UserStrings.KEY.interface_invsee_invslot));
                    invslot.setItemMeta(invslot_m);

                    ItemStack armorslot = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                    ItemMeta armorslot_m =  hotbarslot.getItemMeta();
                    armorslot_m.setDisplayName(language.get(UserStrings.KEY.interface_invsee_armorslot));
                    armorslot.setItemMeta(armorslot_m);

                    ItemStack shieldslot = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                    ItemMeta shieldslot_m =  hotbarslot.getItemMeta();
                    shieldslot_m.setDisplayName(language.get(UserStrings.KEY.interface_invsee_shieldslot));
                    shieldslot.setItemMeta(shieldslot_m);

                    ItemStack slot = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                    ItemMeta slot_m =  hotbarslot.getItemMeta();
                    slot_m.setDisplayName(language.get(UserStrings.KEY.interface_invsee_slot));
                    slot.setItemMeta(slot_m);

                    for (int i = 0; i < 45; i++) {
                        if (inventory.getItem(i) == null) {
                            if (i < 9) { inventory.setItem(i, hotbarslot); }
                            if (i > 8 && i < 36) { inventory.setItem(i, invslot); }
                            if (i > 35 && i < 40) { inventory.setItem(i, armorslot); }
                            if (i == 40) { inventory.setItem(i, shieldslot); }
                            if (i > 40) { inventory.setItem(i, slot); }
                        }
                    }
                    player.openInventory(inventory);
                    SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.commands_invsee_inv_opened).replace("%player%", target.getName()));
                    return true;
                }
            } else {
                SkyLogger.sendMessage(sender, language.get(UserStrings.KEY.errors_noPermission));
                return false;
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
            if(sender.hasPermission("zyneon.skyblock.commands.invsee")) {
                for(Player all : Bukkit.getOnlinePlayers()) {
                    completions.add(all.getName());
                }
            }
        }
        return completions;

    }

}
