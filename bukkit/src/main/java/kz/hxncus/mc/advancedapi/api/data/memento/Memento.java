package kz.hxncus.mc.advancedapi.api.data.memento;

public interface Memento<T> {
    T getTarget();
    void apply();
}
