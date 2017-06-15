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

import display.Display;
import input.Controllers;
import input.Keyboard;
import input.Keys;

public final class Loop {

	// Handle for monitor/window funcs
	private static Display display;
	private static boolean paused = true;
	private static double fps;
	private static Keys keys;
	private Game game;
	
	public Loop(Game game, double fps){
		this.game = game;
		Loop.fps = fps;
	}

	public void start() {
		System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
		System.setProperty("net.java.games.input.librarypath", new File("natives/JInput").getAbsolutePath());
		run();
	}

	public void run() {
		try {
			init();
			loop();
		} catch (Exception e) {
			Logger.error(e);
		} finally {
			// Release window and window callbacks Terminate GLFW and release
			// the GLFWerrorfun
			Keyboard.destroy();
			display.destroyGLFW();
			game.destroy();
		}
	}

	private void init() {
		display = new Display();
		display.initGLFW();
		keys = new Keys();
		Controllers.create();

		paused = true;
		game.init();
	}

	private void loop() {
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.

		double oldTime = GLFW.glfwGetTime();
		double newTime = GLFW.glfwGetTime();
		double delta;
		double sleepTime;
		int error;

		while (glfwWindowShouldClose(display.getWindow()) == GL_FALSE) {

			error = glGetError();

			if (error != GL_NO_ERROR) {
				Logger.error("OpenGL Error: " + error);
			}

			delta = newTime - oldTime;
			oldTime = newTime;
			sleepTime = (1.0 / fps) - delta;
			Logger.debug(1.0 / delta, Logger.Category.ENGINE_STATS);
			if (sleepTime > 0.01) {
				try {
					Thread.sleep((long) (sleepTime * 1000));
					Logger.debug("I slept for " + sleepTime + "seconds.", Logger.Category.ENGINE_STATS);
				} catch (InterruptedException e) {
					Logger.error(e);
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

		game.render();

		glfwSwapBuffers(display.getWindow()); // Swaps front and back buffers to
		// render changes
	}

	private void update() {
		// Poll for window events. The key callback above will only be
		// invoked during this call.
		glfwPollEvents();
		Controllers.poll();
		
		game.update();
		
		display.update();
	}

	public static int ticks(int seconds) {
		return (int) (seconds * getFPS());
	}

	public static Display getDisplay() {
		return display;
	}

	public static double getFPS() {
		return fps;
	}

	public static Keys getKeys() {
		return keys;
	}

	public static boolean isPaused() {
		return paused;
	}

	public static void setPaused(boolean p) {
		paused = p;
	}


}
