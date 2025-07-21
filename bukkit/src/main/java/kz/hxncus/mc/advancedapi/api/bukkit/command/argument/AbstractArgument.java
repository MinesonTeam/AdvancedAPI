package kz.hxncus.mc.advancedapi.api.bukkit.command.argument;

import lombok.Getter;

import java.util.Collection;

@Getter
public abstract class AbstractArgument implements Argument {
    protected final String nodeName;
    protected Collection<String> suggestions;

    protected AbstractArgument(String nodeName) {
        this.nodeName = nodeName;
    }

    @Override
    public Argument setSuggestions(Collection<String> suggestions) {
        this.suggestions = suggestions;
        return this;
    }
}
