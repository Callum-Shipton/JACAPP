package level;

import java.util.Random;

import org.lwjgl.opengl.GL20;

import components.TypeComponent;
import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.movement.BasicMovement;
import display.Art;
import main.ShootEmUp;
import math.Seconds;
import math.Vector2;
import object.Entity;
import save.Save;
import save.SaveHandler;

public class Spawner {

	private int counter = 0;
	private final int ENEMY_SPAWN_RATE = 1;
	private final int MAX_WAVE = 10;
	private final int MAX_LEVEL = 3;
	private int enemies = 0;
	private int totalEnemies = 0;
	private int wave = 1;
	private boolean newWave = true;
	private int radiusLocation;
	private int radiusLocationInst;

	private float radius = 250.0f;
	private float radLevel = 25.0f;

	private Random rand;

	public Spawner() {
		rand = new Random();
		radiusLocation = GL20.glGetUniformLocation(Art.ShaderBase, "radius");
		radiusLocationInst = GL20.glGetUniformLocation(Art.ShaderInst, "radius");
		GL20.glUseProgram(Art.ShaderBase);
		GL20.glUniform1f(radiusLocation, radius);
		GL20.glUseProgram(0);
		GL20.glUseProgram(Art.ShaderInst);
		GL20.glUniform1f(radiusLocationInst, radius);
		GL20.glUseProgram(0);
	}

	public void update() {
		if (newWave) {
			counter++;
			if (counter == Seconds.ticks(ENEMY_SPAWN_RATE)) {
				// creating new Enemy

				if ((totalEnemies == 0) && (wave == MAX_WAVE)) {
					EnemyBuilder.buildEnemy(TypeEnemy.BOSS);
				} else {

					// randomly chooses an enemy
					int prob = rand.nextInt(3);
					if (prob == 0) {
						EnemyBuilder.buildEnemy(TypeEnemy.SMALL);
					} else if (prob == 1) {
						EnemyBuilder.buildEnemy(TypeEnemy.NORMAL);
					} else {
						EnemyBuilder.buildEnemy(TypeEnemy.FLYING);
					}

				}
				// creates the enemy and adds it to the level
				totalEnemies++;
				enemies++;
				if (totalEnemies == wave) {
					newWave = false;
				}
				counter = 0;
			}
		} else if (enemies == 0) {
			totalEnemies = 0;
			if (wave < MAX_WAVE) {
				wave++;
				GL20.glUseProgram(Art.ShaderBase);
				GL20.glUniform1f(radiusLocation, ((wave - 1) * radLevel) + radius);
				GL20.glUseProgram(0);
				GL20.glUseProgram(Art.ShaderInst);
				GL20.glUniform1f(radiusLocationInst, ((wave - 1) * radLevel) + radius);
				GL20.glUseProgram(0);
			} else if (ShootEmUp.currentLevel.getLevel() < MAX_LEVEL) {
				if (ShootEmUp.save == null) {
					ShootEmUp.save = new Save();
				} else {
					ShootEmUp.save.saveCharacter();
				}
				SaveHandler.save(ShootEmUp.save, 1);
				TypeAttack temp = ((PlayerAttack) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK))
						.getAttackType();
				ShootEmUp.currentLevel = new Level(Art.levels, ShootEmUp.currentLevel.getLevel() + 1);
				ShootEmUp.currentLevel.init();
				ShootEmUp.save = SaveHandler.load(1);
				PlayerBuilder.buildPlayer(temp, ShootEmUp.save.getCharacter(temp));
			}
			newWave = true;
		}
	}

	public void removeEnemy() {
		enemies--;
	}

	public void checkSpawn(Entity e) {
		BasicMovement BM = (BasicMovement) e.getComponent(TypeComponent.MOVEMENT);
		ShootEmUp.currentLevel.newEntities.add(e);
		BM.checkCollisionY(e, new Vector2(0, 0));
		BM.checkCollisionX(e, new Vector2(0, 0));
	}

	public int getWave() {
		return wave;
	}
}
