import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;

public class ShootEmUp {

	// Screen Width & Height
	int WIDTH = 1024;
	int HEIGHT = 800;

	// Handle for monitor/window funcs
	private Display d;

	// Will be moved to LEVEL
	Player p;

	public void run() {
		try {
			init();
			loop();

		} finally {

			// Release window and window callbacks Terminate GLFW and release
			// the GLFWerrorfun
			d.destroyGLFW();
			Keyboard.destroy();

		}
	}

	private void init() {
		d = new Display(WIDTH, HEIGHT);
		d.initGLFW();

		p = new Player(100, 100, 1, 10);
		
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

		d.update();
		p.update();

	}

	private void render() {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the
		// framebuffer
		glfwSwapBuffers(d.getWindow());
		


	}

	public static void main(String[] args) {
		new ShootEmUp().run();
	}

}