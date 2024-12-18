package kz.hxncus.mc.advancedapi.bukkit.color.pattern;

import kz.hxncus.mc.advancedapi.api.bukkit.color.pattern.Pattern;
import kz.hxncus.mc.advancedapi.utility.ColorUtil;
import lombok.NonNull;

public class AltColorCodesPattern implements Pattern {
    @Override
    public String process(@NonNull final String message) {
        return ColorUtil.translateAlternateColorCodes(message);
    }
}
