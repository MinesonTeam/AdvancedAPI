package kz.hxncus.mc.minesonapi.bukkit.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Class Armor equip event.
 *
 * @author Hxncus
 * @since 1.0.1
 */
@Getter
@Setter
@ToString
public class ArmorEquipEvent extends EntityEvent implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;
	private final EquipMethod equipMethod;
	private final ArmorType armorType;
	private ItemStack oldArmorPiece, newArmorPiece;
	
	public ArmorEquipEvent(@NonNull final Entity what, @NonNull final EquipMethod equipMethod,
	                       @NonNull final ArmorType armorType, @NonNull final ItemStack oldArmorPiece,
	                       @NonNull final ItemStack newArmorPiece) {
		super(what);
		this.equipMethod = equipMethod;
		this.armorType = armorType;
		this.oldArmorPiece = oldArmorPiece;
		this.newArmorPiece = newArmorPiece;
	}
	
	@Override
	public void setCancelled(final boolean cancel) {
		this.cancelled = cancel;
	}
	
	@Override
	public @NonNull HandlerList getHandlers() {
		return handlers;
	}
	
	/**
	 * The enum Armor type.
	 *
	 * @author Hxncus
	 * @since 1.0.1
	 */
	@Getter
	@ToString
	public enum ArmorType {
		/**
		 * Represents armor belonging to the helmet slot, e.g., helmets, skulls, and carved pumpkins.
		 */
		HELMET(5),
		/**
		 * Represents armor belonging to the chestplate slot, e.g., chestplates and elytras.
		 */
		CHESTPLATE(6),
		/**
		 * Represents leggings.
		 */
		LEGGINGS(7),
		/**
		 * Represents boots.
		 */
		BOOTS(8);
		
		private final int slot;
		
		ArmorType(final int slot) {
			this.slot = slot;
		}
		
		/**
		 * Attempts to match the ArmorType for the specified ItemStack.
		 *
		 * @param itemStack The ItemStack to parse the type of.
		 * @return The parsed ArmorType, or null if not found.
		 */
		public static ArmorType matchType(final ItemStack itemStack) {
			final Material type = itemStack.getType();
			final String typeName = type.name();
			final ArmorType armorType;
			if (typeName.endsWith("_HELMET") || typeName.endsWith("_SKULL") || typeName.endsWith("_HEAD") || Material.CARVED_PUMPKIN.name().equals(typeName)) {
				armorType = HELMET;
			} else if (typeName.endsWith("_CHESTPLATE") || "ELYTRA".equals(typeName)) {
				armorType = CHESTPLATE;
			} else if (typeName.endsWith("_LEGGINGS")) {
				armorType = LEGGINGS;
			} else if (typeName.endsWith("_BOOTS")) {
				armorType = BOOTS;
			} else {
				return null;
			}
			return armorType;
		}
	}
	
	@ToString
	public enum EquipMethod {
		/**
		 * When you shift click an armor piece to equip or unequip
		 */
		SHIFT_CLICK,
		/**
		 * When you drag and drop the item to equip or unequip
		 */
		DRAG,
		/**
		 * When you manually equip or unequip the item. Use to be DRAG
		 */
		PICK_DROP,
		/**
		 * When you right-click an armor piece in the hotbar without the inventory open to equip.
		 */
		HOTBAR,
		/**
		 * When you press the hotbar slot number while hovering over the armor slot to equip or unequip
		 */
		HOTBAR_SWAP,
		/**
		 * When in range of a dispenser that shoots an armor piece to equip.<br>
		 * Requires the spigot version to have {@link org.bukkit.event.block.BlockDispenseArmorEvent} implemented.
		 */
		DISPENSER,
		/**
		 * When an armor piece is removed due to it losing all durabilities.
		 */
		BROKE,
		/**
		 * When you die, causing all armors to unequip
		 */
		DEATH,
	}
}
