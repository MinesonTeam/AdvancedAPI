package kz.hxncus.mc.advancedapi.bukkit.event;

import kz.hxncus.mc.advancedapi.api.service.AbstractService;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * Class Event manager.
 *
 * @author Hxncus
 * @since 1.0.
 */
@EqualsAndHashCode(callSuper = false)
public class EventService extends AbstractService {
	private final PluginManager pluginManager;
	
	/**
	 * Instantiates a new Event manager.
	 *
	 * @param plugin plugin instance
	 */
	public EventService(final Plugin plugin) {
		super(plugin);
		final Server server = plugin.getServer();
		this.pluginManager = server.getPluginManager();
	}
	
	@Override
	public void register() {
		new CustomEvents(this).registerEvents();
	}
	
	@Override
	public void unregister() {
		this.unregisterAll();
	}
	
	/**
	 * Register.
	 *
	 * @param <E>      the type parameter
	 * @param eventClass    the eventClass
	 * @param eventConsumer the eventConsumer
	 */
	public <E extends Event> void register(@NonNull final Class<E> eventClass, @NonNull final EventConsumer<E> eventConsumer) {
		this.register(eventClass, EventPriority.NORMAL, eventConsumer);
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
	 * Register.
	 *
	 * @param <E>      the type parameter
	 * @param event    the event
	 * @param priority the priority
	 * @param consumer the consumer
	 */
	public <E extends Event> void register(final @NonNull Class<? extends E> event, @NonNull final EventPriority priority, @NonNull final Listener consumer) {
		this.pluginManager.registerEvent(event, consumer, priority, (listener, events) -> {
			if (event.isInstance(events)) {
				((EventConsumer<E>) listener).accept(event.cast(events));
			}
		}, this.getPlugin());
	}
	
	/**
	 * Unregister all.
	 */
	private void unregisterAll() {
		HandlerList.unregisterAll(this.getPlugin());
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
		default EventConsumer<E> append(final EventConsumer<? super E> other) {
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
		default EventConsumer<E> prepend(final EventConsumer<? super E> other) {
			return event -> {
				other.accept(event);
				this.accept(event);
			};
		}
	}
}
