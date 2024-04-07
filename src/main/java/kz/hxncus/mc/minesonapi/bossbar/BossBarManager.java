package kz.hxncus.mc.minesonapi.bossbar;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import kz.hxncus.mc.minesonapi.MinesonAPI;
import kz.hxncus.mc.minesonapi.bossbar.animation.IBossBar;
import kz.hxncus.mc.minesonapi.listener.EventManager;
import kz.hxncus.mc.minesonapi.listener.PluginDisablingEvent;
import lombok.Getter;
import lombok.NonNull;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class BossBarManager {
    @Getter private final Plugin plugin;
    protected static final Map<String, IBossBar> BOSS_BAR_MAP = new HashMap<>();
    public BossBarManager(@NonNull Plugin plugin) {
        this.plugin = plugin;
        new CommandAPICommand("abossbar").withPermission("minesonapi.bossbar.use").withAliases("abb")
                .withSubcommand(new CommandAPICommand("reload")
                        .withPermission("minesonapi.bossbar.reload")
                        .executes((sender, args) -> {
                            long time = System.currentTimeMillis();
                            sender.sendMessage("Reloaded in " + (System.currentTimeMillis() - time) + "ms.");
                        }))
                .withSubcommand(new CommandAPICommand("list")
                        .withPermission("minesonapi.bossbar.list")
                        .executes((sender, args) -> {
                            sender.sendMessage(Component.text("Boss bars: " + BOSS_BAR_MAP.keySet()));
                }))
                .withSubcommand(new CommandAPICommand("add")
                        .withPermission("minesonapi.bossbar.add")
                        .withArguments(new StringArgument("barName")
                                .replaceSuggestions(ArgumentSuggestions.strings(BOSS_BAR_MAP.keySet())))
                        .withArguments(new PlayerArgument("player"))
                        .executes((sender, args) -> {
                            String barName = args.getUnchecked("barName");
                            if (barName == null || barName.isEmpty() || !BOSS_BAR_MAP.containsKey(barName)) {
                                sender.sendMessage(Component.text("BossBar name not found"));
                                return;
                            }
                            Player player = args.getUnchecked("player");
                            if (player == null) {
                                sender.sendMessage(Component.text("Player not found"));
                                return;
                            }
                            BOSS_BAR_MAP.get(barName).addPlayer(player);
                            sender.sendMessage(Component.text("Added player " + player.getName() + " to " + barName));
                })).register();
        EventManager.getInstance(plugin).register(PluginDisablingEvent.class, EventPriority.LOWEST, event -> {
            if (event.getPlugin() == plugin) {
                BOSS_BAR_MAP.values().stream().filter(bossBar -> bossBar.getPlugin() == plugin).forEach(IBossBar::stopAnimation);
                BOSS_BAR_MAP.values().removeIf(bossBar -> bossBar.getPlugin() == plugin);
            }
        });
    }
    public static @Nullable IBossBar getBossBar(String name) {
        return BOSS_BAR_MAP.get(name);
    }
}
