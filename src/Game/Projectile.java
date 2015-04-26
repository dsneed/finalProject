package Game;

import java.awt.image.BufferedImage;

import com.sun.javafx.geom.Vec2d;

public class Projectile extends Sprite {
	public int initAngle;
	public Projectile(int angle, BufferedImage texture, Vec2d initPosition,
			int speed, int rows, int columns, int FPS) {
		super(texture, initPosition, speed, rows, columns, FPS);
		
		this.initAngle = angle;
		this.velocity.x = 1;	// TODO: Delete because only for testing
	}
	private int row = 0;
	private int col = 0;
	
	

	
	
}
