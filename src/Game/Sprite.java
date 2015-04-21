package Game;

import com.sun.javafx.geom.Rectangle;
import com.sun.javafx.geom.Vec2d;
import com.sun.prism.Texture;

public class Sprite {
	private Texture texture;
	private Vec2d position;
	private int speed;
	private Vec2d velocity;
	private int rows;
	private int columns;
	private int framesPerSecond;
	private Rectangle boundingBox;
	private int currentFrame;
	private int totalFrames;
		
	public Sprite(){
		totalFrames = rows*columns;
	}
	public void Draw(long elapsedTime) {
		int imageWidth = texture.getContentWidth() / columns;
		int imageHeight = texture.getContentHeight() / rows;
		
		int currentRow = totalFrames / currentFrame;
		int currentColumn = totalFrames / currentFrame;
	}

	
	public int getNumWalls(){
		
		return 0;
	}
	
	public boolean isWall(){
		
		return false;
	}
}
