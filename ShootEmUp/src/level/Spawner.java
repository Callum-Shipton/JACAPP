package level;

import java.util.Random;

import org.lwjgl.opengl.GL20;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.attack.TypeAttack;
import components.movement.BasicMovement;
import display.Art;
import logging.Logger;
import loop.Loop;
import main.ShootEmUp;
import object.Entity;
import save.Save;

public class Spawner {

	private int counter = 0;
	private final int enemySpawnRate;
	private final int maxWave;
	private final int maxLevel;
	private int enemies = 0;
	private int totalEnemies = 0;
	private int wave = 1;
	private boolean newWave = true;
	private int radiusLocation;
	private int radiusLocationInst;

	private float radius;
	private final float radiusIncreasePerLevel;

	private Random rand;

	public Spawner(int enemySpawnRate, int maxWave, int maxLevel, float radius, float radLevel) {
		this.enemySpawnRate = enemySpawnRate;
		this.maxWave = maxWave;
		this.maxLevel = maxLevel;
		this.radius = radius;
		this.radiusIncreasePerLevel = radLevel;

		this.rand = new Random();
		this.radiusLocation = GL20.glGetUniformLocation(Art.ShaderBase, "radius");
		this.radiusLocationInst = GL20.glGetUniformLocation(Art.ShaderInst, "radius");
		changeRadius(0);
	}

	public void changeRadius(float f) {
		GL20.glUseProgram(Art.ShaderBase);
		GL20.glUniform1f(this.radiusLocation, f + this.radius);
		GL20.glUseProgram(0);
		GL20.glUseProgram(Art.ShaderInst);
		GL20.glUniform1f(this.radiusLocationInst, f + this.radius);
		GL20.glUseProgram(0);
	}

	public void checkSpawn(Entity e) {
		BasicMovement BM = e.getComponent(TypeComponent.MOVEMENT);
		ShootEmUp.getCurrentLevel().addEntity(e);
		BM.checkCollisionY(e);
		BM.checkCollisionX(e);
	}

	public int getWave() {
		return this.wave;
	}

	public void removeEnemy() {
		this.enemies--;
	}

	public void update() {
		if (this.newWave) {
			this.counter++;
			if (this.counter == Loop.ticks(this.enemySpawnRate)) {
				// creating new Enemy

				if ((this.totalEnemies == 0) && (this.wave == this.maxWave)) {
					EnemyBuilder.buildEnemy(TypeEnemy.BOSS);
				} else {

					// randomly chooses an enemy
					int prob = this.rand.nextInt(3);
					if (prob == 0) {
						EnemyBuilder.buildEnemy(TypeEnemy.SMALL);
					} else if (prob == 1) {
						EnemyBuilder.buildEnemy(TypeEnemy.NORMAL);
					} else {
						EnemyBuilder.buildEnemy(TypeEnemy.FLYING);
					}

				}
				// creates the enemy and adds it to the level
				this.totalEnemies++;
				this.enemies++;
				if (this.totalEnemies == this.wave) {
					this.newWave = false;
				}
				this.counter = 0;
			}
		} else if (this.enemies == 0) {
			this.totalEnemies = 0;
			if (this.wave < this.maxWave) {
				this.wave++;
				changeRadius((this.wave - 1) * this.radiusIncreasePerLevel);
			} else if (ShootEmUp.getCurrentLevel().getLevel() < this.maxLevel) {
				if (ShootEmUp.getSave() == null) {
					ShootEmUp.setSave(new Save());
				} else {
					ShootEmUp.getSave().saveCharacter();
				}
				ShootEmUp.getSave().saveToSystem(1);
				BaseAttack BA = ShootEmUp.getPlayer().getComponent(TypeComponent.ATTACK);
				TypeAttack temp = BA.getAttackType();
				ShootEmUp.setCurrentLevel(
						new Level(Art.LEVEL_FILE_LOCATION, ShootEmUp.getCurrentLevel().getLevel() + 1));
				try {
					ShootEmUp.getSave().load(1);
				} catch (Exception e) {
					Logger.error(e);
				}
				PlayerBuilder.buildPlayer(temp, ShootEmUp.getSave().getCharacter(temp));
			}
			this.newWave = true;
		}
	}
}
