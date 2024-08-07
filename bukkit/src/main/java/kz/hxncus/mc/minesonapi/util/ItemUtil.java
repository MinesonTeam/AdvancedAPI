package kz.hxncus.mc.minesonapi.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.file.YamlConstructor;
import org.bukkit.configuration.file.YamlRepresenter;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.Collections;
import java.util.Map;

/**
 * Class Item util.
 * @author Hxncus
 * @since  1.0.0
 */
@UtilityClass
public class ItemUtil {
	private final YamlConstructor YAML_CONSTRUCTOR = new YamlConstructor();
	private final YamlRepresenter YAML_REPRESENTER = new YamlRepresenter();
	private final DumperOptions DUMPER_OPTIONS = new DumperOptions();
	private final Yaml yaml = new Yaml(YAML_CONSTRUCTOR, YAML_REPRESENTER, DUMPER_OPTIONS);
	
	/**
	 * Serialize string.
	 *
	 * @param item the item
	 * @return the string
	 */
	@NonNull
	public String serialize(final ItemStack item) {
		final Map<String, Object> root = Collections.singletonMap("item", item);
		return yaml.dumpAs(root, null, DumperOptions.FlowStyle.BLOCK);
	}
	
	/**
	 * Deserialize item stack.
	 *
	 * @param textItem the text item
	 * @return the item stack
	 */
	@NonNull
	public ItemStack deserialize(final String textItem) {
		final Map<String, Object> root = yaml.load(textItem);
		return (ItemStack) root.get("item");
	}
}
