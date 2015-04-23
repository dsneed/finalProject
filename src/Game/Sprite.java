package Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import com.sun.javafx.geom.Rectangle;
import com.sun.javafx.geom.Vec2d;

public class Sprite {
	private BufferedImage texture;
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
	private boolean isBlocked;
		
	public Sprite(BufferedImage texture, Vec2d initPosition, Vec2d velocity, int speed, int rows, int columns, int FPS) {
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
		isBlocked = false;
	}
	
	public void Update(long elapsedTime) {
		UpdateAnimation(elapsedTime);
		UpdatePosition(elapsedTime);
	}
	
	//TODO: find a way to get a spriteBatch or something to draw rectangles with.
	public void Draw(Graphics g) {
		int imageWidth = getWidth();
		int imageHeight = getHeight();
		
		int currentRow = totalFrames / currentFrame;
		int currentColumn = totalFrames % currentFrame;
		
		Image sourceRectangle = texture.getSubimage(imageWidth*currentColumn, imageHeight*currentRow, imageWidth,
                imageHeight);
		//Rectangle destinationRectangle = new Rectangle((int) position.x, (int) position.y, imageWidth, imageHeight);
		g.drawImage(sourceRectangle, (int)position.x, (int)position.y, null);
		
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
	
	public boolean intersects(Sprite s) {
		//One rectangle is on left of the other
		if(this.boundingBox.x > (s.getBoundingBox().x+s.getBoundingBox().width) || 
				s.getBoundingBox().x > (this.boundingBox.x + this.boundingBox.width))
			return false;
		//One rectangle is on top of the other
		if(this.boundingBox.y < (s.getBoundingBox().y+s.getBoundingBox().height) || 
				s.getBoundingBox().y < (this.boundingBox.y + this.boundingBox.height))
			return false;
		return true;
	}
	
	public int getWidth() {
		return texture.getWidth() / columns;
	}
	public int getHeight() {
		return texture.getHeight() / rows;
	}
	public Rectangle getBoundingBox() {
		return boundingBox;
	}
	
	public void setIsBlocked(boolean newVal) {
		isBlocked = newVal;
	}
	public boolean isBlocked() {
		return isBlocked;
	}
	
	public boolean isWall(){
		
		return false;
	}
}
