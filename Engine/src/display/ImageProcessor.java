package display;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.system.MemoryStack.stackPush;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import logging.Logger;
import loop.Loop;

public class ImageProcessor {

	private static Map<String, Image> artFiles = new HashMap<>();

	// Level map file locations
	public static final String LEVEL_FILE_LOCATION = "/Levels/Level";

	// Texture.....stuff
	public static int ShaderBase;
	public static int ShaderInst;
	public static int ShaderStat;

	public static DPDTRenderer base;
	public static DPDTRenderer stat;
	
	public static IRenderer irBack;
	public static IRenderer irWall;
	public static IRenderer irFore;

	public static Image getImage(String filename) {
		return artFiles.get(filename);
	}

	public static void initShaderUniforms() {

		Matrix4f projectionMatrix = new Matrix4f();
		projectionMatrix.ortho(0, Loop.getDisplay().getWidth(), Loop.getDisplay().getHeight(), 0, -1.0f, 1.0f);

		try (MemoryStack stack = stackPush()) {
			FloatBuffer buf = stack.callocFloat(16);
			buf = projectionMatrix.get(buf);

			glUseProgram(ShaderBase);

			int error = glGetError();

			if (error != GL_NO_ERROR) {
				Logger.error("OpenGL Error: " + error);
			}

			final String projectionMatrixString = "projectionMatrix";

			int projectionMatrixLocation = glGetUniformLocation(ShaderBase, projectionMatrixString);
			glUniformMatrix4fv(projectionMatrixLocation, false, buf);
			glUseProgram(0);

			glUseProgram(ShaderInst);
			projectionMatrixLocation = glGetUniformLocation(ShaderInst, projectionMatrixString);
			glUniformMatrix4fv(projectionMatrixLocation, false, buf);
			glUseProgram(0);

			glUseProgram(ShaderStat);
			projectionMatrixLocation = glGetUniformLocation(ShaderStat, projectionMatrixString);
			glUniformMatrix4fv(projectionMatrixLocation, false, buf);
			glUseProgram(0);
		}

	}

	public static void addArt(String key, Image image) {
		artFiles.put(key, image);
	}

	public static void refreshRenderers() {
		base.initRenderData();
		stat.initRenderData();

		if (irWall != null)
			irWall.bindRenderData();
		if (irBack != null)
			irBack.bindRenderData();
		if (irFore != null)
			irFore.bindRenderData();
	}

	public void init(ArtLoader artLoader) {

		initShaders();
		initShaderUniforms();
		artLoader.loadArt();
		initRenderers();

	}

	private static void initRenderers() {
		base = new DPDTRenderer(ShaderBase);
		stat = new DPDTRenderer(ShaderStat);
	}

	private void initShaders() {

		// Load the vertex shader
		int vsId = loadShader("/Shaders/VertexShader.glsl", GL_VERTEX_SHADER);
		// Load the fragment shader
		int fsId = loadShader("/Shaders/FragmentShader.glsl", GL_FRAGMENT_SHADER);

		int IvsId = loadShader("/Shaders/IVertexShader.glsl", GL_VERTEX_SHADER);
		// Load the fragment shader
		int IfsId = loadShader("/Shaders/IFragmentShader.glsl", GL_FRAGMENT_SHADER);

		int SvsId = loadShader("/Shaders/StatVertexShader.glsl", GL_VERTEX_SHADER);

		int SfsId = loadShader("/Shaders/StatFragmentShader.glsl", GL_FRAGMENT_SHADER);

		// Create a new shader program that links both shaders
		ShaderBase = glCreateProgram();
		glAttachShader(ShaderBase, vsId);
		glAttachShader(ShaderBase, fsId);

		// Position information will be attribute 0
		glBindAttribLocation(ShaderBase, 0, "pos");
		// Textute information will be attribute 1
		glBindAttribLocation(ShaderBase, 1, "tex");

		glLinkProgram(ShaderBase);
		glValidateProgram(ShaderBase);

		ShaderInst = glCreateProgram();
		glAttachShader(ShaderInst, IvsId);
		glAttachShader(ShaderInst, IfsId);

		glBindAttribLocation(ShaderInst, 0, "pos");
		// Textute information will be attribute 1
		glBindAttribLocation(ShaderInst, 1, "tex");
		glBindAttribLocation(ShaderInst, 2, "trans");
		// Textute information will be attribute 1
		glBindAttribLocation(ShaderInst, 3, "text");

		glLinkProgram(ShaderInst);
		glValidateProgram(ShaderInst);

		ShaderStat = glCreateProgram();
		glAttachShader(ShaderStat, SvsId);
		glAttachShader(ShaderStat, SfsId);

		// Position information will be attribute 0
		glBindAttribLocation(ShaderStat, 0, "pos");
		// Textute information will be attribute 1
		glBindAttribLocation(ShaderStat, 1, "tex");

		glLinkProgram(ShaderStat);
		glValidateProgram(ShaderStat);

	}

	public int loadShader(String filename, int type) {
		StringBuilder shaderSource = new StringBuilder();
		int shaderID;

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			Logger.error("Could not read shader file.");
			Logger.error(e);
			System.exit(-1);
		}

		shaderID = glCreateShader(type);
		glShaderSource(shaderID, shaderSource);
		glCompileShader(shaderID);

		String infoLog = glGetShaderInfoLog(shaderID, glGetShaderi(shaderID, GL_INFO_LOG_LENGTH));

		if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
			throw new RuntimeException("Failure in compiling " + filename + " shader. Error log:\n" + infoLog);
		}

		return shaderID;
	}

}
