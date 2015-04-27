package Game;

import java.awt.image.BufferedImage;

import com.sun.javafx.geom.Vec2d;

public class Enemy extends Sprite {
	private boolean isDead;

	public Enemy(BufferedImage texture, Vec2d initPosition,
			int speed, int rows, int columns, int FPS) {
		super(texture, initPosition, speed, rows, columns, FPS);
		isDead = false;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
}
