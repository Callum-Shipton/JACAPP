package Level;

import java.util.Random;

import Components.ComponentType;
import Components.Attack.ArcherAttack;
import Components.Attack.BaseAttack;
import Components.Attack.MageAttack;
import Components.Attack.MeleeAttack;
import Components.Attack.WarriorAttack;
import Components.Collision.RigidCollision;
import Components.Control.HomingControl;
import Components.Control.PlayerControl;
import Components.Graphical.AnimatedGraphics;
import Components.Graphical.BaseGraphics;
import Components.Graphical.PlayerGraphics;
import Components.Inventory.PlayerInventory;
import Components.Movement.BaseMovement;
import Components.Movement.BasicMovement;
import Components.Movement.FlyingMovement;
import Components.Spawn.PointSpawn;
import Display.Art;
import Main.ShootEmUp;
import Math.Vector2;
import Object.Entity;
import Object.Weapon;

public class Spawner {
	
	private int counter = 0;
	private final int ENEMY_SPAWN_RATE = 150;
	
	private AnimatedGraphics enemyGraphics;
	private PointSpawn enemySpawn;
	private MeleeAttack enemyAttack;
	private HomingControl enemyControl;
	private RigidCollision enemyCollision;
	private BaseMovement enemyMovement;
	
	private Entity test;
	private Entity newEnemy;
	private Random rand;
	
	public Spawner(){
		rand = new Random();
	}
	
	public Entity createPlayer(int type){
		Entity player = new Entity();
		PlayerGraphics g = new PlayerGraphics(player, Art.player, Art.base);
		PointSpawn s = new PointSpawn(g, new Vector2(480.0f, 480.0f), player);
		PlayerInventory i = new PlayerInventory(0, 1, 3);
		BaseAttack a;
		
		if(type == 0){
			a = new WarriorAttack(s, i, new Weapon(5, 100, 10, false, 1, Art.earthMagic), 3, 100, 3, 3, 50, 3);
		} else if (type == 1){
			 a = new ArcherAttack(s, i, new Weapon(5, 100, 10, false, 1, Art.arrow), 3, 100, 3, 3, 50, 3);
		} else {
			a = new MageAttack(s, i, new Weapon(5, 100, 10, false, 1, Art.fireMagic), 3, 100, 3, 3, 50, 3);
		}
		
		player.addComponent(g);
		RigidCollision c = new RigidCollision(player);
		player.addComponent(c);
		BasicMovement m = new BasicMovement(player,c, g, 5);
		player.addComponent(s);
		player.addComponent(a);
		player.addComponent(m);
		player.addComponent(new PlayerControl(player, g, a, m));
		player.addComponent(i);
		
		return player;
	}
	
	public void update(){
		counter++;
		if (counter == ENEMY_SPAWN_RATE) {
			//creates an enemy to test the spawning position
			testEnemy();
			
			//creating new Enemy
			newEnemy = new Entity();
			
			//randomly chooses an enemy
			int prob = rand.nextInt(3);
			if(prob == 0){
				smallEnemy();
			} else if(prob == 1){
				largeEnemy();
			} else {
				flyingEnemy();
			}			
			
			//creates the enemy and adds it to the level
			addEnemy();
			
			counter = 0;
		}
	}
	
	private void testEnemy(){
		boolean collide = false;
		test = new Entity();
		AnimatedGraphics AG = new AnimatedGraphics(Art.player, Art.base, false);
		test.addComponent(AG);
		RigidCollision RC = new RigidCollision(test);
		test.addComponent(RC);
		test.addComponent(new BasicMovement(test, RC, AG, 5));
		do {
			collide = false;
			((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).setX((float)rand.nextInt((ShootEmUp.currentLevel.map.getBackgroundTiles().length - 1) * 32));
			((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).setY(rand.nextInt((ShootEmUp.currentLevel.map.getBackgroundTiles()[0].length - 1) * 32));

			for (Entity character : ShootEmUp.currentLevel.entities) {
				if ((((BaseMovement) test.getComponent(ComponentType.MOVEMENT)).doesCollide(test, character) != null)) {
					collide = true;
					break;
				}
			}
		} while (collide == true);
	}
	
	private void smallEnemy(){
		enemyGraphics = new AnimatedGraphics(Art.smallEnemy, Art.base, false); 
		enemySpawn = new PointSpawn(enemyGraphics, new Vector2(((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getX(),((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getY()), newEnemy);
		enemyAttack = new MeleeAttack(enemySpawn, enemyGraphics, new Weapon(2, 100, 10, false, 1, Art.iceMagic), 10, 100, 10);
		newEnemy.addComponent(enemyGraphics);
		enemyCollision = new RigidCollision(newEnemy);
		enemyMovement = new BasicMovement(newEnemy, enemyCollision, enemyGraphics, 7);
		enemyControl = new HomingControl(enemyGraphics, enemyMovement);
	}
	
	private void largeEnemy(){
		enemyGraphics = new AnimatedGraphics(Art.enemy, Art.base, false); 
		enemySpawn = new PointSpawn(enemyGraphics, new Vector2(((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getX(),((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getY()), newEnemy);
		enemyAttack = new MeleeAttack(enemySpawn, enemyGraphics, new Weapon(10, 100, 10, false, 1, Art.iceMagic), 10, 100, 10);
		newEnemy.addComponent(enemyGraphics);
		enemyCollision = new RigidCollision(newEnemy);
		enemyMovement = new BasicMovement(newEnemy, enemyCollision, enemyGraphics, 2);
		enemyControl = new HomingControl(enemyGraphics, enemyMovement);
	}
	
	private void flyingEnemy(){
		enemyGraphics = new AnimatedGraphics(Art.flyingEnemy, Art.base, false); 
		enemySpawn = new PointSpawn(enemyGraphics, new Vector2(((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getX(),((BaseGraphics)test.getComponent(ComponentType.GRAPHICS)).getY()), newEnemy);
		enemyAttack = new MeleeAttack(enemySpawn, enemyGraphics, new Weapon(5, 100, 10, false, 1, Art.iceMagic), 10, 100, 10);
		newEnemy.addComponent(enemyGraphics);
		enemyCollision = new RigidCollision(newEnemy);
		enemyMovement = new FlyingMovement(newEnemy, enemyCollision, enemyGraphics, 5);
		enemyControl = new HomingControl(enemyGraphics, enemyMovement);
	}
	
	private void addEnemy(){
		newEnemy.addComponent(enemySpawn);
		newEnemy.addComponent(enemyAttack);
		newEnemy.addComponent(enemyCollision);
		newEnemy.addComponent(enemyControl);
		newEnemy.addComponent(enemyMovement);
		
		ShootEmUp.currentLevel.entities.add(newEnemy);
	}
}
