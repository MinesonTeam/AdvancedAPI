package kz.hxncus.mc.advancedapi.bukkit.command.argument;

import kz.hxncus.mc.advancedapi.api.bukkit.command.argument.AbstractArgument;

import java.util.UUID;

public class UUIDArgument extends AbstractArgument {
    public UUIDArgument(String nodeName) {
        super(nodeName);
    }

    @Override
    public UUID parse(String arg) {
        try {
            return UUID.fromString(arg);
        } catch (IllegalArgumentException ignored) {
            // ignored
        }
        return null;
    }

    @Override
    public Class<UUID> getType() {
        return UUID.class;
    }
}
