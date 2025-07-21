package kz.hxncus.mc.advancedapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Hologram {
    /**
     * Уникальный ID голограммы.
     */
    String id();

    /**
     * Координаты голограммы.
     */
    String world() default "world";
    double x() default 0;
    double y() default 0;
    double z() default 0;

    /**
     * Автообновление: интервал в тиках. 0 = не обновлять.
     */
    int updateInterval() default 0;
}
