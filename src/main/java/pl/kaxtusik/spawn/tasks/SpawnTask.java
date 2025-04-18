package pl.kaxtusik.spawn.tasks;

import com.tcoded.folialib.FoliaLib;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.kaxtusik.spawn.Plugin;
import pl.kaxtusik.spawn.manager.SpawnManager;
import pl.kaxtusik.spawn.config.Messages;
import pl.kaxtusik.spawn.utils.ColorUtils;
import pl.kaxtusik.spawn.utils.MessagesUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SpawnTask implements Runnable {

    private final SpawnManager spawnManager;
    private final FoliaLib foliaLib;
    private final Messages messages;
    private final MessagesUtils messagesUtils;
    private int timeToTeleport;
    private boolean canMove;
    private final Map<UUID, Location> lastLocations = new ConcurrentHashMap<>();
    private final Map<UUID, Long> lastMessageTimes = new ConcurrentHashMap<>();

    public SpawnTask(Plugin plugin) {
        this.spawnManager = plugin.getSpawnManager();
        this.foliaLib = plugin.getFoliaLib();
        this.messages = plugin.getMessages();
        this.messagesUtils = new MessagesUtils();
        this.timeToTeleport = plugin.getPluginConfig().getTimeToTeleport();
        this.canMove = plugin.getPluginConfig().isCanMove();
    }

    @Override
    public void run() {
        if (spawnManager == null || !spawnManager.isSpawnSet()) {
            if (spawnManager != null) {
                spawnManager.getToTeleport().clear();
            }
            return;
        }

        long currentTime = System.currentTimeMillis();

        spawnManager.getToTeleport().entrySet().removeIf(entry -> {
            UUID uuid = entry.getKey();
            long elapsedSeconds = (currentTime - entry.getValue()) / 1000;

            Player player = Bukkit.getPlayer(uuid);
            if (player == null || !player.isOnline()) {
                spawnManager.removeFromTeleport(uuid);
                return true;
            }

            if (!canMove && hasPlayerMoved(player)) {
                messagesUtils.message(player, messages.getTeleportCancelled().getType(), messages.getTeleportCancelled().getMessage());
                spawnManager.removeFromTeleport(uuid);
                return true;
            }

            if (elapsedSeconds >= timeToTeleport) {
                foliaLib.getScheduler().teleportAsync(player, spawnManager.getSpawnLocation());
                messagesUtils.message(player, messages.getTeleported().getType(), messages.getTeleported().getMessage());
                spawnManager.removeFromTeleport(uuid);
                return true;
            } else {
                long lastMessageTime = lastMessageTimes.getOrDefault(uuid, 0L);
                if (currentTime - lastMessageTime >= 1000) {
                    messagesUtils.message(player, messages.getTeleporting().getType(), messages.getTeleporting().getMessage().replace("{TIME}", String.valueOf(timeToTeleport - elapsedSeconds)));
                    lastMessageTimes.put(uuid, currentTime);
                }
            }
            return false;
        });
    }

    public void updateTimeToTeleport(int newTimeToTeleport) {
        if (newTimeToTeleport > 0) {
            this.timeToTeleport = newTimeToTeleport;
        }
    }

    public void updateCanMove(boolean newCanMove) {
        this.canMove = newCanMove;
    }

    private boolean hasPlayerMoved(Player player) {
        if (player == null) {
            return false;
        }

        UUID uuid = player.getUniqueId();
        Location currentLocation = player.getLocation();
        Location lastLocation = lastLocations.get(uuid);

        if (currentLocation == null) {
            return false;
        }

        if (lastLocation != null &&
                currentLocation.getBlockX() == lastLocation.getBlockX() &&
                currentLocation.getBlockY() == lastLocation.getBlockY() &&
                currentLocation.getBlockZ() == lastLocation.getBlockZ()) {
            return false;
        }

        lastLocations.put(uuid, currentLocation);
        return true;
    }
}