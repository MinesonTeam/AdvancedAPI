package kz.hxncus.mc.minesonapi.bukkit.workload;

import java.util.ArrayDeque;
import java.util.Deque;

public class WorkloadRunnable implements Runnable {
    private static final double MAX_MILLIS_PER_TICK = 5;
    private static final int MAX_NANOS_PER_TICK = (int) (MAX_MILLIS_PER_TICK * 1E6);
    private final Deque<Workload> workloads = new ArrayDeque<>();

    public void add(Workload workload) {
        workloads.add(workload);
    }

    @Override
    public void run() {
        long stopTime = System.nanoTime() + MAX_NANOS_PER_TICK;
        Workload nextLoad;
        while (System.nanoTime() <= stopTime && (nextLoad = workloads.poll()) != null) {
            nextLoad.compute();
        }
    }
}
