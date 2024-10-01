package kz.hxncus.mc.minesonapi.api.bukkit.bossbar;

import kz.hxncus.mc.minesonapi.bukkit.scheduler.Scheduler;

/**
 * The interface Animatable.
 * @author Hxncus
 * @since  1.0.1
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
