package kz.hxncus.mc.advancedapi.bukkit.bossbar;

import lombok.NonNull;
import lombok.ToString;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import kz.hxncus.mc.advancedapi.utility.ColorUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Simple boss bar.
 * @author Hxncus
 * @since  1.0.1
 */
@ToString
public class AdvancedBossBar {
	private final List<BossBar> bossBars = new ArrayList<>();
	private final List<Player> players = new ArrayList<>();
	private int currentIndex = 0;
	
	/**
	 * Создает новый BossBar
	 * @param title заголовок
	 * @param color цвет
	 * @param style стиль
	 * @return созданный BossBar
	 */
	public BossBar createBossBar(@NonNull String title, @NonNull BarColor color, @NonNull BarStyle style) {
		BossBar bossBar = Bukkit.createBossBar(ColorUtil.process(title), color, style);
		this.bossBars.add(bossBar);
		this.players.forEach(bossBar::addPlayer);
		return bossBar;
	}
	
	/**
	 * Добавляет игрока
	 * @param player игрок
	 */
	public void addPlayer(@NonNull Player player) {
		if (!this.players.contains(player)) {
			this.players.add(player);
			this.bossBars.forEach(bar -> bar.addPlayer(player));
		}
	}
	
	/**
	 * Удаляет игрока
	 * @param player игрок
	 */
	public void removePlayer(@NonNull Player player) {
		this.players.remove(player);
		this.bossBars.forEach(bar -> bar.removePlayer(player));
	}
	
	/**
	 * Обновляет заголовок текущего BossBar
	 * @param title новый заголовок
	 */
	public void updateTitle(@NonNull String title) {
		if (!this.bossBars.isEmpty()) {
			this.bossBars.get(currentIndex).setTitle(ColorUtil.process(title));
		}
	}
	
	/**
	 * Обновляет прогресс текущего BossBar
	 * @param progress новый прогресс (0.0 - 1.0)
	 */
	public void updateProgress(double progress) {
		if (!this.bossBars.isEmpty()) {
			this.bossBars.get(currentIndex).setProgress(Math.min(1.0, Math.max(0.0, progress)));
		}
	}
}
