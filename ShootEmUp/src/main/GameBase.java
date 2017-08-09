package main;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector2i;

import components.TypeComponent;
import components.graphical.BaseGraphics;
import components.spawn.PointSpawn;
import display.ImageProcessor;
import entity.Entity;
import gui.Hud;
import level.Level;
import logging.Logger;
import maze.Maze;
import save.ShootEmUpSave;

public class GameBase {

	private Level currentLevel;

	private Vector2i levelPosition;
	private Maze maze;

	private Entity player;
	private Hud hud;

	public void update() {
		switch (currentLevel.getLevelState()) {
		case 0:
			currentLevel.update();
			break;
		case 1:
			changeLevel(new Vector2i(levelPosition.x, levelPosition.y - 1));
			break;
		case 2:
			changeLevel(new Vector2i(levelPosition.x - 1, levelPosition.y));
			break;
		case 3:
			changeLevel(new Vector2i(levelPosition.x, levelPosition.y + 1));
			break;
		case 4:
			changeLevel(new Vector2i(levelPosition.x + 1, levelPosition.y));
			break;
		}

		hud.update();
	}

	public void render() {
		if (currentLevel != null) {
			currentLevel.render();
		}
		if (player != null) {
			BaseGraphics baseGraphics = player.getComponent(TypeComponent.GRAPHICS);
			baseGraphics.render(player);
		}
		if (hud != null) {
			hud.render(ImageProcessor.stat);
		}
	}

	private void changeLevel(Vector2i levelPosition) {
		this.levelPosition = levelPosition;

		if (ShootEmUp.getSave() == null) {
			ShootEmUp.setSave(new ShootEmUpSave());
		}

		ShootEmUpSave save = ShootEmUp.getSave();

		// TODO fix saves
		save.saveCharacter("well fuck", player.getComponent(TypeComponent.ATTACK));
		save.setLevel(levelPosition);
		save.saveToSystem(1);

		currentLevel = new Level(maze.getTile(levelPosition));
		currentLevel.init();

		List<String> enemyPrototypes = new ArrayList<>();
		enemyPrototypes.add("Small");
		enemyPrototypes.add("Normal");
		enemyPrototypes.add("Flying");

		currentLevel.addSpawner(enemyPrototypes);

		try {
			ShootEmUp.getSave().load(1);
		} catch (Exception e) {
			Logger.error(e);
		}
		player = new Entity("Characters", "Players", "Warrior");
		PointSpawn bs = player.getComponent(TypeComponent.SPAWN);
		bs.setSpawnLocation(new Vector2f(480.0f, 480.0f));
		bs.spawn();
		currentLevel.addEntity(player);
		hud = new Hud(player, 0, 0);
	}

	public void newGame() {
		maze = new Maze(10);
		maze.generateMaze();

		player = new Entity("Characters", "Players", "Warrior");
		PointSpawn spawn = player.getComponent(TypeComponent.SPAWN);
		spawn.setSpawnLocation(new Vector2f(480.0f, 480.0f));
		spawn.spawn();

		levelPosition = maze.getStart();
		currentLevel = new Level(maze.getTile(levelPosition));
		currentLevel.init();
		currentLevel.addEntity(player);

		List<String> enemyPrototypes = new ArrayList<>();
		enemyPrototypes.add("Small");
		enemyPrototypes.add("Normal");
		enemyPrototypes.add("Flying");
		currentLevel.addSpawner(enemyPrototypes);

		ShootEmUp.startGame();

		hud = new Hud(ShootEmUp.getGame().getPlayer(), 0, 0);
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
}
