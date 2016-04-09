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

import java.io.File;

import org.lwjgl.glfw.GLFW;

import audio.MusicPlayer;
import audio.music.BackgroundMusic;
import components.TypeComponent;
import components.graphical.BaseGraphics;
import display.Art;
import display.Display;
import gui.Hud;
import gui.MenuSystem;
import gui.menus.MainMenu;
import input.Controllers;
import input.Keyboard;
import input.Keys;
import level.Level;
import object.Entity;
import save.Save;

public class ShootEmUp {

	// Handle for monitor/window funcs
	private static Display display;
	private static MusicPlayer musicPlayer;
	private static Level currentLevel;
	private static Entity player;
	private static boolean paused;
	private static MenuSystem menuSystem;
	private static Save save;
	private static double FPS = 60.0;
	private static Keys keys;
	private static Hud hud;

	public static Level getCurrentLevel() {
		return currentLevel;
	}

	public static Display getDisplay() {
		return display;
	}

	public static double getFPS() {
		return FPS;
	}

	public static Keys getKeys() {
		return keys;
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

	public static boolean isPaused() {
		return paused;
	}

	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
		System.setProperty("net.java.games.input.librarypath", new File("natives/JInput").getAbsolutePath());
		new ShootEmUp().run();
	}

	public static void setCurrentLevel(Level currentLevel) {
		ShootEmUp.currentLevel = currentLevel;
	}

	public static void setHud(Hud hud) {
		ShootEmUp.hud = hud;
	}

	public static void setPaused(boolean p) {
		paused = p;
	}

	public static void setPlayer(Entity p) {
		player = p;
	}

	public static void setSave(Save save) {
		ShootEmUp.save = save;
	}

	public static void startGame() {
		paused = false;
		menuSystem.setMainMenu(false);
		menuSystem.clearMenus();
		musicPlayer.changeCurrentMusic(BackgroundMusic.MAIN);
	}

	private void init() {
		display = new Display();
		display.initGLFW();
		musicPlayer = new MusicPlayer();
		menuSystem = new MenuSystem();
		keys = new Keys();
		Controllers.create();

		paused = true;
		menuSystem.addMenu(new MainMenu(Art.getImage("MainMenuScreen")));
		musicPlayer.play();
	}

	private void loop() {
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.

		double oldTime = GLFW.glfwGetTime();
		double newTime = GLFW.glfwGetTime();
		double delta = newTime - oldTime;
		double sleepTime = (1.0 / FPS) - delta;

		int Error = glGetError();

		while (glfwWindowShouldClose(display.getWindow()) == GL_FALSE) {

			Error = glGetError();

			if (Error != GL_NO_ERROR) {
				System.out.println("OpenGL Error: " + Error);
			}

			delta = newTime - oldTime;
			oldTime = newTime;
			sleepTime = (1.0 / FPS) - delta;
			// System.out.println(1.0/delta);
			if (sleepTime > 0.01) {
				try {
					Thread.sleep((long) (sleepTime * 1000));
					// System.out.println("I slept for " + sleepTime + "
					// seconds." );
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			update();
			render();

			newTime = GLFW.glfwGetTime();

		}
	}

	private void render() {
		glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);

		if (!paused) {
			getCurrentLevel().render();
			BaseGraphics BG = ShootEmUp.getPlayer().getComponent(TypeComponent.GRAPHICS);
			BG.render(ShootEmUp.getPlayer());
			hud.render(Art.stat);
		}
		menuSystem.render();

		glfwSwapBuffers(display.getWindow()); // Swaps front and back buffers to
		// render changes
	}

	public void run() {
		try {
			init();
			loop();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// Release window and window callbacks Terminate GLFW and release
			// the GLFWerrorfun
			Keyboard.destroy();
			display.destroyGLFW();
			musicPlayer.destroy();

		}
	}

	private void update() {
		// Poll for window events. The key callback above will only be
		// invoked during this call.
		glfwPollEvents();
		Controllers.poll();
		if (!menuSystem.isMainMenu()) {
			if (Keyboard.getKey(keys.pause) == 1) {
				ShootEmUp.setPaused(!ShootEmUp.isPaused());
				Keyboard.setKey(keys.pause);
				menuSystem.pause();
			}
		}
		if (!paused) {
			getCurrentLevel().update();
			hud.update();
		} else {
			menuSystem.update();
		}
		// dealing with pausing music
		musicPlayer.update();

		display.update();

	}
}
