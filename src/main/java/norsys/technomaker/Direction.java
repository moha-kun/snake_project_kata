package norsys.technomaker;

public enum Direction {

    RIGHT,
    DOWN,
    LEFT,
    UP;

    public Direction getOpposite() {
        switch (this) {
            case UP -> {
                return DOWN;
            }
            case DOWN -> {
                return UP;
            }
            case RIGHT -> {
                return LEFT;
            }
            default -> {
                return RIGHT;
            }
        }
    }

}
