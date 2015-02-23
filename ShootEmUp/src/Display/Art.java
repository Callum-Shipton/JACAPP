package Display;

import static org.lwjgl.opengl.GL11.GL_FALSE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import Main.ShootEmUp;
import Math.Matrix4;

public class Art {

	public static Image floor;

	public static Image wall;

	public static Image enemy;

	public static Image player;

	public static Image particle;

	public static Image infoBox;

	public static Image healthBar;

	public static Image manaBar;

	public static Image xpBar;
	
	public static String level1 = "/levels/level1.png";

	public static int ShaderBase;
	public static int ShaderInst;
	public static int ShaderStat;

	public static Image BarCoin;

	private void initShaders() {
		// Load the vertex shader
		int vsId = loadShader("/shaders/VertexShader.glsl",
				GL20.GL_VERTEX_SHADER);
		// Load the fragment shader
		int fsId = loadShader("/shaders/FragmentShader.glsl",
				GL20.GL_FRAGMENT_SHADER);

		int IvsId = loadShader("/shaders/IVertexShader.glsl",
				GL20.GL_VERTEX_SHADER);
		// Load the fragment shader
		int IfsId = loadShader("/shaders/IFragmentShader.glsl",
				GL20.GL_FRAGMENT_SHADER);
		
		int SvsId = loadShader("/shaders/StatVertexShader.glsl",
				GL20.GL_VERTEX_SHADER);

		// Create a new shader program that links both shaders
		ShaderBase = GL20.glCreateProgram();
		GL20.glAttachShader(ShaderBase, vsId);
		GL20.glAttachShader(ShaderBase, fsId);

		// Position information will be attribute 0
		GL20.glBindAttribLocation(ShaderBase, 0, "pos");
		// Textute information will be attribute 1
		GL20.glBindAttribLocation(ShaderBase, 1, "tex");

		GL20.glLinkProgram(ShaderBase);
		GL20.glValidateProgram(ShaderBase);

		ShaderInst = GL20.glCreateProgram();
		GL20.glAttachShader(ShaderInst, IvsId);
		GL20.glAttachShader(ShaderInst, IfsId);

		GL20.glBindAttribLocation(ShaderInst, 0, "pos");
		// Textute information will be attribute 1
		GL20.glBindAttribLocation(ShaderInst, 1, "tex");
		GL20.glBindAttribLocation(ShaderInst, 2, "trans");
		// Textute information will be attribute 1
		GL20.glBindAttribLocation(ShaderInst, 3, "text");

		GL20.glLinkProgram(ShaderInst);
		GL20.glValidateProgram(ShaderInst);
		
		ShaderStat = GL20.glCreateProgram();
		GL20.glAttachShader(ShaderStat, SvsId);
		GL20.glAttachShader(ShaderStat, fsId);

		// Position information will be attribute 0
		GL20.glBindAttribLocation(ShaderStat, 0, "pos");
		// Textute information will be attribute 1
		GL20.glBindAttribLocation(ShaderStat, 1, "tex");

		GL20.glLinkProgram(ShaderStat);
		GL20.glValidateProgram(ShaderStat);
		

	}

	public int loadShader(String filename, int type) {
		StringBuilder shaderSource = new StringBuilder();
		int shaderID = 0;

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					getClass().getResourceAsStream(filename)));
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

		String infoLog = GL20.glGetShaderInfoLog(shaderID,
				GL20.glGetShaderi(shaderID, GL20.GL_INFO_LOG_LENGTH));

		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL_FALSE)
			throw new RuntimeException("Failure in compiling " + filename
					+ " shader. Error log:\n" + infoLog);
		else {
			System.out.print("Compiling " + filename + " shader successful.");
			if (infoLog != null && !(infoLog = infoLog.trim()).isEmpty())
				System.out.println(" Log:\n" + infoLog);
			else
				System.out.println();
		}

		return shaderID;
	}

	private static void initTextures() {
		
		floor = new Image("/Tiles/floor.png",4,4);

		wall = new Image("/Tiles/wall.png",4,4);

		player = new Image("/img/Player.png",1,8);

		enemy = new Image("/img/Enemy.png",1,8);

		particle = new Image("/img/Particle.png",1,8);
		
		infoBox = new Image("/HUD/BarInfo.png",1,1);

		healthBar = new Image("/HUD/BarHealth.png",1,19);			

		manaBar = new Image("/HUD/BarMana.png",1,19);

		xpBar = new Image("/HUD/BarXP.png",1,19);
		
		BarCoin = new Image("/HUD/BarCoin.png",1,1);

		/*
		 * Template for other Texs
		floor = new Image("/img/floor.png");
		 */
	}

	

	private static void initShaderUniforms() {

		Matrix4 projectionMatrix = new Matrix4();
		projectionMatrix.clearToOrtho(0, ShootEmUp.WIDTH, ShootEmUp.HEIGHT, 0,
				-1.0f, 1.0f);
		FloatBuffer matrix44Buffer = BufferUtils.createFloatBuffer(16);
		matrix44Buffer = projectionMatrix.toBuffer();

		GL20.glUseProgram(ShaderBase);
		int projectionMatrixLocation = GL20.glGetUniformLocation(ShaderBase,
				"projectionMatrix");
		GL20.glUniformMatrix4(projectionMatrixLocation, false, matrix44Buffer);
		GL20.glUseProgram(0);

		GL20.glUseProgram(ShaderInst);
		projectionMatrixLocation = GL20.glGetUniformLocation(ShaderInst,
				"projectionMatrix");
		GL20.glUniformMatrix4(projectionMatrixLocation, false, matrix44Buffer);
		GL20.glUseProgram(0);
		
		GL20.glUseProgram(ShaderStat);
		projectionMatrixLocation = GL20.glGetUniformLocation(ShaderStat,
				"projectionMatrix");
		GL20.glUniformMatrix4(projectionMatrixLocation, false, matrix44Buffer);
		GL20.glUseProgram(0);

	}

	public void init() {
		initShaders();
		initShaderUniforms();
		initTextures();
	}

}
