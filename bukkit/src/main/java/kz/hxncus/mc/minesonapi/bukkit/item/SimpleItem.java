package kz.hxncus.mc.minesonapi.bukkit.item;

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
public class SimpleItem extends ItemStack {
    private ItemMeta itemMeta;

    public SimpleItem(ItemStack itemStack) {
        super(itemStack.getType(), itemStack.getAmount());
        if (itemStack.hasItemMeta()) {
            this.itemMeta = itemStack.getItemMeta();
        }
    }

    public SimpleItem(Material type) {
        super(type);
        if (this.hasItemMeta()) {
            this.itemMeta = getItemMeta();
        }
    }

    public SimpleItem(Material type, int amount) {
        super(type, amount);
        this.itemMeta = getItemMeta();
    }

    public SimpleItem displayName(String name) {
        this.itemMeta.setDisplayName(name);
        return this;
    }

    public SimpleItem lore(String lore) {
        return loreString(Collections.singletonList(lore));
    }

    public SimpleItem lore(String... lore) {
        return loreString(Arrays.asList(lore));
    }

    public SimpleItem loreString(List<String> lore) {
        itemMeta.setLore(lore);
        return this;
    }

    public SimpleItem addLore(String line) {
        addLore(Collections.singletonList(line));
        return this;
    }

    public SimpleItem addLore(String... lines) {
        return addLore(Arrays.asList(lines));
    }

    public SimpleItem addLore(List<String> lines) {
        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            itemMeta.setLore(lines);
        } else {
            lore.addAll(lines);
        }
        return this;
    }

    public SimpleItem setMaterial(Material material) {
        setType(material);
        return this;
    }

    public SimpleItem setData(int data) {
        setDurability((short) data);
        return this;
    }

    public SimpleItem amount(int amount) {
        setAmount(amount);
        return this;
    }

    public SimpleItem color(int color) {
        return color(Color.fromRGB(color));
    }

    public SimpleItem color(@NonNull DyeColor color) {
        return color(color.getColor());
    }

    public SimpleItem color(@NonNull Color color) {
        if (itemMeta instanceof LeatherArmorMeta) {
            armorColor(color);
        } else if (getType().name().endsWith("_wool")) {
            DyeColor dyeColor = DyeColor.getByColor(color);
            if (dyeColor == null) {
                return this;
            }
            setData(dyeColor.getWoolData());
        }
        return this;
    }

    public SimpleItem enchant(Enchantment enchantment) {
        return enchant(enchantment, 1);
    }

    public SimpleItem enchant(Enchantment enchantment, int level) {
        return enchant(enchantment, level, true);
    }

    public SimpleItem enchant(Enchantment enchantment, int level, boolean override) {
        itemMeta.addEnchant(enchantment, level, override);
        return this;
    }

    public SimpleItem removeEnchant(Enchantment enchantment) {
        itemMeta.removeEnchant(enchantment);
        return this;
    }

    public SimpleItem clearEnchants() {
        itemMeta.getEnchants().forEach((enchantment, level) -> itemMeta.removeEnchant(enchantment));
        return this;
    }

    public SimpleItem flags() {
        return flags(ItemFlag.values());
    }

    public SimpleItem flags(ItemFlag... flags) {
        itemMeta.addItemFlags(flags);
        return this;
    }

    public SimpleItem removeFlags() {
        return removeFlags(ItemFlag.values());
    }

    public SimpleItem removeFlags(ItemFlag... flags) {
        itemMeta.removeItemFlags(flags);
        return this;
    }

    public <T> SimpleItem setPDC(NamespacedKey namespacedKey, PersistentDataType<T, T> pdt, T value) {
        itemMeta.getPersistentDataContainer().set(namespacedKey, pdt, value);
        return this;
    }

    public <T> T getPDC(NamespacedKey namespacedKey, PersistentDataType<T, T> pdt) {
        return itemMeta.getPersistentDataContainer().get(namespacedKey, pdt);
    }

    public <T> T getOrDefaultPDC(NamespacedKey namespacedKey, PersistentDataType<T, T> pdt, T def) {
        T value = itemMeta.getPersistentDataContainer().get(namespacedKey, pdt);
        return value == null ? def : value;
    }

    public <T> boolean hasPDC(NamespacedKey namespacedKey, PersistentDataType<T, T> pdt) {
        return itemMeta.getPersistentDataContainer().has(namespacedKey, pdt);
    }

    public SimpleItem removePDC(NamespacedKey namespacedKey) {
        itemMeta.getPersistentDataContainer().remove(namespacedKey);
        return this;
    }

    public Set<NamespacedKey> getPDCKeys() {
        return itemMeta.getPersistentDataContainer().getKeys();
    }

    public SimpleItem addHeadTexture(@NonNull String url){
        return meta(SkullMeta.class, meta -> {
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

    public SimpleItem setSkullOwner(@NonNull String name) {
        return meta(SkullMeta.class, meta -> meta.setOwningPlayer(Bukkit.getOfflinePlayer(name)));
    }

    public SimpleItem compassLodeStone(Location lodestone, boolean lodestoneTracked) {
        return meta(CompassMeta.class, meta -> {
            meta.setLodestone(lodestone);
            meta.setLodestoneTracked(lodestoneTracked);
        });
    }

    public SimpleItem armorColor(Color color) {
        return meta(LeatherArmorMeta.class, meta -> meta.setColor(color));
    }

    public <T extends ItemMeta> SimpleItem meta(Class<T> metaClass, Consumer<T> metaConsumer) {
        if (metaClass.isInstance(itemMeta)) {
            metaConsumer.accept(metaClass.cast(itemMeta));
        }
        return this;
    }

    public String serializeString() {
        return ItemUtil.serialize(this);
    }

    public SimpleItem apply() {
        setItemMeta(itemMeta);
        return this;
    }
}
