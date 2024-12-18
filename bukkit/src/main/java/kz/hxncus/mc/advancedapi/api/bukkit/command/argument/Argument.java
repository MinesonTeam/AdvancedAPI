package kz.hxncus.mc.advancedapi.api.bukkit.command.argument;

import java.util.Collection;
import java.util.Collections;

public interface Argument<T> {
    String getName();
    Collection<T> getSuggestions();
    Argument<T> setSuggestions(Collection<T> suggestions);
    T parse(Object[] args);
    Class<T> getType();
    default Collection<T> getDefaultSuggestions() {
        return Collections.emptyList();
    }
}
