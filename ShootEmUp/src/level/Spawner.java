package level;

import java.util.Random;

import object.Entity;
import main.ShootEmUp;
import math.Vector2;
import components.ComponentType;
import components.attack.BaseAttack;
import components.attack.EnemyAttack;
import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.collision.BaseCollision;
import components.collision.RigidCollision;
import components.control.AIControl;
import components.control.BaseControl;
import components.control.PlayerControl;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.graphical.PlayerGraphics;
import components.inventory.BaseInventory;
import components.inventory.EnemyInventory;
import components.inventory.PlayerInventory;
import components.movement.BaseMovement;
import components.movement.BasicMovement;
import components.movement.FlyingMovement;
import components.spawn.PointSpawn;
import display.Art;

public class Spawner {
	
	private int counter = 0;
	private final int ENEMY_SPAWN_RATE = 150;
	private final int MAX_WAVE = 20;
	private int enemies = 0;
	private int totalEnemies = 0;
	private int wave = 1;
	private boolean newWave = true;
	
	private BaseGraphics enemyGraphics;
	private PointSpawn enemySpawn;
	private BaseAttack enemyAttack;
	private BaseControl enemyControl;
	private BaseCollision enemyCollision;
	private BaseMovement enemyMovement;
	private BaseInventory enemyInventory;
	
	private Entity test;
	private Entity newEnemy;
	private Random rand;
	
	public Spawner(){
		rand = new Random();
	}
	
	public Entity createPlayer(TypeAttack type){
		Entity player = new Entity();
		PlayerGraphics g = new PlayerGraphics(player, Art.player, Art.base);
		PointSpawn s = new PointSpawn(g, new Vector2(480.0f, 480.0f), player);
		PlayerAttack a;
		
		a = new PlayerAttack(type, s);
		
		player.addComponent(g);
		RigidCollision c = new RigidCollision(player);
		player.addComponent(c);
		BasicMovement m = new BasicMovement(player,c, g, 5);
		PlayerInventory i = new PlayerInventory(a, m, 0, 1);
		player.addComponent(s);
		player.addComponent(a);
		player.addComponent(m);
		player.addComponent(new PlayerControl(player, g, a, m));
		player.addComponent(i);
		
		return player;
	}
	
	public void update(){
		if(newWave){
			counter++;
			if (counter == ENEMY_SPAWN_RATE) {
				//creates an enemy to test the spawning position
				testEnemy();
				
				//creating new Enemy
				newEnemy = new Entity();
				
				if((totalEnemies == 0) && (wave == 20)){
					bossEnemy();
				} else {
					//randomly chooses an enemy
					int prob = rand.nextInt(3);
					if(prob == 0){
						smallEnemy();
					} else if(prob == 1){
						largeEnemy();
					} else {
						flyingEnemy();
					}			
				}
				//creates the enemy and adds it to the level
				addEnemy();
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
	
	private void smallEnemy(){
		enemyGraphics = new AnimatedGraphics(Art.smallEnemy, Art.base, false); 
		enemySpawn = new PointSpawn(enemyGraphics, new Vector2(((BaseGraphics) test.getComponent(ComponentType.GRAPHICS)).getX(),((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getY()), newEnemy);
		enemyAttack = new EnemyAttack(TypeAttack.WARRIOR, 1);
		newEnemy.addComponent(enemyGraphics);
		enemyCollision = new RigidCollision(newEnemy);
		enemyMovement = new BasicMovement(newEnemy, enemyCollision, enemyGraphics, 7);
		enemyControl = new AIControl(enemyGraphics,enemyAttack, enemyMovement);
		enemyInventory = new EnemyInventory(enemyGraphics, 1);
	}
	
	private void largeEnemy(){
		enemyGraphics = new AnimatedGraphics(Art.enemy, Art.base, false); 
		enemySpawn = new PointSpawn(enemyGraphics, new Vector2(((BaseGraphics) test.getComponent(ComponentType.GRAPHICS)).getX(),((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getY()), newEnemy);
		enemyAttack = new EnemyAttack(TypeAttack.ARCHER, 3);
		newEnemy.addComponent(enemyGraphics);
		enemyCollision = new RigidCollision(newEnemy);
		enemyMovement = new BasicMovement(newEnemy, enemyCollision, enemyGraphics, 2);
		enemyControl = new AIControl(enemyGraphics,enemyAttack, enemyMovement);
		enemyInventory = new EnemyInventory(enemyGraphics, 1);
	}
	
	private void flyingEnemy(){
		enemyGraphics = new AnimatedGraphics(Art.flyingEnemy, Art.base, false); 
		enemySpawn = new PointSpawn(enemyGraphics, new Vector2(((BaseGraphics) test.getComponent(ComponentType.GRAPHICS)).getX(),((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getY()), newEnemy);
		enemyAttack = new EnemyAttack(TypeAttack.MAGE, 2);
		newEnemy.addComponent(enemyGraphics);
		enemyCollision = new RigidCollision(newEnemy);
		enemyMovement = new FlyingMovement(newEnemy, enemyCollision, enemyGraphics, 5);
		enemyControl = new AIControl(enemyGraphics,enemyAttack, enemyMovement);
		enemyInventory = new EnemyInventory(enemyGraphics, 1);
	}
	
	private void bossEnemy(){
		enemyGraphics = new AnimatedGraphics(Art.bossEnemy, Art.base, false); 
		enemySpawn = new PointSpawn(enemyGraphics, new Vector2(((BaseGraphics) test.getComponent(ComponentType.GRAPHICS)).getX(),((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getY()), newEnemy);
		enemyAttack = new EnemyAttack(TypeAttack.MAGE, 1000);
		newEnemy.addComponent(enemyGraphics);
		enemyCollision = new RigidCollision(newEnemy);
		enemyMovement = new FlyingMovement(newEnemy, enemyCollision, enemyGraphics, 5);
		enemyControl = new AIControl(enemyGraphics,enemyAttack, enemyMovement);
		enemyInventory = new EnemyInventory(enemyGraphics, 1);
	}
	
	private void addEnemy(){
		newEnemy.addComponent(enemySpawn);
		newEnemy.addComponent(enemyAttack);
		newEnemy.addComponent(enemyCollision);
		newEnemy.addComponent(enemyControl);
		newEnemy.addComponent(enemyMovement);
		newEnemy.addComponent(enemyInventory);
		
		ShootEmUp.currentLevel.entities.add(newEnemy);
	}
	
	public int getWave(){
		return wave;
	}
}
