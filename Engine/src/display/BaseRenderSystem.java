package display;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryStack.stackPush;

import java.nio.FloatBuffer;
import java.util.Arrays;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import logging.Logger;
import loop.GameLoop;

public class ImageProcessor {

	// Level map file locations
	public static final String LEVEL_FILE_LOCATION = "/Levels/Level";

	// Texture.....stuff
	public static Shader ShaderBase;
	public static Shader ShaderInst;
	public static Shader ShaderStat;

	public static DPDTRenderer base;
	public static DPDTRenderer stat;
	public static IRenderer irBack;
	public static IRenderer irWall;
	public static IRenderer irFore;

	public static void refreshShaderProjections() {

		Matrix4f projectionMatrix = new Matrix4f();
		projectionMatrix.ortho(0, GameLoop.getDisplay().getWidth(), GameLoop.getDisplay().getHeight(), 0, -1.0f, 1.0f);

		try (MemoryStack stack = stackPush()) {
			FloatBuffer buf = stack.callocFloat(16);
			buf = projectionMatrix.get(buf);

			glUseProgram(ShaderBase.getProgramID());

			int error = glGetError();

			if (error != GL_NO_ERROR) {
				Logger.error("OpenGL Error: " + error);
			}

			final String projectionMatrixString = "projectionMatrix";

			int projectionMatrixLocation = glGetUniformLocation(ShaderBase.getProgramID(), projectionMatrixString);
			glUniformMatrix4fv(projectionMatrixLocation, false, buf);
			glUseProgram(0);

			glUseProgram(ShaderInst.getProgramID());
			projectionMatrixLocation = glGetUniformLocation(ShaderInst.getProgramID(), projectionMatrixString);
			glUniformMatrix4fv(projectionMatrixLocation, false, buf);
			glUseProgram(0);

			glUseProgram(ShaderStat.getProgramID());
			projectionMatrixLocation = glGetUniformLocation(ShaderStat.getProgramID(), projectionMatrixString);
			glUniformMatrix4fv(projectionMatrixLocation, false, buf);
			glUseProgram(0);
		}

	}

	public static void init() {
		initShaders();
		refreshShaderProjections();
		ArtLoader.loadArt();
		initRenderers();
	}

	private static void initRenderers() {
		base = new DPDTRenderer(ShaderBase);
		stat = new DPDTRenderer(ShaderStat);
	}

	private static void initShaders() {

		// Create a new shader program that links both shaders
		ShaderBase = new Shader("/Shaders/CameraVertexShader.glsl","/Shaders/BaseFragmentShader.glsl",Arrays.asList("pos","tex"));
		ShaderInst = new Shader("/Shaders/CameraIVertexShader.glsl","/Shaders/BaseIFragmentShader.glsl",Arrays.asList("pos","tex","trans","text"));
		ShaderStat = new Shader("/Shaders/StaticVertexShader.glsl","/Shaders/BaseFragmentShader.glsl",Arrays.asList("pos","tex"));
		
	}
}
