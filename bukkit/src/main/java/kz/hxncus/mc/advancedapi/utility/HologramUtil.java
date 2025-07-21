package kz.hxncus.mc.advancedapi.utility;

import kz.hxncus.mc.advancedapi.api.bukkit.hologram.Hologram;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@UtilityClass
public class HologramUtil {
    @Getter
    private final Map<String, Hologram> HOLOGRAMS = new HashMap<>();
    public final double LINE_HEIGHT = 0.25;

    public Hologram createHologram(Supplier<? extends Hologram> creator) {
        Hologram hologram = creator.get();
        HologramUtil.addHologram(hologram);
        return hologram;
    }

    public void deleteHologram(Hologram hologram) {
        hologram.despawn();
        HologramUtil.removeHologram(hologram);
    }

    public Optional<Hologram> getHologram(String name) {
        return Optional.of(HologramUtil.HOLOGRAMS.get(name));
    }

    public void spawnAllHolograms() {
        for (Hologram hologram : HologramUtil.HOLOGRAMS.values()) {
            hologram.spawn();
        }
    }

    public void updateAllHolograms() {
        for (Hologram hologram : HologramUtil.HOLOGRAMS.values()) {
            hologram.update();
        }
    }

    private void addHologram(Hologram hologram) {
        HologramUtil.HOLOGRAMS.put(hologram.getName(), hologram);
    }

    private void removeHologram(Hologram hologram) {
        HologramUtil.HOLOGRAMS.remove(hologram.getName());
    }

    public void despawnAllHolograms() {
        for (Hologram hologram : HologramUtil.HOLOGRAMS.values()) {
            hologram.despawn();
        }
    }

    public void addLine(String name, String line) {
        Optional<Hologram> optionalHologram = HologramUtil.getHologram(name);
        optionalHologram.ifPresent(hologram -> hologram.addLine(line));
    }

    public void removeLine(String name, int index) {
        Optional<Hologram> optionalHologram = HologramUtil.getHologram(name);
        if (optionalHologram.isPresent()) {
            Hologram hologram = optionalHologram.get();
            hologram.getLines().remove(index);
            hologram.updateIfSpawned();
        }
    }

    public void moveHologram(String name, Location location) {
        Optional<Hologram> optionalHologram = HologramUtil.getHologram(name);
        if (optionalHologram.isPresent()) {
            Hologram hologram = optionalHologram.get();
            hologram.move(location);
            hologram.updateIfSpawned();
        }
    }

    public void spawnHologram(String name) {
        Optional<Hologram> optionalHologram = HologramUtil.getHologram(name);
        optionalHologram.ifPresent(Hologram::spawn);
    }

    public void despawnHologram(String name) {
        Optional<Hologram> optionalHologram = HologramUtil.getHologram(name);
        optionalHologram.ifPresent(Hologram::despawn);
    }

    public void updateHologram(String name) {
        Optional<Hologram> optionalHologram = HologramUtil.getHologram(name);
        optionalHologram.ifPresent(Hologram::update);
    }
}
