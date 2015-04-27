package Game;

import java.awt.image.BufferedImage;

import com.sun.javafx.geom.Vec2d;

public class Projectile extends Sprite {
	public int initAngle;
	public Projectile(int angle, BufferedImage texture, Vec2d initPosition,
			int speed, int rows, int columns, int FPS) {
		super(texture, initPosition, speed, rows, columns, FPS);
		
		this.initAngle = angle;
		calculateVelocityFromAngle();
	}
	private int row = 0;
	private int col = 0;
	
	private void calculateVelocityFromAngle() {
		velocity.x = Math.cos((double)initAngle);
		velocity.y = Math.sin((double)initAngle);
		
		// Normalize
		double magnitude = Math.sqrt((velocity.x*velocity.x) + (velocity.y*velocity.y));
		if(magnitude != 0) {
			velocity.x /= magnitude;
			velocity.y /= magnitude;	
		}
	}
	
	public void Update(float elapsedTime) {
		super.Update(elapsedTime);
	}
	
	
}
