package Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sun.javafx.geom.Vec2d;

public class Weapon extends Sprite {
	private ProjectileManager projectileManager;
	private Player player;
	private int angle;
	
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
		super.Draw(g);
	}
	
	public void setAngle(int newAngle) {
		this.angle = newAngle;
	}
	public int getAngle() {
		return angle;
	}

}
