package kz.hxncus.mc.minesonapi.util.builder;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.function.Consumer;

public class EntityBuilder {
	private final Entity entity;
	
	public EntityBuilder(@NonNull final Location location, @NonNull final World world, @NonNull final EntityType type) {
		this.entity = world.spawnEntity(location, type);
	}
	
	public EntityBuilder fireworkMeta(final Consumer<FireworkMeta> consumer) {
		return this.meta(Firework.class, firework -> {
			final FireworkMeta meta = firework.getFireworkMeta();
			consumer.accept(meta);
			firework.setFireworkMeta(meta);
		});
	}
	
	public <T extends Entity> EntityBuilder meta(final Class<T> metaClass, final Consumer<T> metaConsumer) {
		return this.meta(meta -> {
			if (metaClass.isInstance(meta)) {
				metaConsumer.accept(metaClass.cast(meta));
			}
		});
	}
	
	public EntityBuilder meta(final Consumer<Entity> metaConsumer) {
		if (this.entity != null) {
			metaConsumer.accept(this.entity);
		}
		return this;
	}
	
	public EntityBuilder itemMeta(final Consumer<Item> consumer) {
		return this.meta(Item.class, consumer);
	}
}
