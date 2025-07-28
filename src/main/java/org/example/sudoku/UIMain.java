package org.example.sudoku;

import org.example.sudoku.ui.custom.button.frame.MainFrame;
import org.example.sudoku.ui.custom.button.panel.MainPanel;

import javax.swing.*;
import java.awt.*;

public class UIMain {
    public static void main(String[] args) {
        Dimension dimension = new Dimension(600, 600);
        JPanel panel = new MainPanel(dimension);
        JFrame main = new MainFrame(dimension, panel);
        main.revalidate();
        main.repaint();
    }
}
