package level;

import java.util.Random;

import components.TypeComponent;
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
import components.inventory.TypeWeapon;
import components.movement.BaseMovement;
import components.movement.BasicMovement;
import components.movement.FlyingMovement;
import display.Art;
import main.ShootEmUp;
import object.Entity;
import object.WeaponBuilder;

public abstract class EnemyBuilder {
	
	private static Entity newEnemy;
	private static Entity test;
	
	private static BaseGraphics enemyGraphics;
	private static BaseAttack enemyAttack;
	private static BaseControl enemyControl;
	private static BaseCollision enemyCollision;
	private static BaseMovement enemyMovement;
	private static BaseInventory enemyInventory;
	
	private static Random rand = new Random();
	
	public static void buildEnemy(TypeEnemy type){
		
		testEnemy();
		
		newEnemy = new Entity();
		
		switch(type){
		case SMALL:
			enemyGraphics = new AnimatedGraphics(Art.smallEnemy, Art.base, false, ((BaseGraphics) test.getComponent(TypeComponent.GRAPHICS)).getX(), ((BaseGraphics) test.getComponent(TypeComponent.GRAPHICS)).getX()); 
			enemyAttack = new EnemyAttack(TypeAttack.WARRIOR, 1, WeaponBuilder.buildWeapon(TypeWeapon.FIRE_STAFF, 1));
			newEnemy.addComponent(enemyGraphics);
			enemyCollision = new RigidCollision(newEnemy);
			enemyMovement = new BasicMovement(newEnemy, enemyCollision, enemyGraphics, 7);
			enemyControl = new AIControl(enemyGraphics,enemyAttack, enemyMovement);
			enemyInventory = new EnemyInventory(enemyGraphics, 1);
			break;
		case NORMAL:
			enemyGraphics = new AnimatedGraphics(Art.enemy, Art.base, false, ((BaseGraphics) test.getComponent(TypeComponent.GRAPHICS)).getX(), ((BaseGraphics) test.getComponent(TypeComponent.GRAPHICS)).getX()); 
			enemyAttack = new EnemyAttack(TypeAttack.ARCHER, 3, WeaponBuilder.buildWeapon(TypeWeapon.ICE_STAFF, 1));
			newEnemy.addComponent(enemyGraphics);
			enemyCollision = new RigidCollision(newEnemy);
			enemyMovement = new BasicMovement(newEnemy, enemyCollision, enemyGraphics, 2);
			enemyControl = new AIControl(enemyGraphics,enemyAttack, enemyMovement);
			enemyInventory = new EnemyInventory(enemyGraphics, 1);
			break;
		case FLYING:
			enemyGraphics = new AnimatedGraphics(Art.flyingEnemy, Art.base, false, ((BaseGraphics) test.getComponent(TypeComponent.GRAPHICS)).getX(), ((BaseGraphics) test.getComponent(TypeComponent.GRAPHICS)).getX()); 
			enemyAttack = new EnemyAttack(TypeAttack.MAGE, 2, WeaponBuilder.buildWeapon(TypeWeapon.EARTH_STAFF, 1));
			newEnemy.addComponent(enemyGraphics);
			enemyCollision = new RigidCollision(newEnemy);
			enemyMovement = new FlyingMovement(newEnemy, enemyCollision, enemyGraphics, 5);
			enemyControl = new AIControl(enemyGraphics,enemyAttack, enemyMovement);
			enemyInventory = new EnemyInventory(enemyGraphics, 1);
			break;
		case BOSS:
			enemyGraphics = new AnimatedGraphics(Art.bossEnemy, Art.base, false, ((BaseGraphics) test.getComponent(TypeComponent.GRAPHICS)).getX(), ((BaseGraphics) test.getComponent(TypeComponent.GRAPHICS)).getX()); 
			enemyAttack = new EnemyAttack(TypeAttack.MAGE, 1000, WeaponBuilder.buildWeapon(TypeWeapon.FIRE_STAFF, 1));
			newEnemy.addComponent(enemyGraphics);
			enemyCollision = new RigidCollision(newEnemy);
			enemyMovement = new FlyingMovement(newEnemy, enemyCollision, enemyGraphics, 5);
			enemyControl = new AIControl(enemyGraphics,enemyAttack, enemyMovement);
			enemyInventory = new EnemyInventory(enemyGraphics, 1);
			break;
		}
		
		newEnemy.addComponent(enemyAttack);
		newEnemy.addComponent(enemyCollision);
		newEnemy.addComponent(enemyControl);
		newEnemy.addComponent(enemyMovement);
		newEnemy.addComponent(enemyInventory);
		
		ShootEmUp.currentLevel.entities.add(newEnemy);
	}
	
	private static void testEnemy(){
		
		boolean collide = false;
		test = new Entity();
		BaseGraphics BG = new AnimatedGraphics(Art.enemy, Art.base, false);
		test.addComponent(BG);
		BaseCollision BC = new RigidCollision(test);
		test.addComponent(BC);
		test.addComponent(new BasicMovement(test, BC, BG, 5));
		float px = ((BaseGraphics) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.GRAPHICS)).getX();
		float py = ((BaseGraphics) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.GRAPHICS)).getY();
		float pw = ((BaseGraphics) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.GRAPHICS)).getWidth();
		float ph = ((BaseGraphics) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.GRAPHICS)).getHeight();
		do {
			collide = false;
			
			BG.setX((float)rand.nextInt((ShootEmUp.currentLevel.map.getBackgroundTiles().length - 1) * 32));
			BG.setY(rand.nextInt((ShootEmUp.currentLevel.map.getBackgroundTiles()[0].length - 1) * 32));
			
			if(Math.abs((BG.getX()+(BG.getWidth()/2)) - (px + (pw/2))) <= (ShootEmUp.width+BG.getWidth()) && Math.abs((BG.getY()+(BG.getHeight()/2)) - (py + (ph/2))) <= (ShootEmUp.height+BG.getHeight())) {
				collide = true;
				continue;
			}
			
			//changed to grid position;
			
			for (Entity character : ShootEmUp.currentLevel.entities) {
				if ((((BaseMovement) test.getComponent(TypeComponent.MOVEMENT)).doesCollide(test, character) != null)) {
					collide = true;
					break;
				}
			}
		} while (collide == true);
	}
}
