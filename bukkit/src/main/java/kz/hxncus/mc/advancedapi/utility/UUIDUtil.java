package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * The type Uuid util.
 * @author Hxncus
 * @since  1.0.0
 */
@UtilityClass
public final class UUIDUtil {
	/**
	 * The constant EMPTY_UNIQUE_ID.
	 */
	public final UUID EMPTY_UNIQUE_ID = new UUID(0, 0);
	
	/**
	 * Unique id from an int array unique id.
	 *
	 * @param array the array
	 * @return the unique id
	 */
	public UUID uniqueIdFromIntArray(final int[] array) {
		return new UUID((long) array[0] << 32 | array[1] & 4294967295L, (long) array[2] << 32 | array[3] & 4294967295L);
	}
	
	/**
	 * Unique id to int array int [ ].
	 *
	 * @param uniqueId the unique id
	 * @return the int [ ]
	 */
	public int[] uniqueIdToIntArray(final UUID uniqueId) {
		final long l = uniqueId.getMostSignificantBits();
		final long m = uniqueId.getLeastSignificantBits();
		return leastMostToIntArray(l, m);
	}
	
	private int[] leastMostToIntArray(final long uuidMost, final long uuidLeast) {
		return new int[]{(int) (uuidMost >> 32), (int) uuidMost, (int) (uuidLeast >> 32), (int) uuidLeast};
	}
	
	/**
	 * Unique id to byte array byte [ ].
	 *
	 * @param uniqueId the unique id
	 * @return the byte [ ]
	 */
	public byte[] uniqueIdToByteArray(final UUID uniqueId) {
		final byte[] bs = new byte[16];
		ByteBuffer.wrap(bs)
		          .order(ByteOrder.BIG_ENDIAN)
		          .putLong(uniqueId.getMostSignificantBits())
		          .putLong(uniqueId.getLeastSignificantBits());
		return bs;
	}
	
	/**
	 * Generate unique id until unique id.
	 *
	 * @param until the until
	 * @return the unique id
	 */
	public UUID generateUniqueIdUntil(Predicate<UUID> until) {
		UUID uniqueId;
		do {
			uniqueId = UUID.randomUUID();
		} while (until.test(uniqueId));
		return uniqueId;
	}

	public UUID generateUniqueId() {
		return UUIDUtil.generateUniqueIdUntil(uniqueId -> false);
	}
}
