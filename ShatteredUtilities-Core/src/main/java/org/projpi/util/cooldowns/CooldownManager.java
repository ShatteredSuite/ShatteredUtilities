package org.projpi.util.cooldowns;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    public static final int DEFAULT_COOLDOWN = 1000; // 1000 millis = 1 second
    private final Map<UUID, Long> lastUse;
    private final int cooldown;

    public CooldownManager() {
        this(DEFAULT_COOLDOWN);
    }

    public CooldownManager(Integer cooldown) {
        lastUse = new HashMap<>();
        if (cooldown == null) {
            this.cooldown = DEFAULT_COOLDOWN;
        } else {
            this.cooldown = cooldown;
        }
    }

    /**
     * Checks if a player has time left on this cooldown.
     *
     * @param uuid The player to check.
     * @return Whether this player has time left on their cooldown.
     */
    public boolean canUse(UUID uuid) {
        if (!lastUse.containsKey(uuid)) {
            return true;
        }
        return System.currentTimeMillis() > (lastUse.get(uuid) + cooldown);
    }

    /**
     * Sets last use of this cooldown to the current time for the given player.
     *
     * @param uuid The player to set the cooldown for.
     */
    public void use(UUID uuid) {
        lastUse.put(uuid, System.currentTimeMillis());
    }

    /**
     * Gets the amount of time left on a cooldown.
     *
     * @param uuid The UUID of the player to check.
     * @return The amount of time left on this cooldown, or 0 if there is none.
     */
    public long timeUntilUse(UUID uuid) {
        if (!lastUse.containsKey(uuid)) {
            return 0;
        }
        long left = (lastUse.get(uuid) + cooldown) - System.currentTimeMillis();
        return left > 0 ? left : 0;
    }
}
