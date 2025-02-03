package com.zyneonstudios.nexus.skyblock.commands;

import com.zyneonstudios.nexus.skyblock.SkyBlock;
import com.zyneonstudios.nexus.skyblock.users.SkyUser;
import com.zyneonstudios.nexus.skyblock.users.UserStrings;
import com.zyneonstudios.nexus.skyblock.utilities.SkyLogger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MoneyCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(CommandSender sender, UserStrings language) {

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        UserStrings language = SkyBlock.getStrings();
        if (sender instanceof Player player) {
            language = SkyBlock.getUser(player).getUserStrings();
        }

        if(args.length == 0) {
            if(sender instanceof Player player) {
                SkyUser user = SkyBlock.getUser(player);
                user.sendMessage(language.get(UserStrings.KEY.commands_money_get_self).replace("%balance%",user.getSkycoins().toString()));
            } else {
                sendSyntax(sender, language);
            }
        } else if(args.length == 1) {
            sendSyntax(sender, language);
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("add")) {
                if(sender instanceof Player player) {
                    player.performCommand("money add " + args[1] + " " + player.getName());
                } else {
                    sendSyntax(sender, language);
                }
            } else if(args[0].equalsIgnoreCase("remove")) {
                if(sender instanceof Player player) {
                    player.performCommand("money remove " + args[1] + " " + player.getName());
                } else {
                    sendSyntax(sender, language);
                }
            } else if(args[0].equalsIgnoreCase("set")) {
                if(sender instanceof Player player) {
                    player.performCommand("money set " + args[1] + " " + player.getName());
                } else {
                    sendSyntax(sender, language);
                }
            } else if(args[0].equalsIgnoreCase("get")) {
                try {
                    if (SkyBlock.getStorage().get("users." + Bukkit.getOfflinePlayer(args[1]).getUniqueId() + ".balance") != null) {
                        double targetBalance = SkyBlock.getStorage().getDouble("users." + Bukkit.getOfflinePlayer(args[1]).getUniqueId() + ".balance");
                        SkyLogger.sendMessage(sender,language.get(UserStrings.KEY.commands_money_get_other).replace("%balance%", Double.toString(targetBalance)).replace("%player%",args[1]));
                    } else {
                        SkyLogger.sendError(sender,language.get(UserStrings.KEY.errors_playerNotFound));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_playerNotFound));
                }
            }
        } else if(args.length == 3) {
            if(args[0].equalsIgnoreCase("add")) {
                if(sender.hasPermission("zyneon.skyblock.commands.money.set")) {
                    try {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                        double balance;
                        try {
                            balance = Double.parseDouble(args[2]);
                        } catch (Exception e) {
                            SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_notANumber));
                            return false;
                        }
                        if(Bukkit.getPlayer(target.getUniqueId()) != null) {
                            SkyUser targetUser = SkyBlock.getUser(target.getUniqueId());
                            targetUser.addSkycoins(balance);
                            targetUser.sendMessage(targetUser.getUserStrings().get(UserStrings.KEY.commands_money_set_self).replace("%balance%", targetUser.getSkycoins().toString()));
                        } else {
                            SkyBlock.getStorage().ensure("users." + target.getUniqueId() + ".balance",0.0);
                            balance = SkyBlock.getStorage().getDouble("users." + target.getUniqueId() + ".balance")+balance;
                            SkyBlock.getStorage().setDouble("users." + target.getUniqueId() + ".balance",balance);
                        }
                        if(sender instanceof Player player) {
                            if(target.getUniqueId().equals(player.getUniqueId())) {
                                return false;
                            }
                        }
                        SkyLogger.sendMessage(sender,language.get(UserStrings.KEY.commands_money_set_other).replace("%player%", args[1]).replace("%balance%", Double.toString(balance)));
                    } catch (Exception e) {
                        SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_playerNotFound));
                    }
                } else {
                    SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_noPermission));
                }
            } else if(args[0].equalsIgnoreCase("remove")) {
                if(sender.hasPermission("zyneon.skyblock.commands.money.set")) {
                    try {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                        double balance;
                        try {
                            balance = Double.parseDouble(args[2]);
                        } catch (Exception e) {
                            SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_notANumber));
                            return false;
                        }
                        if(Bukkit.getPlayer(target.getUniqueId()) != null) {
                            SkyUser targetUser = SkyBlock.getUser(target.getUniqueId());
                            targetUser.removeSkycoins(balance);
                            targetUser.sendMessage(targetUser.getUserStrings().get(UserStrings.KEY.commands_money_set_self).replace("%balance%", targetUser.getSkycoins().toString()));
                        } else {
                            SkyBlock.getStorage().ensure("users." + target.getUniqueId() + ".balance",0.0);
                            balance = SkyBlock.getStorage().getDouble("users." + target.getUniqueId() + ".balance")-balance;
                            if(balance<0) {
                                balance = 0;
                            }
                            SkyBlock.getStorage().setDouble("users." + target.getUniqueId() + ".balance",balance);
                        }
                        if(sender instanceof Player player) {
                            if(target.getUniqueId().equals(player.getUniqueId())) {
                                return false;
                            }
                        }
                        SkyLogger.sendMessage(sender,language.get(UserStrings.KEY.commands_money_set_other).replace("%player%", args[1]).replace("%balance%", Double.toString(balance)));
                    } catch (Exception e) {
                        SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_playerNotFound));
                    }
                } else {
                    SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_noPermission));
                }
            } else if(args[0].equalsIgnoreCase("set")) {
                if(sender.hasPermission("zyneon.skyblock.commands.money.set")) {
                    try {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                        double balance;
                        try {
                            balance = Double.parseDouble(args[2]);
                        } catch (Exception e) {
                            SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_notANumber));
                            return false;
                        }
                        if(Bukkit.getPlayer(target.getUniqueId()) != null) {
                            SkyUser targetUser = SkyBlock.getUser(target.getUniqueId());
                            targetUser.setSkycoins(balance);
                            targetUser.sendMessage(targetUser.getUserStrings().get(UserStrings.KEY.commands_money_set_self).replace("%balance%", targetUser.getSkycoins().toString()));
                        } else {
                            SkyBlock.getStorage().setDouble("users." + target.getUniqueId() + ".balance",balance);
                        }
                        if(sender instanceof Player player) {
                            if(target.getUniqueId().equals(player.getUniqueId())) {
                                return false;
                            }
                        }
                        SkyLogger.sendMessage(sender,language.get(UserStrings.KEY.commands_money_set_other).replace("%player%", args[1]).replace("%balance%", Double.toString(balance)));
                    } catch (Exception e) {
                        SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_playerNotFound));
                    }
                } else {
                    SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_noPermission));
                }
            } else if(args[0].equalsIgnoreCase("pay")) {
                try {
                    if(sender instanceof Player player) {
                        SkyUser user = SkyBlock.getUser(player);
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                        if(user.getUUID().equals(target.getUniqueId())) {
                            user.sendError(language.get(UserStrings.KEY.commands_money_pay_self));
                            return false;
                        }
                        double amount;
                        try {
                            amount = Double.parseDouble(args[2]);
                        } catch (Exception e) {
                            SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_notANumber));
                            return false;
                        }
                        if (amount <= user.getSkycoins()) {
                            user.removeSkycoins(amount);

                            if(Bukkit.getPlayer(target.getUniqueId()) != null) {
                                SkyUser targetUser = SkyBlock.getUser(target.getUniqueId());
                                targetUser.addSkycoins(amount);
                                targetUser.sendPrefix(targetUser.getUserStrings().get(UserStrings.KEY.commands_money_payed).replace("%player%",user.getName()).replace("%amount%", Double.toString(amount)));
                                targetUser.getPlayer().performCommand("money");
                            } else {
                                SkyBlock.getStorage().ensure("users." + target.getUniqueId() + ".balance",0.0);
                                double balance = SkyBlock.getStorage().getDouble("users." + target.getUniqueId() + ".balance")+amount;
                                SkyBlock.getStorage().setDouble("users." + target.getUniqueId() + ".balance",balance);
                            }

                            user.sendPrefix(language.get(UserStrings.KEY.commands_money_pay_other).replace("%player%",user.getName()).replace("%amount%",Double.toString(amount)));
                            player.performCommand("money");
                        } else {
                            user.sendError(language.get(UserStrings.KEY.commands_money_pay_insufficient));
                        }
                    }
                } catch (Exception e) {
                    SkyLogger.sendError(sender, language.get(UserStrings.KEY.errors_playerNotFound));
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        ArrayList<String> completions = new ArrayList<>();
        if(args.length == 1) {
            completions = new ArrayList<>();
            completions.add("get");
            completions.add("pay");
            if(sender.hasPermission("zyneon.skyblock.commands.money.set")) {
                completions.add("set");
                completions.add("add");
                completions.add("remove");
            }
        } else if(args.length == 2) {
            completions = new ArrayList<>();
            for(Player all : Bukkit.getOnlinePlayers()) {
                completions.add(all.getName());
            }
        }
        return completions;
    }
}
