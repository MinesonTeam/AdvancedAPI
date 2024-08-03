package kz.hxncus.mc.minesonapi.nms;

import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.dedicated.DedicatedServer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_20_R3.CraftServer;
import org.bukkit.craftbukkit.v1_20_R3.command.VanillaCommandWrapper;

public class NMS_1_20_R3 {
    public DedicatedServer getMinecraftServer() {
        Server var2 = Bukkit.getServer();
        if (var2 instanceof CraftServer server) {
            return server.getServer();
        } else {
            return null;
        }
    }

    public Command wrapToVanillaCommandWrapper(CommandNode<CommandSourceStack> node) {
        return new VanillaCommandWrapper(getMinecraftServer().vanillaCommandDispatcher, node);
    }
}
