package kz.hxncus.mc.advancedapi.bukkit.command.argument;

import com.google.common.collect.Lists;
import kz.hxncus.mc.advancedapi.api.bukkit.command.argument.AbstractArgument;

import java.util.Collection;

public class BooleanArgument extends AbstractArgument {
    private static final Collection<String> TRUE_CASES = Lists.newArrayList("true", "yes", "on", "enable", "accept", "confirm", "agree", "allow", "1");
    private static final Collection<String> FALSE_CASES = Lists.newArrayList("false", "no", "off", "disable", "reject", "deny", "disagree", "disallow", "0");
    private static final Collection<String> DEFAULT_SUGGESTIONS = Lists.newArrayList("true", "false");

    public BooleanArgument(final String nodeName) {
        super(nodeName);
    }

    @Override
    public Object parse(String arg) {
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
