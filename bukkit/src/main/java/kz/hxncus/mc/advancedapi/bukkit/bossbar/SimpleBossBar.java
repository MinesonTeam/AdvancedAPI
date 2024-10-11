package kz.hxncus.mc.advancedapi.bukkit.bossbar;

import lombok.ToString;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * The type Simple boss bar.
 * @author Hxncus
 * @since  1.0.1
 */
@ToString
public class SimpleBossBar {
	private List<BossBar> bossBarList;
	private List<Player> playerList;
	
	/**
	 * Add player.
	 *
	 * @param player the player
	 */
	public void addPlayer(final Player player) {
	
	}
	
	/**
	 * Remove player.
	 *
	 * @param player the player
	 */
	public void removePlayer(final Player player) {
	
	}
}
