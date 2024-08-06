package kz.hxncus.mc.minesonapi.config;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public enum Messages {
	PREFIX("general.prefix");
	
	private final String path;
	
	Messages(final String path) {
		this.path = path;
	}
	
	@NonNull
	public String toPath() {
		return this.path;
	}
	
	@Override
	public String toString() {
		return this.process(this.getValue()
		                        .toString());
	}
	
	public String toString(final String def) {
		return this.process(this.getValue(def)
		                        .toString());
	}
	
	public String process(final String message) {
		return this.colorize(this.format(message));
	}
	
	@NonNull
	public Object getValue(final Object def) {
		return this.getLanguages()
		           .get(this.path, def);
	}
	
	private String colorize(final String message) {
		return MinesonAPI.getInstance()
		                 .getColorManager()
		                 .process(message);
	}
	
	private String format(String message, final Object... args) {
		for (int i = 0; i < args.length; i++) {
			message = message.replace("{" + i + "}", args[i].toString());
		}
		return message.replace("{PREFIX}", this.colorize(PREFIX.getValue()
		                                                       .toString()));
	}
	
	public YamlConfiguration getLanguages() {
		return MinesonAPI.getInstance()
		                 .getConfigManager()
		                 .getOrCreateConfig("languages.yml");
	}
	
	@NonNull
	public Object getValue() {
		final Object obj = this.getLanguages()
		                       .get(this.path);
		return obj == null ? "" : obj;
	}
	
	public String toString(final Object... args) {
		return this.process(this.getValue()
		                        .toString(), args);
	}
	
	public List<String> toStringList() {
		final List<String> stringList = this.getLanguages()
		                                    .getStringList(this.path);
		stringList.replaceAll(this::process);
		return stringList;
	}
	
	public String process(final String message, final Object... args) {
		return this.colorize(this.format(message, args));
	}
	
	private void send(final CommandSender sender, final String msg, final Object... args) {
		// No need to send an empty or null message.
		if (msg == null || msg.isEmpty()) {
			return;
		}
		sender.sendMessage(this.process(msg, args));
	}
	
	private void sendList(final CommandSender sender, final Object... args) {
		for (final String str : this.toStringList()) {
			this.send(sender, str, args);
		}
	}
	
	public void send(final CommandSender sender, final Object... args) {
		if (this.getValue() instanceof List<?>) {
			this.sendList(sender, args);
		} else {
			this.send(sender, this.toString(), args);
		}
	}
	
	public void log(final Object... args) {
		if (this.getValue() instanceof List<?>) {
			this.sendList(Bukkit.getConsoleSender(), args);
		} else {
			this.send(Bukkit.getConsoleSender(), this.toString(), args);
		}
	}
}
