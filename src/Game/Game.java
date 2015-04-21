package Game;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Game {
	private int numRows;
	private int numCols;
	private String mapFileName;
	private char[][] layout;

	public Game(String inputFile){
		mapFileName = inputFile;
		loadConfigFiles();
		LoadComponents();
	}
	
	public void loadConfigFiles(){
		fillLayout(mapFileName);
	}

	private void LoadComponents() {
		// TODO Auto-generated method stub
		
	}
	
	public void fillLayout(String fileName) throws BadConfigFormatException {
		FileReader fileIn = null;
		try {
			fileIn = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		// TODO: Finish this based on method from Clue
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numCols;
	}
	
	public boolean isWallAt(int row, int col) {
		if (layout[row][col] == 'W') {
			return true;
		}
		return false;
	}
}
