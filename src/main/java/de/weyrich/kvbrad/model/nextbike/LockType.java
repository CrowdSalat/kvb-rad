package de.weyrich.kvbrad.model.nextbike;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.IOException;

public enum LockType {
    FORK_LOCK;

    @JsonValue
    public String toValue() {
        switch (this) {
        case FORK_LOCK: return "fork_lock";
        }
        return null;
    }

    @JsonCreator
    public static LockType forValue(String value) throws IOException {
        if (value.equals("fork_lock")) return FORK_LOCK;
        throw new IOException("Cannot deserialize LockType");
    }
}
