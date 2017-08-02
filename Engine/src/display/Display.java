package display;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowMonitor;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import input.Keyboard;
import input.Keys;

public class Display {

	// Screen Width & Height
	public static final int INITIAL_SCREEN_WIDTH = 1024;

	public static final int INITIAL_SCREEN_HEIGHT = 512;

	private GLFWErrorCallback errorCallback;
	// The window handle
	private long window;
	// The monitor handle (for Fullscreen mode)
	private long monitor;

	GLFWVidMode vidmode;

	private Camera cam;

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
		glfwSetErrorCallback(null).free();
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

	public Camera getCamera() {
		return this.cam;
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
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are
									// already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden
													// after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be
													// resizable

		monitor = glfwGetPrimaryMonitor();

		// Create the window
		window = glfwCreateWindow(width, height, "THE MAZE", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			vidmode = glfwGetVideoMode(monitor);

			// Center the window
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
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
		GL.createCapabilities();

		initGL();
		new ImageProcessor().init(artLoader);
		cam = new Camera(width, height);

		// Initialise key handling

	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void toggleFullscreen() {
		if (fullscreen) {
			width = INITIAL_SCREEN_WIDTH;
			height = INITIAL_SCREEN_HEIGHT;
			glfwSetWindowMonitor(window, NULL, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2, width,
					height, 60);
		} else {
			width = vidmode.width();
			height = vidmode.height();
			glfwSetWindowMonitor(window, monitor, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2, width,
					height, 60);
		}
		fullscreen = !fullscreen;
		glfwSwapInterval(1);
		glViewport(0, 0, this.width, this.height);
		cam.updateCameraSize(width, height);
		;
		ImageProcessor.initShaderUniforms();
	}

	public void update() {
		if (Keyboard.getKey(Keys.getKey("quit")) == 1) {
			if (fullscreen) {
				// toggleFullscreen();
			}
			glfwSetWindowShouldClose(window, true);
			Keyboard.setKey(Keys.getKey("quit"));
		}
		if (Keyboard.getKey(Keys.getKey("fullscreen")) == 1) {
			// toggleFullscreen();

			// We will detect this in
			// our update loop
			Keyboard.setKey(Keys.getKey("fullscreen"));
		}
	}
}
