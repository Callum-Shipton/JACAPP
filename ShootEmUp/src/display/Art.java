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
	public static Image upgradesScreen;
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
	
	public static Image muteButton;
	
	public static Image saveGameButton;
	
	public static Image level1Button;
	public static Image level2Button;
	public static Image level3Button;
	
	public static Image warriorButton;
	public static Image archerButton;
	public static Image mageButton;
	public static Image battleMageButton;
	public static Image rogueButton;
	
	public static Image swordButton;
	public static Image battleaxeButton;
	public static Image maceButton;
	public static Image bowButton;
	public static Image crossbowButton;
	public static Image iceStaffButton;
	public static Image fireStaffButton;
	public static Image earthStaffButton;
	
	public static Image leatherBootsButton;
	public static Image leatherLegsButton;
	public static Image leatherChestButton;
	public static Image leatherHelmetButton;
	public static Image ironBootsButton;
	public static Image ironLegsButton;
	public static Image ironChestButton;
	public static Image ironHelmetButton;	
	
	public static Image levelIcon;	
	public static Image damageIcon;	
	public static Image armourIcon;	
	public static Image rangeIcon;	
	public static Image fireRateIcon;	
	public static Image manaCostIcon;
	public static Image coinIcon;	
	public static Image waveIcon;	
	
	public static Image healthButton;
	public static Image healthRegenButton;
	public static Image manaButton;
	public static Image manaRegenButton;
	
	public static Image potionsButton;
	public static Image inventoryButton;
	
	public static Image invButton;
	public static Image skillsButton;
	public static Image upgradesButton;
	public static Image mapButton;
	public static Image saveButton;
	
	//Tile maps
	public static Image floor;
	public static Image walls;
	
	//Character maps
	public static Image warrior;
	public static Image archer;
	public static Image mage;
	public static Image battleMage;
	public static Image rogue;
	
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
	
	//Status Effect maps
	public static Image fire;
	public static Image frost;
	public static Image poison;
	
	//Armour maps
	public static Image leatherBoots;
	public static Image leatherLegs;
	public static Image leatherChest;
	public static Image leatherHelmet;
	public static Image ironBoots;
	public static Image ironLegs;
	public static Image ironChest;
	public static Image ironHelmet;
	
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
	public static Image infoBoxTop;
	public static Image infoBoxBottom;
	public static Image healthBar;
	public static Image manaBar;
	public static Image xpBar;
	public static Image numbers;
	
	//Level map file locations
	public static String levels = "/Levels/Level";
	
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
		upgradesScreen = new Image("/Images/Menus/Backgrounds/UpgradesScreen.png", 1, 1);
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
		
		muteButton = new Image("/Images/Menus/Buttons/MenuButtons/MuteButton.png",1,2);
		
		saveGameButton = new Image("/Images/Menus/Buttons/MenuButtons/SaveGameButton.png",1,2);
		
		level1Button = new Image("/Images/Menus/Buttons/MenuButtons/Level1Button.png",1,2);
		level2Button = new Image("/Images/Menus/Buttons/MenuButtons/Level2Button.png",1,2);
		level3Button = new Image("/Images/Menus/Buttons/MenuButtons/Level3Button.png",1,2);
		
		warriorButton = new Image("/Images/Menus/Buttons/MenuButtons/WarriorButton.png",1,2);
		archerButton = new Image("/Images/Menus/Buttons/MenuButtons/ArcherButton.png",1,2);
		mageButton = new Image("/Images/Menus/Buttons/MenuButtons/MageButton.png",1,2);
		battleMageButton = new Image("/Images/Menus/Buttons/MenuButtons/BattleMageButton.png",1,2);
		rogueButton = new Image("/Images/Menus/Buttons/MenuButtons/RogueButton.png",1,2);
		
		healthButton = new Image("/Images/Menus/Buttons/MenuButtons/HealthButton.png",1,2);
		healthRegenButton = new Image("/Images/Menus/Buttons/MenuButtons/HealthRegenButton.png",1,2);
		manaButton = new Image("/Images/Menus/Buttons/MenuButtons/ManaButton.png",1,2);
		manaRegenButton = new Image("/Images/Menus/Buttons/MenuButtons/ManaRegenButton.png",1,2);
		
		potionsButton = new Image("/Images/Menus/Buttons/MenuButtons/PotionsButton.png",1,2);
		inventoryButton = new Image("/Images/Menus/Buttons/MenuButtons/InventoryButton.png",1,2);
		
		swordButton = new Image("/Images/Menus/Buttons/Items/SwordButton.png",1,2);
		battleaxeButton = new Image("/Images/Menus/Buttons/Items/BattleaxeButton.png",1,2);
		maceButton = new Image("/Images/Menus/Buttons/Items/MaceButton.png",1,2);
		bowButton = new Image("/Images/Menus/Buttons/Items/BowButton.png",1,2);
		crossbowButton = new Image("/Images/Menus/Buttons/Items/CrossbowButton.png",1,2);
		iceStaffButton = new Image("/Images/Menus/Buttons/Items/IceStaffButton.png",1,2);
		fireStaffButton = new Image("/Images/Menus/Buttons/Items/FireStaffButton.png",1,2);
		earthStaffButton = new Image("/Images/Menus/Buttons/Items/EarthStaffButton.png",1,2);
		
		leatherBootsButton = new Image("/Images/Menus/Buttons/Items/LeatherBootsButton.png",1,2);
		leatherLegsButton = new Image("/Images/Menus/Buttons/Items/LeatherLegsButton.png",1,2);
		leatherChestButton = new Image("/Images/Menus/Buttons/Items/LeatherChestButton.png",1,2);
		leatherHelmetButton = new Image("/Images/Menus/Buttons/Items/LeatherHelmetButton.png",1,2);
		ironBootsButton = new Image("/Images/Menus/Buttons/Items/IronBootsButton.png",1,2);
		ironLegsButton = new Image("/Images/Menus/Buttons/Items/IronLegsButton.png",1,2);
		ironChestButton = new Image("/Images/Menus/Buttons/Items/IronChestButton.png",1,2);
		ironHelmetButton = new Image("/Images/Menus/Buttons/Items/IronHelmetButton.png",1,2);
		
		levelIcon = new Image("/Images/Menus/Icons/LevelIcon.png",1,1);
		damageIcon = new Image("/Images/Menus/Icons/DamageIcon.png",1,1);
		armourIcon = new Image("/Images/Menus/Icons/ArmourIcon.png",1,1);
		rangeIcon = new Image("/Images/Menus/Icons/RangeIcon.png",1,1);
		fireRateIcon = new Image("/Images/Menus/Icons/FireRateIcon.png",1,1);
		manaCostIcon = new Image("/Images/Menus/Icons/ManaCostIcon.png",1,1);
		coinIcon = new Image("/Images/Menus/Icons/CoinIcon.png",1,1);
		waveIcon = new Image("/Images/Menus/Icons/WaveIcon.png",1,1);
		
		invButton = new Image("/Images/Menus/Buttons/Tabs/InventoryButton.png",1,2);
		skillsButton = new Image("/Images/Menus/Buttons/Tabs/SkillButton.png",1,2);
		upgradesButton = new Image("/Images/Menus/Buttons/Tabs/UpgradesButton.png",1,2);
		mapButton = new Image("/Images/Menus/Buttons/Tabs/MapButton.png",1,2);
		saveButton = new Image("/Images/Menus/Buttons/Tabs/SaveButton.png",1,2);
		
		//Load tile maps
		floor = new Image("/Images/Tiles/Floor.png",2,2);
		walls = new Image("/Images/Tiles/Walls.png",6,7);
		
		//Load character maps
		warrior = new Image("/Images/Characters/Players/Warrior.png",6,8);
		archer = new Image("/Images/Characters/Players/Archer.png",6,8);
		mage = new Image("/Images/Characters/Players/Mage.png",6,8);
		battleMage = new Image("/Images/Characters/Players/BattleMage.png",6,8);
		rogue = new Image("/Images/Characters/Players/Rogue.png",6,8);
		
		enemy = new Image("/Images/Characters/Enemies/Enemy.png",6,8);
		smallEnemy = new Image("/Images/Characters/Enemies/SmallEnemy.png",6,8);
		flyingEnemy = new Image("/Images/Characters/Enemies/FlyingEnemy.png",6,8);
		bossEnemy = new Image("/Images/Characters/Enemies/BossEnemy.png",6,8);
		
		//Load particle maps
		coin = new Image("/Images/Drops/Coin.png",6,1);
		
		//Load Magic maps
		earthMagic = new Image("/Images/Particles/EarthMagic.png",1,8);
		fireMagic = new Image("/Images/Particles/FireMagic.png",1,8);
		iceMagic = new Image("/Images/Particles/IceMagic.png",1,8);
		
		arrow = new Image("/Images/Particles/Arrow.png",1,8);
		swordProjectile = new Image("/Images/Particles/Sword.png",1,8);
		
		//load status effect maps
		fire = new Image("/Images/Effects/Fire.png",6,1);
		frost = new Image("/Images/Effects/Frost.png",6,1);
		poison = new Image("/Images/Effects/Poison.png",6,1);
		
		//Load Armour maps
		leatherBoots = new Image("/Images/Drops/Armour/LeatherBoots.png",8,1);
		leatherLegs = new Image("/Images/Drops/Armour/LeatherLegs.png",4,1);
		leatherChest = new Image("/Images/Drops/Armour/LeatherChest.png",4,1);
		leatherHelmet = new Image("/Images/Drops/Armour/LeatherHelmet.png",7,1);
		ironBoots = new Image("/Images/Drops/Armour/IronBoots.png",8,1);
		ironLegs = new Image("/Images/Drops/Armour/IronLegs.png",4,1);
		ironChest = new Image("/Images/Drops/Armour/IronChest.png",4,1);
		ironHelmet = new Image("/Images/Drops/Armour/IronHelmet.png",7,1);
		
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
		infoBoxTop = new Image("/Images/HUD/BarInfoTop.png",1,1);
		healthBar = new Image("/Images/HUD/BarHealth.png",1,2);			
		manaBar = new Image("/Images/HUD/BarMana.png",1,2);
		xpBar = new Image("/Images/HUD/BarXP.png",1,2);
		numbers = new Image("/Images/HUD/Numbers.png",10,1);
		
		infoBoxBottom = new Image("/Images/HUD/BarInfoBottom.png",1,1);
	}

	public static void initShaderUniforms() {

		Matrix4 projectionMatrix = new Matrix4();
		projectionMatrix.clearToOrtho(0, ShootEmUp.width, ShootEmUp.height, 0,
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
		irWall.initRenderData(ShootEmUp.currentLevel.map.getWalls(), new Vector2(walls.getFWidth(), walls.getFHeight()));
		irBack.initRenderData(ShootEmUp.currentLevel.map.getBackgroundTiles(), new Vector2(floor.getFWidth(),floor.getFHeight()));
		irFore.initRenderData(ShootEmUp.currentLevel.map.getForegroundTiles(), new Vector2(walls.getFWidth(), walls.getFHeight()));
		}
	}
	
	

}
