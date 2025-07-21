package kz.hxncus.mc.advancedapi.annotation;

import org.bukkit.event.inventory.InventoryType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Inventory {
    String value() default "";
    int size() default 54;
    InventoryType type() default InventoryType.CHEST;
}
