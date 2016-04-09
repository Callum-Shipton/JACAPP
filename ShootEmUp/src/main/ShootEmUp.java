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
import display.Art;
import display.Display;
import gui.MenuSystem;
import gui.menus.MainMenu;
import input.Controllers;
import input.Keyboard;
import level.Level;
import save.Save;

public class ShootEmUp {

	// Handle for monitor/window funcs
	private static Display display;	
	private static MusicPlayer musicPlayer;
	private static Level currentLevel;	
	private static boolean paused;	
	private static MenuSystem menuSystem;
	private static Save save;
	private static double FPS = 60.0;

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

	private void init() {
		display = new Display();
		display.initGLFW();
		musicPlayer = new MusicPlayer();
		menuSystem = new MenuSystem();
		
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

	private void update() {
		// Poll for window events. The key callback above will only be
		// invoked during this call.
		glfwPollEvents();
		Controllers.poll();
		menuSystem.update();
		if (!paused) {
			getCurrentLevel().update();
		}
		// dealing with pausing music
		musicPlayer.update();

		display.update();

	}

	private void render() {
		glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);

		if (!paused) {
			getCurrentLevel().render();
		}
		menuSystem.render();

		glfwSwapBuffers(display.getWindow()); // Swaps front and back buffers to
		// render changes
	}

	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
		System.setProperty("net.java.games.input.librarypath", new File("natives/JInput").getAbsolutePath());
		new ShootEmUp().run();
	}

	public static void startGame() {
		paused = false;
		menuSystem.setMainMenu(false);
		menuSystem.clearMenus();
		musicPlayer.changeCurrentMusic(BackgroundMusic.MAIN);
	}
	
	public static Display getDisplay() {
		return display;
	}

	public static Save getSave() {
		return save;
	}

	public static void setSave(Save save) {
		ShootEmUp.save = save;
	}

	public static double getFPS() {
		return FPS;
	}

	public static MusicPlayer getMusicPlayer() {
		return musicPlayer;
	}

	public static boolean isPaused() {
		return paused;
	}

	public static void setPaused(boolean p) {
		paused = p;
	}

	public static MenuSystem getMenuSystem() {
		return menuSystem;
	}

	public static Level getCurrentLevel() {
		return currentLevel;
	}

	public static void setCurrentLevel(Level currentLevel) {
		ShootEmUp.currentLevel = currentLevel;
	}
}
