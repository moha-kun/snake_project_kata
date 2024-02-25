package norsys.technomaker;

import norsys.technomaker.exceptions.BiteTailException;
import norsys.technomaker.exceptions.HitWallException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Yard extends Observer {

    private char[][] matrix;
    private Coordinates foodCoordinates;

    public Yard(int height, int width) {
        matrix = new char[height][width];
        Arrays.stream(matrix)
                .forEach(array -> Arrays.fill(array, ' '));
        renderWalls();
    }

    private void renderWalls() {
        int height = matrix.length;
        int width = matrix[0].length;
        Arrays.fill(matrix[0], '*');
        Arrays.fill(matrix[height - 1], '*');
        for (int i = 1; i < height - 1; i++) {
            matrix[i][0] = '*';
            matrix[i][width - 1] = '*';
        }
    }

    public Yard() {
        this(10, 10);
    }

    @Override
    public void update(Observable observable, Object obj) {
        if (obj != null) {
            Coordinates newCoordinates = (Coordinates) obj;
            int xCoordinates = newCoordinates.getX();
            int yCoordinates = newCoordinates.getY();
            if (matrix[xCoordinates][yCoordinates] == '*')
                throw new HitWallException("You hit the yard wall");
            if ("URDL".contains(String.valueOf(matrix[xCoordinates][yCoordinates])))
                throw new BiteTailException("the snake bite its tail");
            if (matrix[xCoordinates][yCoordinates] == 'O')
                foodCoordinates = null;
        }
        cleanYard();
        renderWalls();
        Snake snake = (Snake) observable;
        renderSnake(snake);
        if (foodCoordinates == null)
            foodCoordinates = generateRandomFoodCoordinates(matrix);
        matrix[foodCoordinates.getX()][foodCoordinates.getY()] = 'O';
    }

    private void cleanYard() {
        Arrays.stream(matrix)
                .forEach(array -> Arrays.fill(array, ' '));
    }

    private void renderSnake(Snake snake) {
        Coordinates coordinates = snake.getHeadCoordinates();
        int xCoordinates = coordinates.getX();
        int yCoordinates = coordinates.getY();
        String state = snake.getState();
        int stateLength = state.length();
        for (int stateIndex = 1; stateIndex < stateLength; stateIndex++) {
            char partOfBody = state.charAt(stateIndex);
            matrix[xCoordinates][yCoordinates] = partOfBody;
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

    public Coordinates generateRandomFoodCoordinates(char[][] yard) {
        List<Coordinates> list = new ArrayList<>();
        for (int i = 0; i < yard.length; i++) {
            for (int j = 0; j < yard[0].length; j++) {
                if (yard[i][j] == ' ')
                    list.add(new Coordinates(i, j));
            }
        }
        int index = (int) Math.floor(Math.random() * list.size());
        return list.get(index);
    }
}
