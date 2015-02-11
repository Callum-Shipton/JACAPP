package Display;
import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

import Input.Keyboard;
import Math.Matrix4;

public class Display {

	private GLFWErrorCallback errorCallback;

	// The window handle
	private long window;

	// The monitor handle (for Fullscreen mode)
	private long monitor;

	private int width;
	private int height;

	private int[] texIds = new int[] {0};
	private int pId = 0;
	
	Matrix4 projectionMatrix;
	int projectionMatrixLocation = 0;

	public Display(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void initGLFW() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

		// Initialise GLFW. Most GLFW functions will not work before doing this.
		if (glfwInit() != GL_TRUE)
			throw new IllegalStateException("Unable to initialize GLFW");

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
		
		loadShaders();
		setupTextures();
		setupMatrices();
	}
	
	private void setupMatrices() {
		GL20.glUseProgram(pId);
		projectionMatrixLocation = GL20.glGetUniformLocation(pId, "projectionMatrix");
		

		projectionMatrix = new Matrix4();
		projectionMatrix.clearToOrtho(0, width, height, 0, -1.0f, 1.0f);
		projectionMatrix.transpose();
		FloatBuffer matrix44Buffer = BufferUtils.createFloatBuffer(16);
		matrix44Buffer = projectionMatrix.toBuffer();
		
		GL20.glUniformMatrix4(projectionMatrixLocation, true, matrix44Buffer);
		
		GL20.glUseProgram(0);
        

	}

	private void setupTextures() {
        
		GL20.glUseProgram(pId);
	
		
		texIds[0] = GL11.glGenTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texIds[0]);

		
		Image texIm = new Image(Art.face);
		ByteBuffer buf = texIm.byteBuffer();

         
        // All RGB bytes are aligned to each other and each component is 1 byte
       GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
         
        // Upload the texture data and generate mip maps (for scaling)
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, texIm.getWidth(), texIm.getHeight(), 0, 
                GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
       GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
         
        // Setup the ST coordinate system
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
         
        // Setup what to do when the texture has to be scaled
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL20.glUseProgram(0);
        }

	private void loadShaders() {
		// Load the vertex shader
        int vsId = this.loadShader("src/VertexShader.glsl", GL20.GL_VERTEX_SHADER);
        // Load the fragment shader
        int fsId = this.loadShader("src/FragmentShader.glsl", GL20.GL_FRAGMENT_SHADER);
         
        // Create a new shader program that links both shaders
        pId = GL20.glCreateProgram();
        GL20.glAttachShader(pId, vsId);
        GL20.glAttachShader(pId, fsId);
 
        // Position information will be attribute 0
        GL20.glBindAttribLocation(pId, 0, "pos");
        // Textute information will be attribute 1
        GL20.glBindAttribLocation(pId, 1, "tex");
         
        GL20.glLinkProgram(pId);
        GL20.glValidateProgram(pId);
        
        
		
	}

	private int loadShader(String filename, int type) {
        StringBuilder shaderSource = new StringBuilder();
        int shaderID = 0;
         
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }
         
        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        
        String infoLog = GL20.glGetShaderInfoLog(shaderID, GL20.glGetShaderi(shaderID, GL20.GL_INFO_LOG_LENGTH));
		
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL_FALSE)
			throw new RuntimeException("Failure in compiling " + filename + " shader. Error log:\n" + infoLog);
		else {
			System.out.print("Compiling " + filename + " shader successful.");
			if(infoLog != null && !(infoLog = infoLog.trim()).isEmpty())
				System.out.println(" Log:\n" + infoLog);
			else
				System.out.println();
		}
		
         
        return shaderID;
    }

	private void initGL() {
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 1.0f);
		GL11.glViewport(0, 0, width, height);
	}

	public void destroyGLFW() {
		glfwDestroyWindow(window);
		glfwTerminate();
		errorCallback.release();
	}

	public long getWindow() {
		return window;
	}
	
	public int getSID() {
		return pId;
	}
	public int getTID() {
		return texIds[0];
	}

	public void update() {
		if (Keyboard.getKey(GLFW_KEY_ESCAPE) == 1) {
			glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in
			// our rendering loop
		}

	}
}
