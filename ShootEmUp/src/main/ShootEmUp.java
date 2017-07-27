package main;

import audio.MusicPlayer;
import display.Art;
import display.ImageProcessor;
import game.Game;
import gui.MenuSystem;
import gui.menus.InventoryMenu;
import gui.menus.MainMenu;
import input.Keyboard;
import loop.Loop;
import save.Save;
import save.ShootEmUpSave;

public class ShootEmUp implements Game {

	private static MusicPlayer musicPlayer;
	private static MenuSystem menuSystem = new MenuSystem();
	private static ShootEmUpSave save;
	private static GameBase game = new GameBase();

	private static boolean paused = true;

	public static void main(String[] args) {
		Loop loop = new Loop(new ShootEmUp(), 60.0f, new Art());
		loop.run();
	}

	public static void startGame() {
		menuSystem.setMainMenu(false);
		menuSystem.clearMenus();
		musicPlayer.changeCurrentMusic(musicPlayer.main);
		paused = false;
	}

	@Override
	public void init() {
		musicPlayer = new MusicPlayer();
		Save.init("funbrella0000000");
		paused = true;
		menuSystem.addMenu(new MainMenu(ImageProcessor.getImage("MainMenuScreen")));
		musicPlayer.playBackgroundMusic();
	}

	@Override
	public void render() {
		if (game != null) {
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
			Keyboard.setKey(Loop.getKeys().fullscreen);
		}

		if (!menuSystem.isMainMenu() && (Keyboard.getKey(Loop.getKeys().pause) == 1)) {
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
			InventoryMenu invMenu = new InventoryMenu(ImageProcessor.getImage("InventoryScreen"));
			invMenu.resetMenu();
			menuSystem.addMenu(invMenu);
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

	public static ShootEmUpSave getSave() {
		return save;
	}

	public static void setSave(ShootEmUpSave save) {
		ShootEmUp.save = save;
	}

	public static void setPaused(boolean p) {
		paused = p;
	}

	public static GameBase getGame() {
		return game;
	}
}
