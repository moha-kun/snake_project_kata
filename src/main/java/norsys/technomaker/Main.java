package norsys.technomaker;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Snake snake = new Snake("RLLL", new Coordinates(5, 6));
        Yard yard = new Yard();
        snake.addObserver(yard);
        System.out.println(yard.getYard());
        while (true) {
            System.out.print("Direction: ");
            char direction = scanner.nextLine().charAt(0);
            snake.changeDirection(direction);
            snake.move();
            System.out.println(yard.getYard());
        }
    }

}
