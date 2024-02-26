package norsys.technomaker;

import norsys.technomaker.exceptions.BiteTailException;
import norsys.technomaker.exceptions.HitWallException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class YardTest {

    public static Object[] getDataForShouldThrowHitWallException() {
        return new Object[]{
                new Snake("UDDD", new Coordinates(1, 3)),
                new Snake("RLLL", new Coordinates(4, 10)),
                new Snake("LRRR", new Coordinates(6, 1)),
                new Snake("DUUU", new Coordinates(10, 2)),
        };
    }

    public static Object[] getDataForShouldThrowBiteTailException() {
        return new Object[] {
                new Snake("ULURRR", new Coordinates(5, 4)),
                new Snake("LDLUUU", new Coordinates(5, 4)),
                new Snake("DRDLLL", new Coordinates(5, 4)),
                new Snake("RURDDD", new Coordinates(5, 4))
        };
    }

    public static Object[] getDataForShouldGrowWhenMoveToCellContainsFood() {
        return new Object[][] {
                {new Snake("RLLL", new Coordinates(5, 4)), new Coordinates(5, 5), "RLLLL"},
                {new Snake("RLURR", new Coordinates(5, 4)), new Coordinates(5, 5), "RLLURR"},
                {new Snake("UDD", new Coordinates(5, 4)), new Coordinates(4, 4), "UDDD"}
        };
    }

    @ParameterizedTest
    @MethodSource("getDataForShouldThrowHitWallException")
    public void shouldThrowHitWallException(Snake snake) {
        Yard yard = new Yard(12, 12, snake);
        Assertions.assertThrows(
                HitWallException.class,
                () -> yard.moveSnake()
        );
    }

    @ParameterizedTest
    @MethodSource("getDataForShouldThrowBiteTailException")
    public void shouldThrowBiteTailException(Snake snake) {
        Yard yard = new Yard(12, 12, snake);
        Assertions.assertThrows(
                BiteTailException.class,
                () -> yard.moveSnake()
        );
    }

    @ParameterizedTest
    @MethodSource("getDataForShouldGrowWhenMoveToCellContainsFood")
    public void shouldGrowWhenMoveToCellContainsFood(Snake snake, Coordinates foodCoordinates, String expectedState) {
        Yard yard = new Yard(12, 12, snake, foodCoordinates);
        yard.moveSnake();
        String actualState = yard.getSnake().getState();
        Assertions.assertEquals(expectedState, actualState);
    }

}
