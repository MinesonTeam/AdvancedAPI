package kz.hxncus.mc.advancedapi.bukkit.region;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.util.BoundingBox;

import kz.hxncus.mc.advancedapi.api.bukkit.region.AbstractRegion;
import kz.hxncus.mc.advancedapi.api.bukkit.region.Region;
import lombok.Getter;
import lombok.NonNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class AdvancedRegion extends AbstractRegion { 
    public AdvancedRegion(@NonNull String name, @NonNull UUID owner, @NonNull UUID uniqueId, @NonNull World world, @NonNull BoundingBox boundingBox, int priority) {
        super(name, owner, uniqueId, boundingBox, world, priority);
    }

    public AdvancedRegion(@NonNull String name, @NonNull UUID owner, @NonNull UUID uniqueId, @NonNull World world, @NonNull BoundingBox boundingBox) {
        super(name, owner, uniqueId, boundingBox, world);
    }

    @NonNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("name", name);
        result.put("owner", owner.transform(UUID::toString).orNull());
        result.put("uniqueId", uniqueId.toString());
        result.put("world", world.getUID().toString());
        result.put("boundingBox", boundingBox.serialize());
        result.put("priority", priority);
        return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public static Region deserialize(@NonNull Map<String, Object> args) {
        String name = "" + args.get("name");
        UUID owner = args.get("owner") == null ? null : UUID.fromString("" + args.get("owner"));
        UUID uniqueId = UUID.fromString("" + args.get("uniqueId"));
        World world = Bukkit.getWorld(UUID.fromString("" + args.get("world")));
        BoundingBox boundingBox = BoundingBox.deserialize((Map<String, Object>) args.get("boundingBox"));
        int priority = Integer.parseInt("" + args.get("priority"));
        return new AdvancedRegion(name, owner, uniqueId, world, boundingBox, priority);
    }
}
