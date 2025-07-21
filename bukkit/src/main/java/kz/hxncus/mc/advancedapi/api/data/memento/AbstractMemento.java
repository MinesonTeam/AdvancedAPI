package kz.hxncus.mc.advancedapi.api.data.memento;

import lombok.Getter;

@Getter
public abstract class AbstractMemento<T> implements Memento<T> {
    private final T target;

    protected AbstractMemento(T target) {
        this.target = target;
    }
}
