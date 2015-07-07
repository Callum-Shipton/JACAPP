package main;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import gui.menus.GuiMenu;
import gui.menus.InventoryMenu;
import gui.menus.MainMenu;
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
	public static int WIDTH = 1024;
	public static int HEIGHT = 512;

	// Handle for monitor/window funcs
	public static Display d;
	public static BackgroundMusic m;
	
	public static boolean paused;

	public static Level currentLevel;
	public static Stack<GuiMenu> menuStack = new Stack<GuiMenu>();

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
			d.destroyGLFW();
			m.destoyAL();

		}
	}

	private void init() {
		d = new Display(WIDTH, HEIGHT);
		d.initGLFW();
		m = new BackgroundMusic();
		m.initAL();
		
		paused = true;
		addMenu(new MainMenu(Art.mainMenuScreen));
		m.play(BackgroundMusic.MENU);
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
		
		while (glfwWindowShouldClose(d.getWindow()) == GL_FALSE) {

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
		if(currentLevel != null){
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
		d.update();

	}

	private void render() {
		glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);

		if(!paused && (currentLevel != null)){
			currentLevel.render();
		}
		if (!menuStack.isEmpty()) {
			menuStack.peek().render();
		}

		glfwSwapBuffers(d.getWindow()); // Swaps front and back buffers to
										// render changes
	}

	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
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