package Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.sun.javafx.geom.Vec2d;

public class WallManager {
	private BufferedImage texture;
	//private Vec2d position;
	private Vec2d velocity;
	private int speed;
	private int rows;
	private int columns;
	private int FPS;
	private ArrayList<Wall> walls;
	
	public WallManager(BufferedImage texture, Vec2d initPosition,
			int speed, int rows, int columns, int FPS){
		this.texture = texture;
		//this.position = initPosition;
		this.velocity = new Vec2d(0, 0);
		this.speed = speed;
		this.rows = rows;
		this.columns = columns;
		this.FPS = FPS;
		walls = new ArrayList<Wall>();
	}
	
	public void Update(float timeElapsed) {
		for(Wall w : walls) {
			w.Update(timeElapsed);
		}
	}
	public void Draw(Graphics g) {
		for(Wall w : walls) {
			w.Draw(g);
		}
	}
	
	public void CreateWall(Vec2d position) {
		walls.add(new Wall(texture, position, speed, rows, columns, FPS));
	}
	
	public ArrayList<Wall> getWalls() {
		return walls;
	}
}
