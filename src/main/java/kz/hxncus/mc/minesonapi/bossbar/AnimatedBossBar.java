package kz.hxncus.mc.minesonapi.bossbar;

import kz.hxncus.mc.minesonapi.MinesonAPI;
import kz.hxncus.mc.minesonapi.scheduler.Schedule;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AnimatedBossBar implements BossBar {
    private List<org.bukkit.boss.BossBar> bossBarList;
    private List<Player> playerList;
    private BossBar.AnimationType animationType;
    public AnimatedBossBar() {

    }
    public void addPlayer(Player player) {
        this.bossBarList.forEach(bossBar -> bossBar.addPlayer(player));
        this.playerList.add(player);
    }

    public void removePlayer(Player player) {
        this.bossBarList.forEach(bossBar -> bossBar.removePlayer(player));
        this.playerList.remove(player);
    }

    @Override
    public void setAnimationType(AnimationType type) {
        this.animationType = type;
    }

    @Override
    public void stopAnimation() {

    }

    @Override
    public void startAnimation(Schedule schedule) {
        switch (animationType) {
            case STATIC:
                startStaticAnimation();
                break;
            case PROGRESSIVE:
                startProgressiveAnimation();
                break;
        }
    }

    private void startStaticAnimation() {
        new BukkitRunnable() {
            @Override
            public void run() {

            }
        }.runTaskTimer(MinesonAPI.getPlugin(), 0, 1);
    }

    private void startProgressiveAnimation() {
        new BukkitRunnable() {
            double progress = 0.0;
            boolean increasing = true;

            @Override
            public void run() {
                
            }
        }.runTaskTimer(MinesonAPI.getPlugin(), 0, 1);
    }
}
