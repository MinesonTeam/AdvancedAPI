package kz.hxncus.mc.minesonapi.util;

import lombok.experimental.UtilityClass;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

@UtilityClass
public class UUIDUtil {
    public static final UUID EMPTY_UUID = new UUID(0, 0);

    public UUID uuidFromIntArray(int[] array) {
        return new UUID((long) array[0] << 32 | array[1] & 4294967295L, (long) array[2] << 32 | array[3] & 4294967295L);
    }

    public int[] uuidToIntArray(UUID uuid) {
        long l = uuid.getMostSignificantBits();
        long m = uuid.getLeastSignificantBits();
        return leastMostToIntArray(l, m);
    }

    public byte[] uuidToByteArray(UUID uuid) {
        byte[] bs = new byte[16];
        ByteBuffer.wrap(bs).order(ByteOrder.BIG_ENDIAN).putLong(uuid.getMostSignificantBits()).putLong(uuid.getLeastSignificantBits());
        return bs;
    }

    private static int[] leastMostToIntArray(long uuidMost, long uuidLeast) {
        return new int[]{(int) (uuidMost >> 32), (int) uuidMost, (int) (uuidLeast >> 32), (int) uuidLeast};
    }
}
