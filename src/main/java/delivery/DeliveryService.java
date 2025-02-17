package delivery;

public class DeliveryService {
    private static final double MIN_DELIVERY_COST = 400;

    private DeliveryService() {
    }

    private static int calculateDistance(double distance) {
        if (distance < 0) throw new IllegalArgumentException("Distance should be positive");
        if (distance == 0) throw new IllegalArgumentException("Delivery possible for some distance");
        if (distance <= 2) return 50;
        if (distance <= 10) return 100;
        if (distance <= 30) return 200;
        return 300;
    }

    private static int calculateDimension(Dimension dimension) {
        if (dimension == null) throw new NullPointerException("Dimension is null");
        return switch (dimension) {
            case BIG -> 200;
            case SMALL -> 100;
        };
    }

    private static int calculateFragile(boolean isFragile) {
        return isFragile ? 300 : 0;
    }

    private static double calculateLoad(ServiceLoad serviceLoad) {
        if (serviceLoad == null) throw new NullPointerException("Service load is null");
        return serviceLoad.getLoadIncrease();
    }

    public static double calculateDeliveryCost(double distance, Dimension dimension, boolean isFragile, ServiceLoad serviceLoad) {
        if (isFragile && distance > 30) {
            throw new UnsupportedOperationException("Fragile cargo cannot be delivered for the distance above 30 km");
        }
        int basicDeliveryCost = calculateDistance(distance) + calculateDimension(dimension)
                + calculateFragile(isFragile);
        double finalDeliveryCost = basicDeliveryCost * calculateLoad(serviceLoad);
        return Math.max(finalDeliveryCost, MIN_DELIVERY_COST);
    }
}
