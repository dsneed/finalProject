package Game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.sun.javafx.geom.Vec2d;

public class Player extends Sprite {
	
	public Player(BufferedImage texture, Vec2d initPosition,
			int speed, int rows, int columns, int FPS) {
		super(texture, initPosition, speed, rows, columns, FPS);
	}
	
	public void Update(float elapsedTime, ArrayList<String> inputs) {
		velocity.x = 0;
		velocity.y = 0;
		if(inputs != null) {
			for(String i : inputs) {
				if(i.equals("w")) {
					velocity.y += -1;
				}
				else if(i.equals("a")) {
					velocity.x += -1;
				}
				else if(i.equals("s")) {
					velocity.y += 1;
				}
				else if(i.equals("d")) {
					velocity.x += 1;
				}
			}
		}
		

		double magnitude = Math.sqrt((velocity.x*velocity.x) + (velocity.y*velocity.y));
		if(magnitude != 0) {
			velocity.x /= magnitude;
			velocity.y /= magnitude;	
			
		}
		super.Update(elapsedTime);
	}

	public void adjustPosition() {
		position.y -= velocity.y*2;
		position.x -= velocity.x*2;
		//position.y -= velocity.y*3;
	}

}
