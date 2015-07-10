package level;

import java.util.Random;

import main.ShootEmUp;
import math.Vector2;
import components.ComponentType;
import components.attack.BaseAttack;
import components.attack.EnemyAttack;
import components.attack.TypeAttack;
import components.collision.BaseCollision;
import components.collision.RigidCollision;
import components.control.AIControl;
import components.control.BaseControl;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.inventory.BaseInventory;
import components.inventory.EnemyInventory;
import components.movement.BaseMovement;
import components.movement.BasicMovement;
import components.movement.FlyingMovement;
import display.Art;
import entities.Enemy;
import entities.Entity;
import entities.Types;

public class Spawner {
	
	private int counter = 0;
	private final int ENEMY_SPAWN_RATE = 150;
	private final int MAX_WAVE = 20;
	private int enemies = 0;
	private int totalEnemies = 0;
	private int wave = 1;
	private boolean newWave = true;
	
	private BaseGraphics enemyGraphics;
	private BaseAttack enemyAttack;
	private BaseControl enemyControl;
	private BaseCollision enemyCollision;
	private BaseMovement enemyMovement;
	private BaseInventory enemyInventory;
	
	private Entity test;
	private Enemy newEnemy;
	private Random rand;
	
	public Spawner(){
		rand = new Random();
	}
	
	public void update(){
		if(newWave){
			counter++;
			if (counter == ENEMY_SPAWN_RATE) {
				//creates an enemy to test the spawning position
				testEnemy();

				if((totalEnemies == 0) && (wave == 20)){
					newEnemy = new Enemy(Types.bossEnemy, ((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getX(), ((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getY());

				} else {
					//randomly chooses an enemy
					int prob = rand.nextInt(3);
					if(prob == 0){
						newEnemy = new Enemy(Types.smallEnemy, ((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getX(), ((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getY());
					} else if(prob == 1){
						newEnemy = new Enemy(Types.enemy, ((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getX(), ((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getY());
					} else {
						newEnemy = new Enemy(Types.flyingEnemy, ((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getX(), ((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getY());

					}			
				}
				//creates the enemy and adds it to the level
				ShootEmUp.currentLevel.entities.add(newEnemy);
				totalEnemies++;
				enemies++;
				if(totalEnemies == wave){
					newWave = false;
				}
				counter = 0;
			}
		} else if(enemies == 0){
			totalEnemies = 0;
			if(wave < MAX_WAVE){
				wave++;
			}
			newWave = true;
		}
	}
	
	public void removeEnemy(){
		enemies--;
	}
	
	private void testEnemy(){
		
		boolean collide = false;
		test = new Entity();
		BaseGraphics BG = new AnimatedGraphics(Art.player, Art.base, false);
		test.addComponent(BG);
		BaseCollision BC = new RigidCollision(test);
		test.addComponent(BC);
		test.addComponent(new BasicMovement(test, BC, BG, 5));
		float px = ((BaseGraphics) ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.GRAPHICS)).getX();
		float py = ((BaseGraphics) ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.GRAPHICS)).getY();
		float pw = ((BaseGraphics) ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.GRAPHICS)).getWidth();
		float ph = ((BaseGraphics) ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.GRAPHICS)).getHeight();
		do {
			collide = false;
			
			BG.setX((float)rand.nextInt((ShootEmUp.currentLevel.map.getBackgroundTiles().length - 1) * 32));
			BG.setY(rand.nextInt((ShootEmUp.currentLevel.map.getBackgroundTiles()[0].length - 1) * 32));
			
			if(Math.abs((BG.getX()+(BG.getWidth()/2)) - (px + (pw/2))) <= (ShootEmUp.WIDTH+BG.getWidth()) && Math.abs((BG.getY()+(BG.getHeight()/2)) - (py + (ph/2))) <= (ShootEmUp.HEIGHT+BG.getHeight())) {
				collide = true;
				continue;
			}
			
			//changed to grid position;
			
			for (Entity character : ShootEmUp.currentLevel.entities) {
				if ((((BaseMovement) test.getComponent(ComponentType.MOVEMENT)).doesCollide(test, character) != null)) {
					collide = true;
					break;
				}
			}
		} while (collide == true);
	}
	
	public void checkSpawn(Entity e){
		BasicMovement BM = (BasicMovement) e.getComponent(ComponentType.MOVEMENT);
		ShootEmUp.currentLevel.newEntities.add(e);
		BM.checkCollisionY(e, new Vector2(0,0));
		BM.checkCollisionX(e, new Vector2(0,0));
	}
	
	public int getWave(){
		return wave;
	}
}
