package kz.hxncus.mc.minesonapi.bukkit.scheduler;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * The type Scheduler.
 */
@UtilityClass
public class Scheduler {
	private final Set<Integer> tasksId = new HashSet<>(8);
	private final MinesonAPI plugin = MinesonAPI.getInstance();
	private final BukkitScheduler scheduler = Bukkit.getScheduler();
	/**
	 * The constant 1 second = 20 ticks.
	 */
	private final short SECOND_TICKS = 20;
	
	/**
	 * Run (to - from) time
	 *
	 * @param from      от скольки раз
	 * @param to        до скольки раз
	 * @param consumer функция
	 */
	public void runNTimes(final int from, final int to, final IntConsumer consumer) {
		IntStream.range(from, to).forEach(consumer);
	}
	
	/**
	 * Запустить num раз
	 *
	 * @param num      сколько раз запустить
	 * @param consumer функция
	 */
	public void runNTimes(final int num, final IntConsumer consumer) {
		IntStream.range(0, num).forEach(consumer);
	}
	
	/**
	 * Run numbs
	 *
	 * @param numbs    numbs array to run
	 * @param consumer function with numbing
	 */
	public void runNTimes(final IntConsumer consumer, final int... numbs) {
		Arrays.stream(numbs).forEach(consumer);
	}
	
	/**
	 * Запустить таск
	 *
	 * @param runnable таск
	 * @return {@link BukkitTask}
	 */
	public BukkitTask run(final Runnable runnable) {
		final BukkitTask task = scheduler.runTask(plugin, runnable);
		tasksId.add(task.getTaskId());
		return task;
	}
	
	/**
	 * Запустить таск
	 *
	 * @param task таск
	 */
	public void run(final Consumer<? super BukkitTask> task) {
		scheduler.runTask(plugin, task);
	}
	
	/**
	 * Запустить таск асинхронно
	 *
	 * @param runnable таск
	 * @return bukkit task
	 */
	public BukkitTask runAsync(final Runnable runnable) {
		final BukkitTask task = scheduler.runTaskAsynchronously(plugin, runnable);
		tasksId.add(task.getTaskId());
		return task;
	}
	
	/**
	 * Запустить таск асинхронно
	 *
	 * @param task таск
	 */
	public void runAsync(final Consumer<? super BukkitTask> task) {
		scheduler.runTaskAsynchronously(plugin, task);
	}
	
	/**
	 * Запустить таймер n раз
	 *
	 * @param i        сколько раз запустить
	 * @param period   через сколько повторять
	 * @param time     тип времени
	 * @param consumer функция
	 */
	public void timerNTimes(final int i, final long period, final TimeUnit time, final IntConsumer consumer) {
		timerNTimes(i, 0, time.toSeconds(period) * SECOND_TICKS, consumer);
	}
	
	/**
	 * Запустить таймер n раз в тиках
	 *
	 * @param i        сколько раз запустить
	 * @param delay    через сколько выполнить
	 * @param period   через сколько повторять
	 * @param consumer функция
	 */
	public void timerNTimes(final int i, final long delay, final long period, final IntConsumer consumer) {
		final AtomicInteger integer = new AtomicInteger();
		scheduler.runTaskTimer(plugin, task -> {
            consumer.accept(integer.getAndIncrement());
            if (integer.get() == i) {
                task.cancel();
            }
        }, delay, period);
	}
	
	/**
	 * Запустить таймер n раз в тиках
	 *
	 * @param i        сколько раз запустить
	 * @param period   через сколько повторять
	 * @param consumer функция
	 */
	public void timerNTimes(final int i, final long period, final IntConsumer consumer) {
		timerNTimes(i, 0L, period, consumer);
	}
	
	/**
	 * Запустить таймер n раз
	 *
	 * @param i        сколько раз запустить
	 * @param delay    через сколько выполнить
	 * @param period   через сколько повторять
	 * @param time     тип времени
	 * @param consumer функция
	 */
	public void timerNTimes(final int i, final long delay, final long period, final TimeUnit time,
	                        final IntConsumer consumer) {
		timerNTimes(i, time.toSeconds(delay) * SECOND_TICKS, time.toSeconds(period) * SECOND_TICKS, consumer);
	}
	
	/**
	 * Повторять таймер каждые n раз в тиках
	 *
	 * @param i        сколько раз запустить
	 * @param period   через сколько повторять
	 * @param consumer функция
	 * @return the bukkit task
	 */
	public BukkitTask timerN(final int i, final long period, final IntConsumer consumer) {
		return timerN(i, 0L, period, consumer);
	}
	
	/**
	 * Повторять таймер каждые n раз в тиках
	 *
	 * @param i        сколько раз запустить
	 * @param delay    через сколько выполнить
	 * @param period   через сколько повторять
	 * @param consumer функция
	 * @return BukkitTask bukkit task
	 */
	public BukkitTask timerN(final int i, final long delay, final long period, final IntConsumer consumer) {
		final AtomicInteger integer = new AtomicInteger();
		final BukkitTask task = scheduler.runTaskTimer(plugin, () -> {
			consumer.accept(integer.getAndIncrement());
			if (integer.get() == i) {
				integer.set(0);
			}
		}, delay, period);
		tasksId.add(task.getTaskId());
		return task;
	}
	
	/**
	 * Повторять таймер каждые l раз
	 *
	 * @param i        сколько раз запустить
	 * @param period   через сколько повторять
	 * @param time     тип времени
	 * @param consumer функция
	 * @return BukkitTask bukkit task
	 */
	public BukkitTask timerN(final int i, final long period, final TimeUnit time, final IntConsumer consumer) {
		return timerN(i, 0L, time.toSeconds(period) * SECOND_TICKS, consumer);
	}
	
	/**
	 * Повторять таймер каждые n раз
	 *
	 * @param i        сколько раз запустить
	 * @param delay    через сколько выполнить
	 * @param period   через сколько повторять
	 * @param time     тип времени
	 * @param consumer функция
	 * @return BukkitTask bukkit task
	 */
	public BukkitTask timerN(final int i, final long delay, final long period, final TimeUnit time, final IntConsumer consumer) {
		return timerN(i, time.toSeconds(delay) * SECOND_TICKS, time.toSeconds(period) * SECOND_TICKS, consumer);
	}
	
	/**
	 * Запустить таймер в тиках
	 *
	 * @param period   через сколько повторять
	 * @param runnable таск
	 * @return bukkit task
	 */
	public BukkitTask timer(final long period, final Runnable runnable) {
		return timer(0L, period, runnable);
	}
	
	/**
	 * Запустить таймер в тиках
	 *
	 * @param delay    через сколько выполнить
	 * @param period   через сколько повторять
	 * @param runnable таск
	 * @return bukkit task
	 */
	public BukkitTask timer(final long delay, final long period, final Runnable runnable) {
		final BukkitTask task = scheduler.runTaskTimer(plugin, runnable, delay, period);
		tasksId.add(task.getTaskId());
		return task;
	}
	
	/**
	 * Запустить таймер в тиках
	 *
	 * @param period   через сколько повторять
	 * @param time     тип времени
	 * @param runnable таск
	 * @return bukkit task
	 */
	public BukkitTask timer(final long period, final TimeUnit time, final Runnable runnable) {
		return timer(0L, time.toSeconds(period) * SECOND_TICKS, runnable);
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
	public BukkitTask timer(final long delay, final long period, final TimeUnit time, final Runnable runnable) {
		return timer(time.toSeconds(delay) * SECOND_TICKS, time.toSeconds(period) * SECOND_TICKS, runnable);
	}
	
	/**
	 * Запустить таймер в тиках
	 *
	 * @param period через сколько повторять
	 * @param task   таск
	 */
	public void timer(final long period, final Consumer<? super BukkitTask> task) {
		timer(0, period, task);
	}
	
	/**
	 * Запустить таймер в тиках
	 *
	 * @param delay  через сколько выполнять
	 * @param period через сколько повторять
	 * @param task   таск
	 */
	public void timer(final long delay, final long period, final Consumer<? super BukkitTask> task) {
		scheduler.runTaskTimer(plugin, task, delay, period);
	}
	
	/**
	 * Запустить таймер в тиках
	 *
	 * @param period через сколько повторять
	 * @param time   тип времени
	 * @param task   таск
	 */
	public void timer(final long period, final TimeUnit time, final Consumer<? super BukkitTask> task) {
		timer(0, time.toSeconds(period) * SECOND_TICKS, task);
	}
	
	/**
	 * Запустить таймер в тиках
	 *
	 * @param delay  через сколько выполнять
	 * @param period через сколько повторять
	 * @param time   тип времени
	 * @param task   таск
	 */
	public void timer(final long delay, final long period, final TimeUnit time, final Consumer<? super BukkitTask> task) {
		timer(time.toSeconds(delay) * SECOND_TICKS, time.toSeconds(period) * SECOND_TICKS, task);
	}
	
	/**
	 * Запустить таймер в тиках
	 *
	 * @param period   через сколько повторять
	 * @param runnable таск
	 * @return bukkit task
	 */
	public BukkitTask timerAsync(final long period, final Runnable runnable) {
		return timerAsync(0L, period, runnable);
	}
	
	/**
	 * Запустить таймер в тиках
	 *
	 * @param delay    через сколько выполнить
	 * @param period   через сколько повторять
	 * @param runnable таск
	 * @return bukkit task
	 */
	public BukkitTask timerAsync(final long delay, final long period, final Runnable runnable) {
		final BukkitTask task = scheduler.runTaskTimerAsynchronously(plugin, runnable, delay, period);
		tasksId.add(task.getTaskId());
		return task;
	}
	
	/**
	 * Запустить таймер
	 *
	 * @param period   через сколько повторять
	 * @param time     тип времени
	 * @param runnable таск
	 * @return bukkit task
	 */
	public BukkitTask timerAsync(final long period, final TimeUnit time, final Runnable runnable) {
		return timerAsync(0L, time.toSeconds(period) * SECOND_TICKS, runnable);
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
	public BukkitTask timerAsync(final long delay, final long period, final TimeUnit time, final Runnable runnable) {
		return timerAsync(time.toSeconds(delay) * SECOND_TICKS, time.toSeconds(period) * SECOND_TICKS, runnable);
	}
	
	/**
	 * Запустить таймер в тиках
	 *
	 * @param period через сколько повторять
	 * @param task   таск
	 */
	public void timerAsync(final long period, final Consumer<BukkitTask> task) {
		timerAsync(0L, period, task);
	}
	
	/**
	 * Запустить таймер в тиках
	 *
	 * @param delay  через сколько выполнить
	 * @param period через сколько повторять
	 * @param task   таск
	 */
	public void timerAsync(final long delay, final long period, final Consumer<BukkitTask> task) {
		final Consumer<BukkitTask> taskConsumer = task.andThen(bukkitTask -> tasksId.add(bukkitTask.getTaskId()));
		scheduler.runTaskTimerAsynchronously(plugin, taskConsumer, delay, period);
	}
	
	/**
	 * Запустить таймер
	 *
	 * @param period через сколько повторять
	 * @param time   тип времени
	 * @param task   таск
	 */
	public void timerAsync(final long period, final TimeUnit time, final Consumer<BukkitTask> task) {
		timerAsync(0L, time.toSeconds(period) * SECOND_TICKS, task);
	}
	
	/**
	 * Запустить таймер
	 *
	 * @param delay  через сколько выполнить
	 * @param period через сколько повторять
	 * @param time   тип времени
	 * @param task   таск
	 */
	public void timerAsync(final long delay, final long period, final TimeUnit time, final Consumer<BukkitTask> task) {
		timerAsync(time.toSeconds(delay) * SECOND_TICKS, time.toSeconds(period) * SECOND_TICKS, task);
	}
	
	/**
	 * Выполнить позже
	 *
	 * @param delay    через сколько выполнить
	 * @param time     тип времени
	 * @param runnable таск
	 * @return bukkit task
	 */
	public BukkitTask later(final long delay, final TimeUnit time, final Runnable runnable) {
		return later(time.toSeconds(delay) * SECOND_TICKS, runnable);
	}
	
	/**
	 * Выполнить позже в тиках
	 *
	 * @param delay    через сколько выполнить
	 * @param runnable таск
	 * @return bukkit task
	 */
	public BukkitTask later(final long delay, final Runnable runnable) {
		final BukkitTask task = scheduler.runTaskLater(plugin, runnable, delay);
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
	public BukkitTask laterAsync(final long delay, final TimeUnit time, final Runnable runnable) {
		return laterAsync(time.toSeconds(delay) * SECOND_TICKS, runnable);
	}
	
	
	/**
	 * Выполнить позже в тиках
	 *
	 * @param delay    через сколько выполнить
	 * @param runnable таск
	 * @return bukkit task
	 */
	public BukkitTask laterAsync(final long delay, final Runnable runnable) {
		final BukkitTask task = scheduler.runTaskLaterAsynchronously(plugin, runnable, delay);
		tasksId.add(task.getTaskId());
		return task;
	}
	
	/**
	 * Останавливает все запущенные таймеры
	 */
	public void stopTimers() {
		tasksId.forEach(Scheduler::cancelTask);
		tasksId.clear();
	}
	
	/**
	 * Cancel plugin tasks.
	 *
	 * @param plugin the plugin
	 */
	public void cancelPluginTasks(final Plugin plugin) {
		for (final BukkitWorker activeWorker : scheduler.getActiveWorkers()) {
			if (activeWorker.getOwner() != plugin) {
				continue;
			}
			cancelTask(activeWorker.getTaskId());
		}
	}
	
	/**
	 * Cancel all plugin tasks.
	 */
	public void cancelAllPluginTasks() {
		for (final BukkitWorker activeWorker : scheduler.getActiveWorkers()) {
			cancelTask(activeWorker.getTaskId());
		}
	}
	
	/**
	 * Cancel task.
	 *
	 * @param taskId the task id
	 */
	public void cancelTask(final int taskId) {
		scheduler.cancelTask(taskId);
	}
}
