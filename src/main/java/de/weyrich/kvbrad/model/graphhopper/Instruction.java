package de.weyrich.kvbrad.model.graphhopper;

public class Instruction {
    private Double distance;
    private long[] interval;
    private Long sign;
    private String text;
    private Long time;

    public Double getDistance() { return distance; }
    public void setDistance(Double value) { this.distance = value; }

    public long[] getInterval() { return interval; }
    public void setInterval(long[] value) { this.interval = value; }

    public Long getSign() { return sign; }
    public void setSign(Long value) { this.sign = value; }

    public String getText() { return text; }
    public void setText(String value) { this.text = value; }

    public Long getTime() { return time; }
    public void setTime(Long value) { this.time = value; }
}
