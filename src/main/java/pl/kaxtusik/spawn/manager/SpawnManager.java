package pl.kaxtusik.spawn.manager;

import org.bukkit.Location;
import pl.kaxtusik.spawn.Plugin;
import pl.kaxtusik.spawn.config.Config;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SpawnManager {
    private Location spawnLocation;
    private Config config;
    private final Map<UUID, Long> toTeleport = new ConcurrentHashMap<>();

    public SpawnManager(Plugin plugin) {
        this.config = plugin.getPluginConfig();
        this.spawnLocation = config.getSpawnLocation();
    }

    public void addToTeleport(UUID playerUUID) {
        if (playerUUID != null) {
            toTeleport.put(playerUUID, System.currentTimeMillis());
        }
    }

    public void addToTeleport(UUID playerUUID, long ms) {
        if (playerUUID != null) {
            toTeleport.put(playerUUID, ms);
        }
    }

    public void removeFromTeleport(UUID playerUUID) {
        if (playerUUID != null) {
            toTeleport.remove(playerUUID);
        }
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
        if (newLocation != null) {
            this.spawnLocation = newLocation;
        }
    }
}
