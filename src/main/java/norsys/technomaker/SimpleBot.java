package norsys.technomaker;

public class SimpleBot {

    public char getDirection(Coordinates snakeHeadCoordinates, char currentDirection, Coordinates foodCoordinates) {
        int xSnakeHeadCoordinates = snakeHeadCoordinates.getX();
        int ySnakeHeadCoordinates = snakeHeadCoordinates.getY();
        int xFoodCoordinates = foodCoordinates.getX();
        int yFoodCoordinates = foodCoordinates.getY();
        double xDistance = xFoodCoordinates - xSnakeHeadCoordinates;
        double yDistance = yFoodCoordinates - ySnakeHeadCoordinates;
        char xNextDirection = xDistance  < 0 ? 'U' : 'D';
        char xOppositeDirection = xDistance  > 0 ? 'U' : 'D';
        char yNextDirection = yDistance  < 0 ? 'L' : 'R';
        char yOppositeDirection = yDistance  > 0 ? 'L' : 'R';
        if (currentDirection == xOppositeDirection)
            return yNextDirection;
        if (currentDirection == yOppositeDirection)
            return xNextDirection;
        return '0';
        // TODO: here you need to check if the next direction is not the opposite of the di current direction
    }

}
