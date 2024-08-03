package kz.hxncus.mc.minesonapi.bukkit.event;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
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

@EqualsAndHashCode
public class EventManager {
    private final Plugin plugin;
    private final PluginManager pluginManager;

    public EventManager(Plugin plugin) {
        this.plugin = plugin;
        this.pluginManager = plugin.getServer().getPluginManager();
        registerCustomEvents();
    }

    public void callEvent(@NonNull Event event) {
        this.pluginManager.callEvent(event);
    }

    public <E extends Event> void register(@NonNull Class<E> event, @NonNull EventConsumer<E> consumer) {
        register(event, EventPriority.NORMAL, consumer);
    }

    public <E extends Event> void register(@NonNull Class<E> event, @NonNull EventPriority priority, @NonNull EventConsumer<E> consumer) {
        this.pluginManager.registerEvent(event, consumer, priority, (listener, events) -> {
            if (event.isInstance(events)) {
                ((EventConsumer<E>) listener).accept(event.cast(events));
            }
        }, this.plugin);
    }

    public static void unregisterAll() {
        HandlerList.unregisterAll(MinesonAPI.get());
    }

    private void registerCustomEvents() {
        register(PlayerInteractEvent.class, event -> {
            Action action = event.getAction();
            switch (action) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> callEvent(new PlayerLeftClickEvent(event.getPlayer(), event.getAction(), event.getItem(),
                    event.getClickedBlock(), event.getBlockFace(), event.getHand(), event.getClickedPosition()));
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> callEvent(new PlayerRightClickEvent(event.getPlayer(), event.getAction(), event.getItem(),
                    event.getClickedBlock(), event.getBlockFace(), event.getHand(), event.getClickedPosition()));
                default -> callEvent(new PlayerPhysicalInteractEvent(event.getPlayer(), event.getAction(), event.getItem(),
                    event.getClickedBlock(), event.getBlockFace(), event.getHand(), event.getClickedPosition()));
            }
        });
        register(PlayerStatisticIncrementEvent.class, event -> {
            Statistic statistic = event.getStatistic();
            Player player = event.getPlayer();
            if (statistic == Statistic.JUMP) {
                callEvent(new PlayerJumpEvent(player, player.getLocation(), player.getLocation().clone().add(player.getVelocity())));
            } else if (statistic == Statistic.DAMAGE_BLOCKED_BY_SHIELD) {
                EntityDamageEvent lastDamageCause = player.getLastDamageCause();
                if (lastDamageCause == null) {
                    return;
                }
                callEvent(new PlayerDamageBlockByShieldEvent(player, lastDamageCause.getEntity(), event.getNewValue() - event.getPreviousValue()));
            }
        });
    }

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
}
