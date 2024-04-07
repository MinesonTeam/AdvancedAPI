package kz.hxncus.mc.minesonapi.scheduler;

import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class ScheduleManager {
    private static ScheduleManager instance;
    public static ScheduleManager getInstance() {
        if (ScheduleManager.instance == null) {
            ScheduleManager.instance = new ScheduleManager();
        }
        return ScheduleManager.instance;
    }

    private ScheduleManager() {

    }
    private final Map<String, Schedule> scheduleMap = new HashMap<>();

    public Schedule getSchedule(String name) {
        return scheduleMap.get(name);
    }

    public Schedule putSchedule(Plugin plugin, String name) {
        Schedule schedule = new Schedule(plugin, name);
        scheduleMap.put(name, schedule);
        return schedule;
    }

    public void removeSchedules(Plugin plugin) {
        this.scheduleMap.entrySet().stream().filter(entry -> entry.getValue().getPlugin() == plugin)
            .forEach(entry -> {
                entry.getValue().stopAllTimers();
                scheduleMap.remove(entry.getKey());
            }
        );
    }
}
