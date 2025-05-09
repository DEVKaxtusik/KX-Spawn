package pl.kaxtusik.spawn.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.Location;

public class Config extends OkaeriConfig {

    @Comment("Spawn location")
    private Location spawnLocation;

    @Comment("Should a player be teleported when they first join")
    private boolean teleportOnFirstJoin = true;

    @Comment("Should plugin respawn player on spawn after death")
    private boolean respawnOnSpawn = true;

    @Comment("Time to teleport")
    private int timeToTeleport = 5;

    @Comment("Can player move during teleportation")
    private boolean canMove = true;
    
    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public int getTimeToTeleport() {
        return timeToTeleport;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public void setTimeToTeleport(int timeToTeleport) {
        this.timeToTeleport = timeToTeleport;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isRespawnOnSpawn() {
        return respawnOnSpawn;
    }

    public boolean isTeleportOnFirstJoin() {
        return teleportOnFirstJoin;
    }
}