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
		
	public Sprite(Texture texture, Vec2d initPosition, Vec2d velocity, int speed, int rows, int columns, int FPS) {
		this.texture = texture;
		this.position = initPosition;
		this.velocity = velocity;
		this.speed = speed;
		this.rows = rows;
		this.columns = columns;
		this.framesPerSecond = FPS;
		boundingBox = CreateBoundingBox(position);
		totalFrames = rows*columns;
		currentFrame = 1;
		timeSinceLastFrame = 0;
	}
	
	public void Update(long elapsedTime) {
		UpdateAnimation(elapsedTime);
		UpdatePosition(elapsedTime);
	}
	
	//TODO: find a way to get a spritebatch or something to draw rectangles with.
	public void Draw(long elapsedTime) {
		int imageWidth = getWidth();
		int imageHeight = getHeight();
		
		int currentRow = totalFrames / currentFrame;
		int currentColumn = totalFrames % currentFrame;
		
		Rectangle sourceRectangle = new Rectangle(imageWidth*currentColumn, imageHeight*currentRow, imageWidth,
                imageHeight);
		Rectangle destinationRectangle = new Rectangle((int) position.x, (int) position.y, imageWidth, imageHeight);
		
		//spriteBatch.Draw(texture, destinationRectangle, sourceRectangle);
	}
	
	private void UpdateAnimation(long elapsedTime) {
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
		boundingBox = CreateBoundingBox(position);
	}
	
	private long getSecondsInFrame() {
		return (long)1/framesPerSecond;
	}

	// Creates a new bounding box to define Sprite (for collision purposes) based on where the Sprite currently is
	private Rectangle CreateBoundingBox(Vec2d newPosition) {
		Rectangle boundingBox = new Rectangle(getWidth(), getHeight(), getWidth() + (int)newPosition.x,
				getHeight() + (int)newPosition.y);
		return boundingBox;
	}
	
	public int getNumWalls(){	
		return 0;
	}
	
	public int getWidth() {
		return texture.getContentWidth() / columns;
	}
	public int getHeight() {
		return texture.getContentHeight() / rows;
	}
	
	public boolean isWall(){
		
		return false;
	}
}
