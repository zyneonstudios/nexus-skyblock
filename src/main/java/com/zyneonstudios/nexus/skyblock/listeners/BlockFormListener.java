package com.zyneonstudios.nexus.skyblock.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.concurrent.ThreadLocalRandom;

public class BlockFormListener implements Listener {

    @EventHandler
    public void onFromTo(BlockFromToEvent event) {
        Material type = event.getBlock().getType();
        if (type == Material.WATER || type == Material.LAVA) {
            Block b = event.getToBlock();
            if (b.getType() == Material.AIR) {
                if (generatesCobble(type, b)) {
                    if (b.getLocation().getX() == 0 && b.getLocation().getZ() == 0) {
                        if (b.getLocation().getY() == 99 || b.getLocation().getY() == 100 || b.getLocation().getY() == 101) {
                            event.setCancelled(true);
                            b.setType(Material.AIR);
                            return;
                        }
                    }
                    event.setCancelled(true);
                    b.setType(generatorBlock());
                }
            }
        }
    }

    private final BlockFace[] faces = new BlockFace[]{
            BlockFace.SELF,
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST
    };

    public boolean generatesCobble(Material type, Block b){
        Material mirrorID1 = type == Material.WATER ? Material.LAVA : Material.WATER;
        Material mirrorID2 = type == Material.WATER ? Material.LAVA : Material.WATER;
        for (BlockFace face : faces){
            Block r = b.getRelative(face, 1);
            if (r.getType() == mirrorID1 || r.getType() == mirrorID2){
                return true;
            }
        }
        return false;
    }

    public static Material generatorBlock() {
        Material result;
        int i = ThreadLocalRandom.current().nextInt(0,100);
        if(i>98) {
            int r = ThreadLocalRandom.current().nextInt(0,100);
            if(r<2) {
                result = Material.ANCIENT_DEBRIS;
            } else {
                result = Material.EMERALD_ORE;
            }
        } else if(i>94) {
            int i5 = ThreadLocalRandom.current().nextInt(0,10);
            if(i5 == 3) {
                result = Material.DIAMOND_ORE;
            } else {
                result = Material.STONE;
            }
        } else if(i>92) {
            int r = ThreadLocalRandom.current().nextInt(0,100);
            if(r <= 50) {
                result = Material.REDSTONE_ORE;
            } else {
                result = Material.LAPIS_ORE;
            }
        } else if(i>80) {
            int r = ThreadLocalRandom.current().nextInt(0,100);
            if(r < 80) {
                int rr = ThreadLocalRandom.current().nextInt(0,4);
                if(rr == 3) {
                    result = Material.STONE;
                } else {
                    result = Material.IRON_ORE;
                }
            } else {
                result = Material.GOLD_ORE;
            }
        } else if(i>60) {
            int i5 = ThreadLocalRandom.current().nextInt(0,10);
            if(i5 <= 4 ) {
                result = Material.DEEPSLATE;
            } else if(i5 <= 7) {
                result = Material.COPPER_ORE;
            } else {
                result = Material.COAL_ORE;
            }
        } else {
            int r = ThreadLocalRandom.current().nextInt(0,100);
            if(r > 20) {
                result = Material.STONE;
            } else {
                result = Material.DEEPSLATE;
            }
        }
        return result;
    }
}