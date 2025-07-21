package kz.hxncus.mc.advancedapi.api.bukkit.command.argument;

import java.util.Collection;
import java.util.Collections;

public interface Argument {
    String getNodeName();
    Collection<String> getSuggestions();
    Argument setSuggestions(Collection<String> suggestions);
    Object parse(String arg);
    Class<?> getType();
    default Collection<String> getDefaultSuggestions() {
        return Collections.emptyList();
    }
}
