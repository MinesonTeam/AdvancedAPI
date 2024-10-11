package kz.hxncus.mc.advancedapi.bukkit.config;

import kz.hxncus.mc.advancedapi.AdvancedAPI;
import kz.hxncus.mc.advancedapi.utility.ColorUtil;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

/**
 * The enum Messages.
 * @author Hxncus
 * @since  1.0.1
 */
public enum Messages {
	PREFIX("general.prefix"), TEST("general.test");
	
	private final String path;
	
	Messages(final String path) {
		this.path = path;
	}
	
	/**
	 * To path string.
	 *
	 * @return the string
	 */
	@NonNull
	public String toPath() {
		return this.path;
	}
	
	@Override
	public String toString() {
		return this.process(this.getValue()
		                        .toString());
	}
	
	/**
	 * To string string.
	 *
	 * @param def the def
	 * @return the string
	 */
	public String toString(final String def) {
		return this.process(this.getValue(def)
		                        .toString());
	}
	
	/**
	 * Process string.
	 *
	 * @param message the message
	 * @return the string
	 */
	public String process(final String message) {
		return this.colorize(this.format(message));
	}
	
	/**
	 * Gets value.
	 *
	 * @param def the def
	 * @return the value
	 */
	@NonNull
	public Object getValue(final Object def) {
		return this.getLanguages()
		           .get(this.path, def);
	}
	
	private String colorize(final String message) {
		return ColorUtil.process(message);
	}
	
	private String format(final String message, final Object... args) {
		String result = message;
		for (int i = 0; i < args.length; i++) {
			result = result.replace("{" + i + "}", args[i].toString());
		}
		return result.replace("{PREFIX}", this.colorize(PREFIX.getValue()
		                                                        .toString()));
	}
	
	/**
	 * Gets languages.
	 *
	 * @return the languages
	 */
	public YamlConfiguration getLanguages() {
		return AdvancedAPI.getInstance()
		                  .getConfigService()
		                  .getOrCreateConfig("languages.yml");
	}
	
	/**
	 * Gets value.
	 *
	 * @return the value
	 */
	@NonNull
	public Object getValue() {
		final Object obj = this.getLanguages()
		                       .get(this.path);
		return obj == null ? "" : obj;
	}
	
	/**
	 * To string string.
	 *
	 * @param args the args
	 * @return the string
	 */
	public String toString(final Object... args) {
		return this.process(this.getValue()
		                        .toString(), args);
	}
	
	/**
	 * To string a list.
	 *
	 * @return the list
	 */
	public List<String> toStringList() {
		final List<String> stringList = this.getLanguages()
		                                    .getStringList(this.path);
		stringList.replaceAll(this::process);
		return stringList;
	}
	
	/**
	 * Process string.
	 *
	 * @param message the message
	 * @param args    the args
	 * @return the string
	 */
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
	
	/**
	 * Send.
	 *
	 * @param sender the sender
	 * @param args   the args
	 */
	public void send(final CommandSender sender, final Object... args) {
		if (this.getValue() instanceof List<?>) {
			this.sendList(sender, args);
		} else {
			this.send(sender, this.toString(), args);
		}
	}
	
	/**
	 * Log.
	 *
	 * @param args the args
	 */
	public void log(final Object... args) {
		if (this.getValue() instanceof List<?>) {
			this.sendList(Bukkit.getConsoleSender(), args);
		} else {
			this.send(Bukkit.getConsoleSender(), this.toString(), args);
		}
	}
}
