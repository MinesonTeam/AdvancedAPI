package kz.hxncus.mc.minesonapi.command;

import kz.hxncus.mc.minesonapi.command.argument.Argument;
import kz.hxncus.mc.minesonapi.util.BukkitUtil;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public abstract class AbstractCommand implements ICommand {
    private final CommandMeta commandMeta;
    private final List<Argument> arguments = new ArrayList<>();
    private final List<ICommand> subcommands = new ArrayList<>();

    protected AbstractCommand(String commandName) {
        this.commandMeta = new CommandMeta(commandName);
    }

    public AbstractCommand withArguments(List<Argument> args) {
        this.arguments.addAll(args);
        return this;
    }

    public AbstractCommand withArguments(Argument... args) {
        this.arguments.addAll(Arrays.asList(args));
        return this;
    }

    public AbstractCommand withOptionalArguments(List<Argument> args) {
        for (Argument arg : args) {
            arg.setOptional(true);
            this.arguments.add(arg);
        }
        return this;
    }

    public AbstractCommand withOptionalArguments(Argument... args) {
        for(Argument arg : args) {
            arg.setOptional(true);
            this.arguments.add(arg);
        }
        return this;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }

    public void register() {
        BukkitUtil.getKnownCommands().putIfAbsent(this.commandMeta.getCommandName(), null);
    }
}