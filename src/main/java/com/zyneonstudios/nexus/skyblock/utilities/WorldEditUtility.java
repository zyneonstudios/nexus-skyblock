package com.zyneonstudios.nexus.skyblock.utilities;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SuppressWarnings("unused")
public class WorldEditUtility {

    public static boolean pasteSchematic(File file, double rotationX, double rotationY, double rotationZ, Location location) {
        WorldEdit worldEdit = WorldEdit.getInstance();

        ClipboardFormat format = ClipboardFormats.findByFile(file);
        Clipboard clipboard;

        if(format == null) return false;

        BlockVector3 vector = BlockVector3.at(location.getX(), location.getY(), location.getZ());

        try {
            ClipboardReader reader = format.getReader(new FileInputStream(file));

            if(location.getWorld() == null) return false;

            World world = BukkitAdapter.adapt(location.getWorld());
            EditSession session = worldEdit.newEditSessionBuilder().world(world).build();
            clipboard = reader.read();

            ClipboardHolder holder = new ClipboardHolder(clipboard);
            holder.setTransform(new AffineTransform().rotateY(rotationY).rotateX(rotationX).rotateZ(rotationZ));

            Operation operation = holder
                    .createPaste(session)
                    .to(vector)
                    .ignoreAirBlocks(true)
                    .build();

            Operations.complete(operation);
            session.close();

            return true;
        } catch (IOException | WorldEditException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean pasteSchematic(File file, Location location) {
        WorldEdit worldEdit = WorldEdit.getInstance();

        ClipboardFormat format = ClipboardFormats.findByFile(file);
        Clipboard clipboard;

        if(format == null) return false;

        BlockVector3 vector = BlockVector3.at(location.getX(), location.getY(), location.getZ());

        try {
            ClipboardReader reader = format.getReader(new FileInputStream(file));

            if(location.getWorld() == null) return false;

            World world = BukkitAdapter.adapt(location.getWorld());
            EditSession session = worldEdit.newEditSessionBuilder().world(world).build();
            clipboard = reader.read();

            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(session)
                    .to(vector)
                    .ignoreAirBlocks(true)
                    .build();

            Operations.complete(operation);
            session.close();

            return true;
        } catch (IOException | WorldEditException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean pasteSchematic(Clipboard clipboard, Location location) {
        WorldEdit worldEdit = WorldEdit.getInstance();

        BlockVector3 vector = BlockVector3.at(location.getX(), location.getY(), location.getZ());

        try {

            if(location.getWorld() == null) return false;

            World world = BukkitAdapter.adapt(location.getWorld());
            EditSession session = worldEdit.newEditSessionBuilder().world(world).build();

            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(session)
                    .to(vector)
                    .ignoreAirBlocks(true)
                    .build();

            Operations.complete(operation);
            session.close();

            return true;
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean pasteSchematic(Clipboard clipboard, double rotationX, double rotationY, double rotationZ, Location location) {
        WorldEdit worldEdit = WorldEdit.getInstance();

        BlockVector3 vector = BlockVector3.at(location.getX(), location.getY(), location.getZ());

        try {
            if (location.getWorld() == null) return false;

            World world = BukkitAdapter.adapt(location.getWorld());
            EditSession session = worldEdit.newEditSessionBuilder().world(world).build();

            ClipboardHolder holder = new ClipboardHolder(clipboard);
            holder.setTransform(new AffineTransform().rotateX(rotationX).rotateY(rotationY).rotateZ(rotationZ)); // Corrected

            Operation operation = holder
                    .createPaste(session)
                    .to(vector)
                    .ignoreAirBlocks(true)
                    .build();

            Operations.complete(operation);
            session.close();

            return true;
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }
    }

}