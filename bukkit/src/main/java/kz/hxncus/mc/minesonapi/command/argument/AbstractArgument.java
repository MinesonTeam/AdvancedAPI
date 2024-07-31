package kz.hxncus.mc.minesonapi.command.argument;

import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractArgument implements Argument {
    private final String nodeName;
    private final Set<String> suggestions = new HashSet<>();
    @Setter
    private boolean optional;

    protected AbstractArgument(String nodeName) {
        this.nodeName = nodeName;
    }
}