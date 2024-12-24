package kz.hxncus.mc.advancedapi.bukkit.command.argument;

import java.util.Collection;
import java.util.List;

import kz.hxncus.mc.advancedapi.api.bukkit.command.argument.AbstractArgument;

public class BooleanArgument extends AbstractArgument<Boolean> {
    private static final Collection<String> TRUE_CASES = List.of("true", "yes", "on", "enable", "accept", "confirm", "agree", "allow", "1");
    private static final Collection<String> FALSE_CASES = List.of("false", "no", "off", "disable", "reject", "deny", "disagree", "disallow", "0");
    private static final Collection<String> DEFAULT_SUGGESTIONS = List.of("true", "false");

    public BooleanArgument(final String nodeName) {
        super(nodeName);
    }

    @Override
    public Boolean parse(String arg) {
        String lowerCaseArg = arg.toLowerCase();
        if (TRUE_CASES.contains(lowerCaseArg)) {
            return true;
        } else if (FALSE_CASES.contains(lowerCaseArg)) {
            return false;
        }
        return null;
    }

    @Override
    public Collection<String> getDefaultSuggestions() {
        return BooleanArgument.DEFAULT_SUGGESTIONS;
    }

    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }
}
