package kz.hxncus.mc.advancedapi.utility.builder;

import lombok.NonNull;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

import com.google.common.base.Optional;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Класс для построения сущностей
 */
public class EntityBuilder {
	@NonNull private final Entity entity;
	
	/**
	 * Конструктор для создания сущности
	 * @param entity сущность для построения
	 */
	public EntityBuilder(@NonNull final Entity entity) {
		this.entity = entity;
	}
	
	public EntityBuilder(@NonNull final Location location, @NonNull final World world, @NonNull final EntityType type) {
		this(world.spawnEntity(location, type));
	}
	
	/**
	 * Конструктор для создания сущности
	 * @param location локация для создания сущности
	 * @param world мир для создания сущности
	 * @param type тип сущности
	 */
	public EntityBuilder(@NonNull final Location location, @NonNull final EntityType type) {
		this(location, location.getWorld(), type);
	}

	/**
	 * Конструктор для создания сущности
	 * @param vec3 вектор для создания сущности
	 * @param world мир для создания сущности
	 * @param type тип сущности
	 */
	public EntityBuilder(@NonNull final Vector vector, @NonNull final World world, @NonNull final EntityType type) {
		this(vector.toLocation(world), type);
	}

	/**
	 * Конструктор для создания сущности
	 * @param vec3 вектор для создания сущности
	 * @param worldName имя мира для создания сущности
	 * @param type тип сущности
	 */
	public EntityBuilder(@NonNull final Vector vector, @NonNull final String worldName, @NonNull final EntityType type) {
		this(vector, Bukkit.getWorld(worldName), type);
	}

	
	/**
	 * Устанавливает имя сущности
	 * @param name имя сущности
	 * @return this builder
	 */
	public EntityBuilder setCustomName(@NonNull String name) {
		this.entity.setCustomName(name);
		return this;
	}
	
	/**
	 * Устанавливает видимость имени сущности
	 * @param visible видимость имени
	 * @return this builder
	 */
	public EntityBuilder setCustomNameVisible(boolean visible) {
		this.entity.setCustomNameVisible(visible);
		return this;
	}
	
	/**
	 * Устанавливает свечение сущности
	 * @param glow включить/выключить свечение
	 * @return this builder
	 */
	public EntityBuilder glow(boolean glow) {
		this.entity.setGlowing(glow);
		return this;
	}
	
	/**
	 * Устанавливает неуязвимость сущности
	 * @param invulnerable включить/выключить неуязвимость
	 * @return this builder
	 */
	public EntityBuilder invulnerable(boolean invulnerable) {
		this.entity.setInvulnerable(invulnerable);
		return this;
	}
	
	/**
	 * Устанавливает гравитацию сущности
	 * @param gravity включить/выключить гравитацию
	 * @return this builder
	 */
	public EntityBuilder gravity(boolean gravity) {
		this.entity.setGravity(gravity);
		return this;
	}
	
	/**
	 * Устанавливает бесшумность сущности
	 * @param silent включить/выключить бесшумность
	 * @return this builder
	 */
	public EntityBuilder silent(boolean silent) {
		this.entity.setSilent(silent);
		return this;
	}
	
	/**
	 * Устанавливает пассажира на сущность
	 * @param passenger сущность-пассажир
	 * @return this builder
	 */
	public EntityBuilder addPassenger(@NonNull Entity passenger) {
		this.entity.addPassenger(passenger);
		return this;
	}
	
	/**
	 * Устанавливает скорость сущности
	 * @param velocity вектор скорости
	 * @return this builder
	 */
	public EntityBuilder velocity(@NonNull Vector velocity) {
		this.entity.setVelocity(velocity);
		return this;
	}
	
	/**
	 * Устанавливает поворот сущности
	 * @param yaw поворот по горизонтали
	 * @param pitch поворот по вертикали
	 * @return this builder
	 */
	public EntityBuilder rotation(float yaw, float pitch) {
		this.entity.setRotation(yaw, pitch);
		return this;
	}
	
	/**
	 * Устанавливает тег сущности
	 * @param tag тег для добавления
	 * @return this builder
	 */
	public EntityBuilder addScoreboardTag(@NonNull String tag) {
		this.entity.addScoreboardTag(tag);
		return this;
	}
	
	/**
	 * Устанавливает постоянство сущности
	 * @param persistent включить/выключить постоянство
	 * @return this builder
	 */
	public EntityBuilder persistent(boolean persistent) {
		this.entity.setPersistent(persistent);
		return this;
	}
	
	
	public EntityBuilder teleport(@NonNull Location location) {
		this.entity.teleport(location);
		return this;
	}
	
	/**
	 * Устанавливает мета-данные для firework
	 * @param consumer функция для установки мета-данных
	 * @return this builder
	 */
	public EntityBuilder fireworkMeta(final Consumer<FireworkMeta> consumer) {
		return this.metaConsumer(Firework.class, firework -> {
			final FireworkMeta meta = firework.getFireworkMeta();
			consumer.accept(meta);
			firework.setFireworkMeta(meta);
		});
	}
	
	/**
	 * Получает мета-данные сущности
	 * @param metaClass класс мета-данных
	 * @return мета-данные сущности
	 */
	public <T extends Entity> T getMeta(final Class<T> metaClass) {
		return metaClass.cast(this.entity);
	}
	
	/**
	 * Проверяет, является ли сущность экземпляром мета-данных
	 * @param metaClass класс мета-данных
	 * @return true, если сущность является экземпляром мета-данных
	 */
	public <T extends Entity> boolean isInstance(final Class<T> metaClass) {
		return metaClass.isInstance(this.entity);
	}

	/**
	 * Получает мета-данные сущности
	 * @param metaClass класс мета-данных
	 * @param metaFunction функция для получения мета-данных
	 * @return мета-данные сущности
	 */
	public <T extends Entity, R> Optional<R> metaFunction(final Class<T> metaClass, final Function<T, R> metaFunction) {
		if (this.isInstance(metaClass)) {
			return Optional.fromNullable(metaFunction.apply(this.getMeta(metaClass)));
		}
		return Optional.absent();
	}
	
	/**
	 * Устанавливает мета-данные сущности
	 * @param metaClass класс мета-данных
	 * @param metaConsumer функция для установки мета-данных
	 * @return this builder
	 */
	public <T extends Entity> EntityBuilder metaConsumer(final Class<T> metaClass, final Consumer<T> metaConsumer) {
		if (this.isInstance(metaClass)) {
			metaConsumer.accept(this.getMeta(metaClass));
		}
		return this;
	}
	
	/**
	 * Устанавливает значение атрибута сущности
	 * @param attribute атрибут
	 * @param value значение
	 * @return this builder
	 */
	public EntityBuilder setAttribute(@NonNull Attribute attribute, double value) {
		this.metaConsumer(LivingEntity.class, livingEntity -> livingEntity.getAttribute(attribute).setBaseValue(value));
		return this;
	}
	
	/**
	 * Устанавливает модификатор атрибута
	 */
	public EntityBuilder addAttributeModifier(@NonNull Attribute attribute, @NonNull AttributeModifier modifier) {
		this.metaConsumer(LivingEntity.class, livingEntity -> livingEntity.getAttribute(attribute).addModifier(modifier));
		return this;
	}

	/**
	 * Получает модификатор атрибута
	 * @param attribute атрибут
	 * @return модификатор атрибута
	 */
	public Optional<AttributeInstance> getAttributeInstance(@NonNull Attribute attribute) {
		return this.metaFunction(LivingEntity.class, livingEntity -> livingEntity.getAttribute(attribute));
	}
	
	/**
	 * Получает созданную сущность
	 * @return созданная сущность
	 */
	public Entity build() {
		return this.entity;
	}
}
