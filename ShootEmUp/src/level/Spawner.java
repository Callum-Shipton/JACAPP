package level;

import java.util.Random;

import org.lwjgl.opengl.GL20;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.attack.TypeAttack;
import display.ImageProcessor;
import entity.Entity;
import logging.Logger;
import loop.Loop;
import main.ShootEmUp;
import save.Save;

public class Spawner {

	private int counter = 0;
	private final int enemySpawnRate;
	private final int maxWave;
	private final int maxLevel;
	private int enemies = 0;
	private int totalEnemies = 0;
	private int wave = 1;
	private boolean waveActive = true;
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

		rand = new Random();
		radiusLocation = GL20.glGetUniformLocation(ImageProcessor.ShaderBase, "radius");
		radiusLocationInst = GL20.glGetUniformLocation(ImageProcessor.ShaderInst, "radius");
		changeRadius(0);
	}

	public void changeRadius(float f) {
		GL20.glUseProgram(ImageProcessor.ShaderBase);
		GL20.glUniform1f(radiusLocation, f + radius);
		GL20.glUseProgram(0);
		GL20.glUseProgram(ImageProcessor.ShaderInst);
		GL20.glUniform1f(radiusLocationInst, f + radius);
		GL20.glUseProgram(0);
	}

	public int getWave() {
		return wave;
	}

	public void removeEnemy() {
		enemies--;
	}

	private void spawnEnemies() {
		counter++;
		if (counter == Loop.ticks(enemySpawnRate)) {
			// creating new Enemy

			Entity newEnemy;

			if ((totalEnemies == 0) && (wave == maxWave)) {
				newEnemy = EnemyBuilder.buildEnemy(TypeEnemy.BOSS);
			} else {
				// randomly chooses an enemy
				int prob = this.rand.nextInt(3);
				switch (prob) {
				case 0:
					newEnemy = EnemyBuilder.buildEnemy(TypeEnemy.SMALL);
					break;
				case 1:
					newEnemy = EnemyBuilder.buildEnemy(TypeEnemy.FLYING);
					break;
				default:
					newEnemy = EnemyBuilder.buildEnemy(TypeEnemy.NORMAL);
				}
			}

			ShootEmUp.getCurrentLevel().addEntity(newEnemy);
			// creates the enemy and adds it to the level
			totalEnemies++;
			enemies++;
			if (totalEnemies == wave) {
				waveActive = false;
			}
			counter = 0;
		}
	}

	private void nextLevel() {
		if (ShootEmUp.getSave() == null) {
			ShootEmUp.setSave(new Save());
		} else {
			ShootEmUp.getSave().saveCharacter();
		}
		ShootEmUp.getSave().saveToSystem(1);
		BaseAttack BA = ShootEmUp.getPlayer().getComponent(TypeComponent.ATTACK);
		TypeAttack temp = BA.getAttackType();
		ShootEmUp.setCurrentLevel(
				new Level(ImageProcessor.LEVEL_FILE_LOCATION, ShootEmUp.getCurrentLevel().getLevel() + 1));
		try {
			ShootEmUp.getSave().load(1);
		} catch (Exception e) {
			Logger.error(e);
		}
		PlayerBuilder.buildPlayer(temp, ShootEmUp.getSave().getCharacter(temp));
	}

	private void nextWave() {
		totalEnemies = 0;
		if (wave < maxWave) {
			wave++;
			changeRadius((wave - 1) * radiusIncreasePerLevel);
		} else if (ShootEmUp.getCurrentLevel().getLevel() < maxLevel) {
			nextLevel();
		}
		this.waveActive = true;
	}

	public void update() {
		if (waveActive) {
			spawnEnemies();
		} else if (enemies == 0) {
			nextWave();
		}
	}
}
