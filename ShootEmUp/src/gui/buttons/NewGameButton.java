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
import main.ShootEmUp;

public class NewGameButton implements ButtonAction {

	@Override
	public void click() {
		newPlayer();
		startLevel();
		ShootEmUp.getGame().setHud(new Hud(ShootEmUp.getGame().getPlayer(), 0, 0));
	}

	private void newPlayer() {
		Entity player = new Entity("Characters", "Players", "Warrior");
		PointSpawn spawn = player.getComponent(TypeComponent.SPAWN);
		spawn.setSpawnLocation(new Vector2f(480.0f, 480.0f));
		spawn.spawn();
		ShootEmUp.getGame().setPlayer(player);
	}

	private void startLevel() {
		Level level = new Level(ImageProcessor.LEVEL_FILE_LOCATION, 1);
		level.init();
		level.addEntity(ShootEmUp.getGame().getPlayer());

		ShootEmUp.getGame().setLevel(1);
		ShootEmUp.getGame().setCurrentLevel(level);

		List<String> enemyPrototypes = new ArrayList<>();
		enemyPrototypes.add("Small");
		enemyPrototypes.add("Normal");
		enemyPrototypes.add("Flying");
		level.addSpawner(enemyPrototypes);

		ShootEmUp.startGame();
	}

}
