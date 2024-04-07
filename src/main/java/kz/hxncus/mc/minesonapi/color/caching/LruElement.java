package kz.hxncus.mc.minesonapi.color.caching;

import java.util.Objects;

public class LruElement {
    private final String input;

    private final String result;

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof LruElement other))
            return false;
        if (!other.canEqual(this))
            return false;
        Object this$input = getInput(), other$input = other.getInput();
        if (!Objects.equals(this$input, other$input))
            return false;
        Object this$result = getResult(), other$result = other.getResult();
        return Objects.equals(this$result, other$result);
    }

    protected boolean canEqual(Object other) {
        return other instanceof LruElement;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object input = getInput();
        result = result * 59 + ((input == null) ? 43 : input.hashCode());
        Object $result = getResult();
        return result * 59 + (($result == null) ? 43 : $result.hashCode());
    }

    public String toString() {
        return "LruElement(input=" + getInput() + ", result=" + getResult() + ")";
    }

    public String getInput() {
        return this.input;
    }

    public String getResult() {
        return this.result;
    }

    public LruElement(String input, String result) {
        this.input = input;
        this.result = result;
    }
}
