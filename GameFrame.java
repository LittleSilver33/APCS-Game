/*
 * Author: Zachary Silverstein, Henry Yuan, Junoh Lee
 * Date: 5/20/2021
 * Purpose: Bullet Hell Game
 */

import javax.swing.JFrame;
import java.awt.Dimension;

public class GameFrame extends JFrame {
    private static JFrame frame;
    private static GamePanel panel;

    public GameFrame() {
        frame = new JFrame("Water Hell");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new GamePanel();
        panel.setPreferredSize(new Dimension(1875, 1000));
        frame.getContentPane().add(panel);
    }

    public void display() {
        frame.pack();
        frame.setVisible(true);
    }

    public static void changePanel(boolean b) {
        frame.setVisible(b);
    }
}