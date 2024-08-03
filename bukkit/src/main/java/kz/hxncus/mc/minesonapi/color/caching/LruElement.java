package kz.hxncus.mc.minesonapi.color.caching;

import lombok.Getter;

import java.util.Objects;

@Getter
public final class LruElement {
    private final String input;
    private final String result;

    public LruElement(String input, String result) {
        this.input = input;
        this.result = result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof LruElement))
            return false;
        LruElement other = (LruElement) obj;
        if (!other.canEqual(this))
            return false;
        Object thisInput = input();
        Object otherInput = other.input();
        if (!Objects.equals(thisInput, otherInput))
            return false;
        Object thisResult = result();
        Object otherResult = other.result();
        return Objects.equals(thisResult, otherResult);
    }

    private boolean canEqual(Object other) {
        return other instanceof LruElement;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object input = input();
        result = result * 59 + ((input == null) ? 43 : input.hashCode());
        Object $result = result();
        return result * 59 + (($result == null) ? 43 : $result.hashCode());
    }

    public String toString() {
        return "LruElement(input=" + input() + ", result=" + result() + ")";
    }

    public String input() {
        return input;
    }

    public String result() {
        return result;
    }


}
