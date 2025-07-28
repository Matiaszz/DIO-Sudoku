package org.example.sudoku;

import org.example.sudoku.ui.custom.screen.MainScreen;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UIMain {
    public static void main(String[] args) {
        final var gameConfig = Stream.of(args)
                .collect(Collectors.toMap(
                        k -> k.split(";")[0],
                        v-> v.split(";")[1]
                ));
        MainScreen mainScreen = new MainScreen(gameConfig);
        mainScreen.buildMainScreen();

        int option = -1;
    }
}
