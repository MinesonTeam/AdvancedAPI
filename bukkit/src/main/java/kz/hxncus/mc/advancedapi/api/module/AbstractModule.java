package kz.hxncus.mc.advancedapi.api.module;

import lombok.Getter;

public abstract class AbstractModule implements Module {
	@Getter
	private final String name;
	private boolean isEnabled = false;
	
	protected AbstractModule(String name) {
		this.name = name;
	}
	
	@Override
	public void setEnabled(final boolean enabled) {
		if (isEnabled != enabled) {
			isEnabled = enabled;
			if (isEnabled) {
				onEnable();
			} else {
				onDisable();
			}
		}
	}
	
	@Override
	public boolean isEnabled() {
		return this.isEnabled;
	}
	
	@Override
	public void reload() {
		if (this.isEnabled) {
			onDisable();
			onEnable();
		}
	}
	
	@Override
	public int getPriority() {
		return -1;
	}
}
