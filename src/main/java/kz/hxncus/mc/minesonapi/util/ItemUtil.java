package kz.hxncus.mc.minesonapi.util;

import lombok.experimental.UtilityClass;
import org.bukkit.configuration.file.YamlConstructor;
import org.bukkit.configuration.file.YamlRepresenter;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.Map;

@UtilityClass
public class ItemUtil {
    private final DumperOptions yamlOptions = new DumperOptions();
    private final Yaml yaml = new Yaml(new YamlConstructor(), new YamlRepresenter(), yamlOptions);
    public String serialize(ItemStack item) {
        Map<String, Object> root = new LinkedHashMap<>();
        root.put("item", item);
        return yaml.dumpAs(root, null, DumperOptions.FlowStyle.BLOCK);
    }

    public ItemStack deserialize(String textItem) {
        Map<String, Object> root = yaml.load(textItem);
        return (ItemStack) root.get("item");
    }
}
