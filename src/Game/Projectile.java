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
		velocity.x = Math.cos(Math.toRadians(initAngle));
		velocity.y = Math.sin(Math.toRadians(initAngle));
		
		// Normalize
		double magnitude = Math.sqrt((velocity.x*velocity.x) + (velocity.y*velocity.y));
		if(magnitude != 0) {
			velocity.x /= magnitude;
			velocity.y /= magnitude;	
		}
	}
	
	@Override
	public void Update(float elapsedTime) {
		velocity.y += elapsedTime*GRAVITY_CONSTANT;
		System.out.println(elapsedTime);
		super.Update(elapsedTime);
	}
	
	
}
