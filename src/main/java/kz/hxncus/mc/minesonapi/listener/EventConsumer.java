package kz.hxncus.mc.minesonapi.listener;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

@FunctionalInterface
public interface EventConsumer<E extends Event> extends Listener {
    void accept(E event);

    default EventConsumer<E> append(EventConsumer<E> other) {
        return event -> {
            this.accept(event);
            other.accept(event);
        };
    }

    default EventConsumer<E> prepend(EventConsumer<E> other) {
        return event -> {
            other.accept(event);
            this.accept(event);
        };
    }
}
