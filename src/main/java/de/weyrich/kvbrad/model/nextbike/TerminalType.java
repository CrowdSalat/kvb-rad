package de.weyrich.kvbrad.model.nextbike;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.IOException;

public enum TerminalType {
    CHARACTER, FREE, STELE, UNDEFINED;

    @JsonValue
    public String toValue() {
        switch (this) {
        case CHARACTER: return "character";
        case FREE: return "free";
        case STELE: return "stele";
        }
        return null;
    }

    @JsonCreator
    public static TerminalType forValue(String value) throws IOException {
        if (value.equals("character")) return CHARACTER;
        if (value.equals("free")) return FREE;
        if (value.equals("stele")) return STELE;
        if (value.equals("")) return UNDEFINED;
        throw new IOException("Cannot deserialize TerminalType");
    }
}
