package kz.hxncus.mc.minesonapi;

import kz.hxncus.mc.minesonapi.configuration.Yaml;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Random;

@FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
public class Constants {
    private Constants() {
    }
    public static final Yaml BOSS_BAR = new Yaml(MinesonAPI.getPlugin(), "bossbar.yml");
}
