package Game;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.sun.javafx.geom.Vec2d;

public class Weapon extends Sprite {
	private ProjectileManager projectileManager;
	private Player player;
	private int angle;
	private int locationX;
	private int locationY;
	
	
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
		this.position = player.position;
		setIsBlocked(true);
		if(shotFired) {
			FireShot();
		}
		
		projectileManager.Update(elapsedTime, this.position);
		super.Update(elapsedTime);
	}
	
	public void Draw(Graphics g) {
		projectileManager.Draw(g);
		double rotation = Math.toRadians(angle);
		this.setLocationX(texture.getWidth() / 2);
		this.setLocationY(texture.getHeight() / 2);
		AffineTransform tx = AffineTransform.getRotateInstance(rotation, getLocationX(), getLocationY());
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

	public int getLocationX() {
		return locationX;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}

}
