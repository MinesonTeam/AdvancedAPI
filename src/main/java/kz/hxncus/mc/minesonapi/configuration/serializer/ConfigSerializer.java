package kz.hxncus.mc.minesonapi.configuration.serializer;

public abstract class ConfigSerializer<T, F> {

    private final Class<T> toClass;

    private final Class<F> fromClass;

    protected ConfigSerializer(Class<T> toClass, Class<F> fromClass) {
        this.toClass = toClass;
        this.fromClass = fromClass;
    }

    public F serialize(T from) {
        throw new UnsupportedOperationException();
    }

    public T deserialize(F from) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    public Object serializeRaw(Object from) {
        return this.serialize((T) from);
    }

    @SuppressWarnings("unchecked")
    public Object deserializeRaw(Object from) {
        return this.deserialize((F) from);
    }

    public Class<F> getFromClass() {
        return this.fromClass;
    }

    public Class<T> getToClass() {
        return this.toClass;
    }
}
