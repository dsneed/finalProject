package Game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.sun.javafx.geom.Vec2d;

public class Map extends JPanel {
	public static float FPS = 15;
	private final int CELL_SIZE = 30;
	
	private ArrayList<ArrayList<Sprite>> map;
	private int numRows;
	private int numColumns;
	private int numWalls;
	private String mapFilename;
	private String enemyFilename;
	private String playerFilename;
	private String weaponFilename;
	private String projectileFilename;
	private EnemyManager enemyManager;
	private Player cat;
	private Weapon slingshot;
	private long previousTime;
	private boolean shouldClearInputs;	// boolean so that event listener and game loop don't interfere
	private WeaponListener weaponListener;
	private ArrayList<String> inputs;
	private boolean shotFired;
	LinkedList<KeyEvent> keyQueue;
	LinkedList<MouseEvent> mouseQueue;
	
	public Map(String mapFilename, String enemyFilename, String projectileFilename,
			String weaponFilename){
		this.mapFilename = mapFilename;
		this.enemyFilename = enemyFilename;
		this.projectileFilename = projectileFilename;
		this.weaponFilename = weaponFilename;
		this.previousTime = System.currentTimeMillis();
		this.addKeyListener(new InputListener(this));
		this.weaponListener = new WeaponListener(this);
		this.addMouseListener(weaponListener);
		this.addMouseMotionListener(weaponListener);
		this.inputs = new ArrayList<String>();
		shotFired = false;
		keyQueue = new LinkedList<KeyEvent>();
		mouseQueue = new LinkedList<MouseEvent>();
		
		loadFiles();
	}
	
	public void loadFiles() {
		try {
			loadMap(mapFilename);
			loadEnemyManager(enemyFilename);
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
	}
	
	public void loadEnemyManager(String enemyFilename) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(enemyFilename));
		} catch(IOException e) {
			System.out.println("What?!");
		}
		enemyManager = new EnemyManager(img, new Vec2d(50, 50), 50, 2, 5, 15);
	}

	public void loadMap(String mapFilename) throws BadConfigFormatException {
		map = new ArrayList<ArrayList<Sprite>>();
		int col = -1;
		int countRow = 0;
		int countCol = 0;
		
		FileReader fileIn = null;
		try {
			fileIn = new FileReader(mapFilename);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		Scanner scan = new Scanner(fileIn);
		while (scan.hasNextLine()) {
			String current = scan.nextLine();
			String[] burst = current.split(",");
			if (col == -1) {
				col = burst.length;
			} else {
				if (col != burst.length) {
					throw new BadConfigFormatException(
							"Error in layout format.");
				}
			}
			ArrayList<Sprite> thisRowList = new ArrayList<>();
			for (int i = 0; i < burst.length; i++) {
				countRow++;
				Sprite thisCell;

				if (burst[i].charAt(0) == 'W') {
					//thisCell = new Wall();
				} else if(burst[i].charAt(0) == 'T') {
					//thisCell = new Enemy();
				}
				//thisRowList.add(thisCell);
			}
			map.add(thisRowList);
			countCol++;
		}

		numRows = countRow / col;
		numColumns = col;
		setSize(CELL_SIZE * numColumns,CELL_SIZE * numRows);
	}
	
	public void loadWeapon() {
		// Load shot
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(projectileFilename));
		} catch(IOException e) {
			System.out.println("Projectile file not found.");
		}
		
		ProjectileManager projectileManager = new ProjectileManager(img, new Vec2d(50, 50), 150, 5, 5, 15);
		
		// Load weapon
		BufferedImage img2 = null;
		try {
			img2 = ImageIO.read(new File(weaponFilename));
		} catch(IOException e) {
			System.out.println("Weapon file not found.");
		}
		slingshot = new Weapon(cat, projectileManager, img2, new Vec2d(50, 50), 100, 1, 1, 15);		
	}

	//For testing Sprite
	public void loadCat() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(playerFilename));
		} catch(IOException e) {
			System.out.println("What?!");
		}
		
		cat = new Player(img, new Vec2d(50, 50), 100, 4, 2, 15);
	}
	
	public void Update(long currentTime) {
		long timeElapsed = (currentTime - previousTime) / (long)100;	// For some reason dividing by 100 seems more accurate (I thought the time
																		// in millliseconds, but whatever...
		processInputs();
		//cat.Update(timeElapsed, getInputs());
		enemyManager.Update(timeElapsed);
		int angle = calculateAngle(weaponListener.getinitPos(), weaponListener.getCurrentPos());
		slingshot.Update(shotFired, angle, timeElapsed);
		if(shotFired) {
			shotFired = false;
		}
		if((timeElapsed) >= 1.0f/(float)FPS) {
			repaint();
			timeElapsed = 0;
			previousTime = currentTime;
		}
	}
	
	public void paintComponent(Graphics g) {
		int rows = numRows;
		int columns = numColumns;
		
		super.paintComponent(g);
		
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				if(map.get(i).get(j).equals("T")) {
					//enemyManager.CreateEnemy(new Vec2d(j*CELL_SIZE, i*CELL_SIZE));
				}
				else if (map.get(i).get(j).equals("W")) {
					// TODO: Draw wall
				}
			}
		}
		enemyManager.Draw(g);
		cat.Draw(g);
		slingshot.Draw(g);
	}
	
	// Places the enemies and draws the Map
	public void Draw(Graphics g){

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
	
	public boolean shouldClearInputs() {
		return shouldClearInputs;
	}
	public void clearInputs(boolean val) {
		shouldClearInputs = val;
	}
	
	private class InputListener implements KeyListener {
		private Map map;
		
		InputListener(Map map) {
			super();
			this.map = map;
		}

		@Override
		public void keyTyped(KeyEvent e) { }

		@Override
		public void keyPressed(KeyEvent e) {
			map.addEvent(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			map.clearInputs(true);
		}
	}
	
	private class WeaponListener implements MouseInputListener {
		private Map map;
		private int initYPos;
		private int newYPos;
		
		WeaponListener(Map map) {
			super();
			this.map = map;
			initYPos = 0;
			newYPos = 0;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			initYPos = e.getY();
			newYPos = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			map.shotFired(true);
			System.out.println(initYPos + " " + newYPos);
			//initYPos = 0;
			//newYPos = 0;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			newYPos = e.getY();
			System.out.println(newYPos + "Old pos: " + initYPos);
		}
		
		public int getinitPos() {
			return initYPos;
		}
		public int getCurrentPos() {
			return newYPos;
		}
	}
	
	public void shotFired(boolean val) {
		shotFired = val;
	}
	
	public void addEvent(KeyEvent e) {
		keyQueue.add(e);
	}
	
	public int calculateAngle(int initPos, int newPos) {
		return -1*(newPos - initPos)/5;
	}
	
	public ArrayList<String> getInputs() {
		return inputs;
	}
	
	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	public String getName() {
		return this.mapFilename;
	}
	
	public boolean isWallAt(int row, int col) {
		if (map.get(row).get(col).equals("W")) {
			return true;
		}
		return false;
	}
}
