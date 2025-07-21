package kz.hxncus.mc.advancedapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandCooldown {
    long value(); // Cooldown time in milliseconds.
    String message() default "§cWait %time% seconds before executing this command again.";
}

