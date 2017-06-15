package main;

import audio.MusicPlayer;
import audio.music.BackgroundMusic;
import components.TypeComponent;
import components.graphical.BaseGraphics;
import display.Art;
import gui.Hud;
import gui.MenuSystem;
import gui.menus.MainMenu;
import input.Keyboard;
import level.Level;
import object.Entity;
import save.Save;

public class ShootEmUp implements Game {

	private MusicPlayer musicPlayer;
	private Level currentLevel;
	private Entity player;
	private static MenuSystem menuSystem = new MenuSystem();
	private static Save save;

	private static boolean paused = true;
	private Hud hud;

	public ShootEmUp() {
	}

	public static void main(String[] args) {
		Loop loop = new Loop(new ShootEmUp(), 60.0f);
		loop.start();
	}

	public void startGame() {
		menuSystem.setMainMenu(false);
		menuSystem.clearMenus();
		musicPlayer.changeCurrentMusic(BackgroundMusic.MAIN);
	}

	@Override
	public void init() {
		musicPlayer = new MusicPlayer();

		paused = true;
		menuSystem.addMenu(new MainMenu(Art.getImage("MainMenuScreen")));
		musicPlayer.play();
	}

	@Override
	public void render() {
		if (!paused) {
			currentLevel.render();
			BaseGraphics baseGraphics = player.getComponent(TypeComponent.GRAPHICS);
			baseGraphics.render(player);
			hud.render(Art.stat);
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

	public Level getCurrentLevel() {
		return currentLevel;
	}

	public static MenuSystem getMenuSystem() {
		return menuSystem;
	}

	public MusicPlayer getMusicPlayer() {
		return musicPlayer;
	}

	public Entity getPlayer() {
		return player;
	}

	public static Save getSave() {
		return save;
	}

	public void setCurrentLevel(Level currentLevel) {
		this.currentLevel = currentLevel;
	}

	public void setHud(Hud hud) {
		this.hud = hud;
	}

	public void setPlayer(Entity player) {
		this.player = player;
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
