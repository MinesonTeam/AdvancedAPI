package kz.hxncus.mc.minesonapi.util;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.function.Consumer;

public class EntityBuilder {
    private Entity entity;
    public EntityBuilder(@NonNull Location location, @NonNull EntityType type) {
        this.entity = location.getWorld().spawnEntity(location, type);
    }
    public EntityBuilder edit(Consumer<Entity> consumer) {
        consumer.accept(this.entity);
        return this;
    }
    public EntityBuilder fireworkMeta(Consumer<FireworkMeta> consumer) {
        return edit(e -> {
            if (e instanceof Firework firework) {
                FireworkMeta meta = firework.getFireworkMeta();
                consumer.accept(meta);
                firework.setFireworkMeta(meta);
            }
        });
    }
    public EntityBuilder itemMeta(Consumer<Item> consumer) {
        return edit(e -> {
            if (e instanceof Item item) {
                consumer.accept(item);
            }
        });
    }
}
