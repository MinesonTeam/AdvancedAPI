package kz.hxncus.mc.minesonapi.color.caching;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class LruCache {
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof LruCache other))
            return false;
        if (!other.canEqual(this))
            return false;
        if (getMaxSize() != other.getMaxSize())
            return false;
        Object thisQUE = getQUE();
        Object otherQUE = other.getQUE();
        if (!Objects.equals(thisQUE, otherQUE))
            return false;
        Object thisMAP = getMAP();
        Object otherMAP = other.getMAP();
        return Objects.equals(thisMAP, otherMAP);
    }

    protected boolean canEqual(Object other) {
        return other instanceof LruCache;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + getMaxSize();
        Object QUE = getQUE();
        result = result * 59 + ((QUE == null) ? 43 : QUE.hashCode());
        Object MAP = getMAP();
        return result * 59 + ((MAP == null) ? 43 : MAP.hashCode());
    }

    public String toString() {
        return "LruCache(QUE=" + getQUE() + ", MAP=" + getMAP() + ", maxSize=" + getMaxSize() + ")";
    }

    private final Deque<String> QUE = new LinkedList<>();

    public Deque<String> getQUE() {
        return this.QUE;
    }

    private final Map<String, LruElement> MAP = new ConcurrentHashMap<>();

    private final int maxSize;

    public Map<String, LruElement> getMAP() {
        return this.MAP;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public LruCache(int maxSize) {
        this.maxSize = maxSize;
    }

    public String getResult(String input) {
        if (input != null && this.MAP.containsKey(input)) {
            LruElement curr = this.MAP.get(input);
            synchronized (this.QUE) {
                this.QUE.remove(input);
                this.QUE.addFirst(input);
            }
            return curr.getResult();
        }
        return null;
    }

    public void put(String input, String result) {
        if (input == null || result == null)
            return;
        synchronized (this.QUE) {
            if (this.MAP.containsKey(input)) {
                this.QUE.remove(input);
            } else {
                int size = this.QUE.size();
                if (size == this.maxSize && size > 0) {
                    String temp = this.QUE.removeLast();
                    this.MAP.remove(temp);
                }
            }
            LruElement newObj = new LruElement(input, result);
            this.QUE.addFirst(input);
            this.MAP.put(input, newObj);
        }
    }
}
