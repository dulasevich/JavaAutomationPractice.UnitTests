package unittests;

import delivery.DeliveryService;
import delivery.Dimension;
import delivery.ServiceLoad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeliveryServiceTest {

    @ParameterizedTest
    @DisplayName("Delivery Calculation")
    @Tag("PositiveTest")
    @CsvSource({"1.999, BIG, true, HIGHEST, 880", // border distance 1, highest load, big dimension
            "2, BIG, false, NORMAL, 400", //distance 1, normal load, big dimension, min calculation
            "3, SMALL, false, INCREASED, 400", // border distance 1, increased load, small dimension, min calculation
            "9, SMALL, true, NORMAL, 500", // border distance 2, normal load, small dimension
            "10, SMALL, true, INCREASED, 600", // distance 2, increased load, small dimension
            "11, SMALL, true, INCREASED, 720", // border distance 2, increased load, small dimension
            "29, BIG, false, HIGH, 560", // border distance 3, high load, big dimension
            "30, SMALL, true, HIGHEST, 960", //distance 3, highest load, small dimension
            "31, BIG, false, NORMAL, 500", //distance 4, border distance 3, normal load, big dimension
            "1000, SMALL, false, HIGH, 560"}) // big distance 4, high load, small dimension
    void calculateDeliveryCostPositiveTest(double distance, Dimension dimension, boolean isFragile, ServiceLoad serviceLoad, int expectedCost) {
        assertEquals(expectedCost, DeliveryService.calculateDeliveryCost(distance, dimension, isFragile, serviceLoad));
    }

    @Test
    @DisplayName("Distance negative")
    @Tag("NegativeTest")
    void calculateNegativeDistanceTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> DeliveryService.calculateDeliveryCost(-40, Dimension.BIG, true, ServiceLoad.NORMAL));
        assertEquals("Distance should be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Distance zero")
    @Tag("NegativeTest")
    void calculateZeroDistanceTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> DeliveryService.calculateDeliveryCost(0, Dimension.BIG, true, ServiceLoad.HIGHEST));
        assertEquals("Delivery possible for some distance", exception.getMessage());
    }

    @Test
    @DisplayName("Fragile big distance")
    @Tag("NegativeTest")
    void calculateFragileBigDistanceTest() {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> DeliveryService.calculateDeliveryCost(40, Dimension.BIG, true, ServiceLoad.INCREASED));
        assertEquals("Fragile cargo cannot be delivered for the distance above 30 km", exception.getMessage());
    }

    @Test
    @DisplayName("Null dimension")
    @Tag("NegativeTest")
    void calculateNullDimensionTest() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> DeliveryService.calculateDeliveryCost(10, null, true, ServiceLoad.HIGH));
        assertEquals("Dimension is null", exception.getMessage());
    }
    @Test
    @DisplayName("Null service load")
    @Tag("NegativeTest")
    void calculateNullServiceLoadTest() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> DeliveryService.calculateDeliveryCost(10, Dimension.BIG, true, null));
        assertEquals("Service load is null", exception.getMessage());
    }
}
