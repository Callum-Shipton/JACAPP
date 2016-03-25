package level;

import java.util.Random;

import object.Entity;
import save.CharacterSave;
import save.SaveHandler;
import main.ShootEmUp;
import math.Vector2;
import components.TypeComponent;
import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.collision.RigidCollision;
import components.control.PlayerControl;
import components.graphical.PlayerGraphics;
import components.inventory.PlayerInventory;
import components.movement.BasicMovement;
import components.spawn.PointSpawn;
import display.Art;

public class Spawner {
	
	private int counter = 0;
	private final int ENEMY_SPAWN_RATE = 150;
	private final int MAX_WAVE = 10;
	private final int MAX_LEVEL = 3;
	private int enemies = 0;
	private int totalEnemies = 0;
	private int wave = 1;
	private boolean newWave = true;

	private Random rand;
	
	public Spawner(){
		rand = new Random();
	}
	
	public Entity createPlayer(TypeAttack type){
		Entity player = new Entity();
		PlayerGraphics g;
		if(type == TypeAttack.WARRIOR){
			g = new PlayerGraphics(player, Art.warrior, Art.base);
		} else if (type == TypeAttack.ARCHER){
			g = new PlayerGraphics(player, Art.archer, Art.base);
		} else {
			g = new PlayerGraphics(player, Art.mage, Art.base);
		}
		PointSpawn s = new PointSpawn(g, new Vector2(480.0f, 480.0f), player);
		PlayerAttack a;
		
		a = new PlayerAttack(type);
		
		player.addComponent(g);
		RigidCollision c = new RigidCollision(player);
		player.addComponent(c);
		BasicMovement m = new BasicMovement(player,c, g, 5);
		PlayerInventory i = new PlayerInventory(a, 0, 1);
		player.addComponent(s);
		player.addComponent(a);
		player.addComponent(m);
		player.addComponent(i);
		player.addComponent(new PlayerControl(player, g, a, m, i));
		
		
		return player;
	}
	
	public Entity createPlayer(TypeAttack type, CharacterSave save){
		Entity player = new Entity();
		PlayerGraphics g;
		if(type == TypeAttack.WARRIOR){
			g = new PlayerGraphics(player, Art.warrior, Art.base);
		} else if (type == TypeAttack.ARCHER){
			g = new PlayerGraphics(player, Art.archer, Art.base);
		} else {
			g = new PlayerGraphics(player, Art.mage, Art.base);
		}
		PointSpawn s = new PointSpawn(g, new Vector2(480.0f, 480.0f), player);
		PlayerAttack a;
		
		a = new PlayerAttack(type, save);
		
		player.addComponent(g);
		RigidCollision c = new RigidCollision(player);
		player.addComponent(c);
		BasicMovement m = new BasicMovement(player,c, g, 5);
		PlayerInventory i = new PlayerInventory(a, save.getPlayerLevel(), save.getPlayerLevel() + 1, save);
		player.addComponent(s);
		player.addComponent(a);
		player.addComponent(m);
		player.addComponent(i);
		player.addComponent(new PlayerControl(player, g, a, m, i));
		
		
		return player;
	}
	
	public void update(){
		if(newWave){
			counter++;
			if (counter == ENEMY_SPAWN_RATE) {
				//creating new Enemy
				
				if((totalEnemies == 0) && (wave == MAX_WAVE)){
					EnemyBuilder.buildEnemy(TypeEnemy.BOSS);
				} else {
					
					//randomly chooses an enemy
					int prob = rand.nextInt(3);
					if(prob == 0){
						EnemyBuilder.buildEnemy(TypeEnemy.SMALL);
					} else if(prob == 1){
						EnemyBuilder.buildEnemy(TypeEnemy.NORMAL);
					} else {
						EnemyBuilder.buildEnemy(TypeEnemy.FLYING);
					}
					
				}
				//creates the enemy and adds it to the level
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
			} else if (ShootEmUp.currentLevel.getLevel() < MAX_LEVEL){
				TypeAttack temp = ((PlayerAttack) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK)).getTypeAttack();
				ShootEmUp.currentLevel = new Level(Art.levels, ShootEmUp.currentLevel.getLevel() + 1);
				ShootEmUp.currentLevel.init();
				ShootEmUp.save = SaveHandler.load(1);
				switch(temp){
				case WARRIOR:
					ShootEmUp.currentLevel.createPlayer(TypeAttack.WARRIOR, ShootEmUp.save.getWarrior());
					break;
				case ARCHER:
					ShootEmUp.currentLevel.createPlayer(TypeAttack.ARCHER, ShootEmUp.save.getArcher());
					break;
				case MAGE:
					ShootEmUp.currentLevel.createPlayer(TypeAttack.MAGE, ShootEmUp.save.getMage());
					break;
				}
				
			}
			newWave = true;
		}
	}
	
	public void removeEnemy(){
		enemies--;
	}
	
	public void checkSpawn(Entity e){
		BasicMovement BM = (BasicMovement) e.getComponent(TypeComponent.MOVEMENT);
		ShootEmUp.currentLevel.newEntities.add(e);
		BM.checkCollisionY(e, new Vector2(0,0));
		BM.checkCollisionX(e, new Vector2(0,0));
	}
	
	public int getWave(){
		return wave;
	}
}
