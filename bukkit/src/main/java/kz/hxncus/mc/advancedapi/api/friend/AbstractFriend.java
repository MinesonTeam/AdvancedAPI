package kz.hxncus.mc.advancedapi.api.friend;

import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
public abstract class AbstractFriend implements Friend {
    protected final UUID uniqueId;
    protected final String name;

    public AbstractFriend(@NonNull UUID uniqueId, @NonNull String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }
}
