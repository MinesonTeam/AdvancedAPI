package kz.hxncus.mc.minesonapi.scheduler;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class Schedule {
    @Getter private final Plugin plugin;
    @Getter private final BukkitScheduler bukkitScheduler;
    @Getter private final Set<Integer> tasksId = new HashSet<>();
    public Schedule(@NonNull Plugin plugin) {
        this.plugin = plugin;
        this.bukkitScheduler = Bukkit.getScheduler();
    }

    /**
     * Запустить n раз
     *
     * @param n        сколько раз запустить
     * @param consumer функция
     */
    public static void runNTimes(int n, IntConsumer consumer) {
        IntStream.range(0, n).forEach(consumer);
    }

    /**
     * Запустить таск
     *
     * @param runnable таск
     * @return {@link BukkitTask}
     */
    public BukkitTask run(Runnable runnable) {
        BukkitTask task = this.bukkitScheduler.runTask(this.plugin, runnable);
        tasksId.add(task.getTaskId());
        return task;
    }

    /**
     * Запустить таск
     *
     * @param task таск
     * */
    public void run(Consumer<BukkitTask> task) {
        this.bukkitScheduler.runTask(this.plugin, task);
    }

    /**
     * Запустить таск асинхронно
     *
     * @param runnable таск
     * @return bukkit task
     */
    public BukkitTask runAsync(Runnable runnable) {
        BukkitTask task = this.bukkitScheduler.runTaskAsynchronously(this.plugin, runnable);
        tasksId.add(task.getTaskId());
        return task;
    }

    /**
     * Запустить таск асинхронно
     *
     * @param task таск
     */
    public void runAsync(Consumer<BukkitTask> task) {
        this.bukkitScheduler.runTaskAsynchronously(this.plugin, task);
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
        this.bukkitScheduler.runTaskTimer(this.plugin, task -> {
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
        BukkitTask task = this.bukkitScheduler.runTaskTimer(this.plugin, () -> {
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
        BukkitTask task = this.bukkitScheduler.runTaskTimer(this.plugin, runnable, delay, period);
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
        this.bukkitScheduler.runTaskTimer(this.plugin, task, delay, period);
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
        BukkitTask task = this.bukkitScheduler.runTaskTimerAsynchronously(this.plugin, runnable, delay, period);
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
        task = task.andThen(bukkitTask -> this.tasksId.add(bukkitTask.getTaskId()));
        this.bukkitScheduler.runTaskTimerAsynchronously(this.plugin, task, delay, period);
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
        BukkitTask task = this.bukkitScheduler.runTaskLater(this.plugin, runnable, delay);
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
        BukkitTask task = this.bukkitScheduler.runTaskLaterAsynchronously(this.plugin, runnable, delay);
        tasksId.add(task.getTaskId());
        return task;
    }

    /**
     * Останавливает все запущенные таймеры
     */
    public void stopAllTimers() {
        this.tasksId.forEach(this::stopTimer);
        this.tasksId.clear();
    }

    /**
     * Останавливает таймер.
     * @param runnableID - айди таймера, который будем останавливать.
     */
    public void stopTimer(int runnableID) {
        this.bukkitScheduler.cancelTask(runnableID);
    }
}
