package pl.kaxtusik.spawn.manager;

import org.bukkit.Location;
import pl.kaxtusik.spawn.Plugin;
import pl.kaxtusik.spawn.config.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpawnManager {
    private Location spawnLocation;
    private Config config = Plugin.INSTANCE.getPluginConfig();
    private Map<UUID, Long> toTeleport = new HashMap<>();

    public SpawnManager() {
        this.spawnLocation = config.getSpawnLocation();
    }

    public void addToTeleport(UUID playerUUID) {
        toTeleport.put(playerUUID, System.currentTimeMillis());
    }

    public void removeFromTeleport(UUID playerUUID) {
        toTeleport.remove(playerUUID);
    }

    public Map<UUID, Long> getToTeleport() {
        return toTeleport;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public boolean isSpawnSet() {
        return spawnLocation != null;
    }

    public void updateSpawnLocation(Location newLocation) {
        this.spawnLocation = newLocation;
    }
}