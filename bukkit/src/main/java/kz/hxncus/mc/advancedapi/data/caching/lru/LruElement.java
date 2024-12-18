package kz.hxncus.mc.advancedapi.data.caching.lru;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Class Lru element.
 *
 * @author Hxncus
 * @since 1.0.0
 */
@Getter
@ToString
@EqualsAndHashCode
public class LruElement {
	private final String input;
	private final String result;
	
	/**
	 * Instantiates a new Lru element.
	 *
	 * @param input  the input
	 * @param result the result
	 */
	public LruElement(final String input, final String result) {
		this.input = input;
		this.result = result;
	}
	
	private boolean canEqual(final Object other) {
		return other instanceof LruElement;
	}
	
	/**
	 * Input string.
	 *
	 * @return the string
	 */
	public String input() {
		return this.input;
	}
	
	/**
	 * Result string.
	 *
	 * @return the string
	 */
	public String result() {
		return this.result;
	}
	
	
}
