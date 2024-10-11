package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

/**
 * The type Uuid util.
 * @author Hxncus
 * @since  1.0.0
 */
@UtilityClass
public class UUIDUtil {
	/**
	 * The constant EMPTY_UUID.
	 */
	public final UUID EMPTY_UUID = new UUID(0, 0);
	
	/**
	 * Uuid from an int array uuid.
	 *
	 * @param array the array
	 * @return the uuid
	 */
	public UUID uuidFromIntArray(final int[] array) {
		return new UUID((long) array[0] << 32 | array[1] & 4294967295L, (long) array[2] << 32 | array[3] & 4294967295L);
	}
	
	/**
	 * Uuid to int array int [ ].
	 *
	 * @param uuid the uuid
	 * @return the int [ ]
	 */
	public int[] uuidToIntArray(final UUID uuid) {
		final long l = uuid.getMostSignificantBits();
		final long m = uuid.getLeastSignificantBits();
		return leastMostToIntArray(l, m);
	}
	
	private int[] leastMostToIntArray(final long uuidMost, final long uuidLeast) {
		return new int[]{(int) (uuidMost >> 32), (int) uuidMost, (int) (uuidLeast >> 32), (int) uuidLeast};
	}
	
	/**
	 * Uuid to byte array byte [ ].
	 *
	 * @param uuid the uuid
	 * @return the byte [ ]
	 */
	public byte[] uuidToByteArray(final UUID uuid) {
		final byte[] bs = new byte[16];
		ByteBuffer.wrap(bs)
		          .order(ByteOrder.BIG_ENDIAN)
		          .putLong(uuid.getMostSignificantBits())
		          .putLong(uuid.getLeastSignificantBits());
		return bs;
	}
}
