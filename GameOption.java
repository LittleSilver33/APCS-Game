/*
 * Author: Zachary Silverstein, Henry Yuan, Junoh Lee
 * Date: 5/11/2021
 * Purpose: Game
 */

import javax.swing.*;

public class GameOption {
    private GameFrame game;
    private InstructionFrame instruction;
    private int x;

    public GameOption() {
        game = new GameFrame();
        instruction = new InstructionFrame();
        x = 0;
    }

    public void start() {
        String[] options = { "Play", "Instructions", "Cancel" }; // initial menu
        x = JOptionPane.showOptionDialog(null, "Please Select an Option.", "Water Hell", JOptionPane.YES_OPTION,
                JOptionPane.NO_OPTION, null, options, options[0]);
        if (x == JOptionPane.YES_OPTION) {
            game.display(); // Start game
        } else if (x == JOptionPane.NO_OPTION) {
            instruction.display(); // Instruction Screen
        } else {
            System.exit(0); // Cancel
        }
    }
}