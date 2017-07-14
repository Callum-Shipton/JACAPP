package display;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

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
				if ( !glfwInit() )
					throw new IllegalStateException("Unable to initialize GLFW");

				// Configure GLFW
				glfwDefaultWindowHints(); // optional, the current window hints are already the default
				glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
				glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

				// Create the window
				window = glfwCreateWindow(width, height, "THE MAZE", NULL, NULL);
				if ( window == NULL )
					throw new RuntimeException("Failed to create the GLFW window");

				// Setup a key callback. It will be called every time a key is pressed, repeated or released.
				glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
					if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
						glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
				});

				// Get the thread stack and push a new frame
				try ( MemoryStack stack = stackPush() ) {
					IntBuffer pWidth = stack.mallocInt(1); // int*
					IntBuffer pHeight = stack.mallocInt(1); // int*

					// Get the window size passed to glfwCreateWindow
					glfwGetWindowSize(window, pWidth, pHeight);

					// Get the resolution of the primary monitor
					vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

					// Center the window
					glfwSetWindowPos(
						window,
						(vidmode.width() - pWidth.get(0)) / 2,
						(vidmode.height() - pHeight.get(0)) / 2
					);
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
			glfwSetWindowPos(newWindow, (vidmode.width() - this.width) / 2,
					(vidmode.height() - this.height) / 2);
		} else {
			// ShootEmUp.menuStack.peek().reset(width, height, vm.getWidth(),
			// vm.getHeight());
			this.width = vidmode.width();
			this.height = vidmode.height();
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
		cam = new Camera(width, height);
		Keyboard.init(this.window);
		ImageProcessor.initShaderUniforms();
		ImageProcessor.refreshRenderers();
		/*
		 * if (ShootEmUp.getCurrentLevel() != null) { PlayerGraphics BG =
		 * ShootEmUp.getPlayer().getComponent(TypeComponent.GRAPHICS);
		 * BG.scrollScreen(); }
		 */
		this.fullscreen = !this.fullscreen;
	}

	public void update() {
		if (Keyboard.getKey(Loop.getKeys().quit) == 1) {
			if (fullscreen) {
				// toggleFullscreen();
			}
			glfwSetWindowShouldClose(window, true);
			Keyboard.setKey(Loop.getKeys().quit);
		}
		if (Keyboard.getKey(Loop.getKeys().fullscreen) == 1) {
			// toggleFullscreen();

			// We will detect this in
			// our update loop
			Keyboard.setKey(Loop.getKeys().fullscreen);
		}
	}
}
