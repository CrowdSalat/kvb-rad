package de.weyrich.kvbrad.model.nextbike;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.IOException;

public enum State {
    OK;

    @JsonValue
    public String toValue() {
        switch (this) {
        case OK: return "ok";
        }
        return null;
    }

    @JsonCreator
    public static State forValue(String value) throws IOException {
        if (value.equals("ok")) return OK;
        throw new IOException("Cannot deserialize State");
    }
}
