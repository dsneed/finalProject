package Game;

import com.sun.javafx.geom.Vec2d;
import com.sun.prism.Texture;

public class Projectile extends Sprite {
	public Projectile(Texture texture, Vec2d initPosition, Vec2d velocity,
			int speed, int rows, int columns, int FPS) {
		super(texture, initPosition, velocity, speed, rows, columns, FPS);
		// TODO Auto-generated constructor stub
	}
	private int row = 0;
	private int col = 0;
	
	

	
	
}
