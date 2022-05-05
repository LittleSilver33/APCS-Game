/*
 * Author: Zachary Silverstein, Henry Yuan, Junoh Lee
 * Date: 5/21/2021
 * Purpose: Game
 */

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class InstructionPanel extends JPanel implements ActionListener, KeyListener {
    private int blocX;
    private int blocY;
    private boolean step1;
    private boolean step2;
    private boolean showJ;
    private boolean showC;
    private boolean step3;
    private boolean step4;
    private boolean slow;
    private Crab crabby;
    private Jellyfish jelly;
    private Projectile p;
    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    private Image life = new ImageIcon("Game/life.png").getImage();
    private Image Shark = new ImageIcon("Game/Shark.png").getImage();
    private Image Jellyfish = new ImageIcon("Game/jellyfish.png").getImage();
    private Image Crab = new ImageIcon("Game/Crab.png").getImage();
    private Timer time = new Timer(5, this);
    private Image bkgrd = new ImageIcon("Game/bgd1.png").getImage();
    private Image bkgrd2 = new ImageIcon("Game/bgd2.png").getImage();
    private Image bkgrd3 = new ImageIcon("Game/bgd3.png").getImage();
    private Image bkgrd4 = new ImageIcon("Game/bgd4.png").getImage();

    public InstructionPanel() {
        time.start();
        slow = true;
        step1 = true;
        step2 = false;
        step3 = false;
        step4 = false;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        blocX = 1875 / 4 - 150;
        blocY = 800;
        jelly = new Jellyfish(1400, 0);
        crabby = new Crab(1875 / 4, 0);
        showJ = false;
        showC = false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (step1) { // Shows step 1
            g.drawImage(bkgrd, 0, 0, null);
        }
        if (step2) { // Shows step 2
            g.drawImage(bkgrd2, 0, 0, null);
            if (showJ)
                g.drawImage(Jellyfish, jelly.getXCord(), jelly.getYCord(), null);
        }
        if (step3) { // Shows step 3
            g.drawImage(bkgrd3, 0, 0, null);
            g.drawImage(Crab, crabby.getXCord(), crabby.getYCord(), null);
        }
        if (step4) // Shows step 4
        {
            g.drawImage(bkgrd4, 0, 0, null);
            g.drawImage(life, 33, 900, null);
            g.drawImage(life, 73, 900, null);
            g.drawImage(life, 113, 900, null);
        }
        if (step1 || step2 || step3) // If shark is needed, it is drawn
            g.drawImage(Shark, blocX, blocY, null);

        for (int i = 0; i < projectiles.size(); i++) { // Draws projectiles
            p = (Projectile) projectiles.get(i);
            g.setColor(Color.RED);
            g.fillRect(p.getProjX(), p.getProjY(), 4, 20);
        }
        for (int i = 0; i < projectiles.size(); i++) { // Removes projectiles that shouldn't be seen
            Projectile p = (Projectile) projectiles.get(i);
            if (p.projIsVisible() == false) {
                projectiles.remove(i);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (showJ) { // If the Jellyfish is shown, it checks for hit detection
            for (int i = 0; i < projectiles.size(); i++) {
                if ((projectiles.get(i).getProjX() >= jelly.getXCord())
                        && (projectiles.get(i).getProjX() <= jelly.getXCord() + 41)
                        && (projectiles.get(i).getProjY() >= jelly.getYCord())
                        && (projectiles.get(i).getProjY() <= jelly.getYCord() + 57)) {
                    showJ = false;
                    projectiles.remove(i);
                    step2 = false;
                    step3 = true;
                    showC = true;
                }
            }
        }
        if (showC) { // If the Crab is shown, it checks for hit detection
            for (int i = 0; i < projectiles.size(); i++) {
                if ((projectiles.get(i).getProjX() >= crabby.getXCord())
                        && (projectiles.get(i).getProjX() <= crabby.getXCord() + 173)
                        && (projectiles.get(i).getProjY() >= crabby.getYCord())
                        && (projectiles.get(i).getProjY() <= crabby.getYCord() + 150)) {
                    crabby.setHealth(crabby.getHealth() - 1);
                    projectiles.remove(i);
                }
            }
        }

        if (crabby.getHealth() <= 0) { // Moves on to 4th stage of instruction when the Crab is killed
            showC = false;
            step3 = false;
            step4 = true;
        }

        for (int i = 0; i < projectiles.size(); i++) { // Updates projectiles
            Projectile inter = projectiles.get(i);
            inter.setProjY(inter.getProjY() - inter.getProjSpeedY());
        }

        if (blocX >= 1750 && blocX <= 1800 && step1) { // Moves to the 2nd stage of instructions
            step1 = false;
            step2 = true;
            showJ = true;
        }

        if (step2 && showJ) { // Updates Jellyfish
            jelly.setYCord(jelly.getYCord() + jelly.getSpeed());
        }
        if (step3 && showC) { // Moves the Crab down at half speed
            if (slow)
                crabby.setYCord(crabby.getYCord() + crabby.getSpeed());
            slow = !slow;
        }

        repaint();
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_LEFT) { // Move left
            if (blocX <= -45 && blocX >= -60) {
                blocX = 1870;
            } else if (blocX < -60) {
                blocX += 0;
            } else {
                if (step1 || step2 || step3)
                    blocX -= 12;
            }
        }
        if (c == KeyEvent.VK_RIGHT) { // Move right
            if (blocX >= (1875) && blocX <= 1900) {
                blocX = -45;
            } else if (blocX > 1900) {
                blocX += 0;
            } else {
                if (step1 || step2 || step3)
                    blocX += 12;
            }

        }
        if (c == KeyEvent.VK_UP) { // Move up
            if (blocY <= 0) {
                blocY += 0;
            } else {
                if (step1 || step2 || step3)
                    blocY -= 12;
            }
        }
        if (c == KeyEvent.VK_DOWN) { // Move down
            if (blocY >= (1000 - 109)) {
                blocY += 0;
            } else {
                if (step1 || step2 || step3)
                    blocY += 12;
            }

        }
        if (c == KeyEvent.VK_SPACE) { // Projectile
            shoot();
        }
        if (c == KeyEvent.VK_ENTER) { // Ends instructions
            if (step4) {
                GamePanel.stopAudio();
                InstructionFrame.changePanel(false);
                Runner.main(null);
            }
        }
    }

    public void keyReleased(KeyEvent e) { // Stops movement
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_LEFT) {
            blocX += 0;
        }
        if (c == KeyEvent.VK_RIGHT) {
            blocX += 0;
        }
        if (c == KeyEvent.VK_UP) {
            blocY += 0;
        }
        if (c == KeyEvent.VK_DOWN) {
            blocY += 0;
        }
    }

    public void shoot() { // Shoot
        Projectile p = new Projectile(blocX + 28, blocY + 5);
        projectiles.add(p);
    }
}