package kz.hxncus.mc.minesonapi.configuration.serializer;

import java.util.Collection;

public interface ConfigSerializerCollection {
    Collection<ConfigSerializer<?, ?>> serializers();
}
