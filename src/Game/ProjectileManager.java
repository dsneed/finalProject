package Game;


import java.util.ArrayList;

import com.sun.javafx.geom.Vec2d;
import com.sun.prism.Texture;

public class ProjectileManager {
	private Texture texture;
	private Vec2d position;
	private Vec2d velocity;
	private int speed;
	private int rows;
	private int columns;
	private int FPS;
	private ArrayList<Projectile> projectilePath = new ArrayList<Projectile>(); // ArrayList to hold all the positions of the projectile
	private ArrayList<Projectile> projectiles;
	
	public ProjectileManager(Texture texture, Vec2d initPosition, Vec2d velocity,
			int speed, int rows, int columns, int FPS){
		this.texture = texture;
		this.position = initPosition;
		this.velocity = velocity;
		this.speed = speed;
		this.rows = rows;
		this.columns = columns;
		this.FPS = FPS;
	}
	
	public void Update(long elapsedTime) {
		
	}
	
	public void Draw() {
		
	}
	
	public void CreateProjectile() {
		Projectile p = new Projectile(texture, position, velocity, speed, rows, columns, FPS);
		projectiles.add(p);
	}
	
	public void DeleteProjectile(int index) {
		projectiles.remove(index);
	}
	
	
	public ArrayList<Projectile> ProjectilePath(int angle, int gravity, int velocity,Projectile intialProjectile){
		projectilePath = new ArrayList<Projectile>();
		return projectilePath;
	}
	
	public ArrayList<Projectile> getProjectilePath(){
		return projectilePath;
	}
	
	
	
	

}
