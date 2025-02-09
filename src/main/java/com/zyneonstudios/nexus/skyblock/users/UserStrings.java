package com.zyneonstudios.nexus.skyblock.users;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;

public class UserStrings {

    private static HashMap<String, String> language = new HashMap<>();

    public UserStrings() {
        initDefaults();
    }

    public UserStrings(YamlConfiguration language) {
        initLanguage(language);
    }

    public void initDefaults() {
        language = new HashMap<>();
        language.put("errors.prefix", "§4Error§8: §c");
        language.put("errors.noPermission", "§cYou are not permitted to do that§8!");
        language.put("errors.notANumber", "§cThis is not a valid number§8!");
        language.put("errors.noConsoleCommand", "§cThis command can§8'§ct be used by a console§8!");
        language.put("errors.syntax", "§cWrong syntax§8,§c use§8: §c");
        language.put("errors.playerNotFound", "§cThis player could not be found§8!");
        language.put("errors.worldNotFound", "§cThe world could not be found§8!");
        language.put("errors.worldAlreadyLoaded", "§cThis world is already loaded§8!");

        language.put("commands.gamemode.switched", "Your gamemode has been switched to§8: §e");
        language.put("commands.gamemode.switched.other", "You changed the gamemode from §a%player%§7 to §e%gamemode%§8.");
        language.put("commands.speed.switched.walking", "Your §awalking speed§7 has been changed to §e%speed%§8.");
        language.put("commands.speed.switched.flying", "Your §aflying speed§7 has been changed to §e%speed%§8.");
        language.put("commands.speed.switched.walking.other", "You changed the walking speed from §a%player%§7 to §e%speed%§8.");
        language.put("commands.speed.switched.flying.other", "You changed the flying speed from §a%player%§7 to §e%speed%§8.");
        language.put("commands.heal.healed", "You got §ehealed§8.");
        language.put("commands.heal.healed.other", "You §ehealed §a%player%§8.");
        language.put("commands.kill.killed", "You got §ekilled§8.");
        language.put("commands.kill.killed.other", "You §ekilled §a%player%§8.");
        language.put("commands.clearchat.cleared", "The Chat got §ecleared§8!");
        language.put("commands.clearchat.cleared.permission", "The Chat got §ecleared§8! §7However§8,§7 you can still see it§8,§7 because you got sufficient §epermission§8!");
        language.put("commands.ping.pinged", "Your Ping is §e%ping%§7ms§8!");
        language.put("commands.ping.pinged.other", "The Ping of §a%player%§7 is §e%ping%§7ms§8!");
        language.put("commands.broadcast.information", "§9SkyBlock §8» §7");
        language.put("commands.broadcast.warning", "§6Warning§8: §7");
        language.put("commands.broadcast.error", "§4Error§8: §c");
        language.put("commands.fly.setflight", "Your Flightmode was set to: §e");
        language.put("commands.fly.setflight.other", "Flightmode for §a%player%§7 was set to: §e");
        language.put("commands.world.info.name", "World§8: §e");
        language.put("commands.world.info.difficulty", "Difficulty§8: §e");
        language.put("commands.world.info.generator", "Generator§8: §e");
        language.put("commands.world.info.spawn", "Spawn§8: §e");
        language.put("commands.world.info.structures", "Structures§8: §e");
        language.put("commands.world.info.type", "Type§8: §e");
        language.put("commands.world.create","Open world creator for world §e%world%§8...");
        language.put("commands.world.creating","Creating world§8...");
        language.put("commands.world.created","The world has been §asuccessfully §7created§8!");
        language.put("commands.world.seed","Please enter your seed as a number in the chat§8...");
        language.put("commands.world.seedFailed","§cSeed submission time has expired§8. §7Recreate your world§8.");
        language.put("commands.world.teleport","You have been teleported to the spawn of world §e%world%§8.");
        language.put("commands.world.unload","The world §e%world%§8 §7was §asuccessfully§7 unloaded§8.");
        language.put("commands.world.load","The world §e%world%§8 §7was §asuccessfully§7 loaded§8.");
        language.put("commands.world.delete","The world §e%world%§8 §7was successfully §cdeleted§8.");
        language.put("commands.world.deleteOnExit","§cThe world §e%world%§8 §ccould not§c be deleted§8! §7Trying to delete world on §eexit§8/§eserver restart§8.");
        language.put("commands.money.get.self","You have §e%balance% Skycoins§8.");
        language.put("commands.money.get.other","§a%player%§7 has §e%balance% Skycoins§8.");
        language.put("commands.money.set.self","Your Balance is now set to §e%balance% Skycoins§8.");
        language.put("commands.money.set.other","You've set §a%player%§8'§7s balance to §e%balance% Skycoins§8.");
        language.put("commands.money.payed","You transferred §e%amount% Skycoins§7 to §a%player%§8.");
        language.put("commands.money.pay.self","§cYou cannot transfer money to yourself§8.");
        language.put("commands.money.pay.other", "§a%player%§7 has transferred you §e%amount% Skycoins§8.");
        language.put("commands.money.pay.insufficient", "§cYou do not have enough Skycoins to complete this transaction§8.");
        language.put("commands.vanish.activated", "You are now §evanished§8!");
        language.put("commands.vanish.deactivated", "You aren't §evanished §7anymore§8!");
        language.put("commands.vanish.activated.bypass", "§e%player% §7is now §avanished§8! §7However§8,§7 you can still see him§8,§7 because you got sufficient §epermission§8!");
        language.put("commands.vanish.deactivated.bypass", "§e%player% §7isn't §avanished §7anymore §8!");
        language.put("commands.vanish.bypass.joined", "Currently§8, §7The following players are §avanished§8: §a");
        language.put("commands.god.activated", "You are now §eInvulnerable§8!");
        language.put("commands.god.deactivated", "You aren't longer §eInvulnerable§8!");
        language.put("commands.god.activated.other", "§a%player% §7is now $eInvulnerable§8!");
        language.put("commands.god.deactivated.other", "§a%player% §7isn't longer $eInvulnerable§8!");
        language.put("commands.tell.message.from.other", "§bMessage from §a%player% §8» §f");
        language.put("commands.tell.message.to.other", "§bMessage to §a%player% §8» §f");
        language.put("commands.invsee.inv.opened", "Opened Inventory of §a%player%§8!");
        language.put("commands.enderchest.ec.opened", "Opened Enderchest of §a%player%§8!");

        language.put("interface.worldCreator.title","Create a world...");
        language.put("interface.worldCreator.generator","§bGenerator");
        language.put("interface.worldCreator.environment","§bEnvironment");
        language.put("interface.worldCreator.type","§bWorld type");
        language.put("interface.worldCreator.biomeProvider","§bBiome provider");
        language.put("interface.worldCreator.structures","§bGenerate structures");
        language.put("interface.worldCreator.seed","§bWorld seed");
        language.put("interface.worldCreator.hardcore","§bHardcore");
        language.put("interface.worldCreator.keepSpawnLoaded","§bKeep spawn loaded");
        language.put("interface.worldCreator.create","§bCreate");
        language.put("interface.invsee.title","Armor of %player%");
    }

    public void initLanguage(YamlConfiguration language) {
        initDefaults();
        for(String key : language.getKeys(false)) {
            if(language.contains(key)) {
                UserStrings.language.remove(key);
                UserStrings.language.put(key, language.getString(key));
            }
        }
    }

    public String get(String key) {
        return language.getOrDefault(key, key);
    }

    public String get(KEY key) {
        return get(key.toString().replace("_","."));
    }

    public enum KEY {
        errors_prefix,
        errors_noPermission,
        errors_notANumber,
        errors_noConsoleCommand,
        errors_syntax,
        errors_playerNotFound,
        errors_worldNotFound,
        errors_worldAlreadyLoaded,

        commands_gamemode_switched,
        commands_gamemode_switched_other,
        commands_speed_switched_walking_other,
        commands_speed_switched_flying_other,
        commands_speed_switched_walking,
        commands_speed_switched_flying,
        commands_heal_healed,
        commands_heal_healed_other,
        commands_kill_killed,
        commands_kill_killed_other,
        commands_clearchat_cleared,
        commands_clearchat_cleared_permission,
        commands_ping_pinged,
        commands_ping_pinged_other,
        commands_broadcast_information,
        commands_broadcast_warning,
        commands_broadcast_error,
        commands_fly_setflight,
        commands_fly_setflight_other,
        commands_world_info_name,
        commands_world_info_difficulty,
        commands_world_info_generator,
        commands_world_info_spawn,
        commands_world_info_structures,
        commands_world_info_type,
        commands_world_teleport,
        commands_world_unload,
        commands_world_load,
        commands_world_seed,
        commands_world_seedFailed,
        commands_world_delete,
        commands_world_deleteOnExit,
        commands_world_create,
        commands_world_creating,
        commands_world_created,
        commands_money_get_self,
        commands_money_get_other,
        commands_money_set_self,
        commands_money_set_other,
        commands_money_payed,
        commands_money_pay_self,
        commands_money_pay_other,
        commands_money_pay_insufficient,
        commands_vanish_activated,
        commands_vanish_deactivated,
        commands_vanish_activated_bypass,
        commands_vanish_deactivated_bypass,
        commands_vanish_bypass_joined,
        commands_god_activated,
        commands_god_deactivated,
        commands_god_activated_other,
        commands_god_deactivated_other,
        commands_tell_message_from_other,
        commands_tell_message_to_other,
        commands_invsee_inv_opened,
        commands_enderchest_ec_opened,

        interface_worldCreator_title,
        interface_worldCreator_generator,
        interface_worldCreator_environment,
        interface_worldCreator_type,
        interface_worldCreator_biomeProvider,
        interface_worldCreator_structures,
        interface_worldCreator_seed,
        interface_worldCreator_hardcore,
        interface_worldCreator_keepSpawnLoaded,
        interface_worldCreator_create,
        interface_invsee_title,
    }
}