package kz.hxncus.mc.minesonapi.bukkit.workload;

import java.util.ArrayDeque;
import java.util.Deque;

public class WorkloadRunnable implements Runnable {
    private static final double MAX_MILLIS_PER_TICK = 10;
    private static final int MAX_NANOS_PER_TICK = (int) (MAX_MILLIS_PER_TICK * 1E6);
    private final Deque<Runnable> workloads = new ArrayDeque<>();

    public void add(Runnable runnable) {
        workloads.add(runnable);
    }

    @Override
    public void run() {
        long stopTime = System.nanoTime() + MAX_NANOS_PER_TICK;
        Runnable nextLoad;
        while (System.nanoTime() <= stopTime && (nextLoad = workloads.poll()) != null) {
            nextLoad.run();
        }
    }
}
