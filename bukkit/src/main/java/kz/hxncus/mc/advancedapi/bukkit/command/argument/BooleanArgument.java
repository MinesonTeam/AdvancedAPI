package kz.hxncus.mc.advancedapi.bukkit.command.argument;

import kz.hxncus.mc.advancedapi.api.bukkit.command.argument.AbstractArgument;

public class BooleanArgument extends AbstractArgument<Boolean> {
    public BooleanArgument(final String nodeName) {
        super(nodeName);
    }

    @Override
    public Boolean parse(String arg) {
        String lowerCaseArg = arg.toLowerCase();
        if (lowerCaseArg.equals("true") || lowerCaseArg.equals("1") || lowerCaseArg.equals("yes")) {
            return true;
        } else if (lowerCaseArg.equals("false") || lowerCaseArg.equals("0") || lowerCaseArg.equals("no")) {
            return false;
        }
        return null;
    }

    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }
}
