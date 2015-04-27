package Game;

import java.util.ArrayList;

import com.sun.javafx.geom.Rectangle;

public class CollisionManager {
	private ArrayList<Wall> walls;
	private EnemyManager enemyManager;
	private Weapon weapon;
	private Player player;
	
	public CollisionManager(ArrayList<Wall> walls, EnemyManager enemyManager, 
			Player player, Weapon weapon){
		this.walls = walls;
		this.enemyManager = enemyManager;
		this.player = player;
		this.weapon = weapon;
	}
	
	//Checks Player intersection with Walls
	public boolean isWallHit() {
		for(Wall w: walls) {
			if(player.intersects(w)) {
				player.setIsBlocked(true);
				return true;
			}
		}
		return false;
	}
	
	//Checks Player intersection with Enemies
	public boolean isEnemyHit() {
		for(Enemy e: enemyManager.getEnemies()) {
			if(player.intersects(e)) {
				player.setIsBlocked(true);
				return true;
			}
		}
		return false;
	}

	//Checks Projectile intersection with Walls
	public boolean checkProjectileToWall() {
		for(Wall w: walls) {
			for(Projectile p : weapon.getProjectileManager().getProjectiles()) {
				if(p.intersects(w)) {
					p.setIsBlocked(true);
					return true;
				}
			}
		}
		return false;
	}
	
	//Checks Projectile intersection with Enemies
	private void checkProjectileToEnemy() {
		for(Enemy e: enemyManager.getEnemies()) {
			for(Projectile p : weapon.getProjectileManager().getProjectiles()) {
				if(p.intersects(e)) {
					p.setIsBlocked(true);
				}
			}
		}
	}

	public void Update() {
		checkProjectileToEnemy();
		//checkProjectileToWall();
		isEnemyHit();
		//isWallHit();
	}
}
