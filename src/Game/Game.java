package Game;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.sun.javafx.geom.Rectangle;
import com.sun.javafx.geom.Vec2d;

public class Game extends JPanel {
	public static int MAX_CELLS = 100;
	public static int CELL_LENGTH = 30;
	public static int SCREEN_X = 1000;
	public static float FPS = 15;
	
	//private Timer timer; 
	private int numRows;
	private int numCols;
	private int numWalls;
	private String mapFileName;
	private String playerFileName;
	private String enemyFileName;
	private String projectileFileName;
	private String weaponFileName;
	private String wallFileName;
	private String[][] layout = new String[MAX_CELLS][MAX_CELLS];
	private Player cat;
	private EnemyManager enemyManager;
	private Weapon slingshot;
	private ArrayList<String> inputs;
	private boolean shouldClearInputs;	// boolean so that event listener and game loop don't interfere
	LinkedList<KeyEvent> keyQueue;
	LinkedList<MouseEvent> mouseQueue;
	private long previousTime;
	private WeaponListener weaponListener;
	private boolean shotFired;
	private boolean freeMove;
	private CollisionManager collisionManager;
	private WallManager wallManager;
	private boolean mouseDown;
	private Rectangle background;
	
	public Game(String mapFile, String playerFile, String enemyFile, String projectileFile, String weaponFile, String wallFile){
		this.mapFileName = mapFile;
		this.playerFileName = playerFile;
		this.enemyFileName = enemyFile;
		this.projectileFileName = projectileFile;
		this.weaponFileName = weaponFile;
		this.wallFileName = wallFile;
		this.inputs = new ArrayList<String>();
		this.previousTime = System.currentTimeMillis();
		this.addKeyListener(new InputListener(this));
		this.weaponListener = new WeaponListener(this);
		this.addMouseListener(weaponListener);
		this.addMouseMotionListener(weaponListener);
		shotFired = false;
		keyQueue = new LinkedList<KeyEvent>();
		mouseQueue = new LinkedList<MouseEvent>();
		loadConfigFiles();
		LoadComponents();
		mouseDown = false;
		this.collisionManager = new CollisionManager(wallManager.getWalls(), enemyManager.getEnemies(), cat, slingshot.getProjectileManager());
		this.background = new Rectangle(0, 0, numCols*CELL_LENGTH, numRows*CELL_LENGTH);
		freeMove = false;
	}

	// Call all objects that need to be updated while keeping track of clock and inputs.
	public void Update(long currentTime) {
		long timeElapsed = (long)(currentTime - previousTime);
		processInputs();
		collisionManager.Update();
		wallManager.Update(timeElapsed);
		cat.Update(timeElapsed, getInputs());
		int angle = calculateAngle(weaponListener.getinitPos(), weaponListener.getCurrentPos());
		slingshot.Update(shotFired, angle, timeElapsed);

		if(shotFired) {
			shotFired = false;
			weaponListener.resetValues();
		}
		enemyManager.Update(timeElapsed);
		scrollingAdjust(timeElapsed);
		if((timeElapsed) >= 1.0f/(float)FPS) {
			repaint();
			timeElapsed = 0;
			previousTime = currentTime;
		}
	}
	
	private void scrollingAdjust(float elapsedTime) {
		Vec2d velocity = cat.getVelocity();
		velocity.x *= cat.getSpeed();
		velocity.y *= cat.getSpeed();
		float xPosition = cat.getBoundingBox().x;
		
/*		if(Math.abs(background.x) <= 5) {
			if(cat.getBoundingBox().x <= SCREEN_X/2) {
				freeMove = true;
			}
		}
		
		else if (Math.abs(background.x) - (background.width - SCREEN_X) >= 5) {
			if(cat.getBoundingBox().x >= SCREEN_X/2) {
				freeMove = true;
			}
		}
		else freeMove = false; */
		
		if(!cat.isBlocked() && !freeMove) {
			cat.adjustPosition(elapsedTime, velocity);
			for(Wall w : wallManager.getWalls()) {
				w.adjustPosition(elapsedTime, velocity);
			}
			for(Enemy e : enemyManager.getEnemies()) {
				e.adjustPosition(elapsedTime, velocity);
			}
			slingshot.adjustPosition(elapsedTime, velocity);
			//for(Projectile p : slingshot.getProjectileManager().getProjectiles()) {
			//	p.adjustPosition(elapsedTime, velocity);
			//}
			
			background.x -= elapsedTime*velocity.x/(long)100000000;
		}
	
	}
	
	public void loadConfigFiles() {
		try {
			fillLayout(mapFileName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void LoadComponents() {
		loadCat();	// Load Player
		loadEnemyManager();
		loadWallManager();
		loadWeapon();
		// Load enemies and wall
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				if(layout[i][j].equals("T")) {
					enemyManager.CreateEnemy(new Vec2d(j*CELL_LENGTH, i*CELL_LENGTH));
				}
				else if (layout[i][j].equals("W")) {
					wallManager.CreateWall(new Vec2d(j*CELL_LENGTH, i*CELL_LENGTH));
				}
			}
		}
	}
	
	public void loadWeapon() {
		// Load shot
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(projectileFileName));
		} catch(IOException e) {
			System.out.println("Projectile file not found.");
		}
		
		ProjectileManager projectileManager = new ProjectileManager(img, new Vec2d(50, 50), 140, 1, 1, (int)FPS);
		
		// Load weapon
		BufferedImage img2 = null;
		try {
			img2 = ImageIO.read(new File(weaponFileName));
		} catch(IOException e) {
			System.out.println("Weapon file not found.");
		}
		slingshot = new Weapon(cat, projectileManager, img2, new Vec2d(cat.getBoundingBox().x + cat.getBoundingBox().width/2, cat.getBoundingBox().y + cat.getBoundingBox().height/2), 75, 1, 1, (int)FPS);		
	}

	//For testing Sprite
	public void loadCat() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(playerFileName));
		} catch(IOException e) {
			System.out.println("What?!");
		}
		cat = new Player(img, new Vec2d(200, numRows*CELL_LENGTH - img.getHeight()/2 - 100), 75, 1, 4, (int)FPS/2);
	}
	
	public void loadEnemyManager() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(enemyFileName));
		} catch(IOException e) {
			System.out.println("What?!");
		}
		enemyManager = new EnemyManager(img, new Vec2d(50, 50), 50, 2, 5, (int)FPS);
	}
	
	public void loadWallManager() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(wallFileName));
		} catch(IOException e) {
			System.out.println("What?!");
		}
		wallManager = new WallManager(img, new Vec2d(50, 50), 100, 1, 1, (int)FPS);
	}

	public void fillLayout(String fileName) throws BadConfigFormatException {
		FileReader fileIn = null;
		try {
			fileIn = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		Scanner scan = new Scanner(fileIn);
		String scannedData = "";
		int row = 0;
		String[] cells;
		int column = 0;
		numWalls = 0;
		
		while(scan.hasNextLine()) {
			scannedData = scan.nextLine();
			cells = scannedData.split(",");
			
			column = 0;
			numCols = 0;
			for(String str: cells) {
				if(str.equals("W")) {
					layout[row][column] = new String("W");
					numWalls++;
				}
				else if(str.equals("T")) {
					layout[row][column] = new String("T");
				}
				else if(str.equals("A")) {
					layout[row][column] = new String("A");
				}
				else {
					throw new BadConfigFormatException("Unrecognized objects in map file");
				}
				column++;
			}
			if(numCols != 0 && numCols != column) {
				throw new BadConfigFormatException("Uneven rows");
			}
			numCols = column;
			
			row++;
			numRows++;
		}
		scan.close();
		try {
			fileIn.close();
		} catch (IOException e) {
			System.out.println("IOException");
		}
	}
	
	public int calculateAngle(int initPos, int newPos) {
		return (newPos - initPos)/5;
	}
	
	public void addEvent(KeyEvent e) {
		keyQueue.add(e);
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numCols;
	}
	
	public boolean isWallAt(int row, int col) {
		if (layout[row][col].equals("W")) {
			return true;
		}
		return false;
	}
	
	public int getNumWalls() {
		return numWalls;
	}
	
	public void shotFired(boolean val) {
		shotFired = val;
	}
	
	public void setMouse(boolean val) {
		mouseDown = val;
	}
	
	public ArrayList<String> getInputs() {
		return inputs;
	}
	
	public EnemyManager getEnemyManager() {
		return enemyManager;
	}
	
	public boolean shouldClearInputs() {
		return shouldClearInputs;
	}
	public void clearInputs(boolean val) {
		shouldClearInputs = val;
	}
	// Accept event inputs from queue
	public void processInputs() {
		if(shouldClearInputs) {
			inputs.clear();
			shouldClearInputs = false;
			return;
		}
		if(!keyQueue.isEmpty()) {
			inputs.add(Character.toString(keyQueue.pop().getKeyChar()));
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.CYAN);
		g.fillRect(background.x, background.y, background.width, background.height);
		cat.Draw(g);
		slingshot.Draw(g);
		enemyManager.Draw(g);
		wallManager.Draw(g);
		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(slingshot.getAngle()*-1),(int)slingshot.position.x,(int)slingshot.position.y);

		if(mouseDown) {
			g.drawLine((int)slingshot.position.x + slingshot.getLocationX(), (int)slingshot.position.y + slingshot.getLocationY(),(int)slingshot.position.x * 200, (int)slingshot.position.y );
			if(slingshot.getAngle() < 90) {
				int positionX = ((int)slingshot.position.x + 2000);
				int positionY = (int) ((int)slingshot.position.y + positionX*Math.tan(Math.toRadians(slingshot.getAngle())));
				g.drawLine((int)((int)slingshot.position.x + slingshot.getLocationX()) , (int)(slingshot.position.y + slingshot.getLocationY()),positionX,positionY );
			}
			else if (slingshot.getAngle() >= 90) {
				int positionY = (int) ((int)slingshot.position.y - 2000);
				int positionX = (int) ((int)slingshot.position.x - positionY*Math.tan(Math.toRadians(slingshot.getAngle())));
				g.drawLine((int)((int)slingshot.position.x + slingshot.getLocationX()) , (int)(slingshot.position.y + slingshot.getLocationY()),
						positionX,positionY );

			}
		}

	}

	// Listen for movement input, which is then placed in a queue to be processed
	// in the game loop.
	private class InputListener implements KeyListener {
		private Game game;
		
		InputListener(Game game) {
			super();
			this.game = game;
		}

		@Override
		public void keyTyped(KeyEvent e) { }

		@Override
		public void keyPressed(KeyEvent e) {
			game.addEvent(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			game.clearInputs(true);
		}
	}
	
	// Listen for aiming weapon and shooting projectile, which can then be grabbed
	// from this class in the game loop
	private class WeaponListener implements MouseInputListener {
		private Game game;
		private int initYPos;
		private int newYPos;
		
		WeaponListener(Game game) {
			super();
			this.game = game;
			initYPos = 0;
			newYPos = 0;
		}

		@Override
		public void mouseClicked(MouseEvent e) {		}

		@Override
		public void mousePressed(MouseEvent e) {
			initYPos = e.getY();
			newYPos = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			game.shotFired(true);
			game.setMouse(false);
		}

		@Override
		public void mouseEntered(MouseEvent e) {		}

		@Override
		public void mouseExited(MouseEvent e) {		}
		
		@Override
		public void mouseMoved(MouseEvent e) {		}

		@Override
		public void mouseDragged(MouseEvent e) {
			newYPos = e.getY();
			game.setMouse(true);
		}
		
		public int getinitPos() {
			return initYPos;
		}
		public int getCurrentPos() {
			return newYPos;
		}
		public void resetValues() {
			initYPos = 0;
			newYPos = 0;
		}
	}
	
	public static void main(String args[]) {

		
		JFrame frame = new JFrame();
		Game game = new Game("map.csv", "assets/dragon.png", "assets/enemy.png", "assets/bullet.png", "assets/gunSmall.png",
				"assets/wallTexture.png");
		game.setFocusable(true);	// To allow game to get keyboard inputs
		frame.add(game);
		frame.setSize(SCREEN_X, CELL_LENGTH*game.getNumRows() + 40);
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JFrame frame3 = new JFrame();
		StartMessage message = new StartMessage();
		frame3.setSize(200, 100);
		frame3.setVisible(true);
		frame3.add(message);
		
		// Main game loop
		while(true) {
			game.Update(System.nanoTime());
			if(game.getEnemyManager().getEnemies().isEmpty()) {
				break;
			}
		}
		frame.setVisible(false);
		JFrame frame2 = new JFrame();
		Quiz quiz = new Quiz();
		frame2.setSize(quiz.getSize());
		frame2.setVisible(true);
		frame2.add(quiz);
	}
}