package de.weyrich.kvbrad.model.nextbike;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.IOException;

public enum LockType {
    FORK_LOCK, ANALOG_CODE_LOCK, UNDEFINED;

    @JsonValue
    public String toValue() {
        switch (this) {
        case FORK_LOCK: return "fork_lock";
        case ANALOG_CODE_LOCK: return "analog_code_lock";
        case UNDEFINED: return  "";
        }
        return null;
    }

    @JsonCreator
    public static LockType forValue(String value) throws IOException {
        if (value.equals("fork_lock")) return FORK_LOCK;
        if (value.equals("analog_code_lock")) return ANALOG_CODE_LOCK;
        if (value.equals("")) return UNDEFINED;
        throw new IOException("Cannot deserialize LockType with value " + value);
    }
}
