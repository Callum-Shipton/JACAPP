package main;

import audio.MusicPlayer;
import audio.music.BackgroundMusic;
import components.TypeComponent;
import components.graphical.BaseGraphics;
import display.Art;
import display.ImageProcessor;
import entity.Entity;
import game.Game;
import gui.Hud;
import gui.MenuSystem;
import gui.menus.MainMenu;
import input.Keyboard;
import level.Level;
import loop.Loop;
import save.Save;

public class ShootEmUp implements Game {

	private static MusicPlayer musicPlayer;
	private static Level currentLevel;
	private static Entity player;
	private static MenuSystem menuSystem = new MenuSystem();
	private static Save save;

	private static boolean paused = true;
	private static Hud hud;

	public ShootEmUp() {
	}

	public static void main(String[] args) {
		Loop loop = new Loop(new ShootEmUp(), 60.0f, new Art());
		loop.start();
	}

	public static void startGame() {
		menuSystem.setMainMenu(false);
		menuSystem.clearMenus();
		musicPlayer.changeCurrentMusic(BackgroundMusic.MAIN);
		paused = false;
	}

	@Override
	public void init() {
		musicPlayer = new MusicPlayer();

		paused = true;
		menuSystem.addMenu(new MainMenu(ImageProcessor.getImage("MainMenuScreen")));
		musicPlayer.play();
	}

	@Override
	public void render() {
		if (!paused) {
			currentLevel.render();
			BaseGraphics baseGraphics = player.getComponent(TypeComponent.GRAPHICS);
			baseGraphics.render(player);
			hud.render(ImageProcessor.stat);
		}
		menuSystem.render();
	}

	@Override
	public void update() {

		if (!menuSystem.isMainMenu() && Keyboard.getKey(Loop.getKeys().pause) == 1) {
			paused = !paused;
			Keyboard.setKey(Loop.getKeys().pause);
			menuSystem.pause();
		}

		if (!paused) {
			currentLevel.update();
			hud.update();
		} else {
			menuSystem.update();
		}
		// dealing with pausing music
		musicPlayer.update();
	}

	@Override
	public void destroy() {
		musicPlayer.destroy();
	}

	public static Level getCurrentLevel() {
		return currentLevel;
	}

	public static MenuSystem getMenuSystem() {
		return menuSystem;
	}

	public static MusicPlayer getMusicPlayer() {
		return musicPlayer;
	}

	public static Entity getPlayer() {
		return player;
	}

	public static Save getSave() {
		return save;
	}

	public static void setCurrentLevel(Level currentLevel) {
		ShootEmUp.currentLevel = currentLevel;
	}

	public static void setHud(Hud hud) {
		ShootEmUp.hud = hud;
	}

	public static void setPlayer(Entity player) {
		ShootEmUp.player = player;
	}

	public static void setSave(Save save) {
		ShootEmUp.save = save;
	}

	public static boolean isPaused() {
		return paused;
	}

	public static void setPaused(boolean p) {
		paused = p;
	}
}
