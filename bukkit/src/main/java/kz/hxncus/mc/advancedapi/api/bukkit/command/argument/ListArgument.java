package kz.hxncus.mc.advancedapi.api.bukkit.command.argument;

public abstract class ListArgument<T> extends AbstractArgument<T> {
    protected ListArgument(final String name) {
        super(name);
    }
}
