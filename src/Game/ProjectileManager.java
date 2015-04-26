package Game;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.sun.javafx.geom.Vec2d;

public class ProjectileManager {
	private BufferedImage texture;
	private Vec2d position;
	private Vec2d velocity;
	private int speed;
	private int rows;
	private int columns;
	private int FPS;
	private ArrayList<Projectile> projectilePath = new ArrayList<Projectile>(); // ArrayList to hold all the positions of the projectile
	private ArrayList<Projectile> projectiles;
	
	public ProjectileManager(BufferedImage texture, Vec2d initPosition, Vec2d velocity,
			int speed, int rows, int columns, int FPS){
		this.texture = texture;
		this.position = initPosition;
		this.velocity = velocity;
		this.speed = speed;
		this.rows = rows;
		this.columns = columns;
		this.FPS = FPS;
	}
	
	public void Update(long elapsedTime, Vec2d newPosition) {
		position = newPosition;
		for(Projectile p : projectiles) {
			p.Update(elapsedTime);
			if(p.isBlocked()) {
				DeleteProjectile(p);
			}
		}
	}

	public void Draw(Graphics g) {
		for(Projectile p : projectiles) {
			p.Draw(g);
		}
	}
	
	public void CreateProjectile() {
		Projectile p = new Projectile(texture, position, speed, rows, columns, FPS);
		projectiles.add(p);
	}
	
	public void DeleteProjectile(Projectile p) {
		projectiles.remove(p);
	}
	
	
	public ArrayList<Projectile> ProjectilePath(int angle, int gravity, int velocity,Projectile intialProjectile){
		projectilePath = new ArrayList<Projectile>();
		return projectilePath;
	}
	
	public ArrayList<Projectile> getProjectilePath(){
		return projectilePath;
	}
	
	public ArrayList<Projectile> getProjectiles(){
		return projectiles;
	}

}
