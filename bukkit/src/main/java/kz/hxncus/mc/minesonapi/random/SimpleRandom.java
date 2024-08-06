package kz.hxncus.mc.minesonapi.random;

import lombok.ToString;

import java.io.NotSerializableException;
import java.io.Serial;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.random.RandomGenerator;

/**
 * The type Simple random.
 */
@ToString
public class SimpleRandom extends Random {
	@Serial
	private static final long serialVersionUID = 1L;
	private static final long[] JUMP = {-2337365368286915419L, 1659688472399708668L};
	private static final long[] LONG_JUMP = {-3266927057705177477L, -2459076376072127807L};
	private static SimpleRandom instance;
	private long s0;
	private long s1;
	
	/**
	 * Instantiates a new Simple random.
	 *
	 * @param s0 the s 0
	 * @param s1 the s 1
	 */
	protected SimpleRandom(final long s0, final long s1) {
		this.s0 = s0;
		this.s1 = s1;
	}
	
	/**
	 * Instantiates a new Simple random.
	 */
	protected SimpleRandom() {
		this(RandomGenerator.getDefault().nextLong());
	}
	
	/**
	 * Instantiates a new Simple random.
	 *
	 * @param seed the seed
	 */
	protected SimpleRandom(final long seed) {
		this.setSeed(seed);
	}
	
	public void setSeed(final long seed) {
		final RandomGenerator random = new SplittableRandom(seed);
		this.s0 = random.nextLong();
		this.s1 = random.nextLong();
	}
	
	public void nextBytes(final byte[] bytes) {
		int i = bytes.length;
		while (i != 0) {
			int n = Math.min(i, 8);
			
			for (long bits = this.nextLong(); n != 0; bits >>= 8) {
				n--;
				--i;
				bytes[i] = (byte) bits;
			}
		}
		
	}
	
	public int nextInt() {
		return (int) (this.nextLong() >>> 32);
	}
	
	/**
	 * Next int int.
	 *
	 * @param l the l
	 * @return the int
	 */
	public int nextInt(final long l) {
		return (int) this.nextLong(l);
	}
	
	public long nextLong() {
		final long s0 = this.s0;
		long s1 = this.s1;
		final long result = s0 + s1;
		s1 ^= s0;
		this.s0 = Long.rotateLeft(s0, 24) ^ s1 ^ s1 << 16;
		this.s1 = Long.rotateLeft(s1, 37);
		return result;
	}
	
	public boolean nextBoolean() {
		return this.nextLong() < 0L;
	}
	
	public float nextFloat() {
		return (this.nextLong() >>> 40) * 5.9604645E-8F;
	}
	
	public double nextDouble() {
		return (this.nextLong() >>> 11) * 1.1102230246251565E-16;
	}
	
	@Override
	public int nextInt(final int origin, final int bound) {
		return super.nextInt(bound - origin) + origin;
	}
	
	public long nextLong(final long bound) {
		if (bound <= 0L) {
			throw new IllegalArgumentException("illegal bound " + bound + " (must be positive)");
		} else {
			long t = this.nextLong();
			final long nMinus1 = bound - 1L;
			if ((bound & nMinus1) == 0L) {
				return t >>> Long.numberOfLeadingZeros(nMinus1) & nMinus1;
			} else {
				for (long u = t >>> 1; u + nMinus1 - (t = u % bound) < 0L; u = this.nextLong() >>> 1) {
				}
				
				return t;
			}
		}
	}
	
	/**
	 * Copy simple random.
	 *
	 * @return the simple random
	 */
	public SimpleRandom copy() {
		return new SimpleRandom(this.s0, this.s1);
	}
	
	/**
	 * Next double fast double.
	 *
	 * @return the double
	 */
	public double nextDoubleFast() {
		return Double.longBitsToDouble(4607182418800017408L | this.nextLong() >>> 12) - 1.0;
	}
	
	/**
	 * Jump simple random.
	 *
	 * @return the simple random
	 */
	public SimpleRandom jump() {
		return this.jump(JUMP);
	}
	
	private SimpleRandom jump(final long[] jump) {
		long s0 = 0L;
		long s1 = 0L;
		
		for (final long l : jump) {
			for (int i = 0; i < 64; ++i) {
				if ((l & 1L << i) != 0L) {
					s0 ^= this.s0;
					s1 ^= this.s1;
				}
				
				this.nextLong();
			}
		}
		
		this.s0 = s0;
		this.s1 = s1;
		return this;
	}
	
	/**
	 * Long jump simple random.
	 *
	 * @return the simple random
	 */
	public SimpleRandom longJump() {
		return this.jump(LONG_JUMP);
	}
	
	/**
	 * Get simple random.
	 *
	 * @return the simple random
	 */
	public static SimpleRandom get() {
		// Техника, которую мы здесь применяем, называется «блокировка с двойной
		// проверкой» (Double-Checked Locking). Она применяется, чтобы
		// предотвратить создание нескольких объектов-одиночек, если метод будет
		// вызван из нескольких потоков одновременно.
		//
		// Хотя переменная `result` вполне оправданно кажется здесь лишней, она
		// помогает избежать подводных камней реализации DCL в Java.
		//
		// Больше об этой проблеме можно почитать здесь:
		// https://ru.wikipedia.org/wiki/%D0%91%D0%BB%D0%BE%D0%BA%D0%B8%D1%80%D0%BE%D0%B2%D0%BA%D0%B0_%D1%81_%D0%B4%D0%B2%D0%BE%D0%B9%D0%BD%D0%BE%D0%B9_%D0%BF%D1%80%D0%BE%D0%B2%D0%B5%D1%80%D0%BA%D0%BE%D0%B9
		final SimpleRandom result = instance;
		if (result != null) {
			return result;
		}
		synchronized (SimpleRandom.class) {
			if (instance == null) {
				instance = new SimpleRandom();
			}
			return instance;
		}
	}
	
	/**
	 * Sets state.
	 *
	 * @param state the state
	 */
	public void setState(final long[] state) {
		if (state.length == 2) {
			this.s0 = state[0];
			this.s1 = state[1];
		} else {
			throw new IllegalArgumentException("The argument array contains " + state.length + " longs instead of " + 2);
		}
	}
	
	@Serial
	private void readObject(final java.io.ObjectInputStream in) throws ClassNotFoundException, NotSerializableException {
		throw new NotSerializableException("kz.hxncus.mc.minesonapi.random.SimpleRandom");
	}
	
	@Serial
	private void writeObject(final java.io.ObjectOutputStream out) throws NotSerializableException {
		throw new NotSerializableException("kz.hxncus.mc.minesonapi.random.SimpleRandom");
	}
}
