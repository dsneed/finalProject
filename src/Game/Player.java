package Game;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.sun.javafx.geom.Vec2d;

public class Player extends Sprite {
	
	public Player(BufferedImage texture, Vec2d initPosition,
			int speed, int rows, int columns, int FPS) {
		super(texture, initPosition, speed, rows, columns, FPS);
	}
	
	public void Update(float elapsedTime, ArrayList<String> inputs) {
		Vec2d oldVelocity = new Vec2d();
		oldVelocity.x = velocity.x;
		velocity.x = 0;
		velocity.y = 0;
		if(inputs != null) {
			for(String i : inputs) {
				if(i.equals("w")) {
					// TODO: Nothing??
				}
				else if(i.equals("a")) {
					velocity.x += -1;
				}
				else if(i.equals("s")) {
					// TODO: Nothing??
				}
				else if(i.equals("d")) {
					velocity.x += 1;
				}
			}
		}
		
		
		if((velocity.x < 0 && oldVelocity.x >= 0) ||
				(velocity.x > 0 && oldVelocity.x <= 0)) {
			setIsBlocked(false);
		}
		
		double magnitude = Math.sqrt((velocity.x*velocity.x) + (velocity.y*velocity.y));
		if(magnitude != 0) {
			velocity.x /= magnitude;
			velocity.y /= magnitude;	
		}
		super.Update(elapsedTime);
	}

	public void adjustPosition() {
		//position.y -= velocity.y;
		if(velocity.x != 0)
			position.x -= velocity.x/Math.abs(velocity.x);
	}
	
	public void Draw(Graphics g) {
		AffineTransform  tx = AffineTransform.getScaleInstance(-1,  1);
		tx.translate(-texture.getWidth()/4, 0);
		super.Draw(g, tx);
	}

}
