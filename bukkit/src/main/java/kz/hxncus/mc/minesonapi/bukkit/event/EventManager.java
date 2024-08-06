package kz.hxncus.mc.minesonapi.bukkit.event;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.bukkit.Server;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

@ToString
@EqualsAndHashCode
public class EventManager {
	private final Plugin plugin;
	private final PluginManager pluginManager;
	
	/**
	 * @param plugin plugin instance
	 */
	public EventManager(final Plugin plugin) {
		this.plugin = plugin;
		final Server server = plugin.getServer();
		this.pluginManager = server.getPluginManager();
		this.registerCustomEvents();
	}
	
	private void registerCustomEvents() {
		this.register(PlayerInteractEvent.class, event -> {
			final Action action = event.getAction();
			switch (action) {
				case LEFT_CLICK_BLOCK:
				case LEFT_CLICK_AIR:
					this.callEvent(new PlayerLeftClickEvent(event.getPlayer(), event.getAction(), event.getItem(),
					                                        event.getClickedBlock(), event.getBlockFace(), event.getHand(), event.getClickedPosition()
					));
					break;
				case RIGHT_CLICK_BLOCK:
				case RIGHT_CLICK_AIR:
					this.callEvent(new PlayerRightClickEvent(event.getPlayer(), event.getAction(), event.getItem(),
					                                         event.getClickedBlock(), event.getBlockFace(), event.getHand(), event.getClickedPosition()
					));
					break;
				default:
					this.callEvent(new PlayerPhysicalInteractEvent(event.getPlayer(), event.getAction(), event.getItem(),
					                                               event.getClickedBlock(), event.getBlockFace(), event.getHand(), event.getClickedPosition()
					));
			}
		});
		this.register(PlayerStatisticIncrementEvent.class, event -> {
			final Statistic statistic = event.getStatistic();
			final Player player = event.getPlayer();
			if (statistic == Statistic.JUMP) {
				this.callEvent(new PlayerJumpEvent(player, player.getLocation(), player.getLocation()
				                                                                       .clone().add(player.getVelocity())));
			} else if (statistic == Statistic.DAMAGE_BLOCKED_BY_SHIELD) {
				final EntityDamageEvent lastDamageCause = player.getLastDamageCause();
				if (lastDamageCause == null) {
					return;
				}
				this.callEvent(new PlayerDamageBlockByShieldEvent(player, lastDamageCause.getEntity(), event.getNewValue() - event.getPreviousValue()));
			}
		});
	}
	
	public <E extends Event> void register(@NonNull final Class<E> event, @NonNull final EventConsumer<E> consumer) {
		this.register(event, EventPriority.NORMAL, consumer);
	}
	
	/**
	 * @param event Event that needs to call
	 */
	public void callEvent(@NonNull final Event event) {
		this.pluginManager.callEvent(event);
	}
	
	public <E extends Event> void register(final @NonNull Class<? extends E> event, @NonNull final EventPriority priority, @NonNull final EventConsumer<E> consumer) {
		this.pluginManager.registerEvent(event, consumer, priority, (listener, events) -> {
			if (event.isInstance(events)) {
				((EventConsumer<E>) listener).accept(event.cast(events));
			}
		}, this.plugin);
	}
	
	public void unregisterAll() {
		HandlerList.unregisterAll(MinesonAPI.getInstance());
	}
	
	@FunctionalInterface
	public interface EventConsumer<E extends Event> extends Listener {
		default EventConsumer<E> append(final EventConsumer<E> other) {
			return event -> {
				this.accept(event);
				other.accept(event);
			};
		}
		
		void accept(E event);
		
		default EventConsumer<E> prepend(final EventConsumer<E> other) {
			return event -> {
				other.accept(event);
				this.accept(event);
			};
		}
	}
}
