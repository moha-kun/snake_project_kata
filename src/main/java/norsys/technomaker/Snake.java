package norsys.technomaker;

public class Snake {

    /*
        I use here a format of state based on string whereas the first letter
        represent the direction of the head of the snake, then the rest of the string
        represent the body.
        for example "RLLL" represent a snake of length 3 and the direction of his head is right.
     */
    private static final String DEFAULT_STATE = "RLLL";
    private StringBuilder state;
    private Coordinates headCoordinates;
    private boolean canGrow;

    public Snake(String state, Coordinates headCoordinates) {
        this.state = new StringBuilder(state);
        this.headCoordinates = headCoordinates;
        canGrow = false;
    }

    public Snake(Coordinates coordinates) {
        this(DEFAULT_STATE, coordinates);
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
            case 'L' -> headLeft();
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

    private void headLeft() {
        if (state.charAt(0) != 'R')
            state.replace(0, 1, "L");
    }

    public void move() {
        char direction = state.charAt(0);
        char head = switch (direction) {
            case 'U' -> 'D';
            case 'D' -> 'U';
            case 'R' -> 'L';
            default -> 'R';
        };
        if (canGrow)
            canGrow = false;
        else
            state.deleteCharAt(state.length() - 1);

        state.insert(1, head);
        headCoordinates = nextCoordinates();
    }

    public Coordinates nextCoordinates() {
        char direction = state.charAt(0);
        switch (direction) {
            case 'U' -> {
                return new Coordinates(headCoordinates.getX() - 1, headCoordinates.getY());
            }
            case 'D' -> {
                return new Coordinates(headCoordinates.getX() + 1, headCoordinates.getY());
            }
            case 'R' -> {
                return new Coordinates(headCoordinates.getX(), headCoordinates.getY() + 1);
            }
            default -> {
                return new Coordinates(headCoordinates.getX(), headCoordinates.getY() - 1);
            }
        }
    }

    public void eat() {
        canGrow = true;
    }
}
