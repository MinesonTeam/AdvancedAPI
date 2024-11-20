package kz.hxncus.mc.advancedapi.api.module;

public interface Module {
	String getName();
	
	void setEnabled(boolean enabled);
	
	boolean isEnabled();
	
	void onEnable();
	
	void onDisable();
	
	void reload();
	
	int getPriority();
}
