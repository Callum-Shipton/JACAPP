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
import maze.Direction;
import maze.Maze;
import save.ShootEmUpSave;

public class GameBase {

	private Level currentLevel;

	private Vector2i levelPosition;
	private Maze maze;

	private Entity player;
	private Hud hud;

	public void update() {
		if (currentLevel.getLevelState() == null) {
			currentLevel.update();
		} else {
			changeLevel(currentLevel.getLevelState());
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

	private void changeLevel(Direction direction) {

		Vector2f spawnLocation = new Vector2f(1500.0f, 2400.0f);
		float levelWidth = currentLevel.getMap().getRealWidth();
		float levelHeight = currentLevel.getMap().getRealHeight();

		switch (direction) {
		case N:
			levelPosition = new Vector2i(levelPosition.x, levelPosition.y - 1);
			spawnLocation = new Vector2f(levelWidth / 2, levelHeight - 200.0f);
			break;
		case W:
			levelPosition = new Vector2i(levelPosition.x - 1, levelPosition.y);
			spawnLocation = new Vector2f(levelWidth - 200.0f, levelHeight / 2);
			break;
		case S:
			levelPosition = new Vector2i(levelPosition.x, levelPosition.y + 1);
			spawnLocation = new Vector2f(levelWidth / 2, 200.0f);
			break;
		case E:
			levelPosition = new Vector2i(levelPosition.x + 1, levelPosition.y);
			spawnLocation = new Vector2f(200.0f, levelHeight / 2);
			break;
		}

		if (levelPosition.equals(new Vector2i(5, 9))) {
			System.out.print("YOU WIN!!!!");
			System.exit(0);
		}

		currentLevel.setLevelState(null);

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
		PointSpawn bs = player.getComponent(TypeComponent.SPAWN);
		bs.setSpawnLocation(spawnLocation);
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
