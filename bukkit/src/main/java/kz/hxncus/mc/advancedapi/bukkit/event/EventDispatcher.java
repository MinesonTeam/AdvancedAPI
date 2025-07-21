package kz.hxncus.mc.advancedapi.bukkit.event;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * Class Event dispatcher.
 *
 * @author Hxncus
 * @since 1.0.
 */
@Getter
public class EventDispatcher {
	private final Plugin plugin;
	private final PluginManager pluginManager;

	/**
	 * Instantiates a new Event dispatcher.
	 *
	 * @param plugin plugin instance
	 */
	public EventDispatcher(final Plugin plugin) {
		this.plugin = plugin;
		this.pluginManager = Bukkit.getPluginManager();

		new CustomEvents(this).registerEvents();
	}

	/**
	 * Call event.
	 *
	 * @param event Event that needs to call
	 */
	public void callEvent(@NonNull final Event event) {
		this.pluginManager.callEvent(event);
	}

	/**
	 * Register event.
	 *
	 * @param <E>      the type parameter
	 * @param event    the event
	 * @param eventConsumer the eventConsumer
	 */
	public <E extends Event> void register(@NonNull final Class<E> event, @NonNull final EventConsumer<E> eventConsumer) {
		this.register(event, EventPriority.NORMAL, eventConsumer);
	}

	/**
	 * Register event.
	 *
	 * @param <E>      the type parameter
	 * @param event    the event
	 * @param eventConsumer the eventConsumer
	 */
	public <E extends Event> void register(@NonNull final Class<E> event, @NonNull final EventConsumer<E> eventConsumer, boolean ignoreCancelled) {
		this.register(event, EventPriority.NORMAL, eventConsumer, ignoreCancelled);
	}

	public <E extends Event> void register(final @NonNull Class<E> event, @NonNull final EventPriority priority, @NonNull final EventConsumer<E> consumer) {
		this.register(event, priority, consumer, false);
	}

	/**
	 * Register.
	 *
	 * @param <E>      the type parameter
	 * @param event    the event
	 * @param priority the priority
	 * @param consumer the consumer
	 */
	public <E extends Event> void register(final @NonNull Class<E> event, @NonNull final EventPriority priority, @NonNull final EventConsumer<E> consumer, boolean ignoreCancelled) {
		this.pluginManager.registerEvent(event, consumer, priority, (listener, executorEvent) -> {
			if (event.isInstance(executorEvent)) {
				((EventConsumer<E>) listener).accept(event.cast(executorEvent));
			}
		}, this.getPlugin(), ignoreCancelled);
	}

	public void unregister() {
		HandlerList.unregisterAll(this.getPlugin());
	}

	/**
	 * Unregister all.
	 */
	public static void unregisterAll() {
		HandlerList.unregisterAll();
	}

	public static void unregisterAll(Plugin plugin) {
		HandlerList.unregisterAll(plugin);
	}

	/**
	 * The interface Event consumer.
	 *
	 * @param <E> the type parameter
	 * @author Hxncus
	 * @since 1.0.
	 */
	@FunctionalInterface
	public interface EventConsumer<E extends Event> extends Listener {
		/**
		 * Append event consumer.
		 *
		 * @param other the other
		 * @return the event consumer
		 */
		default EventConsumer<E> append(final EventConsumer<E> other) {
			return event -> {
				this.accept(event);
				other.accept(event);
			};
		}

		/**
		 * Accept.
		 *
		 * @param event the event
		 */
		void accept(E event);

		/**
		 * Prepend event consumer.
		 *
		 * @param other the other
		 * @return the event consumer
		 */
		default EventConsumer<E> prepend(final EventConsumer<E> other) {
			return event -> {
				other.accept(event);
				this.accept(event);
			};
		}
	}
}
