package gui.buttons;

import java.util.ArrayList;
import java.util.List;

import display.ImageProcessor;
import gui.ButtonAction;
import level.Level;
import level.TypeEnemy;
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

		List<TypeEnemy> enemyPrototypes = new ArrayList<>();
		enemyPrototypes.add(TypeEnemy.SMALL);
		enemyPrototypes.add(TypeEnemy.NORMAL);
		enemyPrototypes.add(TypeEnemy.FLYING);
		level.addSpawner(enemyPrototypes);

		ShootEmUp.startGame();
	}
}