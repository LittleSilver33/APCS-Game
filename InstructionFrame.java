/*
 * Author: Zachary Silverstein, Henry Yuan, Junoh Lee
 * Date: 5/20/2021
 * Purpose: Bullet Hell Game
 */

import javax.swing.JFrame;
import java.awt.Dimension;

public class InstructionFrame extends JFrame {
    private static JFrame frame;
    private InstructionPanel panel;

    public InstructionFrame() {
        frame = new JFrame("Water Hell");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new InstructionPanel();
        panel.setPreferredSize(new Dimension(1875, 1000));

        frame.getContentPane().add(panel);
    }

    public static void changePanel(boolean b) {
        frame.setVisible(b);
    }

    public void display() {
        frame.pack();
        frame.setVisible(true);
    }
}