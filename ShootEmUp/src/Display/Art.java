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
import Math.Vector2;

public class Art {
	
	//menu screens
	public static Image mainMenuScreen;
	public static Image invScreen;
	public static Image skillScreen;
	public static Image magicScreen;
	public static Image mapScreen;
	public static Image saveScreen;
	public static Image gameOverScreen;
	
	//buttons
	public static Image newGameButton;
	public static Image loadGameButton;
	public static Image optionsButton;
	public static Image exitButton;
	
	public static Image backButton;
	
	public static Image level1Button;
	public static Image level2Button;
	
	public static Image warriorButton;
	public static Image archerButton;
	public static Image mageButton;
	
	public static Image invButton;
	public static Image skillButton;
	public static Image magicButton;
	public static Image mapButton;
	public static Image saveButton;
	
	public static Image healthButton;
	public static Image healthRegenButton;
	public static Image manaButton;
	public static Image manaRegenButton;
	
	//Tile maps
	public static Image background;
	public static Image wall;
	public static Image foreground;
	
	//Character maps
	public static Image player;
	public static Image enemy;
	public static Image smallEnemy;
	public static Image flyingEnemy;
	
	//Drop maps
	public static Image coin;
	
	//Magic maps
	public static Image earthMagic;
	public static Image fireMagic;
	public static Image iceMagic;
	
	public static Image arrow;
	public static Image sword;
	
	//Armour maps
	public static Image shoes;
	public static Image legs;
	public static Image chest;
	public static Image helmet;
	
	//Item maps
	public static Image healthPotion;
	public static Image manaPotion;
	public static Image speedPotion;
	public static Image knockbackPotion;
	
	//Weapon maps
	public static Image bow;
	
	//HUD Textures
	public static Image infoBox;
	public static Image healthBar;
	public static Image manaBar;
	public static Image xpBar;
	public static Image level;
	public static Image wave;
	public static Image numbers;
	
	//Level map file locations
	public static String level1 = "/levels/level1.png";
	public static String level2 = "/levels/level2.png";
	
	//Texture.....stuff
	public static int ShaderBase;
	public static int ShaderInst;
	public static int ShaderStat;
	
	public static DPDTRenderer base;
	public static DPDTRenderer stat;
	public static IRenderer irBack;
	public static IRenderer irWall;
	public static IRenderer irFore;

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
		
		int SfsId = loadShader("/shaders/StatFragmentShader.glsl",
				GL_FRAGMENT_SHADER);

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
		
		//load menu screen art
		mainMenuScreen = new Image("/Menus/mainMenuScreen.png", 1, 1);
		invScreen = new Image("/Menus/invScreen.png", 1, 1);
		skillScreen = new Image("/Menus/skillScreen.png", 1, 1);
		magicScreen = new Image("/Menus/magicScreen.png", 1, 1);
		mapScreen = new Image("/Menus/mapScreen.png", 1, 1);
		saveScreen = new Image("/Menus/saveScreen.png", 1, 1);
		gameOverScreen = new Image("/Menus/gameOverScreen.png", 1, 1);
		
		//load button art
		newGameButton = new Image("/Buttons/newGameButton.png",1,2);
		
		loadGameButton = new Image("/Buttons/loadGameButton.png",1,2);
		optionsButton = new Image("/Buttons/optionsButton.png",1,2);
		exitButton = new Image("/Buttons/exitButton.png",1,2);
		
		backButton = new Image("/Buttons/backButton.png",1,2);
		
		level1Button = new Image("/Buttons/level1Button.png",1,2);
		level2Button = new Image("/Buttons/level2Button.png",1,2);
		
		warriorButton = new Image("/Buttons/warriorButton.png",1,2);
		archerButton = new Image("/Buttons/archerButton.png",1,2);
		mageButton = new Image("/Buttons/mageButton.png",1,2);
		
		invButton = new Image("/Buttons/invButton.png",1,2);
		skillButton = new Image("/Buttons/skillButton.png",1,2);
		magicButton = new Image("/Buttons/magicButton.png",1,2);
		mapButton = new Image("/Buttons/mapButton.png",1,2);
		saveButton = new Image("/Buttons/saveButton.png",1,2);
		
		healthButton = new Image("/Buttons/healthButton.png",1,2);
		healthRegenButton = new Image("/Buttons/healthRegenButton.png",1,2);
		manaButton = new Image("/Buttons/manaButton.png",1,2);
		manaRegenButton = new Image("/Buttons/manaRegenButton.png",1,2);
		
		
		//Load tile maps
		background = new Image("/Tiles/background.png",4,4);
		wall = new Image("/Tiles/wall.png",8,8);
		foreground = new Image("/Tiles/foreground.png",4,4);
		
		//Load character maps
		player = new Image("/Characters/Player.png",6,8);
		enemy = new Image("/Characters/Enemy.png",6,8);
		smallEnemy = new Image("/Characters/SmallEnemy.png",6,8);
		flyingEnemy = new Image("/Characters/FlyingEnemy.png",6,8);
		
		//Load particle maps
		coin = new Image("/img/Coin.png",6,1);
		
		//Load Magic maps
		earthMagic = new Image("/Magic/EarthMagic.png",1,8);
		fireMagic = new Image("/Magic/FireMagic.png",1,8);
		iceMagic = new Image("/Magic/IceMagic.png",1,8);
		
		arrow = new Image("/Magic/Arrow2.png",1,8);
		sword = new Image("/Magic/Sword.png",1,8);
		
		//Load Armour maps
		shoes = new Image("/Armour/Shoes.png",8,1);
		legs = new Image("/Armour/Legs.png",3,1);
		chest = new Image("/Armour/Chest.png",3,1);
		helmet = new Image("/Armour/Helmet.png",7,1);
		
		//Load Item maps
		healthPotion = new Image("/Items/HealthPotion.png",3,1);
		manaPotion = new Image("/Items/ManaPotion.png",3,1);
		speedPotion = new Image("/Items/SpeedPotion.png",3,1);
		knockbackPotion = new Image("/Items/KnockbackPotion.png",3,1);
		
		//Load Weapon maps
		bow = new Image("/Weapons/Bow.png",8,1);
		
		//Load HUD textures 
		infoBox = new Image("/HUD/BarInfo.png",1,1);
		healthBar = new Image("/HUD/BarHealth.png",1,2);			
		manaBar = new Image("/HUD/BarMana.png",1,2);
		xpBar = new Image("/HUD/BarXP.png",1,2);
		level = new Image("/HUD/Level.png",1,1);
		wave = new Image("/HUD/Wave.png",1,1);
		numbers = new Image("/HUD/Numbers.png",10,1);
	}

	

	public static void initShaderUniforms() {

		Matrix4 projectionMatrix = new Matrix4();
		projectionMatrix.clearToOrtho(0, ShootEmUp.WIDTH, ShootEmUp.HEIGHT, 0,
				-1.0f, 1.0f);
		FloatBuffer matrix44Buffer = BufferUtils.createFloatBuffer(16);
		matrix44Buffer = projectionMatrix.toBuffer();
		


		glUseProgram(ShaderBase);
		
		int Error = glGetError();
		
		if(Error != GL_NO_ERROR){
			System.out.println("OpenGL Error: " + Error);
		}
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
	
	private void initRenderers(){
		base = new DPDTRenderer(ShaderBase);
		stat = new DPDTRenderer(ShaderStat);
	}

	public void init() {
		
		initShaders();
		initShaderUniforms();
		initTextures();
		initRenderers();
		
	}
	
	public static void refreshRenderers(){
		base.initRenderData();
		stat.initRenderData();
		if(ShootEmUp.currentLevel != null){
		irWall.initRenderData(ShootEmUp.currentLevel.map.getWalls(), new Vector2(wall.getFWidth(), wall.getFHeight()));
		irBack.initRenderData(ShootEmUp.currentLevel.map.getBackgroundTiles(), new Vector2(background.getFWidth(),background.getFHeight()));
		irFore.initRenderData(ShootEmUp.currentLevel.map.getForegroundTiles(), new Vector2(foreground.getFWidth(), foreground.getFHeight()));
		}
	}
	
	

}
