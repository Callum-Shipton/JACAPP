package display;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import main.ShootEmUp;
import math.Matrix4;
import math.Vector2;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

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
	
	public static Image controlsButton;
	public static Image soundButton;
	
	public static Image level1Button;
	public static Image level2Button;
	
	public static Image warriorButton;
	public static Image archerButton;
	public static Image mageButton;
	
	public static Image swordButton;
	public static Image battleaxeButton;
	public static Image maceButton;
	public static Image bowButton;
	public static Image crossbowButton;
	public static Image iceStaffButton;
	public static Image fireStaffButton;
	public static Image earthStaffButton;
	
	public static Image bootsButton;
	public static Image legsButton;
	public static Image chestButton;
	public static Image helmetButton;
	
	public static Image healthButton;
	public static Image healthRegenButton;
	public static Image manaButton;
	public static Image manaRegenButton;
	
	public static Image invButton;
	public static Image skillButton;
	public static Image magicButton;
	public static Image mapButton;
	public static Image saveButton;
	
	//Tile maps
	public static Image background;
	public static Image wall;
	public static Image foreground;
	
	//Character maps
	public static Image player;
	public static Image enemy;
	public static Image smallEnemy;
	public static Image flyingEnemy;
	public static Image bossEnemy;
	
	//Drop maps
	public static Image coin;
	
	//Magic maps
	public static Image earthMagic;
	public static Image fireMagic;
	public static Image iceMagic;
	
	public static Image arrow;
	public static Image swordProjectile;
	
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
	public static Image sword;
	public static Image battleaxe;
	public static Image mace;
	public static Image bow;
	public static Image crossbow;
	public static Image fireStaff;
	public static Image iceStaff;
	public static Image earthStaff;
	
	//HUD Textures
	public static Image infoBox;
	public static Image healthBar;
	public static Image manaBar;
	public static Image xpBar;
	public static Image level;
	public static Image wave;
	public static Image numbers;
	
	//Level map file locations
	public static String level1 = "/Levels/Level1.png";
	public static String level2 = "/Levels/Level2.png";
	
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
		int vsId = loadShader("/Shaders/VertexShader.glsl",
				GL_VERTEX_SHADER);
		// Load the fragment shader
		int fsId = loadShader("/Shaders/FragmentShader.glsl",
				GL_FRAGMENT_SHADER);

		int IvsId = loadShader("/Shaders/IVertexShader.glsl",
				GL_VERTEX_SHADER);
		// Load the fragment shader
		int IfsId = loadShader("/Shaders/IFragmentShader.glsl",
				GL_FRAGMENT_SHADER);
		
		int SvsId = loadShader("/Shaders/StatVertexShader.glsl",
				GL_VERTEX_SHADER);
		
		int SfsId = loadShader("/Shaders/StatFragmentShader.glsl",
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
		mainMenuScreen = new Image("/Images/Menus/Backgrounds/MainMenuScreen.png", 1, 1);
		invScreen = new Image("/Images/Menus/Backgrounds/InventoryScreen.png", 1, 1);
		skillScreen = new Image("/Images/Menus/Backgrounds/SkillScreen.png", 1, 1);
		magicScreen = new Image("/Images/Menus/Backgrounds/MagicScreen.png", 1, 1);
		mapScreen = new Image("/Images/Menus/Backgrounds/MapScreen.png", 1, 1);
		saveScreen = new Image("/Images/Menus/Backgrounds/SaveScreen.png", 1, 1);
		gameOverScreen = new Image("/Images/Menus/Backgrounds/GameOverScreen.png", 1, 1);
		
		//load button art
		newGameButton = new Image("/Images/Menus/Buttons/MenuButtons/NewGameButton.png",1,2);
		
		loadGameButton = new Image("/Images/Menus/Buttons/MenuButtons/LoadGameButton.png",1,2);
		optionsButton = new Image("/Images/Menus/Buttons/MenuButtons/OptionsButton.png",1,2);
		exitButton = new Image("/Images/Menus/Buttons/MenuButtons/ExitButton.png",1,2);
		
		backButton = new Image("/Images/Menus/Buttons/MenuButtons/BackButton.png",1,2);
		
		controlsButton = new Image("/Images/Menus/Buttons/MenuButtons/ControlsButton.png",1,2);
		soundButton = new Image("/Images/Menus/Buttons/MenuButtons/SoundButton.png",1,2);
		
		level1Button = new Image("/Images/Menus/Buttons/MenuButtons/Level1Button.png",1,2);
		level2Button = new Image("/Images/Menus/Buttons/MenuButtons/Level2Button.png",1,2);
		
		warriorButton = new Image("/Images/Menus/Buttons/MenuButtons/WarriorButton.png",1,2);
		archerButton = new Image("/Images/Menus/Buttons/MenuButtons/ArcherButton.png",1,2);
		mageButton = new Image("/Images/Menus/Buttons/MenuButtons/MageButton.png",1,2);
		
		healthButton = new Image("/Images/Menus/Buttons/MenuButtons/HealthButton.png",1,2);
		healthRegenButton = new Image("/Images/Menus/Buttons/MenuButtons/HealthRegenButton.png",1,2);
		manaButton = new Image("/Images/Menus/Buttons/MenuButtons/ManaButton.png",1,2);
		manaRegenButton = new Image("/Images/Menus/Buttons/MenuButtons/ManaRegenButton.png",1,2);
		
		swordButton = new Image("/Images/Menus/Buttons/Items/SwordButton.png",1,2);
		battleaxeButton = new Image("/Images/Menus/Buttons/Items/BattleaxeButton.png",1,2);
		maceButton = new Image("/Images/Menus/Buttons/Items/MaceButton.png",1,2);
		bowButton = new Image("/Images/Menus/Buttons/Items/BowButton.png",1,2);
		crossbowButton = new Image("/Images/Menus/Buttons/Items/CrossbowButton.png",1,2);
		iceStaffButton = new Image("/Images/Menus/Buttons/Items/IceStaffButton.png",1,2);
		fireStaffButton = new Image("/Images/Menus/Buttons/Items/FireStaffButton.png",1,2);
		earthStaffButton = new Image("/Images/Menus/Buttons/Items/EarthStaffButton.png",1,2);
		
		bootsButton = new Image("/Images/Menus/Buttons/Items/BootsButton.png",1,2);
		legsButton = new Image("/Images/Menus/Buttons/Items/LegsButton.png",1,2);
		chestButton = new Image("/Images/Menus/Buttons/Items/ChestButton.png",1,2);
		helmetButton = new Image("/Images/Menus/Buttons/Items/HelmetButton.png",1,2);
		
		invButton = new Image("/Images/Menus/Buttons/Tabs/InventoryButton.png",1,2);
		skillButton = new Image("/Images/Menus/Buttons/Tabs/SkillButton.png",1,2);
		magicButton = new Image("/Images/Menus/Buttons/Tabs/MagicButton.png",1,2);
		mapButton = new Image("/Images/Menus/Buttons/Tabs/MapButton.png",1,2);
		saveButton = new Image("/Images/Menus/Buttons/Tabs/SaveButton.png",1,2);
		
		//Load tile maps
		background = new Image("/Images/Tiles/Background.png",4,4);
		wall = new Image("/Images/Tiles/Wall.png",8,8);
		foreground = new Image("/Images/Tiles/Foreground.png",4,4);
		
		//Load character maps
		player = new Image("/Images/Characters/Player.png",6,8);
		enemy = new Image("/Images/Characters/Enemy.png",6,8);
		smallEnemy = new Image("/Images/Characters/SmallEnemy.png",6,8);
		flyingEnemy = new Image("/Images/Characters/FlyingEnemy.png",6,8);
		bossEnemy = new Image("/Images/Characters/BossEnemy.png",6,8);
		
		//Load particle maps
		coin = new Image("/Images/Drops/Coin.png",6,1);
		
		//Load Magic maps
		earthMagic = new Image("/Images/Particles/EarthMagic.png",1,8);
		fireMagic = new Image("/Images/Particles/FireMagic.png",1,8);
		iceMagic = new Image("/Images/Particles/IceMagic.png",1,8);
		
		arrow = new Image("/Images/Particles/Arrow.png",1,8);
		swordProjectile = new Image("/Images/Particles/Sword.png",1,8);
		
		//Load Armour maps
		shoes = new Image("/Images/Drops/Armour/Boots.png",8,1);
		legs = new Image("/Images/Drops/Armour/Legs.png",4,1);
		chest = new Image("/Images/Drops/Armour/Chest.png",4,1);
		helmet = new Image("/Images/Drops/Armour/Helmet.png",7,1);
		
		//Load Item maps
		healthPotion = new Image("/Images/Drops/Potions/HealthPotion.png",3,1);
		manaPotion = new Image("/Images/Drops/Potions/ManaPotion.png",3,1);
		speedPotion = new Image("/Images/Drops/Potions/SpeedPotion.png",3,1);
		knockbackPotion = new Image("/Images/Drops/Potions/KnockbackPotion.png",3,1);
		
		//Load Weapon maps
		sword = new Image("/Images/Drops/Weapons/Sword.png",8,1);
		battleaxe = new Image("/Images/Drops/Weapons/Battleaxe.png",8,1);
		mace = new Image("/Images/Drops/Weapons/Mace.png",8,1);
		bow = new Image("/Images/Drops/Weapons/Bow.png",8,1);
		crossbow = new Image("/Images/Drops/Weapons/Crossbow.png",8,1);
		fireStaff = new Image("/Images/Drops/Weapons/FireStaff.png",8,1);
		iceStaff = new Image("/Images/Drops/Weapons/IceStaff.png",8,1);
		earthStaff = new Image("/Images/Drops/Weapons/EarthStaff.png",8,1);
		
		//Load HUD textures 
		infoBox = new Image("/Images/HUD/BarInfo.png",1,1);
		healthBar = new Image("/Images/HUD/BarHealth.png",1,2);			
		manaBar = new Image("/Images/HUD/BarMana.png",1,2);
		xpBar = new Image("/Images/HUD/BarXP.png",1,2);
		level = new Image("/Images/HUD/Level.png",1,1);
		wave = new Image("/Images/HUD/Wave.png",1,1);
		numbers = new Image("/Images/HUD/Numbers.png",10,1);
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
