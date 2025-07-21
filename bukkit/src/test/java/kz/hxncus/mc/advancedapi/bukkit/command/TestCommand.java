package kz.hxncus.mc.advancedapi.bukkit.command;

import lombok.NonNull;

import java.util.List;

public class TestCommand extends AdvancedCommand {
    public TestCommand(String name) {
        super(name);
    }

    public TestCommand(String name, String permission) {
        super(name, permission);
    }

    public TestCommand(@NonNull String name, @NonNull String description, @NonNull String usageMessage, @NonNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}
