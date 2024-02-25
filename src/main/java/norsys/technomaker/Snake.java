package norsys.technomaker;

public class Snake extends Observable {

    private StringBuilder state;
    private Coordinates headCoordinates;

    public Snake(String state, Coordinates headCoordinates) {
        this.state = new StringBuilder(state);
        this.headCoordinates = headCoordinates;
    }


    public Snake(String state) {
        // TODO: this coordinates are for a yard of area 10*10, when you develop the yard logic you can change it
        this(state, new Coordinates(5, 4));
    }
    /*
        I use here a format of state based on string whereas the first letter
        represent the direction of the head of the snake, then the rest of the string
        represent the body.
        for example "RLLL" represent a snake of length 3 and the direction of his head is right.
     */
    public Snake() {
        this("RLLL");
    }

    public char getDirection() {
        return state.charAt(0);
    }

    public String getState() {
        return state.toString();
    }

    public Coordinates getHeadCoordinates() {
        return headCoordinates;
    }

    public void changeDirection(char direction) {
        switch (direction) {
            case 'U' -> headUp();
            case 'D' -> headDown();
            case 'L' -> headLef();
            case 'R' -> headRight();
        }
    }

    private void headUp() {
        if (state.charAt(0) != 'D')
            state.replace(0, 1, "U");
    }

    private void headDown() {
        if (state.charAt(0) != 'U')
            state.replace(0, 1, "D");
    }

    private void headRight() {
        if (state.charAt(0) != 'L')
            state.replace(0, 1, "R");
    }

    private void headLef() {
        if (state.charAt(0) != 'R')
            state.replace(0, 1, "L");
    }


    /*
    TODO:
     i need to know if the case the snake will move to contains food or not,
     then make the snake grow or leave it the same length
     */
    public void move() {
        char direction = state.charAt(0);
        char neck = switch (direction) {
            case 'U' -> {
                headCoordinates.setX(headCoordinates.getX() - 1);
                yield 'D';
            }
            case 'D' -> {
                headCoordinates.setX(headCoordinates.getX() + 1);
                yield 'U';
            }
            case 'R' -> {
                headCoordinates.setY(headCoordinates.getY() + 1);
                yield 'L';
            }
            default -> {
                headCoordinates.setY(headCoordinates.getY() - 1);
                yield 'R';
            }
        };
        state.deleteCharAt(state.length() - 1);
        state.deleteCharAt(0);
        state.insert(0, neck);
        state.insert(0, direction);
        notifyObservers(headCoordinates);
    }
}
