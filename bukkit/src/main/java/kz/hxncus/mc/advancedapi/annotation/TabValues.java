package kz.hxncus.mc.advancedapi.annotation;

import kz.hxncus.mc.advancedapi.api.bukkit.command.provider.TabProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface TabValues {
    Class<? extends TabProvider> value();
}

