/*
 * Author: Zachary Silverstein, Henry Yuan, Junoh Lee
 * Date: 5/21/2021
 * Purpose: Game
 */

import javax.sound.sampled.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private int blocX; // Shark X location
    private int blocY; // Shark Y location
    private int lives;
    private int hit;
    private int missed;
    private int movement;
    private int level;
    private int score;
    private boolean slow; // Makes the crab move slower
    private boolean moved; // Doesnt start until move
    private boolean end;
    private Projectile p;
    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>(); // Tracks projectiles
    private ArrayList<Enemies> enemies = new ArrayList<Enemies>(); // Tracks enemies

    private Image Shark = new ImageIcon("Shark.png").getImage();
    private Image Jellyfish = new ImageIcon("jellyfish.png").getImage();
    private Image Crab = new ImageIcon("Crab.png").getImage();
    private Timer time = new Timer(5, this);
    private Image bkgrd = new ImageIcon("bgd.png").getImage();
    private Image gameover = new ImageIcon("gameover.png").getImage();
    private Image life = new ImageIcon("life.png").getImage();
    private Image lifeLost = new ImageIcon("lifelost.png").getImage();
    private Image startScreen = new ImageIcon("startScreen.png").getImage();
    private File audioFile = new File("song.wav"); // Plays song
    private AudioInputStream audioStream;
    private AudioFormat format;
    private DataLine.Info info;
    private static Clip audioClip;

    public GamePanel() {
        time.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        moved = false;
        hit = 0;
        missed = 0;
        movement = 0;
        level = 1;
        score = 0;
        blocX = 1875 / 2;
        blocY = 800;
        lives = 3;
        slow = true;
        end = false;
        try {
            audioStream = AudioSystem.getAudioInputStream(audioFile);
            format = audioStream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            audioClip = (Clip) AudioSystem.getLine(info);
            // Sets up audio
            audioClip.open(audioStream);
            audioClip.loop(-1);
            audioClip.start();
            // Plays and loops audio

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
        // Try Catch required for running audio files to prevent error
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!end) // Draws correct background and shark if playing the game
        {
            if (!moved && lives == 3) {
                g.drawImage(startScreen, 0, 0, null);
            } else {
                g.drawImage(bkgrd, 0, 0, null);
            }
            if (moved) {
                if (lives == 3) { // Draws the life counter
                    g.drawImage(life, 33, 900, null);
                    g.drawImage(life, 73, 900, null);
                    g.drawImage(life, 113, 900, null);
                } else if (lives == 2) {
                    g.drawImage(life, 33, 900, null);
                    g.drawImage(life, 73, 900, null);
                    g.drawImage(lifeLost, 113, 900, null);
                } else if (lives == 1) {
                    g.drawImage(life, 33, 900, null);
                    g.drawImage(lifeLost, 73, 900, null);
                    g.drawImage(lifeLost, 113, 900, null);
                }
            }
            if (moved)
                g.drawImage(Shark, blocX, blocY, null);
            for (int i = 0; i < projectiles.size(); i++) { // Draws the projectiles
                p = (Projectile) projectiles.get(i);
                g.setColor(Color.RED);
                g.fillRect(p.getProjX(), p.getProjY(), 4, 20);
            }

            for (int i = 0; i < enemies.size(); i++) { // Draws all of the enemies
                Enemies e = (Enemies) enemies.get(i);
                if (e instanceof Jellyfish) {
                    g.drawImage(Jellyfish, e.getXCord(), e.getYCord(), null);
                } else if (e instanceof Crab) {
                    g.drawImage(Crab, e.getXCord(), e.getYCord(), null);
                }
            }
        } else {
            gameOver(g);
        }
        for (int i = 0; i < enemies.size(); i++) { // Removes enemies if they are not meant to be there
            Enemies e = (Enemies) enemies.get(i);
            if (e.isVisible() == false) {
                enemies.remove(i);
                i--;
            }
        }
        for (int i = 0; i < projectiles.size(); i++) { // Removes projectiles when not supposed to be visible
            Projectile p = (Projectile) projectiles.get(i);
            if (p.projIsVisible() == false) {
                projectiles.remove(i);
            }
        }
        if (enemies.size() == 0) {
            if (moved) {
                for (int i = 0; i < level; i++) // Spawns crabs and jellyfish on the screen
                {
                    if (i % 5 == 0 && level % 5 == 0) {
                        enemies.add(new Crab((int) (Math.random() * 1720), 0));
                    } else {
                        enemies.add(new Jellyfish((int) (Math.random() * 1820), 0));
                    }
                }
                level++;
            }
            // counts the level passed
        }
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < projectiles.size(); i++) {
            for (int j = 0; j < enemies.size(); j++) {
                if (enemies.get(j) instanceof Jellyfish && (projectiles.get(i).getProjX() >= enemies.get(j).getXCord())
                        && (projectiles.get(i).getProjX() <= enemies.get(j).getXCord() + 41)
                        && (projectiles.get(i).getProjY() >= enemies.get(j).getYCord())
                        && (projectiles.get(i).getProjY() <= enemies.get(j).getYCord() + 57)) { // Checks hit detection
                    enemies.get(j).setVisible(false);
                    projectiles.get(i).setProjVisible(false);
                    score += 100;
                    hit++;
                } else if (enemies.get(j) instanceof Crab
                        && (projectiles.get(i).getProjX() >= enemies.get(j).getXCord())
                        && (projectiles.get(i).getProjX() <= enemies.get(j).getXCord() + 173)
                        && (projectiles.get(i).getProjY() >= enemies.get(j).getYCord())
                        && (projectiles.get(i).getProjY() <= enemies.get(j).getYCord() + 150)) { // Checks hit detection
                    if (enemies.get(j).getHealth() > 1) {
                        enemies.get(j).setHealth(enemies.get(j).getHealth() - 1); // Crab only disappears at 0 hit
                                                                                  // points
                        projectiles.get(i).setProjVisible(false);
                        hit++;
                    } else {
                        enemies.get(j).setVisible(false);
                        projectiles.get(i).setProjVisible(false);
                        score += 300;
                        hit++;
                    }
                }
            }
        }

        for (int i = 0; i < enemies.size(); i++) // removes a life if the enemy is below the bottom
        {
            if (enemies.get(i).getYCord() > 1010) {
                enemies.get(i).setVisible(false);
                lives -= enemies.get(i).getDamage();
            }
        }

        if (lives <= 0) {
            for (int i = 0; i < enemies.size(); i++) // Removes all enemies and projectiles if game ends
            {
                enemies.get(i).setVisible(false);
            }
            for (int i = 0; i < projectiles.size(); i++) {
                projectiles.get(i).setProjVisible(false);
            }
            end = true;
            lives = 1;
            moved = false;
        }

        for (int i = 0; i < projectiles.size(); i++) // Moves projectiles
        {
            Projectile inter = projectiles.get(i);
            inter.setProjY(inter.getProjY() - inter.getProjSpeedY());
            if (projectiles.get(i).getProjY() <= -20) {
                missed++;
                projectiles.get(i).setProjVisible(false);
            }
        }

        if (enemies.size() > 1) {
            for (int i = 0; i < enemies.size(); i++) // Make sure none of enemies are on top of each other
            {
                for (int j = 0; j < enemies.size(); j++) {
                    while (j != i && enemies.get(j) instanceof Jellyfish
                            && (enemies.get(i).getXCord() >= enemies.get(j).getXCord())
                            && (enemies.get(i).getXCord() <= enemies.get(j).getXCord() + 41)) {
                        enemies.get(j).setXCord((int) (Math.random() * 1820));
                    }
                    while (j != i && enemies.get(j) instanceof Crab
                            && (enemies.get(i).getXCord() >= enemies.get(j).getXCord())
                            && (enemies.get(i).getXCord() <= enemies.get(j).getXCord() + 173)) {
                        enemies.get(j).setXCord((int) (Math.random() * 1720));
                    }

                }
            }
        }
        if (moved) {
            for (int i = 0; i < enemies.size(); i++) // Moves the enemies down
            {
                if (enemies.get(i) instanceof Jellyfish) {
                    Enemies inter = enemies.get(i);
                    inter.setYCord(inter.getYCord() + inter.getSpeed());
                } else if (slow && enemies.get(i) instanceof Crab) {
                    Enemies inter = enemies.get(i);
                    inter.setYCord(inter.getYCord() + inter.getSpeed());
                }
            }
            slow = !slow;
        }
        repaint();
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (!moved && level == 0) {
            moved = true;
        }
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_LEFT) { // Move left
            movement++;
            if (blocX <= -45 && blocX >= -60) {
                blocX = 1870;
            } else if (blocX < -60) {
                blocX += 0;
            } else {
                blocX -= 12;
                moved = true;

            }
        }
        if (c == KeyEvent.VK_RIGHT) { // Move right
            movement++;
            if (blocX >= (1875) && blocX <= 1900) {
                blocX = -45;
            } else {
                blocX += 12;
                moved = true;

            }

        }
        if (c == KeyEvent.VK_UP) { // Move up
            movement++;
            if (blocY <= 0) {
                blocY += 0;
            } else {
                blocY -= 12;
                moved = true;

            }
        }
        if (c == KeyEvent.VK_DOWN) { // Move down
            movement++;
            if (blocY >= (1000 - 109)) {
                blocY += 0;
            } else {
                blocY += 12;
                moved = true;

            }

        }
        if (c == KeyEvent.VK_SPACE) { // Shoot projectile
            shoot();
            moved = true;
        }
        if (c == KeyEvent.VK_ENTER) {

            if (end) // Restarts the game
            {
                audioClip.stop();
                GameFrame.changePanel(false);
                Runner.main(null);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
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

    public void shoot() { // creates a projectile at the location of the shark's eyes
        Projectile p = new Projectile(blocX + 28, blocY + 5);
        projectiles.add(p);
    }

    public void gameOver(Graphics g) {
        // Displays all data from the previous game
        g.drawImage(gameover, 0, 0, null);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.drawString("Shots Hit: " + hit, 700, 500);
        g.drawString("Shots Missed: " + missed, 700, 550);
        g.drawString("Total Movements: " + movement, 1050, 500);
        g.drawString("Level Reached: " + (level - 1), 1050, 550);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.drawString("Score: " + score, 910, 620);
    }

    public static void stopAudio() {
        audioClip.stop();
    }
}