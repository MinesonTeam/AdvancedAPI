package kz.hxncus.mc.advancedapi.bukkit.workload;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;

public class WorkloadRunnable implements Runnable {
	private static final double MAX_MILLIS_PER_TICK = 1;
	private static final int MAX_NANOS_PER_TICK = (int) (MAX_MILLIS_PER_TICK * 1.0E6);
	private static WorkloadRunnable instance;
	private final Deque<Runnable> workloads = new ArrayDeque<>();
	
	protected WorkloadRunnable() {
	}
	
	public static WorkloadRunnable getInstance() {
		// Техника, которую мы здесь применяем, называется «блокировка с двойной
		// проверкой» (Double-Checked Locking). Она применяется, чтобы
		// предотвратить создание нескольких объектов-одиночек, если метод будет
		// вызван из нескольких потоков одновременно.
		//
		// Хотя переменная `result` вполне оправданно кажется здесь лишней, она
		// помогает избежать подводных камней реализации DCL в Java.
		//
		// Больше об этой проблеме можно почитать здесь:
		// https://ru.wikipedia.org/wiki/%D0%91%D0%BB%D0%BE%D0%BA%D0%B8%D1%80%D0%BE%D0%B2%D0%BA%D0%B0_%D1%81_%D0%B4%D0%B2%D0%BE%D0%B9%D0%BD%D0%BE%D0%B9_%D0%BF%D1%80%D0%BE%D0%B2%D0%B5%D1%80%D0%BA%D0%BE%D0%B9
		final WorkloadRunnable result = instance;
		if (result != null) {
			return result;
		}
		synchronized (WorkloadRunnable.class) {
			if (instance == null) {
				instance = new WorkloadRunnable();
			}
			return instance;
		}
	}
	
	public void add(final Runnable runnable) {
		this.workloads.add(runnable);
	}
	
	public void addAll(Runnable... runnables) {
		Collections.addAll(this.workloads, runnables);
	}
	
	public void addAll(Collection<Runnable> runnables) {
		this.workloads.addAll(runnables);
	}
	
	@Override
	public void run() {
		final long stopTime = System.nanoTime() + MAX_NANOS_PER_TICK;
		Runnable nextLoad;
		while (System.nanoTime() <= stopTime && (nextLoad = this.workloads.poll()) != null) {
			nextLoad.run();
		}
	}
}
