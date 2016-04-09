package level;

import java.util.Random;

import org.lwjgl.opengl.GL20;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.attack.TypeAttack;
import components.movement.BasicMovement;
import display.Art;
import main.ShootEmUp;
import math.Seconds;
import math.Vector2;
import object.Entity;
import save.Save;

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
		BM.checkCollisionY(e, new Vector2(0, 0));
		BM.checkCollisionX(e, new Vector2(0, 0));
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
			if (this.counter == Seconds.ticks(this.ENEMY_SPAWN_RATE)) {
				// creating new Enemy

				if ((this.totalEnemies == 0) && (this.wave == this.MAX_WAVE)) {
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
			if (this.wave < this.MAX_WAVE) {
				this.wave++;
				changeRadius((this.wave - 1) * this.radLevel);
			} else if (ShootEmUp.getCurrentLevel().getLevel() < this.MAX_LEVEL) {
				if (ShootEmUp.getSave() == null) {
					ShootEmUp.setSave(new Save());
				} else {
					ShootEmUp.getSave().saveCharacter();
				}
				ShootEmUp.getSave().saveToSystem(1);
				BaseAttack BA = ShootEmUp.getPlayer().getComponent(TypeComponent.ATTACK);
				TypeAttack temp = BA.getAttackType();
				ShootEmUp.setCurrentLevel(new Level(Art.levels, ShootEmUp.getCurrentLevel().getLevel() + 1));
				ShootEmUp.getSave().load(1);
				PlayerBuilder.buildPlayer(temp, ShootEmUp.getSave().getCharacter(temp));
			}
			this.newWave = true;
		}
	}
}
