package kz.hxncus.mc.advancedapi.api.bukkit.command.argument;

import java.util.Collection;
import java.util.Collections;

public interface Argument<T> {
    String getNodeName();
    Collection<String> getSuggestions();
    Argument<T> setSuggestions(Collection<String> suggestions);
    T parse(String arg);
    Class<T> getType();
    default Collection<String> getDefaultSuggestions() {
        return Collections.emptyList();
    }
}
