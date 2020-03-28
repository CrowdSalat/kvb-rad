package de.weyrich.kvbrad.model.nextbike;

//TODO merge with BikeTypes
public class PlaceBikeTypes {
    private Long the15;
    private Long undefined;

    public Long getThe15() { return the15; }
    public void setThe15(Long value) { this.the15 = value; }

    public Long getUndefined() { return undefined; }
    public void setUndefined(Long value) { this.undefined = value; }

    @Override
    public String toString() {
        return "PlaceBikeTypes{" +
                "the15=" + the15 +
                ", undefined=" + undefined +
                '}';
    }
}
