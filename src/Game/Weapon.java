package Game;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.sun.javafx.geom.Vec2d;

public class Weapon extends Sprite {
	private ProjectileManager projectileManager;
	private Player player;
	private int angle;
	private double locationX;
	private double locationY;
	
	public Weapon(Player player, ProjectileManager projectileManager, BufferedImage texture, Vec2d initPosition, int speed,
			int rows, int columns, int FPS) {
		super(texture, initPosition, speed, rows, columns, FPS);
		
		this.player = player;
		this.projectileManager = projectileManager;
	}
	
	public void FireShot() {
		projectileManager.CreateProjectile(angle);
	}
	
	public void Update(boolean shotFired, int angle, float elapsedTime) {
		this.angle = angle;
		this.velocity = player.velocity;
		if(shotFired) {
			FireShot();
		}
		
		projectileManager.Update(elapsedTime, this.position);
		super.Update(elapsedTime);
	}
	
	public void Draw(Graphics g) {
		projectileManager.Draw(g);
		double rotation = Math.toRadians(angle);
		this.setLocationX(texture.getWidth());
		this.setLocationY(texture.getHeight());
		AffineTransform tx = AffineTransform.getRotateInstance(rotation, this.getLocationX(), this.getLocationY());
		super.Draw(g, tx);
	}
	
	public void setAngle(int newAngle) {
		this.angle = newAngle;
	}
	public int getAngle() {
		return angle;
	}
	public ProjectileManager getProjectileManager() {
		return projectileManager;
	}

	public double getLocationX() {
		return locationX;
	}

	public void setLocationX(double locationX) {
		this.locationX = locationX;
	}

	public double getLocationY() {
		return locationY;
	}

	public void setLocationY(double locationY) {
		this.locationY = locationY;
	}

}
