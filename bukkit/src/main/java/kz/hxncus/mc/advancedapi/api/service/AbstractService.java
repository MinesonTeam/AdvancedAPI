package kz.hxncus.mc.advancedapi.api.service;

import lombok.Getter;
import org.bukkit.plugin.Plugin;

public abstract class AbstractService implements Service {
	@Getter
	private final Plugin plugin;
	private boolean isRegistered = false;
	
	protected AbstractService(final Plugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void setRegistered(final boolean enabled) {
		if (this.isRegistered != enabled) {
			this.isRegistered = enabled;
			if (this.isRegistered) {
				this.register();
			} else {
				this.unregister();
			}
		}
	}
	
	@Override
	public boolean isRegistered() {
		return this.isRegistered;
	}
	
	@Override
	public void reload() {
		this.unregister();
		this.register();
	}
	
	@Override
	public int getPriority() {
		return -1;
	}
}
