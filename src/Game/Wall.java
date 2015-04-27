package Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sun.javafx.geom.Vec2d;

public class Wall extends Sprite{
	private static String filename = "assets/wall.jpg";

	public Wall(BufferedImage texture, Vec2d initPosition, int speed,
			int rows, int columns, int FPS) {
		super(texture, initPosition, speed, rows, columns, FPS);
		
	}
	
	public void draw(Graphics g) {
		
	}
}
