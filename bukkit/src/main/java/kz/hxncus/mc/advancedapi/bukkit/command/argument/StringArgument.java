package kz.hxncus.mc.advancedapi.bukkit.command.argument;

import kz.hxncus.mc.advancedapi.api.bukkit.command.argument.AbstractArgument;

public class StringArgument extends AbstractArgument<String> {
    public StringArgument(final String nodeName) {
        super(nodeName);
    }

    @Override
    public String parse(String arg) {
        return arg;
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
}
