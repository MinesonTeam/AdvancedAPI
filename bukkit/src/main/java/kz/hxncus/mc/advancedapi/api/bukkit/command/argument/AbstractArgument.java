package kz.hxncus.mc.advancedapi.api.bukkit.command.argument;

import java.util.Collection;

import lombok.Getter;

@Getter
public abstract class AbstractArgument<T> implements Argument<T> {
    protected final String name;
    protected Collection<T> suggestions;

    protected AbstractArgument(String name) {
        this.name = name;
    }

    @Override
    public Argument<T> setSuggestions(Collection<T> suggestions) {
        this.suggestions = suggestions;
        return this;
    }
}
