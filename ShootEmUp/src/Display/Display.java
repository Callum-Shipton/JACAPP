package Display;
import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import Input.Keyboard;

public class Display {

	private GLFWErrorCallback errorCallback;

	// The window handle
	private long window;

	// The monitor handle (for Fullscreen mode)
	private long monitor;

	private int width;
	private int height;

	public Display(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void initGLFW() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		

		// Initialise GLFW. Most GLFW functions will not work before doing this.
		if (glfwInit() != GL11.GL_TRUE)
			throw new IllegalStateException("Unable to initialize GLFW");
		
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

		// Configure our window
		glfwDefaultWindowHints(); // optional, the current window hints are
	
		// already the default
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden
		// after creation
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE); // the window will be
		// resizable

		// Find primary monitor
		monitor = glfwGetPrimaryMonitor();
		if (monitor == NULL)
			throw new RuntimeException("Failed to find primary monitor");

		ByteBuffer vidmode = glfwGetVideoMode(monitor);

		// Create the window
		window = glfwCreateWindow(width, height, "Hello World!", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Center our window
		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2,
				(GLFWvidmode.height(vidmode) - height) / 2);

		// Make the GLFW OpenGL context current
		glfwMakeContextCurrent(window);
		
		// Enable v-sync
		glfwSwapInterval(1);
		// Make the window visible
		glfwShowWindow(window);

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the ContextCapabilities instance and makes the OpenGL
		// bindings available for use.
		GLContext.createFromCurrent();
		
		initGL();
		Art a = new Art();
		a.init();
	}
	
	private void initGL() {
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 1.0f);
		GL11.glViewport(0, 0, width, height);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void destroyGLFW() {
		glfwDestroyWindow(window);
		glfwTerminate();
		errorCallback.release();
	}

	public long getWindow() {
		return window;
	}

	public void update() {
		if (Keyboard.getKey(GLFW_KEY_ESCAPE) == 1) {
			glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in
			// our update loop
		}

	}
}
