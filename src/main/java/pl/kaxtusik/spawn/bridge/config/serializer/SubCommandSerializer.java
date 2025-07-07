package pl.kaxtusik.spawn.bridge.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import pl.kaxtusik.spawn.bridge.litecommands.models.SubCommand;

public class SubCommandSerializer implements ObjectSerializer<SubCommand> {
    @Override
    public boolean supports(@NonNull Class<? super SubCommand> type) {
        return type.isAssignableFrom(SubCommand.class);
    }

    @Override
    public void serialize(@NonNull SubCommand object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("name", object.name);
        data.add("enabled", object.enabled);
        data.add("aliases", object.aliases);
        data.add("permissions", object.permissions);
    }

    @Override
    public SubCommand deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        SubCommand subCommand = new SubCommand();
        subCommand.name = data.get("name", String.class);
        subCommand.enabled = data.get("enabled", Boolean.class);
        subCommand.aliases = data.getAsList("aliases", String.class);
        subCommand.permissions = data.getAsList("permissions", String.class);
        return subCommand;
    }
}
