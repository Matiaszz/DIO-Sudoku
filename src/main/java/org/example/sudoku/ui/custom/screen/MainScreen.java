package org.example.sudoku.ui.custom.screen;

import org.example.sudoku.model.Space;
import org.example.sudoku.service.BoardService;
import org.example.sudoku.service.EventEnum;
import org.example.sudoku.service.NotifierService;
import org.example.sudoku.ui.custom.button.CheckGameStatusButton;
import org.example.sudoku.ui.custom.button.FinishGameButton;
import org.example.sudoku.ui.custom.button.ResetButton;
import org.example.sudoku.ui.custom.frame.MainFrame;
import org.example.sudoku.ui.custom.input.NumberText;
import org.example.sudoku.ui.custom.panel.MainPanel;
import org.example.sudoku.ui.custom.panel.SudokuSector;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainScreen {
    private final static Dimension dimension = new Dimension(600, 600);
    private final BoardService boardService;
    private final NotifierService notifierService;

    private JButton checkGameStatusButton;
    private JButton finishGameButton;
    private JButton resetGameButton;

    public MainScreen (Map<String, String> gameConfig){
        this.boardService = new BoardService(gameConfig);
        this.notifierService = new NotifierService();
    }

    public void buildMainScreen(){
        JPanel panel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, panel);
        for (int r = 0; r < 9; r+=3) {
            var endRow = r + 2;
            for (int c = 0; c < 9; c+=3) {
                var endCol = c + 2;
                var spaces = getSpacesFromSector(boardService.getSpaces(), c, endCol, r, endRow);
                JPanel sector = generateSection(spaces);
                panel.add(sector);
            }
        }
        addResetButton(panel);
        addShowGameStatusButton(panel);
        addFinishGameButton(panel);

         mainFrame.revalidate(); mainFrame.repaint();
    }

    private List<Space> getSpacesFromSector(List<List<Space>> spaces,
                                            final int initCol, final int endCol,
                                            final int initRow, final int endRow) {
        List<Space> spaceSector = new LinkedList<>();

        for (int r = initRow; r <= endRow; r++) {
            for (int c = initCol; c <= endCol; c++) {
                spaceSector.add(spaces.get(c).get(r));
            }
        }
        return spaceSector;
    }

    private JPanel generateSection(final List<Space> spaces) {
        List<NumberText> fields = new LinkedList<>(spaces.stream().map(NumberText::new).toList());
        fields.forEach(t -> notifierService.subscriber(EventEnum.CLEAR_SPACE, t));
        return new SudokuSector(fields);

    }

    private void addFinishGameButton(JPanel panel) {
        finishGameButton = new FinishGameButton(e -> {
            if (boardService.gameIsFinished()){
                JOptionPane.showMessageDialog(null, "Congrats! You have concluded the game!");
                resetGameButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            } else{
                JOptionPane.showMessageDialog(null, "Your game have some issue, adjust it and try again.");
            }
        });

        panel.add(finishGameButton);
    }

    private void addShowGameStatusButton(JPanel panel) {
        checkGameStatusButton = new CheckGameStatusButton(e -> {
            var hasErrors = boardService.hasErrors();
            var gameStatus = boardService.getStatus();
            var msg = switch (gameStatus){
                case NON_STARTED -> "The game was not started yet";
                case INCOMPLETE -> "The game is incomplete";
                case COMPLETE -> "The game is complete";
            };
            msg += hasErrors ? " and contains errors" : " and don't contains errors";
            JOptionPane.showMessageDialog(null, msg);
        });
        panel.add(checkGameStatusButton);
    }

    private void addResetButton(JPanel panel) {
        resetGameButton = new ResetButton(e -> {
            var dialogResult = JOptionPane.showConfirmDialog(
                    null,
                    "You really want to restart the game?",
                    "Clear game",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (dialogResult == 0) {
                boardService.reset();
                notifierService.notify(EventEnum.CLEAR_SPACE);
            }


        });
        panel.add(resetGameButton);
    }
}
