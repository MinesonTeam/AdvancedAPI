package kz.hxncus.mc.advancedapi.bukkit.command.argument;

import kz.hxncus.mc.advancedapi.api.bukkit.command.argument.AbstractArgument;

public class BooleanArgument extends AbstractArgument<Boolean> {
    public BooleanArgument(final String nodeName) {
        super(nodeName);
    }

    @Override
    public Boolean parse(String arg) {
        return this.parseBoolean(arg);
    }

    @Override
    public Class getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

    private boolean parseBoolean(String arg) {
        String lowerCaseArg = arg.toLowerCase();
        if (lowerCaseArg.equals("true") || lowerCaseArg.equals("1") || lowerCaseArg.equals("yes")) {
            return true;
        } else if (lowerCaseArg.equals("false") || lowerCaseArg.equals("0") || lowerCaseArg.equals("no")) {
            return false;
        }
        return false;
    }
}
