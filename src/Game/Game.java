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

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.sun.javafx.geom.Vec2d;

public class Game extends JFrame {
	private static final int CELL_SIZE = 30;
	
	//private Timer timer; 
	private String mapFilename;
	private String playerFilename;
	private String enemyFilename;
	private String projectileFilename;
	private String weaponFilename;
	private String wallFilename;
	private Map map;
	
	public Game(String mapFile, String playerFile, String enemyFile, String projectileFile, 
			String weaponFile, String wallFile){
		this.mapFilename = mapFile;
		this.playerFilename = playerFile;
		this.enemyFilename = enemyFile;
		this.projectileFilename = projectileFile;
		this.weaponFilename = weaponFile;
		this.wallFilename = wallFile;
		map = new Map(mapFile, playerFile, enemyFile, projectileFile, weaponFile, wallFile);
	}
	
	public void Update(long timeElapsed) {
		map.Update(timeElapsed);
	}
	
	public Map getMap() {
		return map;
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		Game game = new Game("src/TestLevel.csv", "assets/runningcat.png", "assets/enemy.png", "assets/projectile.png",
				"assets/gun.jpg", "assets/wall.jpg");
		game.getMap().setFocusable(true);	// To allow game to get keyboard inputs
		game.add(game.map);
		game.setSize(1000, CELL_SIZE*game.map.getNumRows());
		game.setDefaultCloseOperation(EXIT_ON_CLOSE);
		game.setVisible(true);
		
		// Main game loop
		while(true) {
			game.Update(System.currentTimeMillis());
		}
	}
}