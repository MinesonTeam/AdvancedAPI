package kz.hxncus.mc.advancedapi;

import kz.hxncus.mc.advancedapi.api.bukkit.command.argument.ArgumentType;
import kz.hxncus.mc.advancedapi.bukkit.command.AdvancedCommand;
import kz.hxncus.mc.advancedapi.bukkit.command.argument.StringArgument;
import kz.hxncus.mc.advancedapi.bukkit.scheduler.AdvancedScheduler;
import kz.hxncus.mc.advancedapi.module.ModuleService;
import kz.hxncus.mc.advancedapi.service.ServiceModule;
import kz.hxncus.mc.advancedapi.utility.CommandUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
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
	
	@NonNull private ModuleService moduleService;
	@NonNull private ServiceModule serviceModule;
	
	private boolean isLoaded = false;
	
	@Override
	public void onLoad() {
		if (this.isLoaded) {
			return;
		}
		instance = this;
		
		this.moduleService = new ModuleService(this);
		this.serviceModule = new ServiceModule(this);
		
		this.moduleService.addModule(this.serviceModule);
		this.serviceModule.addService(this.moduleService);

		this.isLoaded = true;
	}
	
	@Override
	public void onEnable() {
		if (!this.isLoaded) {
			throw new RuntimeException("Plugin not loaded yet!");
		}

		new AdvancedCommand("ebat")
			.argument(new StringArgument("test").setSuggestions(List.of("test", "aaa")))
			.subCommands(new AdvancedCommand("apopa")
				.complete((sender, command, alias, args) -> {
					return ArgumentType.ADVANCEMENT.getList((Player) sender);
				})
				.execute((sender, command, label, args) -> {
					sender.sendMessage("apopaaaaaaaa: " + Arrays.toString(args.getArgs()));
				}),
			new AdvancedCommand("pipa")
				.complete((sender, command, alias, args) -> {
					return Arrays.asList("pipa1", "pipa2");
				})
				.execute((sender, command, label, args) -> {
					sender.sendMessage("pipa: " + Arrays.toString(args.getArgs()));
				})
			)
			.complete((sender, command, alias, args) -> {
				return Arrays.asList("AAA321", "EEE123");
			})
			.execute((sender, command, label, args) -> {
				sender.sendMessage("jopa: " + Arrays.toString(args.getArgs()));
			})
			.register();
		
		this.serviceModule.setEnabled(true);
		this.moduleService.register();
	}
	
	@Override
	public void onDisable() {
		if (!this.isLoaded) {
			return;
		}
		
		this.isLoaded = false;
		AdvancedScheduler.cancelPluginTasks(this);
		CommandUtil.unregisterMyCommands();
		this.moduleService.unregister();
		this.serviceModule.onDisable();
		AdvancedAPI.instance = null;
	}
}
