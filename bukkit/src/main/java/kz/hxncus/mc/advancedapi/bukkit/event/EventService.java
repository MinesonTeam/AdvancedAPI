package kz.hxncus.mc.advancedapi.bukkit.event;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * Class Event manager.
 *
 * @author Hxncus
 * @since 1.0.
 */
@ToString
@EqualsAndHashCode
public class EventService {
	private final Plugin plugin;
	private final PluginManager pluginManager;
	
	/**
	 * Instantiates a new Event manager.
	 *
	 * @param plugin plugin instance
	 */
	public EventService(final Plugin plugin) {
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
		this.register(InventoryClickEvent.class, event -> {
		
		});
		this.register(BlockDispenseArmorEvent.class, event -> {
			final ArmorEquipEvent.ArmorType armorType = ArmorEquipEvent.ArmorType.matchType(event.getItem());
			if (armorType == null) {
				return;
			}
			final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(event.getTargetEntity(),
			                                                            ArmorEquipEvent.EquipMethod.DISPENSER, armorType,
			                                                            new ItemStack(Material.AIR), event.getItem());
			this.callEvent(armorEquipEvent);
			if (armorEquipEvent.isCancelled()) {
				event.setCancelled(true);
			}
		});
	}
	
	/**
	 * Register.
	 *
	 * @param <E>      the type parameter
	 * @param event    the event
	 * @param consumer the consumer
	 */
	public <E extends Event> void register(@NonNull final Class<E> event, @NonNull final EventConsumer<E> consumer) {
		this.register(event, EventPriority.NORMAL, consumer);
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
		}, this.plugin);
	}
	
	/**
	 * Unregister all.
	 */
	public void unregisterAll() {
		HandlerList.unregisterAll(AdvancedAPI.getInstance());
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
