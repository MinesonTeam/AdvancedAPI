package kz.hxncus.mc.advancedapi.api.bukkit.region;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.World;
import org.bukkit.util.BoundingBox;

import java.util.UUID;

@Getter
@Setter
public abstract class AbstractRegion implements Region {
    @NonNull protected String name;
    protected UUID owner;
    @NonNull protected final UUID uniqueId;
    @NonNull protected final BoundingBox boundingBox;
    @NonNull protected World world;
    protected int priority;
    
    protected AbstractRegion(@NonNull String name, UUID owner, @NonNull UUID uniqueId, @NonNull BoundingBox boundingBox, @NonNull World world, int priority) {
        this.name = name;
        this.owner = owner;
        this.uniqueId = uniqueId;
        this.boundingBox = boundingBox;
        this.world = world;
        this.priority = priority;
    }

    protected AbstractRegion(@NonNull String name, UUID owner, @NonNull UUID uniqueId, @NonNull BoundingBox boundingBox, @NonNull World world) {
        this.name = name;
        this.owner = owner;
        this.uniqueId = uniqueId;
        this.boundingBox = boundingBox;
        this.world = world;
        this.priority = 0;
    }

    @Override
    public void setOwner(@NonNull UUID owner) {
        this.owner = owner;
    }
}
