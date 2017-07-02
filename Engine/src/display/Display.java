package display;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

import input.Keyboard;
import loop.Loop;

public class Display {

	// Screen Width & Height
	public static final int INITIAL_SCREEN_WIDTH = 1024;

	public static final int INITIAL_SCREEN_HEIGHT = 512;

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
	
	private ArtLoader artLoader;

	public Display(ArtLoader artLoader) {
		this.artLoader = artLoader;
		
		this.width = INITIAL_SCREEN_WIDTH;
		this.height = INITIAL_SCREEN_HEIGHT;
	}

	public void destroyGLFW() {
		glfwDestroyWindow(this.window);
		glfwTerminate();
		this.errorCallback.release();
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	public long getWindow() {
		return this.window;
	}

	private void initGL() {
		glClearColor(0.4f, 0.6f, 0.9f, 1.0f);
		glViewport(0, 0, this.width, this.height);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	public void initGLFW() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.

		// Initialise GLFW. Most GLFW functions will not work before doing this.
		if (glfwInit() != GL_TRUE) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		glfwSetErrorCallback(this.errorCallback = errorCallbackPrint(System.err));

		// Configure our window
		glfwDefaultWindowHints(); // optional, the current window hints are

		// already the default
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden
		// after creation
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE); // the window will be
		// resizable

		// Find primary monitor
		this.monitor = glfwGetPrimaryMonitor();
		if (this.monitor == NULL) {
			throw new RuntimeException("Failed to find primary monitor");
		}

		this.vidmode = glfwGetVideoMode(this.monitor);
		this.vm = new GLFWvidmode(this.vidmode);

		// Create the window
		this.window = glfwCreateWindow(this.width, this.height, "THE MAZE", NULL, NULL);
		if (this.window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}

		// Center our window
		glfwSetWindowPos(this.window, (GLFWvidmode.width(this.vidmode) - this.width) / 2,
				(GLFWvidmode.height(this.vidmode) - this.height) / 2);

		// Make the GLFW OpenGL context current
		glfwMakeContextCurrent(this.window);

		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(this.window);

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the ContextCapabilities instance and makes the OpenGL
		// bindings available for use.
		GLContext.createFromCurrent();

		initGL();
		new ImageProcessor().init(artLoader);

		// Initialise key handling
		Keyboard.keyCheck(this.window);
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	
	public void toggleFullscreen() {
		long newWindow;
		if (this.fullscreen) {
			// ShootEmUp.menuStack.peek().reset(width, height, 1024, 512);
			this.width = INITIAL_SCREEN_WIDTH;
			this.height = INITIAL_SCREEN_HEIGHT;
			newWindow = glfwCreateWindow(this.width, this.height, "THE MAZE", NULL, this.window);
			if (newWindow == NULL) {
				throw new RuntimeException("Failed to create the NEW GLFW window");
			}

			// Center our window
			glfwSetWindowPos(newWindow, (GLFWvidmode.width(this.vidmode) - this.width) / 2,
					(GLFWvidmode.height(this.vidmode) - this.height) / 2);
		} else {
			// ShootEmUp.menuStack.peek().reset(width, height, vm.getWidth(),
			// vm.getHeight());
			this.width = this.vm.getWidth();
			this.height = this.vm.getHeight();
			newWindow = glfwCreateWindow(this.width, this.height, "THE MAZE", this.monitor, this.window);
			if (newWindow == NULL) {
				throw new RuntimeException("Failed to create the GLFW window");
			}
		}
		glfwMakeContextCurrent(newWindow);
		glfwSwapInterval(1);
		glfwShowWindow(newWindow);
		glfwDestroyWindow(this.window);
		this.window = newWindow;
		initGL();
		Keyboard.keyCheck(this.window);
		ImageProcessor.initShaderUniforms();
		ImageProcessor.refreshRenderers();
		/*
		if (ShootEmUp.getCurrentLevel() != null) {
		PlayerGraphics BG = ShootEmUp.getPlayer().getComponent(TypeComponent.GRAPHICS);
			BG.scrollScreen();
		}
		*/
		this.fullscreen = !this.fullscreen;
	}
	

	public void update() {
		if (Keyboard.getKey(Loop.getKeys().quit) == 1) {
			if (fullscreen) {
				// toggleFullscreen();
			}
			glfwSetWindowShouldClose(this.window, GL_TRUE);
			Keyboard.setKey(Loop.getKeys().quit);
		}
		if (Keyboard.getKey(Loop.getKeys().fullscreen) == 1) {
			//toggleFullscreen();
			
			// We will detect this in
			// our update loop
			Keyboard.setKey(Loop.getKeys().fullscreen);
		}
	}
}
