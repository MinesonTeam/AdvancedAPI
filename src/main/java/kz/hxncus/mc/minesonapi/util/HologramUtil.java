package kz.hxncus.mc.minesonapi.util;

import eu.decentsoftware.holograms.api.DHAPI;
import lombok.NonNull;

public class HologramUtil {
    public void removeHologram(@NonNull String name) {
        DHAPI.removeHologram(name);
    }
}
