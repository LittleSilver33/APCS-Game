/*
 * Author: Zachary Silverstein, Henry Yuan, Junoh Lee
 * Date: 5/21/2021
 * Purpose: Game
 */

public class Projectile {
	private int projX, projY, speedY; // Projectile location and speed
	private boolean visible;

	public Projectile(int startX, int startY) {
		projX = startX;
		projY = startY;
		speedY = 7;
		visible = true;

	}

	public int getProjX() {
		return projX;
	}

	public int getProjY() {
		return projY;
	}

	public int getProjSpeedY() {
		return speedY;
	}

	public boolean projIsVisible() {
		return visible;
	}

	public void setProjY(int y) {
		this.projY = y;
	}

	public void setProjSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public void setProjVisible(boolean visible) {
		this.visible = visible;
	}

}