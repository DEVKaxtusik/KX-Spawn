package pl.kaxtusik.spawn.bridge.litecommands.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Command {

    public String name;
    public boolean enabled;
    public List<String> aliases = new ArrayList<>();
    public List<String> permissions = new ArrayList<>();
    public Map<String, SubCommand> subCommands = new HashMap<>();

    public Command() {}

    public Command(String name, List<String> aliases, List<String> permissions, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
        this.aliases = aliases;
        this.permissions = permissions;
    }

    public Command(String name, List<String> aliases, List<String> permissions, Map<String, SubCommand> subCommands, boolean enabled) {
        this.name = name;
        this.aliases = aliases;
        this.permissions = permissions;
        this.subCommands = subCommands;
        this.enabled = enabled;
    }
}
