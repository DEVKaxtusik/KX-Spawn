package pl.kaxtusik.spawn.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@Getter
public class Config extends OkaeriConfig {

    @Setter
    @Comment("Spawn location")
    private Location spawnLocation;

    @Comment("Should a player be teleported when they first join")
    private boolean teleportOnFirstJoin = true;

    @Comment("Should plugin respawn player on spawn after death")
    private boolean respawnOnSpawn = true;

    @Setter
    @Comment("Time to teleport")
    private int timeToTeleport = 5;

    @Setter
    @Comment("Can player move during teleportation")
    private boolean canMove = true;
}
