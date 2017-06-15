package main;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glGetError;

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

public class ShootEmUp implements Game{

	private MusicPlayer musicPlayer;
	private Level currentLevel;
	private Entity player;
	private MenuSystem menuSystem;
	private Save save;
	private Hud hud;
	private static Loop loop;

	public ShootEmUp(){}
	
	public static void main(String[] args) {
		loop = new Loop(new ShootEmUp(), 60.0f);
		loop.start();
	}

	public void startGame() {
		menuSystem.setMainMenu(false);
		menuSystem.clearMenus();
		musicPlayer.changeCurrentMusic(BackgroundMusic.MAIN);
	}

	@Override
	public void init(){
		musicPlayer = new MusicPlayer();
		menuSystem = new MenuSystem();

		loop.setPaused(true);
		menuSystem.addMenu(new MainMenu(Art.getImage("MainMenuScreen")));
		musicPlayer.play();
	}

	@Override
	public void render() {
		if(!loop.isPaused()){
			currentLevel.render();
			BaseGraphics baseGraphics = player.getComponent(TypeComponent.GRAPHICS);
			baseGraphics.render(player);
			hud.render(Art.stat);
		}
		menuSystem.render();
	}
	
	@Override
	public void update() {
		/*
		if (!menuSystem.isMainMenu() && Keyboard.getKey(keys.pause) == 1) {
			loop.setPaused(!loop.isPaused());
			Keyboard.setKey(keys.pause);
			menuSystem.pause();
		}
		*/
		if (!loop.isPaused()) {
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

	public MenuSystem getMenuSystem() {
		return menuSystem;
	}

	public MusicPlayer getMusicPlayer() {
		return musicPlayer;
	}

	public Entity getPlayer() {
		return player;
	}

	public Save getSave() {
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

	public void setSave(Save save) {
		this.save = save;
	}
}
