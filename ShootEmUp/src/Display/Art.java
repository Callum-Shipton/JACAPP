package Display;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;


import Main.ShootEmUp;
import Math.Matrix4;

public class Art {
	public static Image back;
	public static Image exit;
	public static Image invScreen;
	
	//Tile maps
	public static Image background;
	public static Image wall;
	public static Image foreground;
	
	//Character maps
	public static Image enemy;
	public static Image player;
	
	//Drop maps
	public static Image exp;
	public static Image coin;
	
	//Magic maps
	public static Image earthMagic;
	public static Image fireMagic;
	public static Image iceMagic;
	
	//Armour maps
	public static Image shoes;
	public static Image legs;
	public static Image chest;
	public static Image helmet;
	public static Image ring;
	
	//Item maps
	public static Image healthPotion;
	public static Image manaPotion;
	
	//Weapon maps
	public static Image bow;
	
	//HUD Textures
	public static Image infoBox;
	public static Image healthBar;
	public static Image manaBar;
	public static Image xpBar;
	public static Image BarCoin;
	public static Image numbers;
	
	//Level map file locations
	public static String level1 = "/levels/level1.png";
	
	//Texture.....stuff
	public static int ShaderBase;
	public static int ShaderInst;
	public static int ShaderStat;


	private void initShaders() {
		// Load the vertex shader
		int vsId = loadShader("/shaders/VertexShader.glsl",
				GL_VERTEX_SHADER);
		// Load the fragment shader
		int fsId = loadShader("/shaders/FragmentShader.glsl",
				GL_FRAGMENT_SHADER);

		int IvsId = loadShader("/shaders/IVertexShader.glsl",
				GL_VERTEX_SHADER);
		// Load the fragment shader
		int IfsId = loadShader("/shaders/IFragmentShader.glsl",
				GL_FRAGMENT_SHADER);
		
		int SvsId = loadShader("/shaders/StatVertexShader.glsl",
				GL_VERTEX_SHADER);

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
		glAttachShader(ShaderStat, fsId);

		// Position information will be attribute 0
		glBindAttribLocation(ShaderStat, 0, "pos");
		// Textute information will be attribute 1
		glBindAttribLocation(ShaderStat, 1, "tex");

		glLinkProgram(ShaderStat);
		glValidateProgram(ShaderStat);
		

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

		shaderID = glCreateShader(type);
		glShaderSource(shaderID, shaderSource);
		glCompileShader(shaderID);

		String infoLog = glGetShaderInfoLog(shaderID,
				glGetShaderi(shaderID, GL_INFO_LOG_LENGTH));

		if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE)
			throw new RuntimeException("Failure in compiling " + filename
					+ " shader. Error log:\n" + infoLog);

		return shaderID;
	}

	private static void initTextures() {
		
		//new Image("/filepath.png", maxFrameWidth, maxFrameHeight);
		
		invScreen = new Image("/Menus/invScreen.png", 1, 1);
		back = new Image("/buttons/back.png",1,2);
		exit = new Image("/buttons/exit.png",1,2);
		
		//Load tile maps
		background = new Image("/Tiles/background.png",4,4);
		wall = new Image("/Tiles/wall.png",8,8);
		foreground = new Image("/Tiles/foreground.png",4,4);
		
		//Load character maps
		player = new Image("/Characters/Player.png",6,8);
		enemy = new Image("/Characters/Enemy.png",6,8);
		
		//Load particle maps
		exp = new Image("/img/Exp.png",6,1);
		coin = new Image("/img/Coin.png",6,1);
		
		//Load Magic maps
		earthMagic = new Image("/Magic/EarthMagic.png",1,8);
		fireMagic = new Image("/Magic/FireMagic.png",1,8);
		iceMagic = new Image("/Magic/IceMagic.png",1,8);
		
		//Load Armour maps
		shoes = new Image("/Armour/Shoes.png",8,1);
		legs = new Image("/Armour/Legs.png",3,1);
		chest = new Image("/Armour/Chest.png",3,1);
		helmet = new Image("/Armour/Helmet.png",3,1);
		ring = new Image("/Armour/Ring.png",6,1);
		
		//Load Item maps
		healthPotion = new Image("/Items/HealthPotion.png",3,1);
		manaPotion = new Image("/Items/ManaPotion.png",3,1);
		
		//Load Weapon maps
		bow = new Image("/Weapons/Bow.png",8,1);
		
		//Load HUD textures 
		infoBox = new Image("/HUD/BarInfo.png",1,1);
		healthBar = new Image("/HUD/BarHealth.png",1,19);			
		manaBar = new Image("/HUD/BarMana.png",1,19);
		xpBar = new Image("/HUD/BarXP.png",1,19);
		BarCoin = new Image("/HUD/BarCoin.png",1,1);
		numbers = new Image("/HUD/Numbers.png",10,1);
	}

	

	private static void initShaderUniforms() {

		Matrix4 projectionMatrix = new Matrix4();
		projectionMatrix.clearToOrtho(0, ShootEmUp.WIDTH, ShootEmUp.HEIGHT, 0,
				-1.0f, 1.0f);
		FloatBuffer matrix44Buffer = BufferUtils.createFloatBuffer(16);
		matrix44Buffer = projectionMatrix.toBuffer();

		glUseProgram(ShaderBase);
		int projectionMatrixLocation = glGetUniformLocation(ShaderBase,
				"projectionMatrix");
		glUniformMatrix4(projectionMatrixLocation, false, matrix44Buffer);
		glUseProgram(0);

		glUseProgram(ShaderInst);
		projectionMatrixLocation = glGetUniformLocation(ShaderInst,
				"projectionMatrix");
		glUniformMatrix4(projectionMatrixLocation, false, matrix44Buffer);
		glUseProgram(0);
		
		glUseProgram(ShaderStat);
		projectionMatrixLocation = glGetUniformLocation(ShaderStat,
				"projectionMatrix");
		glUniformMatrix4(projectionMatrixLocation, false, matrix44Buffer);
		glUseProgram(0);

	}

	public void init() {
		initShaders();
		initShaderUniforms();
		initTextures();
	}

}
