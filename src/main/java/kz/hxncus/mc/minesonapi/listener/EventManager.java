package kz.hxncus.mc.minesonapi.listener;

import lombok.NonNull;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class EventManager implements Listener {
    private static EventManager instance;
    private EventManager() {
    }

    public static EventManager getInstance() {
        if (EventManager.instance == null) {
            EventManager.instance = new EventManager();
        }
        return EventManager.instance;
    }

    public void callEvent(@NonNull Plugin plugin, @NonNull Event event) {
        plugin.getServer().getPluginManager().callEvent(event);
    }

    public <E extends Event> EventManager register(Plugin plugin, @NonNull Class<E> event, @NonNull Consumer<E> consumer) throws IllegalStateException, IllegalArgumentException {
        return register(plugin, event, EventPriority.NORMAL, consumer);
    }

    public <E extends Event> EventManager register(Plugin plugin, @NonNull Class<E> event, @NonNull EventPriority priority, @NonNull Consumer<E> consumer) {
        plugin.getServer().getPluginManager().registerEvent(event, this, priority, (listener, events) -> {
            if (event.isInstance(events)) {
                consumer.accept(event.cast(events));
            }
        }, plugin);
        return this;
    }

    public void unregisterAll() {
        HandlerList.unregisterAll(this);
    }
}
