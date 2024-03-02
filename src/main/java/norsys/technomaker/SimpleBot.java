package norsys.technomaker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    this bot is for testing that the game essential functionalities work, so there is no unit test for it.
    the idea is a simple dfs to find the best path to get the food, but it's not the best everytime,
    if you have an idea to improve this let me know :)

*/

public class SimpleBot {

    public char getDirection(char[][] grid, Coordinates snakeHeadCoordinates, char currentDirection, Coordinates foodCoordinates) {
        int xSnakeHeadCoordinates = snakeHeadCoordinates.getX();
        int ySnakeHeadCoordinates = snakeHeadCoordinates.getY();
        int xFoodCoordinates = foodCoordinates.getX();
        int yFoodCoordinates = foodCoordinates.getY();
        double xDistance = xFoodCoordinates - xSnakeHeadCoordinates;
        double yDistance = yFoodCoordinates - ySnakeHeadCoordinates;
        Direction current = getDirection(currentDirection);
        if (xDistance == 0) {
            Direction dir = yDistance  > 0 ? Direction.RIGHT : Direction.LEFT;
            Coordinates newCoordinates = mapDirectionToCoordinates(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY()).get(dir);
            if (dir != current.getOpposite() && grid[newCoordinates.getX()][newCoordinates.getY()] != '*')
                return dir.toString().charAt(0);
        }
        if (yDistance == 0) {
            Direction dir = xDistance  > 0 ? Direction.DOWN : Direction.UP;
            Coordinates newCoordinates = mapDirectionToCoordinates(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY()).get(dir);
            if (dir != current.getOpposite() && grid[newCoordinates.getX()][newCoordinates.getY()] != '*')
                return dir.toString().charAt(0);
        }

        List<Direction> possibleDirections = getPossibleDirections(current);
        int res = Integer.MIN_VALUE;
        Direction dirToMoveTo = null;
        for (Direction dir : possibleDirections) {
            Coordinates coordinates = mapDirectionToCoordinates(snakeHeadCoordinates.getX(), snakeHeadCoordinates.getY()).get(dir);
            int temp = getBestDirection(grid, coordinates, dir, 30, new HashMap<>());
            if (temp >= res) {
                res = temp;
                dirToMoveTo = dir;
            }
        }
        return dirToMoveTo.toString().charAt(0);
    }

    private Direction getDirection(char direction) {
        switch (direction) {
            case 'U' -> {
                return Direction.UP;
            }
            case 'D' -> {
                return Direction.DOWN;
            }
            case 'R' -> {
                return Direction.RIGHT;
            }
            default -> {
                return Direction.LEFT;
            }
        }
    }

    public int getBestDirection(char[][] grid, Coordinates currentCord, Direction direction, int depth, Map<String, Integer> memo) {
        String key = currentCord+"|"+direction+"|"+depth;
        if (memo.containsKey(key))
            return memo.get(key);
        if (currentCord.getX() <= 0 || currentCord.getX() >= grid.length - 1 ||
                currentCord.getY() <= 0 || currentCord.getY() >= grid[0].length - 1)
            return -100;
        if (grid[currentCord.getX()][currentCord.getY()] == '*')
            return -100;
        if (grid[currentCord.getX()][currentCord.getY()] == 'O')
            return 10* depth;
        if (depth == 1)
            return 0;

        List<Direction> possibleDirections = getPossibleDirections(direction);
        int max = Integer.MIN_VALUE;
        for (Direction dir : possibleDirections) {
            Coordinates coordinates = mapDirectionToCoordinates(currentCord.getX(), currentCord.getY()).get(dir);
            int temp = getBestDirection(grid, coordinates, dir, depth - 1, memo) - 1;
            if (temp > max) {
                max = temp;
            }
        }
        memo.put(key, max);
        return max;
    }

    public List<Direction> getPossibleDirections(Direction direction) {
        if (direction == Direction.UP)
            return List.of(Direction.UP, Direction.LEFT, Direction.RIGHT);
        if (direction == Direction.DOWN)
            return List.of(Direction.DOWN, Direction.LEFT, Direction.RIGHT);
        if (direction == Direction.RIGHT)
            return List.of(Direction.UP, Direction.DOWN, Direction.RIGHT);
        return List.of(Direction.UP, Direction.DOWN, Direction.LEFT);
    }

    public Map<Direction, Coordinates> mapDirectionToCoordinates(int x, int y) {
        return Map.of(
                Direction.UP, new Coordinates(x - 1, y),
                Direction.DOWN, new Coordinates(x + 1, y),
                Direction.LEFT, new Coordinates(x, y - 1),
                Direction.RIGHT, new Coordinates(x, y + 1)
        );
    }

}
