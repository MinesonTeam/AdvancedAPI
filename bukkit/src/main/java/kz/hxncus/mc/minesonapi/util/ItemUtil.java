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

@UtilityClass
public class ItemUtil {
    private final YamlConstructor YAML_CONSTRUCTOR = new YamlConstructor();
    private final YamlRepresenter YAML_REPRESENTER = new YamlRepresenter();
    private final DumperOptions DUMPER_OPTIONS = new DumperOptions();
    private final Yaml yaml = new Yaml(YAML_CONSTRUCTOR, YAML_REPRESENTER, DUMPER_OPTIONS);

    @NonNull
    public String serialize(ItemStack item) {
        Map<String, Object> root = Collections.singletonMap("item", item);
        return yaml.dumpAs(root, null, DumperOptions.FlowStyle.BLOCK);
    }

    @NonNull
    public ItemStack deserialize(String textItem) {
        Map<String, Object> root = yaml.load(textItem);
        return (ItemStack) root.get("item");
    }
}
