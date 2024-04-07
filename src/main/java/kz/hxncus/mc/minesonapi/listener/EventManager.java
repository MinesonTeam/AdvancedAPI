package kz.hxncus.mc.minesonapi.listener;

import kz.hxncus.mc.minesonapi.random.MSRandom;
import lombok.NonNull;
import org.bukkit.event.*;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class EventManager implements Listener {
    @NonNull
    private final Plugin plugin;
    private static EventManager instance;
    private EventManager(@NonNull Plugin plugin) {
        this.plugin = plugin;
        registerPluginDisabling();
    }

    public static EventManager getInstance(Plugin plugin) {
        if (instance == null) {
            instance = new EventManager(plugin);
        }
        return instance;
    }

    public void callEvent(@NonNull Event event) {
        this.plugin.getServer().getPluginManager().callEvent(event);
    }

    public <E extends Event> EventManager register(@NonNull Class<E> event, @NonNull Consumer<E> consumer) throws IllegalStateException, IllegalArgumentException {
        return register(event, EventPriority.NORMAL, consumer);
    }
    public <E extends Event> EventManager register(@NonNull Class<E> event, @NonNull EventPriority priority, @NonNull Consumer<E> consumer) {
        this.plugin.getServer().getPluginManager().registerEvent(event, this, priority, (listener, events) -> {
            if (event.isInstance(events)) {
                consumer.accept(event.cast(events));
            }
        }, this.plugin);
        return this;
    }

    public void registerPluginDisabling() {
        this.register(PluginDisablingEvent.class, event -> {
            if (event.getPlugin() == this.plugin) {
                HandlerList.unregisterAll(this.plugin);
            }
        });
    }

    public void unregisterAll() {
        HandlerList.unregisterAll(this);
    }
}
