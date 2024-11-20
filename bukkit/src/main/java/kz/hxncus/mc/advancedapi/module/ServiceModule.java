package kz.hxncus.mc.advancedapi.module;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import kz.hxncus.mc.advancedapi.api.module.AbstractModule;
import kz.hxncus.mc.advancedapi.api.service.Service;
import kz.hxncus.mc.advancedapi.bukkit.event.EventService;
import kz.hxncus.mc.advancedapi.bukkit.inventory.InventoryService;
import kz.hxncus.mc.advancedapi.bukkit.server.ServerService;
import kz.hxncus.mc.advancedapi.bukkit.world.WorldService;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ServiceModule extends AbstractModule {
	private static Plugin plugin;
	private static final List<Service> services = new ArrayList<>();
	
	public ServiceModule(Plugin plugin) {
		super("service");
		ServiceModule.plugin = plugin;
	}
	
	@Override
	public void onEnable() {
		this.addPluginServices();
		this.registerServices();
	}
	
	@Override
	public void onDisable() {
		this.unregisterServices();
		this.clearServices();
	}
	
	private void addPluginServices() {
		AdvancedAPI api = AdvancedAPI.getInstance();
		this.addServices(new EventService(ServiceModule.plugin), new WorldService(api), new InventoryService(api),
		                 new ServerService(api));
	}
	
	private void sortServices(final boolean isReversed) {
		Comparator<Service> comparator = Comparator.comparingInt(Service::getPriority);
		if (isReversed) {
			ServiceModule.services.sort(comparator.reversed());
		} else {
			ServiceModule.services.sort(comparator);
		}
	}
	
	public void registerServices(Plugin plugin) {
		this.sortServices(true);
		for (Service service : ServiceModule.services) {
			if (service.getPlugin() == plugin) {
				service.setRegistered(true);
			}
		}
	}
	
	public void unregisterServices(Plugin plugin) {
		this.sortServices(false);
		for (Service service : ServiceModule.services) {
			if (service.getPlugin() == plugin) {
				service.setRegistered(false);
			}
		}
	}
	
	private void registerServices() {
		this.sortServices(true);
		for (Service service : ServiceModule.services) {
			service.setRegistered(true);
		}
	}
	
	private void unregisterServices() {
		this.sortServices(false);
		for (Service service : ServiceModule.services) {
			service.setRegistered(false);
		}
	}
	
	public boolean isExists(Service service) {
		for (Service services : ServiceModule.services){
			if (services == service || services.getClass().equals(service.getClass())) {
				return true;
			}
		}
		return false;
	}
	
	public void addService(Service service) {
		if (this.isExists(service)) {
			return;
		}
		ServiceModule.services.add(service);
	}
	
	public void addServices(List<Service> services) {
		for (Service service : services) {
			this.addService(service);
		}
	}
	
	public void addServices(Service... services) {
		for (Service service : services) {
			this.addService(service);
		}
	}
	
	public void clearServices(Plugin plugin) {
		ServiceModule.services.removeIf(service -> service.getPlugin() == plugin);
	}
	
	private void clearServices() {
		ServiceModule.services.clear();
	}
	
	public List<Service> getServices(Plugin plugin) {
		return ServiceModule.services.stream().filter(service -> service.getPlugin() == plugin).toList();
	}
	
	public List<Service> getServices() {
		return Collections.unmodifiableList(ServiceModule.services);
	}
	
	@Nullable
	public static <T extends Service> Service getRawService(Class<T> serviceClass) {
		if (serviceClass.equals(Service.class)) {
			throw new RuntimeException("Service.class");
		}
		
		for (Service service : ServiceModule.services) {
			if (service.getClass().equals(serviceClass)) {
				return service;
			}
		}
		
		return null;
	}
	
	@Nullable
	public static <T extends Service> T getService(Class<T> serviceClass) {
		Service rawService = getRawService(serviceClass);
		return rawService == null ? null : serviceClass.cast(rawService);
	}
}
