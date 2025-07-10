package pl.kaxtusik.spawn.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Message {
    private String type;
    private String message;

    public String getType() {
        return type.toUpperCase();
    }
}