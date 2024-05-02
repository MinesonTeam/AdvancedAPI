package kz.hxncus.mc.minesonapi.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitWorker;

@UtilityClass
public class ScheduleUtil {
    public void cancelAllActiveSchedulers() {
        for (BukkitWorker activeWorker : Bukkit.getScheduler().getActiveWorkers()) {
            Bukkit.getScheduler().cancelTask(activeWorker.getTaskId());
        }
    }
}
