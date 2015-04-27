package Game;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.sun.javafx.geom.Vec2d;

public class EnemyManager {
	private BufferedImage texture;
	//private Vec2d position;
	private Vec2d velocity;
	private int speed;
	private int rows;
	private int columns;
	private int FPS;
	private ArrayList<Enemy> EnemyPath = new ArrayList<Enemy>(); // ArrayList to hold all the positions of the Enemy
	private ArrayList<Enemy> enemies;
	
	public EnemyManager(BufferedImage texture, Vec2d initPosition,
			int speed, int rows, int columns, int FPS){
		this.texture = texture;
		//this.position = initPosition;
		this.velocity = new Vec2d(0, 0);
		this.speed = speed;
		this.rows = rows;
		this.columns = columns;
		this.FPS = FPS;
		enemies = new ArrayList<Enemy>();
	}
	
	public void Update(float elapsedTime) {
		//position = newPosition;
		for(Enemy e : enemies) {
			e.Update(elapsedTime);
			if(e.isDead()) {
				DeleteEnemy(e);
				return;
			}
		}
	}

	public void Draw(Graphics g) {
		for(Enemy e : enemies) {
			e.Draw(g);
		}
	}
	
	public void CreateEnemy(Vec2d position) {
		Enemy p = new Enemy(texture, position, speed, rows, columns, FPS);
		enemies.add(p);
	}
	
	public void DeleteEnemy(Enemy p) {
		enemies.remove(p);
	}
	
	
	public ArrayList<Enemy> EnemyPath(int angle, int gravity, int velocity,Enemy intialEnemy){
		EnemyPath = new ArrayList<Enemy>();
		return EnemyPath;
	}
	
	public ArrayList<Enemy> getEnemyPath(){
		return EnemyPath;
	}
	
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}

}
