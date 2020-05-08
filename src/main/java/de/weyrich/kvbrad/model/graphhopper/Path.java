package de.weyrich.kvbrad.model.graphhopper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Path {
    private double[] bbox;
    private Double distance;
    private Instruction[] instructions;
    private String points;
    @JsonProperty("points_encoded")
    private Boolean pointsEncoded;
    private Details details;
    private Long time;

    public double[] getBbox() { return bbox; }
    public void setBbox(double[] value) { this.bbox = value; }

    public Double getDistance() { return distance; }
    public void setDistance(Double value) { this.distance = value; }

    public Instruction[] getInstructions() { return instructions; }
    public void setInstructions(Instruction[] value) { this.instructions = value; }

    public String getPoints() { return points; }
    public void setPoints(String value) { this.points = value; }

    public Boolean getPointsEncoded() { return pointsEncoded; }
    public void setPointsEncoded(Boolean value) { this.pointsEncoded = value; }

    public Details getDetails() { return details; }
    public void setDetails(Details value) { this.details = value; }

    public Long getTime() { return time; }
    public void setTime(Long value) { this.time = value; }
}
