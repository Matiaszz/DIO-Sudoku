package org.example.sudoku.ui.custom.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CheckGameStatusButton extends JButton {
    public CheckGameStatusButton(final ActionListener actionListener) {
        this.setText("Verify game");
        this.addActionListener(actionListener);
    }
}
