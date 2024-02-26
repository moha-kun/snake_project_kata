package norsys.technomaker;

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
            char dir = yDistance  > 0 ? 'R' : 'L';
            Coordinates newCoord = getNewCoordinates(snakeHeadCoordinates, dir);
            if (grid[newCoord.getX()][newCoord.getY()] != '*')
                return dir;
        }
        if (yDistance == 0) {
            char dir = xDistance  > 0 ? 'D' : 'U';
            Coordinates newCoord = getNewCoordinates(snakeHeadCoordinates, dir);
            if (grid[newCoord.getX()][newCoord.getY()] != '*')
                return dir;
        }
        Direction xNextDirection = xDistance  < 0 ? getDirection('U') : getDirection('D');
        Direction yNextDirection = yDistance  < 0 ? getDirection('L') : getDirection('R');
        if (current == xNextDirection.getOpposite()) {
            char dir = yNextDirection.toString().charAt(0);
            Coordinates newCoord = getNewCoordinates(snakeHeadCoordinates, dir);
            if (grid[newCoord.getX()][newCoord.getY()] != '*')
                return dir;
        }
        if (current == yNextDirection.getOpposite()) {
            char dir =  xNextDirection.toString().charAt(0);
            Coordinates newCoord = getNewCoordinates(snakeHeadCoordinates, dir);
            if (grid[newCoord.getX()][newCoord.getY()] != '*')
                return dir;
        }
        return '0';
        // TODO: here you need to check if the next direction is not the opposite of the current direction
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

    private Coordinates getNewCoordinates(Coordinates currentCoordinates, char direction) {
        switch (direction) {
            case 'U' -> {
                return new Coordinates(currentCoordinates.getX() - 1, currentCoordinates.getY());
            }
            case 'D' -> {
                return new Coordinates(currentCoordinates.getX() + 1, currentCoordinates.getY());
            }
            case 'R' -> {
                return new Coordinates(currentCoordinates.getX(), currentCoordinates.getY() + 1);
            }
            default -> {
                return new Coordinates(currentCoordinates.getX(), currentCoordinates.getY() - 1);
            }
        }
    }

}
