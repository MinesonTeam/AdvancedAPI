package kz.hxncus.mc.advancedapi.bukkit.hologram;

import kz.hxncus.mc.advancedapi.api.bukkit.hologram.AbstractHologram;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class AdvancedHologram extends AbstractHologram {
    public AdvancedHologram(String name, Location location) {
        super(name, location);
    }
    
    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> map = new HashMap<>();
        map.put("name", this.getName());
        map.put("location", this.getLocation().serialize());
        return map;
    }

    public static AdvancedHologram deserialize(Map<String, Object> map) {
        final String name = (String) map.get("name");
        final Location location = Location.deserialize((Map<String, Object>) map.get("location"));
        return new AdvancedHologram(name, location);
    }
}
