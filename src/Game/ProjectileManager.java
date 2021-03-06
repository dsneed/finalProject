package Game;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import com.sun.javafx.geom.Vec2d;

public class ProjectileManager {
	private BufferedImage texture;
	private Vec2d position;
	private int speed;
	private int rows;
	private int columns;
	private int FPS;
	private ArrayList<Projectile> projectilePath = new ArrayList<Projectile>(); // ArrayList to hold all the positions of the projectile
	private ArrayList<Projectile> projectiles;
	
	public ProjectileManager(BufferedImage texture, Vec2d initPosition,
			int speed, int rows, int columns, int FPS){
		this.texture = texture;
		this.position = initPosition;
		this.speed = speed;
		this.rows = rows;
		this.columns = columns;
		this.FPS = FPS;
		
		projectiles = new ArrayList<Projectile>();
	}
	
	public void Update(float elapsedTime, Vec2d newPosition) {
		position = newPosition;
		for(Projectile p : projectiles) {
			p.Update(elapsedTime);
			if(p.isBlocked()) {
				DeleteProjectile(p);
				return;
			}
		}
	}

	public void Draw(Graphics g) {
		if(projectiles.isEmpty())
			return;
		for(Projectile p : projectiles) {
			p.Draw(g);
		}
	}

	public void CreateProjectile(int angle) {
		projectiles.add(new Projectile(angle, texture, position, speed, rows, columns, FPS));
	}

	public void DeleteProjectile(Projectile p) {
		projectiles.remove(p);
	}


	public ArrayList<Projectile> ProjectilePath(int angle, int gravity, int velocity, Projectile intialProjectile){
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
