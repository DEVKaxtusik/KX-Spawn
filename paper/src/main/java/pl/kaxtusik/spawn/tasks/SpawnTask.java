package pl.kaxtusik.spawn.tasks;

import com.cryptomorin.xseries.XSound;
import com.tcoded.folialib.FoliaLib;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.kaxtusik.spawn.Plugin;
import pl.kaxtusik.spawn.bridge.config.Messages;
import pl.kaxtusik.spawn.manager.SpawnManager;
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
                lastLocations.remove(uuid);
                return true;
            }

            if (!canMove && hasPlayerMoved(player)) {
                messagesUtils.message(player, messages.getTeleportCancelled().getType(), messages.getTeleportCancelled().getMessage());
                spawnManager.removeFromTeleport(uuid);
                lastLocations.remove(uuid);
                return true;
            }

            if (elapsedSeconds >= timeToTeleport) {
                foliaLib.getScheduler().teleportAsync(player, spawnManager.getSpawnLocation());
                messagesUtils.message(player, messages.getTeleported().getType(), messages.getTeleported().getMessage());
                if (!messages.getTeleportedSound().equals("null")) {
                    XSound.of(messages.getTeleportedSound()).ifPresent(action -> action.play(player));
                }
                spawnManager.removeFromTeleport(uuid);
                lastLocations.remove(uuid);
                return true;
            }

            int remainingTime = (int) (timeToTeleport - elapsedSeconds);
            String formattedTimeUnit = formatTime(remainingTime);
            String teleportMessage = messages.getTeleporting().getMessage()
                    .replace("{TIME}", String.valueOf(remainingTime))
                    .replace("{FORMAT}", formattedTimeUnit);
            messagesUtils.message(player, messages.getTeleporting().getType(), teleportMessage);
            if (!messages.getTeleportingSound().equals("null")) {
                XSound.of(messages.getTeleportingSound()).ifPresent(sound -> {
                    float pitch = 0.5f + (0.5f * (timeToTeleport - remainingTime) / timeToTeleport);
                    sound.play(player, 1.0f, pitch);
                });
            }

            return false;
        });
    }

    private String formatTime(int seconds) {
        Map<Integer, String> localFormat = messages.getLocalFormat();

        if (localFormat.containsKey(seconds)) {
            return localFormat.get(seconds);
        }

        if (seconds == 1) {
            return "second";
        } else {
            return "seconds";
        }
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
        if (player == null) return false;

        UUID uuid = player.getUniqueId();
        Location currentLocation = player.getLocation();

        Location lastLocation = lastLocations.get(uuid);

        if (lastLocation == null) {
            lastLocations.put(uuid, currentLocation.clone());
            return false;
        }

        if (currentLocation.distanceSquared(lastLocation) > 0.01) {
            lastLocations.put(uuid, currentLocation.clone());
            return true;
        }

        return false;
    }
}
