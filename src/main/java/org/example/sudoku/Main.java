package org.example.sudoku;

import org.example.sudoku.model.Board;
import org.example.sudoku.model.Space;
import org.example.sudoku.util.BoardTemplate;

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

        int option = -1;

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

            option = sc.nextInt();

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
                var positionConfig = positions.get("%s,%s".formatted(i,j));
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
        List<Integer> colAndRow = getColAndRow();
        int col = colAndRow.getFirst();
        int row = colAndRow.getLast();

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

        List<Integer> colAndRow = getColAndRow();
        int col = colAndRow.getFirst();
        int row = colAndRow.getLast();

        if (!board.clearValue(col, row)){
            System.out.printf("The position [%d / %d] has a fixed value", col, row);
        }
    }

    private static void showCurrentGame() {
        if (isNull(board)) {
            System.out.println("The game already wasn't started.");
            return;
        }

        var args = new Object[81];
        var argPos = 0;

        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (List<Space> col : board.getSpaces()){
                args[argPos ++] = " " + (isNull(col.get(i).getActual()) ? " " : col.get(i).getActual());
            }
        }

        System.out.println("Here are you current game: ");
        System.out.printf((BoardTemplate.BOARD_TEMPLATE) + "\n", args);
    }

    private static void showGameStatus() {
        if (isNull(board)) {
            System.out.println("The game wasn't started.");
            return;
        }

        System.out.printf("The game status is: %s\n", board.getStatus().getLabel());
        if (board.hasErrors()){
            System.out.println("Contain errors in the game.");
            return;
        }

        System.out.println("The game don't contains any error");
    }

    private static void clearGame() {
        if (isNull(board)) {
            System.out.println("The game already wasn't started.");
            return;
        }

        String msg = "Are you sure that you want to clear your game and lost all your progress (Y/N)?";
        System.out.println(msg);
        var confirm = sc.next();

        while (!confirm.equalsIgnoreCase("y") && !confirm.equalsIgnoreCase("n")){
            System.out.println("Unknown option");
            confirm = sc.next();
        }

        if (confirm.equalsIgnoreCase("y")){
            board.reset();
        }

    }

    private static void finishGame() {
        if (isNull(board)) {
            System.out.println("The game already wasn't started.");
            return;
        }

        if (board.gameIsFinished()){
            System.out.println("Congrats! you have concluded the game successfully!");
            showCurrentGame();
            board = null;
        } else if (board.hasErrors()){
            System.out.println("Has errors in your board, verify and adjust it");
        } else{
            System.out.println("You must fill all spaces in the board.");
        }
    }


    private static int runUntilGetValidNumber(final int min, final int max) {
        int current = -1;

        while (true) {
            System.out.printf("Type a number between %s and %s: ", min, max);

            if (sc.hasNextInt()) {
                current = sc.nextInt();
                sc.nextLine();

                if (current >= min && current <= max) return current;
                System.out.println("Number out of range.");
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }
    }

    private static List<Integer> getColAndRow(){
        System.out.println("Type the COLUMN that the number will be inserted");
        int col = runUntilGetValidNumber(0,8);

        System.out.println("Type the ROW that the number will be inserted");
        int row = runUntilGetValidNumber(0,8);

        return List.of(col, row);
    }

}
