package main;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import gui.menus.GuiMenu;
import gui.menus.InventoryMenu;
import gui.menus.MainMenu;
import input.Controllers;
import input.Keyboard;

import java.io.File;
import java.util.Stack;

import level.Level;

import org.lwjgl.glfw.GLFW;

import display.Art;
import display.Display;
import audio.music.BackgroundMusic;

public class ShootEmUp {

	// Screen Width & Height
	public static int width = 1024;
	public static int height = 512;

	// Handle for monitor/window funcs
	public static Display display;
	public static BackgroundMusic backgroundMusic;
	public static int currentMusic = BackgroundMusic.MENU;
	public static boolean musicPause = false;
	
	public static boolean paused;
	public static boolean mainMenu = true;

	public static Level currentLevel;
	public static Stack<GuiMenu> menuStack = new Stack<GuiMenu>();
	
	public static Save[] saves;

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
			backgroundMusic.destoyAL();

		}
	}

	private void init() {
		display = new Display(width, height);
		backgroundMusic = new BackgroundMusic();
		backgroundMusic.initAL();
		
		Controllers.create();
		
		paused = true;
		addMenu(new MainMenu(Art.mainMenuScreen));
		backgroundMusic.play(currentMusic);
	}

	private void loop() {
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.

		double FPS = 60.0;
		double oldTime = GLFW.glfwGetTime();
		double newTime = GLFW.glfwGetTime();
		double delta = newTime - oldTime;
		double sleepTime = (1.0 / FPS) - delta;

		int Error = glGetError();
		
		while (glfwWindowShouldClose(display.getWindow()) == GL_FALSE) {

			Error = glGetError();
			
			if(Error != GL_NO_ERROR){
				System.out.println("OpenGL Error: " + Error);
			}
			
			delta = newTime - oldTime;
			oldTime = newTime;
			sleepTime = (1.0 / FPS) - delta;
			//System.out.println(1.0/delta);
			if (sleepTime > 0.01)
				try {
					Thread.sleep((long) (sleepTime * 1000));
					 //System.out.println("I slept for " + sleepTime + " seconds." );
				} catch (InterruptedException e) {
					e.printStackTrace();
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
		if(!mainMenu){
			if(Keyboard.getKey(GLFW_KEY_P) == 1){
					paused = !paused;
					Keyboard.setKey(GLFW_KEY_P);
					if(paused) addMenu(new InventoryMenu(Art.invScreen));
					else clearMenus();
			}
			if(!paused){
				currentLevel.update();
			}
		}
		if (!menuStack.isEmpty()) {
			menuStack.peek().update();
		}
		
		//dealing with pausing music
		if(Keyboard.getKey(GLFW_KEY_M) == 1){
			Keyboard.setKey(GLFW_KEY_M);
			if(musicPause){
				backgroundMusic.play(currentMusic);
			} else {
				backgroundMusic.pause(currentMusic);
			}
			musicPause = !musicPause;
		}
		
		display.update();

	}

	private void render() {
		glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);

		if(!paused){
			currentLevel.render();
		}
		if (!menuStack.isEmpty()) {
			menuStack.peek().render();
		}

		glfwSwapBuffers(display.getWindow()); // Swaps front and back buffers to
										// render changes
	}

	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
		System.setProperty("net.java.games.input.librarypath", new File("natives/JInput").getAbsolutePath());
		new ShootEmUp().run();
	}
	
	public static void clearMenus() {
		while (!menuStack.isEmpty()) {
			menuStack.pop();
		}
	}

	public static void addMenu(GuiMenu menu) {
		menuStack.add(menu);
	}

}
