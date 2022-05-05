/*
 * Author: Zachary Silverstein, Henry Yuan, Junoh Lee
 * Date: 5/21/2021
 * Purpose: Game
 */

public class Enemies {
    private int health;
    private int xCord;
    private int yCord;
    private int damage;
    private int speed;
    private boolean visible;

    public Enemies(int x, int y, int h, int d, int s) {
        health = h;
        xCord = x;
        yCord = y;
        damage = d;
        speed = s;
        visible = true;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getXCord() {
        return this.xCord;
    }

    public void setXCord(int xCord) {
        this.xCord = xCord;
    }

    public int getYCord() {
        return this.yCord;
    }

    public void setYCord(int yCord) {
        this.yCord = yCord;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visable) {
        this.visible = visable;
    }

}
