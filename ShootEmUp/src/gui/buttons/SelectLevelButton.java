package gui.buttons;

import java.util.ArrayList;
import java.util.List;

import display.ImageProcessor;
import gui.ButtonAction;
import level.Level;
import main.ShootEmUp;

public class SelectLevelButton implements ButtonAction {

	private int levelNum;

	public SelectLevelButton(int levelNum) {
		this.levelNum = levelNum;
	}

	@Override
	public void click() {
		Level level = new Level(ImageProcessor.LEVEL_FILE_LOCATION, levelNum);
		level.init();
		level.addEntity(ShootEmUp.getGame().getPlayer());

		ShootEmUp.getGame().setLevel(levelNum);
		ShootEmUp.getGame().setCurrentLevel(level);

		List<String> enemyPrototypes = new ArrayList<>();
		enemyPrototypes.add("Small");
		enemyPrototypes.add("Normal");
		enemyPrototypes.add("Flying");
		level.addSpawner(enemyPrototypes);

		ShootEmUp.startGame();
	}
}