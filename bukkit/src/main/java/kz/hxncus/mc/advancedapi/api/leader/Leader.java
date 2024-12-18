package kz.hxncus.mc.advancedapi.api.leader;

import kz.hxncus.mc.advancedapi.api.bukkit.profile.Profile;
import lombok.NonNull;

/**
 * The Leader interface represents a leader in a game or group.
 * It provides methods for getting and setting the leader profile,
 * as well as checking if a given profile is the leader.
 *
 * @param <P> the type of profile that the leader is associated with
 */
public interface Leader<P extends Profile> {
    
    /**
     * Gets the leader profile.
     *
     * @return the leader profile
     */
    @NonNull
    P getLeader();
    
    /**
     * Sets the leader profile.
     *
     * @param profile the leader profile to set
     * @return true if the leader profile was set successfully, false otherwise
     */
    boolean setLeader(P profile);
    
    /**
     * Checks if the given profile is the leader.
     *
     * @param profile the profile to check
     * @return true if the given profile is the leader, false otherwise
     */
    default boolean isLeader(P profile) {
        return this.getLeader().getUniqueId() == profile.getUniqueId();
    }
}
