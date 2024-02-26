package norsys.technomaker;

public class Game {

    public void start() {
        Yard yard = new Yard(12, 12);
        System.out.println(yard.getYard());
        System.out.print("\033[H\033[2J");
        System.out.flush();
        SimpleBot simpleBot = new SimpleBot();
        new Thread(
                () -> {
                    while (true) {
                        System.out.println(yard.getYard());
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        yard.getSnake().changeDirection(simpleBot.getDirection(yard.getSnake().getHeadCoordinates(), yard.getSnake().getDirection(), yard.getFoodCoordinates()));
                        yard.moveSnake();
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                    }
                }
        ).start();
    }

}
