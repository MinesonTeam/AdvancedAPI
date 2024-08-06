package kz.hxncus.mc.minesonapi.nms;

import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.dedicated.DedicatedServer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_20_R3.CraftServer;
import org.bukkit.craftbukkit.v1_20_R3.command.VanillaCommandWrapper;
import org.bukkit.event.Listener;

/**
 * The type Nms 1 20 r 3.
 */
public class NMS_1_20_R3 implements Listener {
    /**
     * Gets minecraft server.
     *
     * @return the minecraft server
     */
    public DedicatedServer getMinecraftServer() {
        final Server var2 = Bukkit.getServer();
        if (var2 instanceof final CraftServer server) {
            return server.getServer();
        } else {
            return null;
        }
    }
    
    /**
     * Wrap-to-vanilla command wrapper command.
     *
     * @param node the node
     * @return the command
     */
    public Command wrapToVanillaCommandWrapper(final CommandNode<CommandSourceStack> node) {
        return new VanillaCommandWrapper(this.getMinecraftServer().vanillaCommandDispatcher, node);
    }
}
