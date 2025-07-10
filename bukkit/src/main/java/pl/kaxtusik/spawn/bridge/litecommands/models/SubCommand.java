package pl.kaxtusik.spawn.bridge.litecommands.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubCommand {

    public String name;
    public boolean enabled;
    public List<String> aliases = new ArrayList<>();
    public List<String> permissions = new ArrayList<>();

    public SubCommand() {}

    public SubCommand(String name, boolean enabled, List<String> aliases, List<String> permissions) {
        this.name = name;
        this.enabled = enabled;
        this.aliases = aliases;
        this.permissions = permissions;
    }
}
