package kz.hxncus.mc.minesonapi.util.builder;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.function.Consumer;

public class EntityBuilder {
    private final Entity entity;

    public EntityBuilder(@NonNull Location location, @NonNull EntityType type) {
        this.entity = location.getWorld().spawnEntity(location, type);
    }

    public EntityBuilder meta(Consumer<Entity> metaConsumer) {
        if (entity != null) {
            metaConsumer.accept(entity);
        }
        return this;
    }

    public <T extends Entity> EntityBuilder meta(Class<T> metaClass, Consumer<T> metaConsumer) {
        return meta(meta -> {
            if (metaClass.isInstance(meta)) {
                metaConsumer.accept(metaClass.cast(meta));
            }
        });
    }

    public EntityBuilder fireworkMeta(Consumer<FireworkMeta> consumer) {
        return meta(Firework.class, firework -> {
            FireworkMeta meta = firework.getFireworkMeta();
            consumer.accept(meta);
            firework.setFireworkMeta(meta);
        });
    }

    public EntityBuilder itemMeta(Consumer<Item> consumer) {
        return meta(Item.class, consumer);
    }
}
