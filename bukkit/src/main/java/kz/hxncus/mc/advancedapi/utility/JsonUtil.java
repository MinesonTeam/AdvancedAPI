package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtil {
    public String escapeJson(String input) {
        return input.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }
}
