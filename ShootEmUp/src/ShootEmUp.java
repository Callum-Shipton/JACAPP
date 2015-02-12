import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;



import Display.Art;
import Display.Display;
import Display.Renderer;
import Input.Keyboard;

public class ShootEmUp {

	// Screen Width & Height
	int WIDTH = 1024;
	int HEIGHT = 512;

	// Handle for monitor/window funcs
	private Display d;

	// Will be moved to LEVEL
	Renderer r;
	
	Level level1;

	public void run() {
		try {
			init();
			loop();

		}
		catch(Exception e){
			e.printStackTrace();
		}
			finally {

			// Release window and window callbacks Terminate GLFW and release
			// the GLFWerrorfun
			Keyboard.destroy();
			d.destroyGLFW();

		}
	}

	private void init() {
		d = new Display(WIDTH, HEIGHT);
		d.initGLFW();
		
		level1 = new Level(Art.level1);
		
		// Initialise key handling
		Keyboard.keyCheck(d.getWindow());

	}

	private void loop() {
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while (glfwWindowShouldClose(d.getWindow()) == GL_FALSE) {
			update();
			render();

		}
	}

	private void update() {
		// Poll for window events. The key callback above will only be
		// invoked during this call.
		glfwPollEvents();
		
		level1.update();
		d.update();
	}

	private void render() {
		 glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
	        glClear(GL_COLOR_BUFFER_BIT);
	        
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
	        GL11.glBindTexture(GL11.GL_TEXTURE_2D, d.textureIDs[0]);
	        
	        level1.render();

		glfwSwapBuffers(d.getWindow()); // Swaps front and back buffers to render changes
		
		
	}

	public static void main(String[] args){
		new ShootEmUp().run();
	}

}