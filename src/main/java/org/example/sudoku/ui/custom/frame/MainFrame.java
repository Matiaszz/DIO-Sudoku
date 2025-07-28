package org.example.sudoku.ui.custom.frame;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame (final Dimension dimension, final JPanel panel){
        super("Sudoku");
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        // centralize
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.add(panel);
    }

}
