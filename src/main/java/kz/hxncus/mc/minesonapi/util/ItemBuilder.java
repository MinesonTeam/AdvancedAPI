package kz.hxncus.mc.minesonapi.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import lombok.NonNull;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ItemBuilder {
    private final ItemStack itemStack;
    private final ItemMeta itemMeta;
    public ItemBuilder(Material material) {
        this(new ItemStack(material));
    }

    public ItemBuilder(@NonNull ItemStack item) {
        this.itemStack = item.clone();
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder displayName(String name) {
        return displayName(Component.text(name));
    }

    public ItemBuilder displayName(Component name) { return meta(meta -> meta.displayName(name)); }
    public ItemBuilder lore(String lore) {
        return lore(Component.text(lore));
    }
    public ItemBuilder lore(Component lore) {
        return loreComponent(Collections.singletonList(lore));
    }

    public ItemBuilder lore(String... lore) {
        return loreString(Arrays.asList(lore));
    }

    public ItemBuilder loreString(List<String> lore) {
        return loreComponent(lore.stream().map(Component::text).collect(Collectors.toList()));
    }

    public ItemBuilder lore(Component... lore) {
        return loreComponent(Arrays.asList(lore));
    }

    public ItemBuilder loreComponent(List<Component> lore) {
        return meta(meta -> meta.lore(lore));
    }

    public ItemBuilder addLore(String lore) { return addLore(Component.text(lore)); }

    public ItemBuilder addLore(Component lore) {
        return meta(meta -> {
            List<Component> list = meta.lore();
            if (list == null) {
                meta.lore(Collections.singletonList(lore));
            } else {
                list.add(lore);
            }
        });
    }

    public ItemBuilder addLore(String... lines) {
        return addLoreString(Arrays.asList(lines));
    }

    public ItemBuilder addLoreString(List<String> lines) {
        return addLoreComponent(lines.stream().map(s -> (Component) Component.text(s)).toList());
    }

    public ItemBuilder addLore(Component... lore) {
        return addLoreComponent(Arrays.asList(lore));
    }

    public ItemBuilder addLoreComponent(List<Component> lines) {
        return meta(meta -> {
            List<Component> lore = meta.lore();
            if (lore == null || lore.isEmpty()) {
                meta.lore(lines);
                return;
            }
            lore.addAll(lines);
        });
    }

    public ItemBuilder data(int data) {
        return durability((short) data);
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder durability(short durability) {
        return edit(item -> item.setDurability(durability));
    }

    public ItemBuilder amount(int amount) {
        return edit(item -> item.setAmount(amount));
    }
    public ItemBuilder type(Material material) {
        return edit(item -> item.setType(material));
    }

    public ItemBuilder edit(Consumer<ItemStack> function) {
        function.accept(this.itemStack);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment) {
        return enchant(enchantment, 1);
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        return meta(meta -> meta.addEnchant(enchantment, level, true));
    }

    public ItemBuilder removeEnchant(Enchantment enchantment) {
        return meta(meta -> meta.removeEnchant(enchantment));
    }

    public ItemBuilder clearEnchants() {
        return meta(meta -> meta.getEnchants().keySet().forEach(meta::removeEnchant));
    }

    public ItemBuilder flags() {
        return flags(ItemFlag.values());
    }

    public ItemBuilder flags(ItemFlag... flags) {
        return meta(meta -> meta.addItemFlags(flags));
    }

    public ItemBuilder removeFlags() {
        return removeFlags(ItemFlag.values());
    }

    public ItemBuilder removeFlags(ItemFlag... flags) {
        return meta(meta -> meta.removeItemFlags(flags));
    }

    public ItemBuilder meta(Consumer<ItemMeta> metaConsumer) {
        if (itemMeta != null) {
            metaConsumer.accept(itemMeta);
        }
        return this;
    }

    public ItemBuilder skullMeta(Consumer<SkullMeta> consumer) {
        return meta(meta -> {
                if (meta instanceof SkullMeta skullMeta) {
                    consumer.accept(skullMeta);
                }
            }
        );
    }

    public ItemBuilder addHeadTexture(@NonNull String url){
        return skullMeta(skullMeta -> {
                try {
                    PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
                    profile.getProperties().add(new ProfileProperty("textures", URI.create(url).toURL().toExternalForm()));
                    skullMeta.setPlayerProfile(profile);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        );
    }

    public ItemBuilder setSkullOwner(@NonNull String name) {
        return skullMeta(skullMeta -> skullMeta.setOwningPlayer(Bukkit.getOfflinePlayerIfCached(name)));
    }

    public ItemBuilder compassLodeStone(Location lodestone, boolean lodestoneTracked) {
        return meta(meta -> {
                if (meta instanceof CompassMeta compassMeta) {
                    compassMeta.setLodestone(lodestone);
                    compassMeta.setLodestoneTracked(lodestoneTracked);
                }
            }
        );
    }

    public ItemBuilder armorColor(Color color) {
        return meta(LeatherArmorMeta.class, meta -> meta.setColor(color));
    }

    public <T extends ItemMeta> ItemBuilder meta(Class<T> metaClass, Consumer<T> metaConsumer) {
        return meta(meta -> {
            if (metaClass.isInstance(meta)) {
                metaConsumer.accept(metaClass.cast(meta));
            }
        });
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}

