package kz.hxncus.mc.minesonapi.scheduler;

import kz.hxncus.mc.minesonapi.listener.EventManager;
import kz.hxncus.mc.minesonapi.listener.PluginDisablingEvent;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ScheduleManager {
    private final Map<String, Schedule> SCHEDULE_MAP = new HashMap<>();
    public Schedule getSchedule(String name) {
        return SCHEDULE_MAP.get(name);
    }
    private static ScheduleManager instance;

    public Schedule putSchedule(Plugin plugin, String name) {
        Schedule schedule = new Schedule(plugin, name);
        EventManager.getInstance(plugin).register(PluginDisablingEvent.class,
                EventPriority.LOWEST, event -> SCHEDULE_MAP.values().stream().filter(schedules ->
                        schedules.getPlugin() == event.getPlugin()).forEach(schedules -> {
                            schedules.stopAllTimers();
                            SCHEDULE_MAP.remove(schedules.getName());
                }));
        SCHEDULE_MAP.put(name, schedule);
        return schedule;
    }
}
