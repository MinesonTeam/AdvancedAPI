package kz.hxncus.mc.advancedapi.bukkit.scheduler;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * The type Scheduler.
 */
@UtilityClass
public class AdvancedScheduler {
	private final Set<Integer> tasksId = new HashSet<>(8);
	private final AdvancedAPI plugin = AdvancedAPI.getInstance();
	private final BukkitScheduler scheduler = Bukkit.getScheduler();
	/**
	 * The constant 1 second = 20 ticks.
	 */
	private final short SECOND_TICKS = 20;
	
	/**
	 * Run (to–from) time
	 *
	 * @param from      от скольки раз
	 * @param to        до скольки раз
	 * @param consumer функция
	 */
	public void runNTimes(final int from, final int to, final IntConsumer consumer) {
		for (int i = from; i < to; i++) {
			consumer.accept(i);
		}
	}
	
	/**
	 * Запустить num раз
	 *
	 * @param num      сколько раз запустить
	 * @param consumer функция
	 */
	public void runNTimes(final int num, final IntConsumer consumer) {
		for (int i = 0; i < num; i++) {
			consumer.accept(i);
		}
	}
	
	/**
	 * Run numbs
	 *
	 * @param numbs    numbs an array to run
	 * @param consumer function with numbing
	 */
	public void runNTimes(final IntConsumer consumer, final int... numbs) {
		for (int num : numbs) {
			consumer.accept(num);
		}
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
	 * Выполнить задачу в главном потоке сервера и ждать ее выполнения.
	 */
	@SneakyThrows
	public void runAndWait(Runnable runnable) {
		if (Bukkit.isPrimaryThread()) {
			runnable.run();
		} else {
			RunnableFuture<Void> future = new FutureTask<>(runnable, null);
			AdvancedScheduler.run(future);
			try {
				future.get(20, TimeUnit.SECONDS);
			} catch (ExecutionException e) {
				throw e.getCause();
			} catch (TimeoutException e) {
				e.printStackTrace();
				throw new TimeoutException("Таймаут ожидания bukkit-таска " + runnable);
			}
		}
	}
	
	/**
	 * Выполнить задачу в главном потоке сервера, ждать ее выполнения и вернуть результат.
	 */
	@SneakyThrows
	public <V> V runAndWait(Callable<V> callable) {
		if (Bukkit.isPrimaryThread()) {
			return callable.call();
		} else {
			CompletableFuture<V> future = new CompletableFuture<>();
			AdvancedScheduler.run(() -> {
				try {
					future.complete(callable.call());
				} catch (Exception e){
					future.completeExceptionally(e);
				}
			});
			try {
				return future.get(20, TimeUnit.SECONDS);
			} catch (ExecutionException e) {
				throw e.getCause();
			} catch (TimeoutException e) {
				e.printStackTrace();
				throw new TimeoutException("Таймаут ожидания bukkit-таска " + callable);
			}
		}
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
	 * Cancel task.
	 *
	 * @param taskId the task ID
	 */
	public void cancelTask(final int taskId) {
		scheduler.cancelTask(taskId);
	}
	
	/**
	 * Останавливает все запущенные таймеры
	 */
	public void cancelTasks() {
		tasksId.forEach(AdvancedScheduler::cancelTask);
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
	public void cancelPluginsTasks() {
		for (final BukkitWorker activeWorker : scheduler.getActiveWorkers()) {
			cancelTask(activeWorker.getTaskId());
		}
	}
}
