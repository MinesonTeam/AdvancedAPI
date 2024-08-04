package kz.hxncus.mc.minesonapi;

import kz.hxncus.mc.minesonapi.bukkit.block.PlacableBlock;
import kz.hxncus.mc.minesonapi.bukkit.command.SimpleCommand;
import kz.hxncus.mc.minesonapi.bukkit.command.argument.StringArgument;
import kz.hxncus.mc.minesonapi.bukkit.event.EventManager;
import kz.hxncus.mc.minesonapi.bukkit.event.PlayerJumpEvent;
import kz.hxncus.mc.minesonapi.bukkit.event.PlayerLeftClickEvent;
import kz.hxncus.mc.minesonapi.bukkit.event.PlayerRightClickEvent;
import kz.hxncus.mc.minesonapi.bukkit.inventory.InventoryManager;
import kz.hxncus.mc.minesonapi.bukkit.inventory.SimpleInventory;
import kz.hxncus.mc.minesonapi.bukkit.item.SimpleItem;
import kz.hxncus.mc.minesonapi.bukkit.scheduler.Scheduler;
import kz.hxncus.mc.minesonapi.bukkit.server.ServerManager;
import kz.hxncus.mc.minesonapi.bukkit.workload.WorkloadRunnable;
import kz.hxncus.mc.minesonapi.bukkit.world.WorldManager;
import kz.hxncus.mc.minesonapi.color.ColorManager;
import kz.hxncus.mc.minesonapi.config.ConfigManager;
import kz.hxncus.mc.minesonapi.util.VectorUtil;
import kz.hxncus.mc.minesonapi.util.tuples.Pair;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.UUID;

@Getter
public final class MinesonAPI extends JavaPlugin {
    private static MinesonAPI instance;
    private ColorManager colorManager;
    private ConfigManager configManager;
    private EventManager eventManager;
    private InventoryManager inventoryManager;
    private ServerManager serverManager;
    private WorldManager worldManager;
    private WorkloadRunnable runnable;

    public MinesonAPI() {
        instance = this;
    }

    public static MinesonAPI get() {
        return instance;
    }

    public Location firstPos;
    public Location secondPos;

    @Override
    public void onEnable() {
        new SimpleCommand("test").withArguments(new StringArgument("player_name"));
        registerManagers();
        runnable = new WorkloadRunnable();
        Scheduler.timer(1L, 1L, runnable);
        SimpleInventory inventory = new SimpleInventory(54).setItem(0, new SimpleItem(Material.ACACIA_LOG).displayName("Next page").addLore("321", "123").apply(), (page, event) -> {
            page.getPage(0).open(event.getWhoClicked());
        });
        inventory.addPage(new SimpleInventory(54).setItem(0, new SimpleItem(Material.ACACIA_BOAT).displayName("Previous page").apply(), (page, event) -> {
            page.getPage(0).open(event.getWhoClicked());
        }));
        eventManager.register(PlayerLeftClickEvent.class,event -> {
            getLogger().info("left click event");
            Player player = event.getPlayer();
            if (player.getInventory().getItemInMainHand().getType() == Material.WOODEN_AXE) {
                firstPos = event.getBlockClicked().getLocation();
                player.sendMessage("First position set");
            }
        });
        eventManager.register(PlayerRightClickEvent.class, event -> {
            Player player = event.getPlayer();
            if (player.getInventory().getItemInMainHand().getType() == Material.WOODEN_AXE) {
                player.sendMessage("Second position set");
                secondPos = event.getBlockClicked().getLocation();
            }
        });
        eventManager.register(PlayerJumpEvent.class, event -> {
            if (firstPos == null || secondPos == null) {
                return;
            }
            Player player = event.getPlayer();
            player.sendMessage("Jumped");
            UUID uid = firstPos.getWorld().getUID();
            Pair<Vector, Vector> minAndMaxVector = VectorUtil.getMinAndMaxVector(firstPos, secondPos);
            Vector min = minAndMaxVector.getLeft();
            Vector max = minAndMaxVector.getRight();
            for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
                for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                    for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                        runnable.add(new PlacableBlock(uid, x, y, z, Material.STONE));
                    }
                }
            }
        });
    }

    @Override
    public void onDisable() {
        InventoryManager.closeAll();
        EventManager.unregisterAll();
        Scheduler.stopAllTimers();
    }

    public void registerManagers() {
        this.colorManager = new ColorManager();
        this.configManager = new ConfigManager(this);
        this.eventManager = new EventManager(this);
        this.worldManager = new WorldManager(this);
        this.inventoryManager = new InventoryManager(this);
        this.serverManager = new ServerManager(this, getServer());
    }
}
