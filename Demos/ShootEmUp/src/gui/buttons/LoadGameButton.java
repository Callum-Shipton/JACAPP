package gui.buttons;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import components.TypeComponent;
import components.spawn.PointSpawn;
import display.ImageProcessor;
import entity.Entity;
import gui.ButtonAction;
import gui.Hud;
import level.Level;
import logging.Logger;
import main.ShootEmUp;
import save.ShootEmUpSave;

public class LoadGameButton implements ButtonAction {

	int save;

	public LoadGameButton(int save) {
		this.save = save;
	}

	@Override
	public void click() {
		ShootEmUpSave save = ShootEmUp.getSave();

		if (save != null) {
			try {
				save.load(1);
			} catch (Exception e) {
				Logger.error(e);
			}

			newPlayer();
			ShootEmUp.getMenuSystem().clearMenus();
			//startLevel(save.getLevel());
			startLevel();

			ShootEmUp.getGame().setHud(new Hud(ShootEmUp.getGame().getPlayer(), 0, 0));
		}
	}

	private void newPlayer() {
		Entity player = new Entity("Characters", "Players", "Warrior");
		PointSpawn spawn = player.getComponent(TypeComponent.SPAWN);
		spawn.setSpawnLocation(new Vector2f(480.0f, 480.0f));
		spawn.spawn();
		ShootEmUp.getGame().setPlayer(player);
	}

	private void startLevel() {
		/*
		Level level = new Level(ImageProcessor.LEVEL_FILE_LOCATION, levelNum);
		level.init();
		level.addEntity(ShootEmUp.getGame().getPlayer());

		ShootEmUp.getGame().setLevel(levelNum);
		ShootEmUp.getGame().setCurrentLevel(level);
		*/

		List<String> enemyPrototypes = new ArrayList<>();
		enemyPrototypes.add("Small");
		enemyPrototypes.add("Normal");
		enemyPrototypes.add("Flying");
		//level.addSpawner(enemyPrototypes);

		ShootEmUp.startGame();
	}
}
