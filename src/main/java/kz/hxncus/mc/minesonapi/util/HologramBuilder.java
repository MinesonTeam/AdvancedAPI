package kz.hxncus.mc.minesonapi.util;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.holograms.HologramLine;
import eu.decentsoftware.holograms.api.holograms.HologramPage;
import eu.decentsoftware.holograms.api.utils.items.HologramItem;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class HologramBuilder {
    public final Hologram hologram;
    public HologramBuilder(@NonNull String name, @NonNull Location location) {
        this(name, location, false);
    }

    public HologramBuilder(@NonNull String name, @NonNull Location location, boolean saveToFile) {
        this(name, location, saveToFile, Collections.emptyList());
    }

    public HologramBuilder(@NonNull String name, @NonNull Location location, List<String> lines) {
        this(name, location, false, lines);
    }

    public HologramBuilder(@NonNull String name, @NonNull Location location, boolean saveToFile, List<String> lines) {
        Hologram holo = DHAPI.getHologram(name);
        if (holo != null) {
            this.hologram = holo;
        } else {
            this.hologram = DHAPI.createHologram(name, location, saveToFile, lines);
        }
    }

    public HologramBuilder moveHologram(@NonNull Location location) {
        DHAPI.moveHologram(this.hologram, location);
        return this;
    }

    public HologramBuilder updateHologram() {
        this.hologram.updateAll();
        return this;
    }

    public HologramBuilder removeHologram() {
        this.hologram.delete();
        return this;
    }

    public HologramBuilder editHologram(Consumer<Hologram> function) {
        function.accept(this.hologram);
        this.hologram.save();
        this.hologram.updateAll();
        this.hologram.updateAnimationsAll();
        return this;
    }
    public HologramBuilder editHologramPage(int index, Consumer<HologramPage> consumer) {
        return editHologram(holo -> {
            consumer.accept(holo.getPage(index));
            holo.save();
        });
    }
    public HologramBuilder editHologramLine(int page, int line, Consumer<HologramLine> consumer) {
        return editHologramPage(page, hologramPage -> {
            consumer.accept(hologramPage.getLine(line));
        });
    }

    public HologramBuilder addHologramLine(@NonNull Material material) throws IllegalArgumentException {
        return addHologramLine(new ItemStack(material));
    }

    public HologramBuilder addHologramLine(@NonNull ItemStack item) throws IllegalArgumentException {
        return addHologramLine("#ICON:" + HologramItem.fromItemStack(item).getContent());
    }

    public HologramBuilder addHologramLine(String content) throws IllegalArgumentException {
        return addHologramLine(0, content);
    }

    public HologramBuilder addHologramLine(int page, @NonNull Material material) throws IllegalArgumentException {
        return addHologramLine(page, new ItemStack(material));
    }

    public HologramBuilder addHologramLine(int page, @NonNull ItemStack item) throws IllegalArgumentException {
        return addHologramLine(page, "#ICON:" + HologramItem.fromItemStack(item).getContent());
    }

    public HologramBuilder addHologramLine(int page, @NonNull String content) throws IllegalArgumentException {
        return editHologramPage(page, hologramPage -> {
            HologramLine line = new HologramLine(hologramPage, hologramPage.getNextLineLocation(), content);
            hologramPage.addLine(line);
        });
    }
    public HologramBuilder addHologramPage() {
        return addHologramPage(Collections.emptyList());
    }

    public HologramBuilder addHologramPage(List<String> lines) {
        return editHologram(holo -> {
            HologramPage page = holo.addPage();
            if (lines == null || lines.isEmpty()) {
                return;
            }
            Iterator<String> iterator = lines.iterator();

            while(iterator.hasNext()) {
                HologramLine line = new HologramLine(page, page.getNextLineLocation(), iterator.next());
                page.addLine(line);
            }
        });
    }

    public HologramBuilder insertHologramPage(int index) {
        return insertHologramPage(index, Collections.emptyList());
    }

    public HologramBuilder insertHologramPage(int index, List<String> lines) {
        return editHologram(holo -> {
            HologramPage page = holo.insertPage(index);
            if (page == null) {
                throw new IllegalArgumentException("Given page index is out of bounds for the hologram.");
            } else {
                if (lines == null || lines.isEmpty()) {
                    return;
                }
                Iterator<String> iterator = lines.iterator();

                while(iterator.hasNext()) {
                    HologramLine line = new HologramLine(page, page.getNextLineLocation(), iterator.next());
                    page.addLine(line);
                }
            }
        });
    }
    public HologramBuilder setHologramLineContent(int page, int line, @NonNull String content) {
        return editHologramLine(page, line, holoLine -> {
            holoLine.setContent(content);
        });
    }
    public HologramBuilder removeHologramPage(int index) {
        return editHologram(holo -> holo.removePage(index));
    }
    public Hologram build() {
        hologram.save();
        hologram.updateAll();
        return hologram;
    }
}
