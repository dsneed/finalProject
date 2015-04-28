package Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.sun.javafx.geom.Rectangle;
import com.sun.javafx.geom.Vec2d;

public class Sprite {
	protected BufferedImage texture;
	protected Vec2d position;
	private int speed;
	protected Vec2d velocity;
	private int rows;
	private int columns;
	private int framesPerSecond;
	private Rectangle boundingBox;
	private int currentFrame;
	private int totalFrames;
	private float timeSinceLastFrame;
	private boolean isBlocked;
	private Weapon weapon;
		
	public Sprite(BufferedImage texture, Vec2d initPosition, int speed, int rows, int columns, int FPS) {
		this.texture = texture;
		this.position = initPosition;
		this.velocity = new Vec2d(0, 0);
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
	
	public void Update(float elapsedTime) {
		UpdateAnimation(elapsedTime);
		UpdatePosition(elapsedTime);
	}
	
	public void Draw(Graphics g) {
		int imageWidth = getWidth();
		int imageHeight = getHeight();
		
		int currentRow = currentFrame / columns;
		int currentColumn = currentFrame % columns;
		
		Image sourceRectangle = texture.getSubimage(imageWidth*currentColumn, imageHeight*currentRow, imageWidth, imageHeight);
		//Rectangle destinationRectangle = new Rectangle((int) position.x, (int) position.y, imageWidth, imageHeight);
		g.drawImage(sourceRectangle, (int)position.x, (int)position.y, null);
		//spriteBatch.Draw(texture, destinationRectangle, sourceRectangle);
	}
	public void Draw(Graphics g, AffineTransform tx) {
		int imageWidth = getWidth();
		int imageHeight = getHeight();
		
		int currentRow = currentFrame / columns;
		int currentColumn = currentFrame % columns;
		
		BufferedImage sourceRectangle = texture.getSubimage(imageWidth*currentColumn, imageHeight*currentRow, imageWidth, imageHeight);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(op.filter(sourceRectangle, null), (int)position.x, (int)position.y, null);
	}
	
	private void UpdateAnimation(float elapsedTime) {
		if(timeSinceLastFrame >= getNanoSecondsInFrame()) {
			currentFrame++;
			currentFrame = currentFrame % totalFrames;
			timeSinceLastFrame = 0;
		}
		timeSinceLastFrame += elapsedTime;
	}
	
	private void UpdatePosition(float elapsedTime) {
		Vec2d newPosition = new Vec2d();
		newPosition.x = position.x + (speed*velocity.x*elapsedTime)/(long)100000000;
		newPosition.y = position.y + speed*velocity.y*elapsedTime/(long)100000000;
		// Check for if touching edge of screen (movementBounds)
		
		position = newPosition;
		boundingBox = CreateBoundingBox(position);
		
	}
	
	private float getNanoSecondsInFrame() {
		return ((float)1/(float)framesPerSecond)*1000000000;
	}

	// Creates a new bounding box to define Sprite (for collision purposes) based on where the Sprite currently is
	private Rectangle CreateBoundingBox(Vec2d newPosition) {
		Rectangle boundingBox = new Rectangle((int)newPosition.x, (int)newPosition.y, getWidth(),
				getHeight());
		return boundingBox;
	}
	
	public int getNumWalls(){	
		return 0;
	}
	
	public boolean intersects(Sprite s) {
		Rectangle box1 = this.boundingBox;
		Rectangle box2 = s.getBoundingBox();
		
		if(!(box1.intersection(box2).isEmpty()))
			return true;
		return false;
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
	
	public Vec2d getVelocity() {
		return velocity;
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
