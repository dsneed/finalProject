package Game;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.sun.javafx.geom.Vec2d;

public class Game extends JPanel {
	public static int MAX_CELLS = 100;
	public static int CELL_LENGTH = 20;
	public static float FPS = 15;
	
	//private Timer timer; 
	private int numRows;
	private int numCols;
	private int numWalls;
	private String mapFileName;
	private String playerFileName;
	private String enemyFileName;
	private String[][] layout = new String[MAX_CELLS][MAX_CELLS];
	public Sprite cat;
	public EnemyManager enemyManager;
	
	public Game(String mapFile, String playerFile, String enemyFile){
		this.mapFileName = mapFile;
		this.playerFileName = playerFile;
		this.enemyFileName = enemyFile;
		loadConfigFiles();
		LoadComponents();
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
		// Load enemies and wall
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				if(layout[i][j].equals("T")) {
					enemyManager.CreateEnemy(new Vec2d(j*CELL_LENGTH, i*CELL_LENGTH));
				}
				else if (layout[i][j].equals("W")) {
					// TODO: Draw wall
				}
			}
		}
	}

	//For testing Sprite
	public void loadCat() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(playerFileName));
		} catch(IOException e) {
			System.out.println("What?!");
		}
		
		cat = new Sprite(img, new Vec2d(50, 50), 100, 4, 2, 15);
	}
	
	public void loadEnemyManager() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(enemyFileName));
		} catch(IOException e) {
			System.out.println("What?!");
		}
		enemyManager = new EnemyManager(img, new Vec2d(50, 50), 100, 2, 5, 15);
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
	
	public void paintComponent(Graphics g) {
		  super.paintComponent(g);
		  cat.Draw(g);
		  enemyManager.Draw(g);
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		Game game = new Game("TestLevel.csv", "assets/runningcat.png", "assets/enemy.png");
		frame.add(game);
		frame.setSize(1000, CELL_LENGTH*game.getNumRows());
		frame.setVisible(true);
		long timeElapsed = 0;
		long startTime = System.currentTimeMillis();
		long currentTime = System.currentTimeMillis();
		while(true) {
			timeElapsed = (currentTime - startTime) / (long)100;	// For some reason dividing by 100 seems more accurate (I thought the time
																	// in millliseconds, but whatever...
			game.cat.Update(timeElapsed);
			game.enemyManager.Update(timeElapsed);
			if((timeElapsed) >= 1.0f/(float)FPS) {
				game.repaint();
				timeElapsed = 0;
				startTime = currentTime;
			}
			currentTime = System.currentTimeMillis();
		}
	}
}