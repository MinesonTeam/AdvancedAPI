package kz.hxncus.mc.minesonapi.util;

import kz.hxncus.mc.minesonapi.bossbar.MSBossBar;
import kz.hxncus.mc.minesonapi.configuration.Yaml;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class BossBarUtil {
    public List<MSBossBar> getBossBarsFromFile(Yaml file, String path, Constructor<?> constructor) {
        List<MSBossBar> bossBarList = new ArrayList<>();
        for (Object object : file.getClassList(path, constructor)) {
            if (object instanceof MSBossBar bossBar) {
                bossBarList.add(bossBar);
            }
        }
        return bossBarList;
    }
}
