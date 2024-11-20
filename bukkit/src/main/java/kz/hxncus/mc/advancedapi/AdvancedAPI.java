package kz.hxncus.mc.advancedapi;

import kz.hxncus.mc.advancedapi.bukkit.scheduler.AdvancedScheduler;
import kz.hxncus.mc.advancedapi.module.ServiceModule;
import kz.hxncus.mc.advancedapi.service.ModuleService;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The type Mineson api.
 * @author Hxncus
 * @since 1.0.0
 */
@Getter
@ToString
public class AdvancedAPI extends JavaPlugin {
	@Getter
	private static AdvancedAPI instance;
	
	private ModuleService moduleService;
	private ServiceModule serviceModule;
	
	private boolean isLoaded = false;
	
	@Override
	public void onLoad() {
		if (this.isLoaded) {
			return;
		}
		instance = this;
		
		this.moduleService = new ModuleService(this);
		this.serviceModule = new ServiceModule(this);
		
		this.isLoaded = true;
	}
	
	@Override
	public void onEnable() {
		if (!this.isLoaded) {
			throw new RuntimeException("Plugin not loaded yet!");
		}
		
		this.moduleService.addModule(this.serviceModule);
		this.serviceModule.addService(this.moduleService);
		
		this.serviceModule.setEnabled(true);
		this.moduleService.register();
		
	}
	
	@Override
	public void onDisable() {
		if (!this.isLoaded) {
			return;
		}
		
		this.moduleService.unregister();
		this.serviceModule.onDisable();
		AdvancedScheduler.cancelTasks();
	}
}
