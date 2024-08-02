package kz.hxncus.mc.minesonapi.command;

import kz.hxncus.mc.minesonapi.exception.InvalidCommandNameException;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
public class CommandMeta {
    private final String commandName;
    private String permission;
    private String[] aliases;
    private Predicate<CommandSender> requirements;
    private Optional<String> shortDescription;
    private Optional<String> fullDescription;
    private Optional<String[]> usageDescription;
    private Optional<String> helpTopic;

    public CommandMeta(String commandName) {
        this.permission = "";
        this.aliases = new String[0];
        this.requirements = commandSender -> true;
        this.shortDescription = Optional.empty();
        this.fullDescription = Optional.empty();
        this.usageDescription = Optional.empty();
        this.helpTopic = Optional.empty();
        if (commandName != null && !commandName.isEmpty() && !commandName.contains(" ")) {
            this.commandName = commandName;
        } else {
            throw new InvalidCommandNameException(commandName);
        }
    }

    public CommandMeta(CommandMeta original) {
        this(original.commandName);
        this.permission = original.permission;
        this.aliases = Arrays.copyOf(original.aliases, original.aliases.length);
        this.requirements = original.requirements;
        this.shortDescription = original.shortDescription;
        this.fullDescription = original.fullDescription;
        this.usageDescription = original.usageDescription;
        this.helpTopic = original.helpTopic;
    }
}
