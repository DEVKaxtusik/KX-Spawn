package pl.kaxtusik.spawn.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import pl.kaxtusik.spawn.models.Message;

public class Messages extends OkaeriConfig {

    @Comment("Message displayed after reloading the configuration")
    private Message reloadedConfig = new Message("CHAT", "&aConfiguration reloaded!");

    @Comment("Message displayed after reloading the messages")
    private Message reloadedMessages = new Message("CHAT", "&aMessages reloaded!");

    @Comment("Message displayed when spawn location is not set")
    private Message spawnNotSet = new Message("CHAT", "&cSpawn location is not set!");

    @Comment("Message displayed after setting the spawn location")
    private Message spawnSet = new Message("CHAT", "&aSpawn location set!");

    @Comment("Message displayed during teleportation with remaining time")
    private Message teleporting = new Message("CHAT", "&aTeleporting in {TIME} seconds!");

    @Comment("Message displayed after teleporting another player")
    private Message teleportingOthers = new Message("CHAT", "&aYou have teleported {PLAYER} to spawn!");

    @Comment("Message displayed after the player has been teleported")
    private Message teleported = new Message("CHAT", "&aYou have been teleported to spawn!");

    @Comment("Message when teleportation is cancelled due to movement")
    private Message teleportCancelled = new Message("CHAT","&cYou moved! Teleportation cancelled.");

    @Comment("No permission message")
    private Message noPermission = new Message("CHAT", "&cYou don't have permission to do that! &f({PERMISSION})");

    @Comment("Message displayed when player uses command incorrectly")
    private Message invalidUsage = new Message("CHAT", "&cInvalid usage. {USAGE}");

    @Comment("Message sent to player when player does not exist")
    private Message playerNotFound = new Message("CHAT", "&cPlayer not found!");

    @Comment("Message sent when console tries to use a player command")
    private Message playerOnly = new Message("CHAT", "&cThis command can only be used by players!");

    public Message getReloadedConfig() {
        return reloadedConfig;
    }

    public Message getReloadedMessages() {
        return reloadedMessages;
    }

    public Message getSpawnNotSet() {
        return spawnNotSet;
    }

    public Message getSpawnSet() {
        return spawnSet;
    }

    public Message getTeleporting() {
        return teleporting;
    }

    public Message getTeleportingOthers() {
        return teleportingOthers;
    }

    public Message getTeleported() {
        return teleported;
    }

    public Message getTeleportCancelled() {
        return teleportCancelled;
    }

    public Message getPlayerNotFound() {
        return playerNotFound;
    }

    public Message getPlayerOnly() {
        return playerOnly;
    }

    public Message getInvalidUsage() {
        return invalidUsage;
    }

    public Message getNoPermission() {
        return noPermission;
    }
}