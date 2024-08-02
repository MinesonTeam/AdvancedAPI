package kz.hxncus.mc.minesonapi.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitWorker;

@UtilityClass
public class ScheduleUtil {
    public void cancelAllActiveSchedulers() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        for (BukkitWorker activeWorker : scheduler.getActiveWorkers()) {
            scheduler.cancelTask(activeWorker.getTaskId());
        }
    }
}
