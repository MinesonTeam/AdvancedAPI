package kz.hxncus.mc.minesonapi.bukkit.bossbar;

import kz.hxncus.mc.minesonapi.bukkit.scheduler.Scheduler;

/**
 * The interface Animatable.
 */
public interface Animatable {
	/**
	 * Stop animation.
	 */
	void stopAnimation();
	
	/**
	 * Start animation.
	 *
	 * @param scheduler the scheduler
	 */
	void startAnimation(Scheduler scheduler);
	
}
