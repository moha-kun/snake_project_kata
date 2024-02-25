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
                new Snake("RLLL", new Coordinates(4, 8)),
                new Snake("LRRR", new Coordinates(6, 1)),
                new Snake("DUUU", new Coordinates(8, 2)),
        };
    }

    public static Object[] getDataForShouldThrowBiteTailException() {
        return new Object[] {
                new Snake("ULURRR"),
                new Snake("LDLUUU"),
                new Snake("DRDLLL"),
                new Snake("RURDDD")
        };
    }

    @ParameterizedTest
    @MethodSource("getDataForShouldThrowHitWallException")
    public void shouldThrowHitWallException(Snake snake) {
        Yard yard = new Yard();
        snake.addObserver(yard);
        Assertions.assertThrows(
                HitWallException.class,
                () -> snake.move()
        );
    }

    @ParameterizedTest
    @MethodSource("getDataForShouldThrowBiteTailException")
    public void shouldThrowBiteTailException(Snake snake) {
        Yard yard = new Yard();
        snake.addObserver(yard);
        Assertions.assertThrows(
                BiteTailException.class,
                () -> snake.move()
        );
    }

}
