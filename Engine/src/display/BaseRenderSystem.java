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

public class BaseRenderSystem {

	// Level map file locations
	public static final String LEVEL_FILE_LOCATION = "/Levels/Level";

	// Texture.....stuff
	public static Shader ShaderBase;
	public static Shader ShaderStat;

	public static DPDTRenderer base;
	public static DPDTRenderer stat;


	public void init() {
		initShaders();
		ArtLoader.loadArt();
		initRenderers();
	}

	private void initRenderers() {
		base = new DPDTRenderer(ShaderBase);
		stat = new DPDTRenderer(ShaderStat);
	}

	protected void initShaders() {

		// Create a new shader program that links both shaders
		ShaderBase = new Shader("/Shaders/CameraVertexShader.glsl","/Shaders/BaseFragmentShader.glsl",Arrays.asList("pos","tex"));
		ShaderStat = new Shader("/Shaders/StaticVertexShader.glsl","/Shaders/BaseFragmentShader.glsl",Arrays.asList("pos","tex"));
	}
}
