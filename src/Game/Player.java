package Game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.sun.javafx.geom.Vec2d;

public class Player extends Sprite {

	public Player(BufferedImage texture, Vec2d initPosition,
			int speed, int rows, int columns, int FPS) {
		super(texture, initPosition, speed, rows, columns, FPS);
		// TODO Auto-generated constructor stub
	}
	
	public void Update(float elapsedTime, ArrayList<String> inputs) {
		velocity.x = 0;
		velocity.y = 0;
		for(String i : inputs) {
			if(i.equals("w")) {
				// TODO: Nothing??
			}
			if(i.equals("a")) {
				velocity.x += -1;
			}
			if(i.equals("s")) {
				// TODO: Nothing??
			}
			if(i.equals("d")) {
				velocity.x += 1;
			}
		}
		super.Update(elapsedTime);
	}

}
