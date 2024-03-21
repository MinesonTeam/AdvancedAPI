package kz.hxncus.mc.minesonapi.listener;

import lombok.NonNull;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class EventManager implements Listener {
    @NonNull
    private final Plugin plugin;
    public EventManager(@NonNull Plugin plugin) {
        this.plugin = plugin;
    }
    public <E extends Event> void register(@NonNull Class<E> event, @NonNull Consumer<E> consumer) throws IllegalStateException, IllegalArgumentException {
        register(event, EventPriority.NORMAL, consumer);
    }
    public <E extends Event> void register(@NonNull Class<E> event, @NonNull EventPriority priority, @NonNull Consumer<E> consumer) {
        plugin.getServer().getPluginManager().registerEvent(event, this, priority, (l, e) -> {
            if (event.isInstance(e)) {
                consumer.accept(event.cast(e));
            }
        }, plugin);
    }
}
