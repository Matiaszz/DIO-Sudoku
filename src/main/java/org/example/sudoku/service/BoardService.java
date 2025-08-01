package org.example.sudoku.service;

import org.example.sudoku.model.Board;
import org.example.sudoku.model.Space;
import org.example.sudoku.model.StatusEnum;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BoardService {
    private final static int BOARD_LIMIT = 9;


    private final Board board;

    public BoardService(final Map<String, String> gameConfig) {

        this.board = new Board(initBoard(gameConfig));
    }

    public List<List<Space>> getSpaces() {
        return this.board.getSpaces();
    }

    public void reset() {
        this.board.reset();
    }

    public boolean hasErrors() {
        return this.board.hasErrors();
    }

    public StatusEnum getStatus(){
        return this.board.getStatus();
    }

    public boolean gameIsFinished() {
        return this.board.gameIsFinished();
    }

    private List<List<Space>> initBoard(final Map<String, String> gameConfig) {
        List<List<Space>> spaces = new LinkedList<>();
        for (int i = 0; i < BOARD_LIMIT; i++){
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = gameConfig.get("%s,%s".formatted(i,j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);

                Space currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        return spaces;
    }
}
