package pl.kaxtusik.spawn.bridge.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import lombok.Getter;
import pl.kaxtusik.spawn.models.Message;

import java.util.Map;

@Getter
@Header({
        "In messages you can use types: TITLE, TITLE_SUBTITLE, SUBTITLE, CHAT, ACTIONBAR",
        "In TITLE_SUBTITLE use '\\n' to separate title and subtitle",
        "Sound names can be set to null to disable sounds",
        "Use Minecraft sound names or XSeries compatible sound names"
})
public class Messages extends OkaeriConfig {

    @Comment("Message displayed after reloading the configuration")
    private Message reloadedConfig = new Message("CHAT", "&aConfiguration reloaded!");

    @Comment("Message displayed after reloading the messages")
    private Message reloadedMessages = new Message("CHAT", "&aMessages reloaded!");

    @Comment("Message displayed when spawn location is not set")
    private Message spawnNotSet = new Message("CHAT", "&cSpawn location is not set!");

    @Comment("Message displayed after setting the spawn location")
    private Message spawnSet = new Message("CHAT", "&aSpawn location set!");

    @Comment("Local time format for specific numbers (number -> time unit)")
    private Map<Integer, String> localFormat = Map.of(
            1, "second",
            2, "seconds",
            3, "seconds"
    );

    @Comment("Message displayed during teleportation with remaining time. Available placeholders: {TIME}, {FORMAT}")
    private Message teleporting = new Message("CHAT", "&aTeleporting in {TIME} {FORMAT}!");

    @Comment("Sound name that plays during countdown")
    private String teleportingSound = "UI_BUTTON_CLICK";

    @Comment("Message displayed after teleporting another player. Available placeholder: {PLAYER}")
    private Message teleportingOthers = new Message("CHAT", "&aYou have teleported {PLAYER} to spawn!");

    @Comment("Message displayed after the player has been teleported")
    private Message teleported = new Message("CHAT", "&aYou have been teleported to spawn!");

    @Comment("Sound name that plays on successful teleportation")
    private String teleportedSound = "BLOCK_NOTE_BLOCK_PLING";

    @Comment("Message when teleportation is cancelled due to movement")
    private Message teleportCancelled = new Message("CHAT","&cYou moved! Teleportation cancelled.");

    @Comment("No permission message. Available placeholder: {PERMISSION}")
    private Message noPermission = new Message("CHAT", "&cYou don't have permission to do that! &f({PERMISSION})");

    @Comment("Message displayed when player uses command incorrectly. Available placeholder: {USAGE} ")
    private Message invalidUsage = new Message("CHAT", "&cInvalid usage. {USAGE}");

    @Comment("Message sent to player when player does not exist")
    private Message playerNotFound = new Message("CHAT", "&cPlayer not found!");

    @Comment("Message sent when console tries to use a player command")
    private Message playerOnly = new Message("CHAT", "&cThis command can only be used by players!");
}
