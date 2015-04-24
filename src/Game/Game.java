package Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.javafx.geom.Vec2d;

public class Game extends JPanel {
	public static int MAX_CELLS = 100;
	private int numRows;
	private int numCols;
	private int numWalls;
	private String mapFileName;
	private String playerFileName;
	private String[][] layout = new String[MAX_CELLS][MAX_CELLS];
	public Sprite cat;
	public Game(String mapFile, String playerFile){
		this.mapFileName = mapFile;
		this.playerFileName = playerFile;
		loadConfigFiles();
		LoadComponents();
	}
	
	public void loadConfigFiles() {
		try {
			fillLayout(mapFileName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		loadCat();
	}

	private void LoadComponents() {
		// TODO Auto-generated method stub
		
	}
	
	//For testing Sprite
	public void loadCat() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("runningcat.png"));
		} catch(IOException e) {

		}
		
		cat = new Sprite(img, new Vec2d(50, 50), new Vec2d(0,0), 5, 4, 2, 10);
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
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		Game game = new Game("TestLevel.csv", "runningcat.png");
		frame.add(game);
		frame.setVisible(true);
		
		while(true) {
			
			game.cat.Update((long).0001);
			game.repaint();
		}
	}
}
