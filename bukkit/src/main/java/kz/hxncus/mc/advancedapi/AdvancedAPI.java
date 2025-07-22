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
		if (loaded) {
			getLogger().warning("Попытка повторной загрузки плагина");
			return;
		}
		scanner = new AnnotationScanner(this);
		scanner.load();
		loaded = true;
		getLogger().info("AdvancedAPI loaded.");
	}
	
	@Override
	public void onEnable() {
		if (!this.loaded) {
			getLogger().severe("Плагин не загружен.");
			return;
		}
		scanner.enable();
		getLogger().info("AdvancedAPI enabled.");
	}
	
	@Override
	public void onDisable() {
		if (!this.loaded) {
			return;
		}
		scanner.disable();
		getLogger().info("AdvancedAPI disabled.");
	}
}
