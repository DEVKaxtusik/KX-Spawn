package pl.kaxtusik.spawn.manager;

import lombok.Getter;
import org.bukkit.Location;
import pl.kaxtusik.spawn.Plugin;
import pl.kaxtusik.spawn.bridge.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SpawnManager {
    @Getter
    private Location spawnLocation;
    private Config config;

    @Getter
    private List<String> instantRegions;

    @Getter
    private final Map<UUID, Long> toTeleport = new ConcurrentHashMap<>();

    public SpawnManager(Plugin plugin) {
        this.config = plugin.getPluginConfig();
        this.spawnLocation = config.getSpawnLocation();
        this.instantRegions = new ArrayList<>(config.getInstantRegions());
    }

    public void addToTeleport(UUID playerUUID) {
        if (playerUUID != null) {
            toTeleport.put(playerUUID, System.currentTimeMillis());
        }
    }

    public void removeFromTeleport(UUID playerUUID) {
        if (playerUUID != null) {
            toTeleport.remove(playerUUID);
        }
    }

    public boolean isSpawnSet() {
        return spawnLocation != null;
    }

    public void updateSpawnLocation(Location newLocation) {
        if (newLocation != null) {
            this.spawnLocation = newLocation;
        }
    }

    public void updateInstantRegions(List<String> newInstantRegions) {
        if (newInstantRegions != null) {
            this.instantRegions = new ArrayList<>(newInstantRegions);
        }
    }
}
