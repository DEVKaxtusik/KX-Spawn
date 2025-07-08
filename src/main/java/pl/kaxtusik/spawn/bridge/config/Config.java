package pl.kaxtusik.spawn.bridge.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import pl.kaxtusik.spawn.bridge.litecommands.models.Command;
import pl.kaxtusik.spawn.bridge.litecommands.models.SubCommand;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Config extends OkaeriConfig {

    @Getter
    @Comment("Weather metrics should be enabled or not")
    private boolean enableMetrics = true;

    @Setter
    @Comment("Spawn location")
    private Location spawnLocation;

    @Setter
    @Comment("Regions where player can instantly be teleported to spawn")
    private List<String> instantRegions = List.of(
            "spawn"
    );
    @Comment("Should a player be teleported when they first join")
    private boolean teleportOnFirstJoin = true;

    @Comment("Should a player be teleported with every join")
    private boolean teleportOnEveryJoin = false;

    @Comment("Should plugin respawn player on spawn after death")
    private boolean respawnOnSpawn = true;

    @Setter
    @Comment("Time to teleport")
    private int timeToTeleport = 5;

    @Setter
    @Comment("Can player move during teleportation")
    private boolean canMove = true;

    @Setter
    @Comment("Can player take damage when teleporting")
    private boolean canBeDamaged = false;

    @Comment("Command configurations")
    private Map<String, Command> commands = createDefaultCommands();

    private Map<String, Command> createDefaultCommands() {
        Map<String, Command> defaultCommands = new HashMap<>();

        Map<String, SubCommand> subCommands = new HashMap<>();
        subCommands.put("set", new SubCommand("set", true, Arrays.asList("setspawn"), Arrays.asList("kx.spawn.set")));
        subCommands.put("reload", new SubCommand("reload", true, Arrays.asList("rl"), Arrays.asList("kx.spawn.reload")));

        Command spawnCommand = new Command("spawn", Arrays.asList("lobby"), Arrays.asList("kx.spawn.use"), subCommands, true);
        defaultCommands.put("spawn", spawnCommand);

        return defaultCommands;
    }

    @Setter
    private String teleportOthersPermission = "kx.spawn.others";
    @Setter
    private String teleportBypassPermission = "kx.spawn.bypass";

    public Command getCommand(String name) {
        return commands.get(name);
    }
}
