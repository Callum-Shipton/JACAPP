package main;

import audio.MusicPlayer;
import audio.music.BackgroundMusic;
import display.Art;
import display.ImageProcessor;
import game.Game;
import gui.MenuSystem;
import gui.menus.InventoryMenu;
import gui.menus.MainMenu;
import input.Keyboard;
import loop.Loop;
import save.Save;

public class ShootEmUp implements Game {

	private static MusicPlayer musicPlayer;
	private static MenuSystem menuSystem = new MenuSystem();
	private static Save save;
	private static GameBase game = new GameBase();

	private static boolean paused = true;

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
			game.render();
		}
		menuSystem.render();
	}

	@Override
	public void update() {

		if (Keyboard.getKey(Loop.getKeys().fullscreen) == 1) {
			Loop.getDisplay().toggleFullscreen();
			if (game.getHud() != null) {
				game.getHud().resetHud();
			}
			menuSystem.resetMenus();
			// TODO write code for changing position of menu items
		}

		if (!menuSystem.isMainMenu() && Keyboard.getKey(Loop.getKeys().pause) == 1) {
			pause();
		}

		if (!paused) {
			game.update();
		} else {
			menuSystem.update();
		}
		// dealing with pausing music
		musicPlayer.update();
	}

	public void pause() {
		paused = !paused;
		Keyboard.setKey(Loop.getKeys().pause);
		if (paused) {
			menuSystem.addMenu(new InventoryMenu(ImageProcessor.getImage("InventoryScreen")));
		} else {
			menuSystem.clearMenus();
		}
	}

	@Override
	public void destroy() {
		musicPlayer.destroy();
	}

	public static MenuSystem getMenuSystem() {
		return menuSystem;
	}

	public static MusicPlayer getMusicPlayer() {
		return musicPlayer;
	}

	public static Save getSave() {
		return save;
	}

	public static void setSave(Save save) {
		ShootEmUp.save = save;
	}

	public static void setPaused(boolean p) {
		paused = p;
	}

	public static GameBase getGame() {
		return game;
	}
}
