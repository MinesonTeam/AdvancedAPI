package kz.hxncus.mc.advancedapi.bukkit.player;

import kz.hxncus.mc.advancedapi.api.data.memento.AbstractMemento;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.Scoreboard;

@Getter
public class PlayerMemento extends AbstractMemento<Player> {
    private final double health;

    private final int food;
    private final float saturation;

    private final int level;
    private final float exp;

    private final ItemStack[] armor;
    private final ItemStack[] inventory;

    private final Scoreboard scoreboard;

    private final Location compassTarget;

    private final GameMode gameMode;

    public PlayerMemento(Player target) {
        super(target);

        this.health = target.getHealth();

        this.food = target.getFoodLevel();
        this.saturation = target.getSaturation();

        this.level = target.getLevel();
        this.exp = target.getExp();

        this.scoreboard = target.getScoreboard();

        this.compassTarget = target.getCompassTarget();

        this.armor = target.getInventory().getArmorContents();
        this.inventory = target.getInventory().getContents();

        this.gameMode = target.getGameMode();
    }

    @Override
    public void apply() {
        Player target = this.getTarget();
        target.setHealth(health);
        
        target.setFoodLevel(food);
        target.setSaturation(saturation);

        target.setLevel(level);
        target.setExp(exp);

        if (scoreboard != null) {
            target.setScoreboard(scoreboard);
        }
        if (compassTarget != null) {
            target.setCompassTarget(compassTarget);
        }

        PlayerInventory inv = target.getInventory();
        inv.setArmorContents(armor);
        inv.setContents(inventory);
        target.updateInventory();

        target.setGameMode(gameMode);
    }
}
