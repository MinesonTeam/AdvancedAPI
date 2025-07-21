package kz.hxncus.mc.advancedapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandConfirm {
    int value() default 10;
    String message() default "Write the command again to confirm.";
}
