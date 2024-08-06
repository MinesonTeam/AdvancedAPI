package kz.hxncus.mc.minesonapi.color.caching;

import lombok.Getter;

import java.util.Objects;

@Getter
public final class LruElement {
	private final String input;
	private final String result;
	
	public LruElement(final String input, final String result) {
		this.input = input;
		this.result = result;
	}
	
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object input = this.input();
		result = result * 59 + ((input == null) ? 43 : input.hashCode());
		final Object $result = this.result();
		return result * 59 + (($result == null) ? 43 : $result.hashCode());
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof final LruElement other))
			return false;
		if (!other.canEqual(this))
			return false;
		final Object thisInput = this.input();
		final Object otherInput = other.input();
		if (!Objects.equals(thisInput, otherInput))
			return false;
		final Object thisResult = this.result();
		final Object otherResult = other.result();
		return Objects.equals(thisResult, otherResult);
	}
	
	private boolean canEqual(final Object other) {
		return other instanceof LruElement;
	}
	
	public String toString() {
		return "LruElement(input=" + this.input() + ", result=" + this.result() + ")";
	}
	
	public String input() {
		return this.input;
	}
	
	public String result() {
		return this.result;
	}
	
	
}
