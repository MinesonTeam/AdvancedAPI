package kz.hxncus.mc.minesonapi.configuration;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Placeholders {
    private Placeholders() {
    }

    private static final Pattern EXACTLY_MATCHES = Pattern.compile("^\\{(?!_)[A-Z\\d_]+(?<!_)}$");
    private static final Pattern LOWERCASE = Pattern.compile("^(?!-)[a-z\\d-]+(?<!-)$");
    private static final Pattern UPPERCASE = Pattern.compile("^(?!_)[A-Z\\d_]+(?<!_)$");

    static final Map<Integer, String[]> INTEGER_MAP = new HashMap<>();

    public static String[] getPlaceholders(Object value) {
        int hashCode = System.identityHashCode(value);
        if (!INTEGER_MAP.containsKey(hashCode)) {
            throw new IllegalStateException("Invalid input");
        }
        return INTEGER_MAP.get(hashCode);
    }

    public static int addPlaceholders(Object value, String... placeholders) {
        int hashCode = System.identityHashCode(value);
        Placeholders.INTEGER_MAP.put(hashCode, Stream.of(placeholders)
                                                     .map(Placeholders::toPlaceholderName)
                                                     .toArray(String[]::new));
        return hashCode;
    }

    public static void removePlaceholders(Object value) {
        removePlaceholders(System.identityHashCode(value));
    }

    public static void removePlaceholders(int hash) {
        INTEGER_MAP.remove(hash);
    }

    public static boolean hasPlaceholders(Object value) {
        return INTEGER_MAP.containsKey(System.identityHashCode(value));
    }

    public static String replace(String value, Object... values) {
        String[] placeholders = getPlaceholders(value);
        String stringValue = value;
        for (int i = 0; i < Math.min(placeholders.length, values.length); i++) {
            stringValue = stringValue.replace(toPlaceholderName(placeholders[i]), String.valueOf(values[i]));
        }
        return stringValue;
    }

    private static String toPlaceholderName(String name) {
        if (EXACTLY_MATCHES.matcher(name)
                           .matches()) {
            return name;
        } else if (LOWERCASE.matcher(name)
                            .matches()) {
            return '{' + name.toUpperCase(Locale.ROOT)
                             .replace('-', '_') + '}';
        } else if (UPPERCASE.matcher(name)
                            .matches()) {
            return '{' + name + '}';
        } else {
            throw new IllegalStateException("Invalid placeholder: " + name);
        }
    }

}
