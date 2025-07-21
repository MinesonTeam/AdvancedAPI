package kz.hxncus.mc.advancedapi.annotation.processor;

import kz.hxncus.mc.advancedapi.annotation.*;
import kz.hxncus.mc.advancedapi.utility.ArrayUtil;
import kz.hxncus.mc.advancedapi.utility.InventoryUtil;
import kz.hxncus.mc.advancedapi.utility.ReflectionUtil;
import lombok.experimental.UtilityClass;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class InventoryProcessor {
    private final Map<org.bukkit.inventory.Inventory, Object> inventoryObjects = new HashMap<>();
    private final Map<Object, Method[]> objectMethods = new HashMap<>();

    public void process(Class<?> clazz, Object obj) {
        if (!clazz.isAnnotationPresent(Inventory.class)) {
            return;
        }
        Inventory inventory = clazz.getAnnotation(Inventory.class);
        org.bukkit.inventory.Inventory bukkitInventory = InventoryUtil.createInventory(null, inventory.type(), inventory.size(), inventory.value());
        inventoryObjects.putIfAbsent(bukkitInventory, obj);
        objectMethods.putIfAbsent(obj, clazz.getDeclaredMethods());
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType() == org.bukkit.inventory.Inventory.class) {
                ReflectionUtil.setField(field, null, bukkitInventory);
            } else if (field.isAnnotationPresent(InventoryItem.class)) {
                int[] slots = field.getAnnotation(InventoryItem.class).value();
                for (int slot : slots) {
                    if (slot < 0 || slot >= inventory.size()) {
                        continue;
                    }
                    bukkitInventory.setItem(slot, ReflectionUtil.getFieldValue(field, obj));
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent event) {
        Object obj = inventoryObjects.get(event.getInventory());
        if (obj == null) {
            return;
        }
        for (Method method : objectMethods.get(obj)) {
            InventoryOpen inventoryOpen = method.getAnnotation(InventoryOpen.class);
            if (inventoryOpen == null) {
                continue;
            }
            if (!inventoryOpen.ignoreCancelled() && event.isCancelled()) {
                continue;
            }
            ReflectionUtil.invoke(method, obj, event);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Object obj = inventoryObjects.get(event.getInventory());
        if (obj == null) {
            return;
        }
        for (Method method : objectMethods.get(obj)) {
            InventoryClose inventoryClose = method.getAnnotation(InventoryClose.class);
            if (inventoryClose == null) {
                continue;
            }
            ReflectionUtil.invoke(method, obj, event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        Object obj = inventoryObjects.get(event.getInventory());
        if (obj == null) {
            return;
        }
        for (Method method : objectMethods.get(obj)) {
            InventoryClick inventoryClick = method.getAnnotation(InventoryClick.class);
            if (inventoryClick == null) {
                continue;
            }
            if (!inventoryClick.ignoreCancelled() && event.isCancelled()) {
                continue;
            }
            int[] slots = inventoryClick.value();
            if (slots.length != 0 && !ArrayUtil.contains(slots, event.getSlot())) {
                return;
            }
            ClickType[] clickTypes = inventoryClick.clickTypes();
            if (clickTypes.length != 0 && !ArrayUtil.contains(clickTypes, event.getClick())) {
                return;
            }
            ReflectionUtil.invoke(method, obj, event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event) {
        Object obj = inventoryObjects.get(event.getInventory());
        if (obj == null) {
            return;
        }
        for (Method method : objectMethods.get(obj)) {
            InventoryDrag inventoryDrag = method.getAnnotation(InventoryDrag.class);
            if (inventoryDrag == null) {
                continue;
            }
            if (!inventoryDrag.ignoreCancelled() && event.isCancelled()) {
                continue;
            }
            ReflectionUtil.invoke(method, obj, event);
        }
    }
}
