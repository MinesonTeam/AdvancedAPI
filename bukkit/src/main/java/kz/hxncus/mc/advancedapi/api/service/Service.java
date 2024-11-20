package kz.hxncus.mc.advancedapi.api.service;

import org.bukkit.plugin.Plugin;

public interface Service {
	Plugin getPlugin();
	
	void setRegistered(boolean enabled);
	
	boolean isRegistered();
	
	void register();
	
	void unregister();
	
	void reload();
	
	int getPriority();
}
