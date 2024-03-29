package norsys.technomaker;

import norsys.technomaker.exceptions.BiteTailException;
import norsys.technomaker.exceptions.HitWallException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Yard {

    private static final int DEFAULT_HEIGHT = 12;
    private static final int DEFAULT_WIDTH = 30;
    private static final char SNAKE_HEAD = '@';
    private static final char SNAKE_BODY = '*';
    private static final char FOOD_SHAPE = 'O';
    private char[][] matrix;
    private Coordinates foodCoordinates;
    private Snake snake;

    // this constructor is only for testing
    public Yard(int height, int width, Snake snake, Coordinates foodCoordinates) {
        matrix = new char[height][width];
        Arrays.stream(matrix)
                .forEach(array -> Arrays.fill(array, ' '));
        this.snake = snake;
        this.foodCoordinates = foodCoordinates;
        update();
    }

    public Yard(int height, int width, Snake snake) {
        this(height, width, snake, null);
    }

    public Yard(int height, int width) {
        this(height, width, new Snake());
    }

    public Yard() {
        this(DEFAULT_HEIGHT, DEFAULT_WIDTH);
    }

    private void renderWalls() {
        int height = matrix.length;
        int width = matrix[0].length;
        Arrays.fill(matrix[0], '-');
        Arrays.fill(matrix[height - 1], '-');
        for (int i = 1; i < height - 1; i++) {
            matrix[i][0] = '|';
            matrix[i][width - 1] = '|';
        }
    }

    public Coordinates getFoodCoordinates() {
        return foodCoordinates;
    }

    public char[][] getGrid() {
        return matrix;
    }

    public void moveSnake() {
        Coordinates nextCoordinates = snake.nextCoordinates();
        checkForCollision(nextCoordinates);
        snake.move();
        update();
    }

    public void checkForCollision(Coordinates nextCoordinates) {
        int xCoordinates = nextCoordinates.getX();
        int yCoordinates = nextCoordinates.getY();
        if (isOutOfYard(xCoordinates, yCoordinates))
            throw new HitWallException("You hit the yard wall");
        if (matrix[xCoordinates][yCoordinates] == SNAKE_BODY)
            throw new BiteTailException("the snake bite its tail");
        if (matrix[xCoordinates][yCoordinates] == FOOD_SHAPE) {
            snake.eat();
            foodCoordinates = null;
        }
    }

    private void update() {
        cleanYard();
        renderWalls();
        renderSnake();
        if (foodCoordinates == null)
            foodCoordinates = generateRandomFoodCoordinates(matrix);
        matrix[foodCoordinates.getX()][foodCoordinates.getY()] = FOOD_SHAPE;
    }

    private void cleanYard() {
        Arrays.stream(matrix)
                .forEach(array -> Arrays.fill(array, ' '));
    }

    private void renderSnake() {
        Coordinates coordinates = snake.getHeadCoordinates();
        int xCoordinates = coordinates.getX();
        int yCoordinates = coordinates.getY();
        String state = snake.getState();
        int stateLength = state.length();
        for (int stateIndex = 1; stateIndex < stateLength; stateIndex++) {
            char partOfBody = state.charAt(stateIndex);
            matrix[xCoordinates][yCoordinates] = stateIndex == 1 ? SNAKE_HEAD : SNAKE_BODY;
            switch (partOfBody) {
                case 'L' -> yCoordinates--;
                case 'R' -> yCoordinates++;
                case 'U' -> xCoordinates--;
                case 'D' -> xCoordinates++;
            }
        }
    }

    public String getYard() {
        return Arrays.stream(matrix)
                .map(array -> String.valueOf(array))
                .collect(Collectors.joining("\n"));
    }

    public Snake getSnake() {
        return snake;
    }

    public Coordinates generateRandomFoodCoordinates(char[][] yard) {
        List<Coordinates> list = new ArrayList<>();
        for (int i = 1; i < yard.length - 1; i++) {
            for (int j = 1; j < yard[0].length - 1; j++) {
                if (yard[i][j] == ' ')
                    list.add(new Coordinates(i, j));
            }
        }
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(list.size() - 1);
        return list.get(index);
    }

    private boolean isOutOfYard(int xCoordinate, int yCoordinate) {
        int height = matrix.length;
        int width = matrix[0].length;
        return xCoordinate <= 0 ||
                xCoordinate >= height - 1 ||
                yCoordinate <= 0 ||
                yCoordinate >= width - 1;
    }
}
