package de.weyrich.kvbrad.model.nextbike;

public class BikeTypes {
    private long the14;
    private long the15;
    private long the17;
    private long undefined;

    public long getThe14() { return the14; }
    public void setThe14(long value) { this.the14 = value; }

    public long getThe15() { return the15; }
    public void setThe15(long value) { this.the15 = value; }

    public long getThe17() { return the17; }
    public void setThe17(long value) { this.the17 = value; }

    public long getUndefined() { return undefined; }
    public void setUndefined(long value) { this.undefined = value; }

    @Override
    public String toString() {
        return "BikeTypes{" +
                "the14=" + the14 +
                ", the15=" + the15 +
                ", the17=" + the17 +
                ", undefined=" + undefined +
                '}';
    }
}
