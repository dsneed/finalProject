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
	private long timeSinceLastFrame;
		
	public Sprite(){
		totalFrames = rows*columns;
		currentFrame = 1;
		timeSinceLastFrame = 0;
	}
	
	public void Update(long elapsedTime) {
		UpdateAnimation(elapsedTime);
		UpdatePosition(elapsedTime);
	}
	public void Draw(long elapsedTime) {

	}
	
	private void UpdateAnimation(long elapsedTime) {
		int imageWidth = texture.getContentWidth() / columns;
		int imageHeight = texture.getContentHeight() / rows;
		
		int currentRow = totalFrames / currentFrame;
		int currentColumn = totalFrames % currentFrame;
		
		if(timeSinceLastFrame >= getSecondsInFrame()) {
			currentFrame++;
			currentFrame = totalFrames % currentFrame;
			timeSinceLastFrame = 0;
		}
		timeSinceLastFrame += elapsedTime;
	}
	private void UpdatePosition(long elapsedTime) {
		Vec2d newPosition = new Vec2d();
		newPosition.x = position.x + speed*velocity.x*elapsedTime;
		newPosition.y = position.y + speed*velocity.y*elapsedTime;
		// Check for if touching edge of screen (movementBounds)
		position = newPosition;
	}
	
	private long getSecondsInFrame() {
		return (long)1/framesPerSecond;
	}

	
	public int getNumWalls(){
		
		return 0;
	}
	
	public boolean isWall(){
		
		return false;
	}
}
