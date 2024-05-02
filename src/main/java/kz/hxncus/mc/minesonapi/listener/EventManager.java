package kz.hxncus.mc.minesonapi.listener;

import lombok.NonNull;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class EventManager {
    private final Plugin plugin;

    public EventManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void callEvent(@NonNull Event event) {
        this.plugin.getServer().getPluginManager().callEvent(event);
    }

    public <E extends Event> void register(@NonNull Class<E> event, @NonNull EventConsumer<E> consumer) {
        register(event, EventPriority.NORMAL, consumer);
    }

    public <E extends Event> void register(@NonNull Class<E> event, @NonNull EventPriority priority, @NonNull EventConsumer<E> consumer) {
        this.plugin.getServer().getPluginManager().registerEvent(event, consumer, priority, (listener, events) -> {
            if (event.isInstance(events)) {
                ((EventConsumer<E>) listener).accept(event.cast(events));
            }
        }, this.plugin);
    }

    public void unregisterAll() {
        HandlerList.unregisterAll(this.plugin);
    }
}
