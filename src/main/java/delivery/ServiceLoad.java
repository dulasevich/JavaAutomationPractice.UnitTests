package delivery;

public enum ServiceLoad {
    NORMAL(1),
    INCREASED(1.2),
    HIGH(1.4),
    HIGHEST(1.6);

    private final double loadIncrease;

    ServiceLoad(double loadIncrease) {
        this.loadIncrease = loadIncrease;
    }

    public double getLoadIncrease() {
        return loadIncrease;
    }

}
