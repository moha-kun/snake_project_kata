package norsys.technomaker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SnakeTest {

    public static Object[] getDataForShouldChangeDirection() {
        return new Object[][]{
                {new Snake("RLLL", new Coordinates()), 'U', 'U'},
                {new Snake("LLLL", new Coordinates()), 'U', 'U'},
                {new Snake("ULLL", new Coordinates()), 'U', 'U'},
                {new Snake("RLLL", new Coordinates()), 'D', 'D'},
                {new Snake("LLLL", new Coordinates()), 'D', 'D'},
                {new Snake("DLLL", new Coordinates()), 'D', 'D'},
                {new Snake("ULLL", new Coordinates()), 'L', 'L'},
                {new Snake("DLLL", new Coordinates()), 'L', 'L'},
                {new Snake("LLLL", new Coordinates()), 'L', 'L'},
                {new Snake("ULLL", new Coordinates()), 'R', 'R'},
                {new Snake("DLLL", new Coordinates()), 'R', 'R'},
                {new Snake("RLLL", new Coordinates()), 'R', 'R'}
        };
    }

    public static Object[] getDataForShouldNotChangeDirection() {
        return new Object[][]{
                {new Snake("RLLL", new Coordinates()), 'L', 'R'},
                {new Snake("LLLL", new Coordinates()), 'R', 'L'},
                {new Snake("ULLL", new Coordinates()), 'D', 'U'},
                {new Snake("DLLL", new Coordinates()), 'U', 'D'}
        };
    }

    public static Object[] getDataForShouldMoveWhenCallMove() {
        return new Object[][]{
                {new Snake("RLLL", new Coordinates(5, 4)), 'U', "UDLL", new Coordinates(4, 4)},
                {new Snake("UDLL", new Coordinates(4, 4)), 'R', "RLDL", new Coordinates(4, 5)},
                {new Snake("RLDL", new Coordinates(4, 5)), 'D', "DULD", new Coordinates(5, 5)},
                {new Snake("DULD", new Coordinates(5, 5)), 'L', "LRUL", new Coordinates(5, 4)},
                {new Snake("RLLL", new Coordinates(5, 4)), 'R', "RLLL", new Coordinates(5, 5)},
                {new Snake("RLLL", new Coordinates(5, 5)), 'R', "RLLL", new Coordinates(5, 6)},
                {new Snake("RLLL", new Coordinates(5, 6)), 'R', "RLLL", new Coordinates(5, 7)},
        };
    }

    @ParameterizedTest
    @MethodSource("getDataForShouldChangeDirection")
    public void shouldChangeDirection(Snake snake, char newDirection, char expectedDirection) {
        snake.changeDirection(newDirection);
        char actualDirection = snake.getDirection();
        Assertions.assertEquals(expectedDirection, actualDirection);
    }

    @ParameterizedTest
    @MethodSource("getDataForShouldNotChangeDirection")
    public void shouldNotChangeDirection(Snake snake, char newDirection, char expectedDirection) {
        snake.changeDirection(newDirection);
        char actualDirection = snake.getDirection();
        Assertions.assertEquals(expectedDirection, actualDirection);
    }

    @ParameterizedTest
    @MethodSource("getDataForShouldMoveWhenCallMove")
    public void shouldMoveWhenCallMove(Snake snake, char direction, String expectedState, Coordinates expectedCoordinates) {
        snake.changeDirection(direction);
        snake.move();
        String actualState = snake.getState();
        Assertions.assertEquals(expectedState, actualState);
        Coordinates actualCoordinates = snake.getHeadCoordinates();
        Assertions.assertEquals(expectedCoordinates, actualCoordinates);
    }

}
