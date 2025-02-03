package com.zyneonstudios.nexus;

import com.zyneonstudios.nexus.skyblock.SkyBlock;
import org.bukkit.plugin.java.JavaPlugin;

public final class Preloader extends JavaPlugin {

    private static SkyBlock skyBlock;

    @Override
    public void onLoad() {
        skyBlock = new SkyBlock(this);
        skyBlock.load();
    }

    @Override
    public void onEnable() {
        skyBlock.enable();
    }

    @Override
    public void onDisable() {
        skyBlock.disable();
    }
}
