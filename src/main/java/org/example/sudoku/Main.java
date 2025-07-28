package org.example.sudoku;

import org.example.sudoku.model.Board;
import org.example.sudoku.model.Space;

import java.sql.Array;
import java.util.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static java.util.Objects.isNull;


public class Main {

    private final static Scanner sc = new Scanner(System.in);

    private static Board board;

    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) {

        final var positions = Stream.of(args)
                .collect(Collectors.toMap(
                        k -> k.split(";")[0],
                        v-> v.split(";")[1]
                ));

        var option = -1;

        while (true){
            System.out.println("Choice one option");
            System.out.println("1 - Start new game");
            System.out.println("2 - Put new number");
            System.out.println("3 - Remove a number");
            System.out.println("4 - Visualize the current game");
            System.out.println("5 - Verify game status");
            System.out.println("6 - Clean game");
            System.out.println("7 - Finish game");
            System.out.println("8 - Exit");

            option = sc.nextInt(); sc.nextLine();

            switch (option){
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Invalid option, choice an exiting option");

            }



        }
    }

    private static void startGame(Map<String, String> positions) {
        if (nonNull(board)) {
            System.out.println("The game already was started.");
            return;
        }

        List<List<Space>> spaces = new LinkedList<>();
        for (int i = 0; i < BOARD_LIMIT; i++){
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get("%s.%s".formatted(i,j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);

                Space currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
        System.out.println("The game is ready to start");
    }

    private static void inputNumber() {
        if (isNull(board)) {
            System.out.println("The game already wasn't started.");
            return;
        }

        int col = getColAndRow().getFirst();
        int row = getColAndRow().getLast();

        System.out.printf("Type the number that will stay in position: [%d / %d]\n", col, row);

        int value = runUntilGetValidNumber(1, 9);

        if (!board.changeValue(col, row, value)){
            System.out.printf("The position [%d / %d] has a fixed value", col, row);
        }

    }

    private static void removeNumber() {
        if (isNull(board)) {
            System.out.println("The game already wasn't started.");
            return;
        }


        int col = getColAndRow().getFirst();
        int row = getColAndRow().getLast();

        if (!board.clearValue(col, row)){
            System.out.printf("The position [%d / %d] has a fixed value", col, row);
        }



    }

    private static void finishGame() {
    }

    private static void clearGame() {
    }

    private static void showGameStatus() {
    }

    private static void showCurrentGame() {
    }




    private static int runUntilGetValidNumber(final int min, final int max){
        int current = sc.nextInt(); sc.nextLine();

        while (current < min || current > max){
            System.out.printf("Type a number between %s and %s\n", min, max);
            current = sc.nextInt(); sc.nextLine();
        }
        return current;
    }

    private static List<Integer> getColAndRow(){
        System.out.println("Type the COLUMN that the number will be inserted");
        int col = runUntilGetValidNumber(0,8);

        System.out.println("Type the ROW that the number will be inserted");
        int row = runUntilGetValidNumber(0,8);

        return List.of(col, row);
    }

}
