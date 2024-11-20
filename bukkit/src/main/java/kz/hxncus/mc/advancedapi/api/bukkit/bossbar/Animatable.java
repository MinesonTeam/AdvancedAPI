package kz.hxncus.mc.advancedapi.api.bukkit.bossbar;

import kz.hxncus.mc.advancedapi.bukkit.scheduler.AdvancedScheduler;

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
	 * @param advancedScheduler the scheduler
	 */
	void startAnimation(AdvancedScheduler advancedScheduler);
	
}
