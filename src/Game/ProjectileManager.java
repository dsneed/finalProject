package Game;


import java.util.ArrayList;

public class ProjectileManager {
	
	private ArrayList<Projectile> projectilePath = new ArrayList<Projectile>(); // ArrayList to hold all the positions of the projectile
	
	public ProjectileManager(){
		
	}
	
	
	public ArrayList<Projectile> ProjectilePath(int angle, int gravity, int velocity,Projectile intialProjectile){
		projectilePath = new ArrayList<Projectile>();
		return projectilePath;
	}
	
	public ArrayList<Projectile> getProjectilePath(){
		return projectilePath;
	}
	
	
	
	

}
