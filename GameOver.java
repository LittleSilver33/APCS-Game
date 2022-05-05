/*
 * Author: Zachary Silverstein, Henry Yuan, Junoh Lee
 * Date: 5/21/2021
 * Purpose: Game
 */

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Color;

public class GameOver extends JFrame {
    private JFrame frame;
    private GamePanel panel;

    public GameOver() {
        frame = new JFrame("Game Over");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new GamePanel();
        panel.setPreferredSize(new Dimension(560, 720));
        panel.setBackground(new Color(0, 0, 255));

        frame.getContentPane().add(panel);
    }

    public void display() {
        frame.pack();
        frame.setVisible(true);
    }
}