package kz.hxncus.mc.minesonapi.configuration;

import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.lang.annotation.*;

public class YamlTest {
    @Getter
    private final File file;
    private YamlConfiguration config;
    public YamlTest(Plugin plugin) {
        this.file = new File(plugin.getDataFolder(), "config.yml");

    }
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    protected @interface Create {

    }

    @Target({
            ElementType.FIELD,
            ElementType.TYPE
    })
    @Retention(RetentionPolicy.RUNTIME)
    protected @interface Comment {

        String[] value();

        /**
         * Comment position.
         */
        YamlConfig.Comment.At at() default YamlConfig.Comment.At.PREPEND;

        enum At {

            /**
             * The comment will be placed before the field.
             *
             * <pre> {@code
             *   # Line1
             *   # Line2
             *   regular-field: "regular value"
             * } </pre>
             */
            PREPEND,
            /**
             * The comment will be placed on the same line with the field.
             *
             * <p>The comment text shouldn't have more than one line.
             *
             * <pre> {@code
             *   regular-field: "regular value" # Line1
             * } </pre>
             */
            SAME_LINE,
            /**
             * The comment will be placed after the field.
             *
             * <pre> {@code
             *   regular-field: "regular value"
             *   # Line1
             *   # Line2
             * } </pre>
             */
            APPEND
        }
    }

    @Target({
            ElementType.FIELD,
            ElementType.TYPE
    })
    @Retention(RetentionPolicy.RUNTIME)
    protected @interface Ignore {

    }
}
