package kz.hxncus.mc.advancedapi.service;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import kz.hxncus.mc.advancedapi.api.module.AbstractModule;
import kz.hxncus.mc.advancedapi.api.service.Service;
import kz.hxncus.mc.advancedapi.bukkit.event.EventService;
import kz.hxncus.mc.advancedapi.bukkit.inventory.InventoryService;
import kz.hxncus.mc.advancedapi.bukkit.minigame.party.PartyService;
import kz.hxncus.mc.advancedapi.bukkit.world.WorldService;
import lombok.NonNull;

import org.bukkit.plugin.Plugin;

import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ServiceModule extends AbstractModule {
	private static AdvancedAPI plugin;
	private static final List<Service> services = new ArrayList<>();
	
	public ServiceModule(final AdvancedAPI plugin) {
		super("service");
		ServiceModule.plugin = plugin;
	}
	
	@Override
	public void onEnable() {
		this.addDefaultServices();
		this.registerServices(plugin);
	}
	
	@Override
	public void onDisable() {
		this.unregisterServices();
		this.clearServices();
	}
	
	private void addDefaultServices() {
		this.addServices(new EventService(plugin), new WorldService(plugin), new InventoryService(plugin), 
		    new PartyService(plugin));
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
	
	public void addService(@NonNull Service service) {
		if (this.isExists(service)) {
			return;
		}
		ServiceModule.services.add(service);
	}
	
	public void addServices(@NonNull List<? extends Service> services) {
		for (Service service : services) {
			this.addService(service);
		}
	}
	
	public void addServices(@NonNull Service... services) {
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
	
	public List<? extends Service> getServices(Plugin plugin) {
		return ServiceModule.services.stream().filter(service -> service.getPlugin() == plugin).toList();
	}
	
	public List<? extends Service> getServices() {
		return Collections.unmodifiableList(ServiceModule.services);
	}
	
	public static <T extends Service> Optional<T> getRawService(Class<T> serviceClass) {
		if (serviceClass.equals(Service.class)) {
			throw new RuntimeException("Service.class");
		}
		
		for (Service service : ServiceModule.services) {
			if (service.getClass().equals(serviceClass)) {
				return Optional.fromNullable(serviceClass.cast(service));
			}
		}
		
		return Optional.absent();
	}
	
	public static <T extends Service> Optional<T> getService(Class<T> serviceClass) {
		return getRawService(serviceClass).transform(serviceClass::cast);
	}
}
