package kz.hxncus.mc.advancedapi.annotation;

import org.bukkit.event.inventory.ClickType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface InventoryClick {
    int[] value() default {};
    ClickType[] clickTypes() default {};
    boolean ignoreCancelled() default false;
}
