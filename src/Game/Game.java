package Game;

public class Game {
	private int numRows;
	private int numCols;
	private String mapFileName;

	public Game(String inputFile){
		mapFileName = inputFile;
		loadConfigFiles();
		LoadComponents();
	}
	
	public void loadConfigFiles(){
		
	}

	private void LoadComponents() {
		// TODO Auto-generated method stub
		
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numCols;
	}
}
