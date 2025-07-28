package org.example.sudoku.ui.custom.screen;

import org.example.sudoku.service.BoardService;
import org.example.sudoku.ui.custom.button.CheckGameStatusButton;
import org.example.sudoku.ui.custom.button.FinishGameButton;
import org.example.sudoku.ui.custom.button.ResetButton;
import org.example.sudoku.ui.custom.frame.MainFrame;
import org.example.sudoku.ui.custom.panel.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainScreen {
    private final static Dimension dimension = new Dimension(600, 600);
    private final BoardService boardService;

    private JButton checkGameStatusButton;
    private JButton finishGameButton;
    private JButton resetGameButton;

    public MainScreen (Map<String, String> gameConfig){
        this.boardService = new BoardService(gameConfig);
    }

    public void buildMainScreen(){
        JPanel panel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, panel);
        addResetButton(panel);
        addShowGameStatusButton(panel);
        addFinishGameButton(panel);




         mainFrame.revalidate(); mainFrame.repaint();

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
            }


        });
        panel.add(resetGameButton);
    }
}
