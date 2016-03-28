package level;

import java.util.Random;

import object.Entity;
import save.SaveHandler;
import main.ShootEmUp;
import math.Seconds;
import math.Vector2;
import components.TypeComponent;
import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.movement.BasicMovement;
import display.Art;

public class Spawner {
	
	private int counter = 0;
	private final int ENEMY_SPAWN_RATE = 3;
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
	
	
	
	public void update(){
		if(newWave){
			counter++;
			if (counter == Seconds.ticks(ENEMY_SPAWN_RATE)) {
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
					PlayerBuilder.buildPlayer(TypeAttack.WARRIOR, ShootEmUp.save.getWarrior());
					break;
				case ARCHER:
					PlayerBuilder.buildPlayer(TypeAttack.ARCHER, ShootEmUp.save.getArcher());
					break;
				case MAGE:
					PlayerBuilder.buildPlayer(TypeAttack.MAGE, ShootEmUp.save.getMage());
					break;
				case BATTLE_MAGE:
					PlayerBuilder.buildPlayer(TypeAttack.BATTLE_MAGE, ShootEmUp.save.getBattleMage());
					break;
				case ROGUE:
					PlayerBuilder.buildPlayer(TypeAttack.ROGUE, ShootEmUp.save.getRogue());
					break;
				default:
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
