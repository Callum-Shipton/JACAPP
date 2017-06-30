package main;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.attack.TypeAttack;
import components.graphical.BaseGraphics;
import display.ImageProcessor;
import entity.Entity;
import gui.Hud;
import level.Level;
import level.PlayerBuilder;
import logging.Logger;
import save.Save;

public class GameBase {

	private Level currentLevel;

	private int levelNumber;
	private static final int MAX_LEVEL = 3;

	private Entity player;
	private Hud hud;

	public void update() {
		if (!currentLevel.getLevelFinished()) {
			currentLevel.update();
		} else if (levelNumber < MAX_LEVEL) {
			nextLevel();
		}

		hud.updateWaveCounter(currentLevel.getWave());
		hud.update();
	}

	public void render() {
		currentLevel.render();
		BaseGraphics baseGraphics = player.getComponent(TypeComponent.GRAPHICS);
		baseGraphics.render(player);

		hud.render(ImageProcessor.stat);
	}

	private void nextLevel() {
		if (ShootEmUp.getSave() == null) {
			ShootEmUp.setSave(new Save());
		}
		ShootEmUp.getSave().saveCharacter();
		ShootEmUp.getSave().saveToSystem(1);
		BaseAttack playerAttack = player.getComponent(TypeComponent.ATTACK);
		TypeAttack temp = playerAttack.getAttackType();
		currentLevel = new Level(ImageProcessor.LEVEL_FILE_LOCATION, levelNumber + 1);
		currentLevel.init();
		try {
			ShootEmUp.getSave().load(1);
		} catch (Exception e) {
			Logger.error(e);
		}
		player = PlayerBuilder.buildPlayer(temp, ShootEmUp.getSave().getCharacter(temp));

		if (hud == null) {
			hud = new Hud(player, 0, 0);
		}
	}

	public Entity getPlayer() {
		return player;
	}

	public void setCurrentLevel(Level currentLevel) {
		this.currentLevel = currentLevel;
	}

	public Level getCurrentLevel() {
		return currentLevel;
	}

	public void setPlayer(Entity player) {
		this.player = player;
	}

	public Hud getHud() {
		return hud;
	}

	public void setHud(Hud hud) {
		this.hud = hud;
	}

	public int getLevel() {
		return levelNumber;
	}

}
