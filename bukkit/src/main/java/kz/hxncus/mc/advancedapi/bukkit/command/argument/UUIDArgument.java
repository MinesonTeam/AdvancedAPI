package kz.hxncus.mc.advancedapi.bukkit.command.argument;

import java.util.UUID;

import kz.hxncus.mc.advancedapi.api.bukkit.command.argument.AbstractArgument;

public class UUIDArgument extends AbstractArgument<UUID> {
    public UUIDArgument(String name) {
        super(name);
    }

    @Override
    public UUID parse(String arg) {
        return UUID.fromString(arg);
    }

    @Override
    public Class<UUID> getType() {
        return UUID.class;
    }
}
