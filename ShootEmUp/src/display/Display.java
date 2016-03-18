package display;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import input.Keyboard;

import java.nio.ByteBuffer;

import main.ShootEmUp;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

import components.ComponentType;
import components.graphical.PlayerGraphics;

public class Display {

	private GLFWErrorCallback errorCallback;

	// The window handle
	private long window;

	// The monitor handle (for Fullscreen mode)
	private long monitor;
	private ByteBuffer vidmode;
	private GLFWvidmode vm;

	private int width;
	private int height;
	
	boolean fullscreen = false;

	public Display(int width, int height) {
		this.width = width;
		this.height = height;
		initGLFW();
	}

	private void initGLFW() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.

		// Initialise GLFW. Most GLFW functions will not work before doing this.
		if (glfwInit() != GL_TRUE)
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
		
		vidmode = glfwGetVideoMode(monitor);
		vm = new GLFWvidmode(vidmode);

		// Create the window
		window = glfwCreateWindow(width, height, "THE MAZE", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Center our window
		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2,
				(GLFWvidmode.height(vidmode) - height) / 2);

		// Make the GLFW OpenGL context current
		glfwMakeContextCurrent(window);

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
		new Art().init();;
		
		// Initialise key handling
		Keyboard.keyCheck(window);
	}

	private void initGL() {
		glClearColor(0.4f, 0.6f, 0.9f, 1.0f);
		glViewport(0, 0, width, height);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
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
			if(fullscreen)toggleFullscreen();
			glfwSetWindowShouldClose(window, GL_TRUE);
			Keyboard.setKey(GLFW_KEY_ESCAPE);
		}
		if (Keyboard.getKey(GLFW_KEY_F) == 1) {
			toggleFullscreen();
			 // We will detect this in
			// our update loop
			Keyboard.setKey(GLFW_KEY_F);
		}
	}
	public void toggleFullscreen(){
		long newWindow;
		if(fullscreen){
			ShootEmUp.menuStack.peek().reset(width, height, 1024, 512);
			ShootEmUp.width = 1024;
			ShootEmUp.height = 512;
			width = 1024;
			height = 512;
			newWindow = glfwCreateWindow(width, height, "THE MAZE", NULL, window);
			if (newWindow == NULL)
				throw new RuntimeException("Failed to create the NEW GLFW window");

			// Center our window
			glfwSetWindowPos(newWindow, (GLFWvidmode.width(vidmode) - width) / 2,
					(GLFWvidmode.height(vidmode) - height) / 2);
		}else{
			ShootEmUp.menuStack.peek().reset(width, height, vm.getWidth(), vm.getHeight());
			ShootEmUp.width = vm.getWidth();
			ShootEmUp.height = vm.getHeight();
			width = vm.getWidth();
			height = vm.getHeight();
			newWindow = glfwCreateWindow(width, height, "THE MAZE", monitor, window);
			if (newWindow == NULL)
				throw new RuntimeException("Failed to create the GLFW window");
		}
		glfwMakeContextCurrent(newWindow);
		glfwSwapInterval(1);
		glfwShowWindow(newWindow);
		glfwDestroyWindow(window);
		window = newWindow;
		initGL();
		Keyboard.keyCheck(window);
		Art.initShaderUniforms();
		Art.refreshRenderers();
		if(ShootEmUp.currentLevel != null) ((PlayerGraphics) ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.GRAPHICS)).scrollScreen(null);
		fullscreen = !fullscreen;
	}
}
