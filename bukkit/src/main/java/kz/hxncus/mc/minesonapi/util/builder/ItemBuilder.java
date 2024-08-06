package kz.hxncus.mc.minesonapi.util.builder;

import kz.hxncus.mc.minesonapi.util.ItemUtil;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@EqualsAndHashCode(callSuper = false)
public class ItemBuilder {
	private final ItemStack itemStack;
	private final ItemMeta itemMeta;
	
	public ItemBuilder(final ItemStack itemStack) {
		this.itemStack = itemStack;
		this.itemMeta = itemStack.getItemMeta();
	}
	
	public ItemBuilder(final Material type) {
		this.itemStack = new ItemStack(type);
		this.itemMeta = this.itemStack.getItemMeta();
	}
	
	public ItemBuilder(final Material type, final int amount) {
		this.itemStack = new ItemStack(type, amount);
		this.itemMeta = this.itemStack.getItemMeta();
	}
	
	public ItemBuilder displayName(final String name) {
		this.itemMeta.setDisplayName(name);
		return this;
	}
	
	public ItemBuilder lore(final String lore) {
		return this.loreString(Collections.singletonList(lore));
	}
	
	public ItemBuilder loreString(final List<String> lore) {
		this.itemMeta.setLore(lore);
		return this;
	}
	
	public ItemBuilder lore(final String... lore) {
		return this.loreString(Arrays.asList(lore));
	}
	
	public ItemBuilder addLore(final String line) {
		this.addLore(Collections.singletonList(line));
		return this;
	}
	
	public ItemBuilder addLore(final List<String> lines) {
		final List<String> lore = this.itemMeta.getLore();
		if (lore == null) {
			this.itemMeta.setLore(lines);
		} else {
			lore.addAll(lines);
		}
		return this;
	}
	
	public ItemBuilder addLore(final String... lines) {
		return this.addLore(Arrays.asList(lines));
	}
	
	public ItemBuilder setMaterial(final Material material) {
		this.itemStack.setType(material);
		return this;
	}
	
	public ItemBuilder amount(final int amount) {
		this.itemStack.setAmount(amount);
		return this;
	}
	
	public ItemBuilder color(final int color) {
		return this.color(Color.fromRGB(color));
	}
	
	public ItemBuilder color(@NonNull final Color color) {
		if (this.itemMeta instanceof LeatherArmorMeta) {
			this.armorColor(color);
		} else if (this.itemStack.getType()
		                         .name()
		                         .endsWith("_wool")) {
			final DyeColor dyeColor = DyeColor.getByColor(color);
			if (dyeColor == null) {
				return this;
			}
			this.setData(dyeColor.getWoolData());
		}
		return this;
	}
	
	public ItemBuilder armorColor(final Color color) {
		return this.meta(LeatherArmorMeta.class, meta -> meta.setColor(color));
	}
	
	public ItemBuilder setData(final int data) {
		this.itemStack.setDurability((short) data);
		return this;
	}
	
	public <T extends ItemMeta> ItemBuilder meta(final Class<T> metaClass, final Consumer<T> metaConsumer) {
		if (metaClass.isInstance(this.itemMeta)) {
			metaConsumer.accept(metaClass.cast(this.itemMeta));
		}
		return this;
	}
	
	public ItemBuilder color(@NonNull final DyeColor color) {
		return this.color(color.getColor());
	}
	
	public ItemBuilder enchant(final Enchantment enchantment) {
		return this.enchant(enchantment, 1);
	}
	
	public ItemBuilder enchant(final Enchantment enchantment, final int level) {
		return this.enchant(enchantment, level, true);
	}
	
	public ItemBuilder enchant(final Enchantment enchantment, final int level, final boolean override) {
		this.itemMeta.addEnchant(enchantment, level, override);
		return this;
	}
	
	public ItemBuilder removeEnchant(final Enchantment enchantment) {
		this.itemMeta.removeEnchant(enchantment);
		return this;
	}
	
	public ItemBuilder clearEnchants() {
		this.itemMeta.getEnchants()
		             .forEach((enchantment, level) -> this.itemMeta.removeEnchant(enchantment));
		return this;
	}
	
	public ItemBuilder flags() {
		return this.flags(ItemFlag.values());
	}
	
	public ItemBuilder flags(final ItemFlag... flags) {
		this.itemMeta.addItemFlags(flags);
		return this;
	}
	
	public ItemBuilder removeFlags() {
		return this.removeFlags(ItemFlag.values());
	}
	
	public ItemBuilder removeFlags(final ItemFlag... flags) {
		this.itemMeta.removeItemFlags(flags);
		return this;
	}
	
	public <T> ItemBuilder setPDC(final NamespacedKey namespacedKey, final PersistentDataType<T, T> pdt, final T value) {
		this.itemMeta.getPersistentDataContainer()
		             .set(namespacedKey, pdt, value);
		return this;
	}
	
	public <T> T getPDC(final NamespacedKey namespacedKey, final PersistentDataType<T, T> pdt) {
		return this.itemMeta.getPersistentDataContainer()
		                    .get(namespacedKey, pdt);
	}
	
	public <T> T getOrDefaultPDC(final NamespacedKey namespacedKey, final PersistentDataType<T, T> pdt, final T def) {
		final T value = this.itemMeta.getPersistentDataContainer()
		                             .get(namespacedKey, pdt);
		return value == null ? def : value;
	}
	
	public <T> boolean hasPDC(final NamespacedKey namespacedKey, final PersistentDataType<T, T> pdt) {
		return this.itemMeta.getPersistentDataContainer()
		                    .has(namespacedKey, pdt);
	}
	
	public ItemBuilder removePDC(final NamespacedKey namespacedKey) {
		this.itemMeta.getPersistentDataContainer()
		             .remove(namespacedKey);
		return this;
	}
	
	public Set<NamespacedKey> getPDCKeys() {
		return this.itemMeta.getPersistentDataContainer()
		                    .getKeys();
	}
	
	public ItemBuilder addHeadTexture(@NonNull final String url) {
		return this.meta(SkullMeta.class, meta -> {
			//            try {
			//                PlayerProfile profile = Bukkit.createP(UUID.randomUUID());
			//                PlayerTextures textures = profile.getTextures();
			//                textures.setSkin(URI.create(url).toURL());
			//                profile.setTextures(textures);
			//                meta.setOwnerProfile(profile);
			//            } catch (MalformedURLException e) {
			//                throw new RuntimeException(e);
			//            }
		});
	}
	
	public ItemBuilder setSkullOwner(@NonNull final String name) {
		return this.meta(SkullMeta.class, meta -> meta.setOwningPlayer(Bukkit.getOfflinePlayer(name)));
	}
	
	public ItemBuilder compassLodeStone(final Location lodestone, final boolean lodestoneTracked) {
		return this.meta(CompassMeta.class, meta -> {
			meta.setLodestone(lodestone);
			meta.setLodestoneTracked(lodestoneTracked);
		});
	}
	
	public String serializeString() {
		return ItemUtil.serialize(this.itemStack);
	}
	
	public ItemStack build() {
		this.itemStack.setItemMeta(this.itemMeta);
		return this.itemStack;
	}
}
