package kz.hxncus.mc.advancedapi.api.bukkit.hologram;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class AbstractHologram implements Hologram {
    private String name;
    private Location location;
    private boolean spawned;
    private List<String> lines;
    private List<ArmorStand> entities;

    protected AbstractHologram(String name, Location location) {
        this.name = name;
        this.location = location;
        this.spawned = false;
        this.lines = new ArrayList<>();
        this.entities = new ArrayList<>();
    }
}
