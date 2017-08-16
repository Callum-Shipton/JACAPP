package main;

import audio.MusicPlayer;
import display.ArtLoader;
import display.ExtendedRenderSystem;
import game.Game;
import gui.MenuSystem;
import gui.menus.InventoryMenu;
import gui.menus.MainMenu;
import input.Keyboard;
import input.Keys;
import loop.GameLoop;
import save.Save;
import save.ShootEmUpSave;

public class ShootEmUp implements Game {
	
	private static final float FPS = 60.0f;
	private static final int SCREEN_WIDTH = 1024;
	private static final int SCREEN_HEIGHT = 512;
	private static final String GAME_NAME = "The Maze";

	private static MusicPlayer musicPlayer;
	private static MenuSystem menuSystem = new MenuSystem();
	private static ShootEmUpSave save;
	private static GameBase game = new GameBase();
	private static ExtendedRenderSystem renderSystem = new ExtendedRenderSystem();

	private static boolean paused = true;

	public static void main(String[] args) {
		GameLoop loop = new GameLoop(new ShootEmUp(), FPS, SCREEN_WIDTH, SCREEN_HEIGHT, GAME_NAME);
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
		renderSystem.init();
		musicPlayer = new MusicPlayer();
		Save.init("funbrella0000000");
		menuSystem.addMenu(new MainMenu());
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

		if (Keyboard.getKey(Keys.getKey("fullscreen")) == 1) {
			GameLoop.getDisplay().toggleFullscreen();
			if (game.getHud() != null) {
				game.getHud().resetHud();
			}
			menuSystem.resetMenus();
			Keyboard.setKey(Keys.getKey("fullscreen"));
		}

		if (!menuSystem.isMainMenu() && (Keyboard.getKey(Keys.getKey("pause")) == 1)) {
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
		Keyboard.setKey(Keys.getKey("pause"));
		if (paused) {
			InventoryMenu invMenu = new InventoryMenu(ArtLoader.getImage("InventoryScreen"));
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
