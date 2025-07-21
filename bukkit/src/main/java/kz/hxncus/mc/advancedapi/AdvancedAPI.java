package kz.hxncus.mc.advancedapi;

import kz.hxncus.mc.advancedapi.annotation.scanner.AnnotationScanner;
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
	private boolean loaded = false;
	private AnnotationScanner scanner;

	@Override
	public void onLoad() {
		if (this.loaded) {
			this.getLogger().warning("Попытка повторной загрузки плагина");
			return;
		}
		this.scanner = new AnnotationScanner(this);
		this.scanner.load();
		this.loaded = true;
		this.getLogger().info("AdvancedAPI loaded.");
	}
	
	@Override
	public void onEnable() {
		if (!this.loaded) {
			this.getLogger().severe("Плагин не загружен.");
			return;
		}
		this.scanner.enable();
		this.getLogger().info("AdvancedAPI enabled.");
	}
	
	@Override
	public void onDisable() {
		if (!this.loaded) {
			return;
		}
		this.scanner.disable();
		this.getLogger().info("AdvancedAPI disabled.");
	}
}
