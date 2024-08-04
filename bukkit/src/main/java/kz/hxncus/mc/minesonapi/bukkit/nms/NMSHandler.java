package kz.hxncus.mc.minesonapi.bukkit.nms;

import java.lang.reflect.InvocationTargetException;

public class NMSHandler {
    public static NMSChunk chunk;

    public static NMSChunk getChunk() {
        if (chunk != null) {
            return chunk;
        }
        try {
            return (NMSChunk) Class.forName("kz.hxncus.mc.minesonapi.nms.Chunk_1_20_R3").getConstructor().newInstance();
        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException |
                 InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
