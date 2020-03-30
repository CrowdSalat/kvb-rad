package de.weyrich.kvbrad.model.nextbike;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Arrays;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Bike {
    private String number;
    private long bikeType;
    private LockType[] lockTypes;
    private boolean active;
    private State state;
    private boolean electricLock;
    private long boardcomputer;
    private Object pedelecBattery;
    private Object batteryPack;

    public String getNumber() { return number; }
    public void setNumber(String value) { this.number = value; }

    public long getBikeType() { return bikeType; }
    public void setBikeType(long value) { this.bikeType = value; }

    public LockType[] getLockTypes() { return lockTypes; }
    public void setLockTypes(LockType[] value) { this.lockTypes = value; }

    public boolean getActive() { return active; }
    public void setActive(boolean value) { this.active = value; }

    public State getState() { return state; }
    public void setState(State value) { this.state = value; }

    public boolean getElectricLock() { return electricLock; }
    public void setElectricLock(boolean value) { this.electricLock = value; }

    public long getBoardcomputer() { return boardcomputer; }
    public void setBoardcomputer(long value) { this.boardcomputer = value; }

    public Object getPedelecBattery() { return pedelecBattery; }
    public void setPedelecBattery(Object value) { this.pedelecBattery = value; }

    public Object getBatteryPack() { return batteryPack; }
    public void setBatteryPack(Object value) { this.batteryPack = value; }

    @Override
    public String
    toString() {
        return "Bike{" +
                "number='" + number + '\'' +
                ", bikeType=" + bikeType +
                ", lockTypes=" + Arrays.toString(lockTypes) +
                ", active=" + active +
                ", state=" + state +
                ", electricLock=" + electricLock +
                ", boardcomputer=" + boardcomputer +
                ", pedelecBattery=" + pedelecBattery +
                ", batteryPack=" + batteryPack +
                '}';
    }
}
