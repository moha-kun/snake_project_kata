package norsys.technomaker;

/*

 */
public class Game {

    public void start() {
        SimpleBot simpleBot = new SimpleBot();
        Yard yard = new Yard();
        System.out.println(yard.getYard());
        System.out.print("\033[H\033[2J");
        System.out.flush();
        new Thread(
                () -> {
                    while (true) {
                        System.out.println(yard.getYard());
                        System.out.println("score: " + (yard.getSnake().getState().length() - 4));
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        yard.getSnake().changeDirection(simpleBot.getDirection(yard.getGrid(), yard.getSnake().getHeadCoordinates(), yard.getSnake().getDirection(), yard.getFoodCoordinates()));
                        yard.moveSnake();
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                    }
                }
        ).start();
    }

}
