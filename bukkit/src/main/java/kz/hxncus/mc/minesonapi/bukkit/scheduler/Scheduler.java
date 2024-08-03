package kz.hxncus.mc.minesonapi.bukkit.scheduler;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

@UtilityClass
public class Scheduler {
    private final Set<Integer> tasksId = new HashSet<>();
    private final MinesonAPI plugin = MinesonAPI.get();
    /**
     * Запустить (z - n) раз
     *
     * @param n        от скольки раз
     * @param z        до скольки раз
     * @param consumer функция
     */
    public void runNTimes(int n, int z, IntConsumer consumer) {
        IntStream.range(n, z).forEach(consumer);
    }

    /**
     * Запустить n раз
     *
     * @param n        сколько раз запустить
     * @param consumer функция
     */
    public void runNTimes(int n, IntConsumer consumer) {
        IntStream.range(0, n).forEach(consumer);
    }

    /**
     * Запустить таск
     *
     * @param runnable таск
     * @return {@link BukkitTask}
     */
    public BukkitTask run(Runnable runnable) {
        BukkitTask task = Bukkit.getScheduler().runTask(plugin, runnable);
        tasksId.add(task.getTaskId());
        return task;
    }

    /**
     * Запустить таск
     *
     * @param task таск
     * */
    public void run(Consumer<BukkitTask> task) {
        Bukkit.getScheduler().runTask(plugin, task);
    }

    /**
     * Запустить таск асинхронно
     *
     * @param runnable таск
     * @return bukkit task
     */
    public BukkitTask runAsync(Runnable runnable) {
        BukkitTask task = Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
        tasksId.add(task.getTaskId());
        return task;
    }

    /**
     * Запустить таск асинхронно
     *
     * @param task таск
     */
    public void runAsync(Consumer<BukkitTask> task) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }

    /**
     * Запустить таймер n раз
     *
     * @param n        сколько раз запустить
     * @param period   через сколько повторять
     * @param time     тип времени
     * @param consumer функция
     */
    public void timerNTimes(long n, long period, TimeUnit time, IntConsumer consumer) {
        timerNTimes(n, 0, time.toSeconds(period) * 20L, consumer);
    }

    /**
     * Запустить таймер n раз в тиках
     *
     * @param n        сколько раз запустить
     * @param period   через сколько повторять
     * @param consumer функция
     */
    public void timerNTimes(long n, long period, IntConsumer consumer) {
        timerNTimes(n, 0L, period, consumer);
    }

    /**
     * Запустить таймер n раз
     *
     * @param n        сколько раз запустить
     * @param delay    через сколько выполнить
     * @param period   через сколько повторять
     * @param time     тип времени
     * @param consumer функция
     */
    public void timerNTimes(long n, long delay, long period, TimeUnit time, IntConsumer consumer) {
        timerNTimes(n, time.toSeconds(delay) * 20L, time.toSeconds(period) * 20L, consumer);
    }

    /**
     * Запустить таймер n раз в тиках
     *
     * @param n        сколько раз запустить
     * @param delay    через сколько выполнить
     * @param period   через сколько повторять
     * @param consumer функция
     */
    public void timerNTimes(long n, long delay, long period, IntConsumer consumer) {
        AtomicInteger integer = new AtomicInteger();
        Bukkit.getScheduler().runTaskTimer(plugin, task -> {
                    consumer.accept(integer.getAndIncrement());
                    if (integer.get() == n) {
                        task.cancel();
                    }
                },
                delay, period);
    }

    /**
     * Повторять таймер каждые n раз в тиках
     *
     * @param n        сколько раз запустить
     * @param period   через сколько повторять
     * @param consumer функция
     */
    public BukkitTask timerN(long n, long period, IntConsumer consumer) {
        return timerN(n, 0L, period, consumer);
    }

    /**
     * Повторять таймер каждые n раз
     *
     * @param n        сколько раз запустить
     * @param period   через сколько повторять
     * @param time     тип времени
     * @param consumer функция
     */
    public BukkitTask timerN(long n, long period, TimeUnit time, IntConsumer consumer) {
        return timerN(n, 0L, time.toSeconds(period) * 20L, consumer);
    }

    /**
     * Повторять таймер каждые n раз
     *
     * @param n        сколько раз запустить
     * @param delay    через сколько выполнить
     * @param period   через сколько повторять
     * @param time     тип времени
     * @param consumer функция
     */
    public BukkitTask timerN(long n, long delay, long period, TimeUnit time, IntConsumer consumer) {
        return timerN(n, time.toSeconds(delay) * 20L, time.toSeconds(period) * 20L, consumer);
    }

    /**
     * Повторять таймер каждые n раз в тиках
     *
     * @param n        сколько раз запустить
     * @param delay    через сколько выполнить
     * @param period   через сколько повторять
     * @param consumer функция
     */
    public BukkitTask timerN(long n, long delay, long period, IntConsumer consumer) {
        AtomicInteger integer = new AtomicInteger();
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            consumer.accept(integer.getAndIncrement());
            if (integer.get() == n) {
                integer.set(0);
            }
        }, delay, period);
        tasksId.add(task.getTaskId());
        return task;
    }

    /**
     * Запустить таймер в тиках
     *
     * @param period   через сколько повторять
     * @param runnable таск
     * @return bukkit task
     */
    public BukkitTask timer(long period, Runnable runnable) {
        return timer(0L, period, runnable);
    }

    /**
     * Запустить таймер в тиках
     *
     * @param period   через сколько повторять
     * @param time     тип времени
     * @param runnable таск
     * @return bukkit task
     */
    public BukkitTask timer(long period, TimeUnit time, Runnable runnable) {
        return timer(0L, time.toSeconds(period) * 20L, runnable);
    }

    /**
     * Запустить таймер
     *
     * @param delay    через сколько выполнить
     * @param period   через сколько повторять
     * @param time     тип времени
     * @param runnable таск
     * @return bukkit task
     */
    public BukkitTask timer(long delay, long period, TimeUnit time, Runnable runnable) {
        return timer(time.toSeconds(delay) * 20L, time.toSeconds(period) * 20L, runnable);
    }

    /**
     * Запустить таймер в тиках
     *
     * @param delay    через сколько выполнить
     * @param period   через сколько повторять
     * @param runnable таск
     * @return bukkit task
     */
    public BukkitTask timer(long delay, long period, Runnable runnable) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay, period);
        tasksId.add(task.getTaskId());
        return task;
    }
    
    /**
     * Запустить таймер в тиках
     *
     * @param period через сколько повторять
     * @param task   таск
     */
    public void timer(long period, Consumer<BukkitTask> task) {
        timer(0, period, task);
    }
    
    /**
     * Запустить таймер в тиках
     *
     * @param period через сколько повторять
     * @param time   тип времени
     * @param task   таск
     */
    public void timer(long period, TimeUnit time, Consumer<BukkitTask> task) {
        timer(0, time.toSeconds(period) * 20L, task);
    }
    
    /**
     * Запустить таймер в тиках
     *
     * @param delay  через сколько выполнять
     * @param period через сколько повторять
     * @param time   тип времени
     * @param task   таск
     */
    public void timer(long delay, long period, TimeUnit time, Consumer<BukkitTask> task) {
        timer(time.toSeconds(delay) * 20L, time.toSeconds(period) * 20L, task);
    }
    
    /**
     * Запустить таймер в тиках
     * 
     * @param delay  через сколько выполнять
     * @param period через сколько повторять
     * @param task   таск
     */
    public void timer(long delay, long period, Consumer<BukkitTask> task) {
        Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period);
    }

    /**
     * Запустить таймер в тиках
     *
     * @param period   через сколько повторять
     * @param runnable таск
     * @return bukkit task
     */
    public BukkitTask timerAsync(long period, Runnable runnable) {
        return timerAsync(0L, period, runnable);
    }

    /**
     * Запустить таймер
     *
     * @param period   через сколько повторять
     * @param runnable таск
     * @return bukkit task
     */
    public BukkitTask timerAsync(long period, TimeUnit time, Runnable runnable) {
        return timerAsync(0L, time.toSeconds(period) * 20L, runnable);
    }

    /**
     * Запустить таймер
     *
     * @param delay    через сколько выполнить
     * @param period   через сколько повторять
     * @param runnable таск
     * @return bukkit task
     */
    public BukkitTask timerAsync(long delay, long period, TimeUnit time, Runnable runnable) {
        return timerAsync(time.toSeconds(delay) * 20L, time.toSeconds(period) * 20L, runnable);
    }

    /**
     * Запустить таймер в тиках
     *
     * @param delay    через сколько выполнить
     * @param period   через сколько повторять
     * @param runnable таск
     * @return bukkit task
     */
    public BukkitTask timerAsync(long delay, long period, Runnable runnable) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, period);
        tasksId.add(task.getTaskId());
        return task;
    }
    
    /**
     * Запустить таймер в тиках
     *
     * @param period через сколько повторять
     * @param task   таск
     */
    public void timerAsync(long period, Consumer<BukkitTask> task) {
        timerAsync(0L, period, task);
    }

    /**
     * Запустить таймер
     *
     * @param period через сколько повторять
     * @param task   таск
     */
    public void timerAsync(long period, TimeUnit time, Consumer<BukkitTask> task) {
        timerAsync(0L, time.toSeconds(period) * 20L, task);
    }

    /**
     * Запустить таймер
     *
     * @param delay  через сколько выполнить
     * @param period через сколько повторять
     * @param task   таск
     */
    public void timerAsync(long delay, long period, TimeUnit time, Consumer<BukkitTask> task) {
        timerAsync(time.toSeconds(delay) * 20L, time.toSeconds(period) * 20L, task);
    }

    /**
     * Запустить таймер в тиках
     *
     * @param delay  через сколько выполнить
     * @param period через сколько повторять
     * @param task   таск
     */
    public void timerAsync(long delay, long period, Consumer<BukkitTask> task) {
        task = task.andThen(bukkitTask -> tasksId.add(bukkitTask.getTaskId()));
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task, delay, period);
    }
    
    /**
     * Выполнить позже
     *
     * @param delay    через сколько выполнить
     * @param time     тип времени
     * @param runnable таск
     * @return bukkit task
     */
    public BukkitTask later(long delay, TimeUnit time, Runnable runnable) {
        return later(time.toSeconds(delay) * 20L, runnable);
    }

    /**
     * Выполнить позже в тиках
     *
     * @param delay    через сколько выполнить
     * @param runnable таск
     * @return bukkit task
     */
    public BukkitTask later(long delay, Runnable runnable) {
        BukkitTask task = Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
        tasksId.add(task.getTaskId());
        return task;
    }

    /**
     * Выполнить позже
     *
     * @param delay    через сколько выполнить
     * @param time     тип времени
     * @param runnable таск
     * @return bukkit task
     */
    public BukkitTask laterAsync(long delay, TimeUnit time, Runnable runnable) {
        return laterAsync(time.toSeconds(delay) * 20L, runnable);
    }


    /**
     * Выполнить позже в тиках
     *
     * @param delay    через сколько выполнить
     * @param runnable таск
     * @return bukkit task
     */
    public BukkitTask laterAsync(long delay, Runnable runnable) {
        BukkitTask task = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
        tasksId.add(task.getTaskId());
        return task;
    }

    /**
     * Останавливает все запущенные таймеры
     */
    public void stopAllTimers() {
        tasksId.forEach(Scheduler::stopTimer);
        tasksId.clear();
    }

    /**
     * Останавливает таймер.
     * @param runnableID - айди таймера, который будем останавливать.
     */
    public void stopTimer(int runnableID) {
        Bukkit.getScheduler().cancelTask(runnableID);
    }

    public void cancelPluginSchedulers(Plugin plugin) {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        for (BukkitWorker activeWorker : scheduler.getActiveWorkers()) {
            if (activeWorker.getOwner() != plugin) {
                continue;
            }
            scheduler.cancelTask(activeWorker.getTaskId());
        }
    }

    public void cancelAllPluginsSchedulers() {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        for (BukkitWorker activeWorker : scheduler.getActiveWorkers()) {
            scheduler.cancelTask(activeWorker.getTaskId());
        }
    }
}
