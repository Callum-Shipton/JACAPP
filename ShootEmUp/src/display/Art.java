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
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.HashMap;

import level.LevelMap;
import main.Logger;
import main.Loop;
import math.Matrix4;
import math.Vector2;

public class Art {

	private static HashMap<String, Image> artFiles = new HashMap<String, Image>();

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

		Matrix4 projectionMatrix = new Matrix4();
		projectionMatrix.clearToOrtho(0, Loop.getDisplay().getWidth(), Loop.getDisplay().getHeight(), 0,
				-1.0f, 1.0f);
		FloatBuffer matrix44Buffer = projectionMatrix.toBuffer();

		glUseProgram(ShaderBase);

		int error = glGetError();

		if (error != GL_NO_ERROR) {
			Logger.error("OpenGL Error: " + error);
		}
		int projectionMatrixLocation = glGetUniformLocation(ShaderBase, "projectionMatrix");
		glUniformMatrix4(projectionMatrixLocation, false, matrix44Buffer);
		glUseProgram(0);

		glUseProgram(ShaderInst);
		projectionMatrixLocation = glGetUniformLocation(ShaderInst, "projectionMatrix");
		glUniformMatrix4(projectionMatrixLocation, false, matrix44Buffer);
		glUseProgram(0);

		glUseProgram(ShaderStat);
		projectionMatrixLocation = glGetUniformLocation(ShaderStat, "projectionMatrix");
		glUniformMatrix4(projectionMatrixLocation, false, matrix44Buffer);
		glUseProgram(0);

	}

	private static void initTextures() {

		// new Image("/filepath.png", maxFrameWidth, maxFrameHeight);

		// load menu screen art
		artFiles.put("MainMenuScreen", new Image("/Images/Menus/Backgrounds/MainMenuScreen.png", 1, 1));
		artFiles.put("InventoryScreen", new Image("/Images/Menus/Backgrounds/InventoryScreen.png", 1, 1));
		artFiles.put("SkillScreen", new Image("/Images/Menus/Backgrounds/SkillScreen.png", 1, 1));
		artFiles.put("UpgradesScreen", new Image("/Images/Menus/Backgrounds/UpgradesScreen.png", 1, 1));
		artFiles.put("MapScreen", new Image("/Images/Menus/Backgrounds/MapScreen.png", 1, 1));
		artFiles.put("SaveScreen", new Image("/Images/Menus/Backgrounds/SaveScreen.png", 1, 1));
		artFiles.put("GameOverScreen", new Image("/Images/Menus/Backgrounds/GameOverScreen.png", 1, 1));

		// load button art
		artFiles.put("NewGameButton", new Image("/Images/Menus/Buttons/MenuButtons/NewGameButton.png", 1, 2));

		artFiles.put("LoadGameButton", new Image("/Images/Menus/Buttons/MenuButtons/LoadGameButton.png", 1, 2));
		artFiles.put("OptionsButton", new Image("/Images/Menus/Buttons/MenuButtons/OptionsButton.png", 1, 2));
		artFiles.put("ExitButton", new Image("/Images/Menus/Buttons/MenuButtons/ExitButton.png", 1, 2));

		artFiles.put("BackButton", new Image("/Images/Menus/Buttons/MenuButtons/BackButton.png", 1, 2));

		artFiles.put("ControlsButton", new Image("/Images/Menus/Buttons/MenuButtons/ControlsButton.png", 1, 2));
		artFiles.put("SoundButton", new Image("/Images/Menus/Buttons/MenuButtons/SoundButton.png", 1, 2));

		artFiles.put("MuteButton", new Image("/Images/Menus/Buttons/MenuButtons/MuteButton.png", 1, 2));

		artFiles.put("SaveGameButton", new Image("/Images/Menus/Buttons/MenuButtons/SaveGameButton.png", 1, 2));

		artFiles.put("Level1Button", new Image("/Images/Menus/Buttons/MenuButtons/Level1Button.png", 1, 2));
		artFiles.put("Level2Button", new Image("/Images/Menus/Buttons/MenuButtons/Level2Button.png", 1, 2));
		artFiles.put("Level3Button", new Image("/Images/Menus/Buttons/MenuButtons/Level3Button.png", 1, 2));

		artFiles.put("WarriorButton", new Image("/Images/Menus/Buttons/MenuButtons/WarriorButton.png", 1, 2));
		artFiles.put("ArcherButton", new Image("/Images/Menus/Buttons/MenuButtons/ArcherButton.png", 1, 2));
		artFiles.put("MageButton", new Image("/Images/Menus/Buttons/MenuButtons/MageButton.png", 1, 2));
		artFiles.put("BattleMageButton", new Image("/Images/Menus/Buttons/MenuButtons/BattleMageButton.png", 1, 2));
		artFiles.put("RogueButton", new Image("/Images/Menus/Buttons/MenuButtons/RogueButton.png", 1, 2));

		artFiles.put("HealthButton", new Image("/Images/Menus/Buttons/MenuButtons/HealthButton.png", 1, 2));
		artFiles.put("HealthRegenButton", new Image("/Images/Menus/Buttons/MenuButtons/HealthRegenButton.png", 1, 2));
		artFiles.put("ManaButton", new Image("/Images/Menus/Buttons/MenuButtons/ManaButton.png", 1, 2));
		artFiles.put("ManaRegenButton", new Image("/Images/Menus/Buttons/MenuButtons/ManaRegenButton.png", 1, 2));

		artFiles.put("PotionsButton", new Image("/Images/Menus/Buttons/MenuButtons/PotionsButton.png", 1, 2));
		artFiles.put("InventoryButton", new Image("/Images/Menus/Buttons/MenuButtons/InventoryButton.png", 1, 2));

		artFiles.put("IronDaggerButton", new Image("/Images/Menus/Buttons/Items/IronDaggerButton.png", 1, 2));
		artFiles.put("SteelDaggerButton", new Image("/Images/Menus/Buttons/Items/SteelDaggerButton.png", 1, 2));
		artFiles.put("GreatswordButton", new Image("/Images/Menus/Buttons/Items/GreatswordButton.png", 1, 2));
		artFiles.put("SwordButton", new Image("/Images/Menus/Buttons/Items/SwordButton.png", 1, 2));
		artFiles.put("BattleaxeButton", new Image("/Images/Menus/Buttons/Items/BattleaxeButton.png", 1, 2));
		artFiles.put("MaceButton", new Image("/Images/Menus/Buttons/Items/MaceButton.png", 1, 2));
		artFiles.put("LongbowButton", new Image("/Images/Menus/Buttons/Items/LongbowButton.png", 1, 2));
		artFiles.put("ShortbowButton", new Image("/Images/Menus/Buttons/Items/ShortbowButton.png", 1, 2));
		artFiles.put("CompoundCrossbowButton",
				new Image("/Images/Menus/Buttons/Items/CompoundCrossbowButton.png", 1, 2));
		artFiles.put("RecurveCrossbowButton", new Image("/Images/Menus/Buttons/Items/RecurveCrossbowButton.png", 1, 2));
		artFiles.put("IceStaffButton", new Image("/Images/Menus/Buttons/Items/IceStaffButton.png", 1, 2));
		artFiles.put("FireStaffButton", new Image("/Images/Menus/Buttons/Items/FireStaffButton.png", 1, 2));
		artFiles.put("EarthStaffButton", new Image("/Images/Menus/Buttons/Items/EarthStaffButton.png", 1, 2));

		artFiles.put("LeatherBootsButton", new Image("/Images/Menus/Buttons/Items/LeatherBootsButton.png", 1, 2));
		artFiles.put("LeatherLegsButton", new Image("/Images/Menus/Buttons/Items/LeatherLegsButton.png", 1, 2));
		artFiles.put("LeatherChestButton", new Image("/Images/Menus/Buttons/Items/LeatherChestButton.png", 1, 2));
		artFiles.put("LeatherHelmetButton", new Image("/Images/Menus/Buttons/Items/LeatherHelmetButton.png", 1, 2));
		artFiles.put("IronBootsButton", new Image("/Images/Menus/Buttons/Items/IronBootsButton.png", 1, 2));
		artFiles.put("IronLegsButton", new Image("/Images/Menus/Buttons/Items/IronLegsButton.png", 1, 2));
		artFiles.put("IronChestButton", new Image("/Images/Menus/Buttons/Items/IronChestButton.png", 1, 2));
		artFiles.put("IronHelmetButton", new Image("/Images/Menus/Buttons/Items/IronHelmetButton.png", 1, 2));

		artFiles.put("InvButton", new Image("/Images/Menus/Buttons/Tabs/InventoryButton.png", 1, 2));
		artFiles.put("SkillButton", new Image("/Images/Menus/Buttons/Tabs/SkillButton.png", 1, 2));
		artFiles.put("UpgradesButton", new Image("/Images/Menus/Buttons/Tabs/UpgradesButton.png", 1, 2));
		artFiles.put("MapButton", new Image("/Images/Menus/Buttons/Tabs/MapButton.png", 1, 2));
		artFiles.put("SaveButton", new Image("/Images/Menus/Buttons/Tabs/SaveButton.png", 1, 2));

		artFiles.put("LevelIcon", new Image("/Images/Menus/Icons/LevelIcon.png", 1, 1));
		artFiles.put("DamageIcon", new Image("/Images/Menus/Icons/DamageIcon.png", 1, 1));
		artFiles.put("ArmourIcon", new Image("/Images/Menus/Icons/ArmourIcon.png", 1, 1));
		artFiles.put("RangeIcon", new Image("/Images/Menus/Icons/RangeIcon.png", 1, 1));
		artFiles.put("FireRateIcon", new Image("/Images/Menus/Icons/FireRateIcon.png", 1, 1));
		artFiles.put("ManaCostIcon", new Image("/Images/Menus/Icons/ManaCostIcon.png", 1, 1));
		artFiles.put("CoinIcon", new Image("/Images/Menus/Icons/CoinIcon.png", 1, 1));
		artFiles.put("WaveIcon", new Image("/Images/Menus/Icons/WaveIcon.png", 1, 1));
		artFiles.put("LivesIcon", new Image("/Images/Menus/Icons/LivesIcon.png", 1, 1));

		// Load tile maps
		artFiles.put("Floor", new Image("/Images/Tiles/Floor.png", 2, 2));
		artFiles.put("Walls", new Image("/Images/Tiles/Walls.png", 6, 7));

		// Load character maps
		artFiles.put("Warrior", new Image("/Images/Characters/Players/Warrior.png", 6, 8));
		artFiles.put("Archer", new Image("/Images/Characters/Players/Archer.png", 6, 8));
		artFiles.put("Mage", new Image("/Images/Characters/Players/Mage.png", 6, 8));
		artFiles.put("BattleMage", new Image("/Images/Characters/Players/BattleMage.png", 6, 8));
		artFiles.put("Rogue", new Image("/Images/Characters/Players/Rogue.png", 6, 8));

		artFiles.put("Enemy", new Image("/Images/Characters/Enemies/Enemy.png", 6, 8));
		artFiles.put("SmallEnemy", new Image("/Images/Characters/Enemies/SmallEnemy.png", 6, 8));
		artFiles.put("FlyingEnemy", new Image("/Images/Characters/Enemies/FlyingEnemy.png", 6, 8));
		artFiles.put("BossEnemy", new Image("/Images/Characters/Enemies/BossEnemy.png", 6, 8));

		// Load particle maps
		artFiles.put("Coin", new Image("/Images/Drops/Coin.png", 6, 1));

		// Load Magic maps
		artFiles.put("EarthMagic", new Image("/Images/Particles/EarthMagic.png", 1, 8));
		artFiles.put("FireMagic", new Image("/Images/Particles/FireMagic.png", 1, 8));
		artFiles.put("IceMagic", new Image("/Images/Particles/IceMagic.png", 1, 8));

		artFiles.put("Arrow", new Image("/Images/Particles/Arrow.png", 1, 8));
		artFiles.put("SwordProjectile", new Image("/Images/Particles/Sword.png", 1, 8));

		// load status effect maps
		artFiles.put("Fire", new Image("/Images/Effects/Fire.png", 6, 1));
		artFiles.put("Frost", new Image("/Images/Effects/Frost.png", 6, 1));
		artFiles.put("Poison", new Image("/Images/Effects/Poison.png", 6, 1));

		// Load Armour maps
		artFiles.put("LeatherBoots", new Image("/Images/Drops/Armour/LeatherBoots.png", 8, 1));
		artFiles.put("LeatherLegs", new Image("/Images/Drops/Armour/LeatherLegs.png", 4, 1));
		artFiles.put("LeatherChest", new Image("/Images/Drops/Armour/LeatherChest.png", 4, 1));
		artFiles.put("LeatherHelmet", new Image("/Images/Drops/Armour/LeatherHelmet.png", 7, 1));
		artFiles.put("IronBoots", new Image("/Images/Drops/Armour/IronBoots.png", 8, 1));
		artFiles.put("IronLegs", new Image("/Images/Drops/Armour/IronLegs.png", 4, 1));
		artFiles.put("IronChest", new Image("/Images/Drops/Armour/IronChest.png", 4, 1));
		artFiles.put("IronHelmet", new Image("/Images/Drops/Armour/IronHelmet.png", 7, 1));

		// Load Item maps
		artFiles.put("Health", new Image("/Images/Drops/Potions/HealthPotion.png", 3, 1));
		artFiles.put("Mana", new Image("/Images/Drops/Potions/ManaPotion.png", 3, 1));
		artFiles.put("Speed", new Image("/Images/Drops/Potions/SpeedPotion.png", 3, 1));
		artFiles.put("Knockback", new Image("/Images/Drops/Potions/KnockbackPotion.png", 3, 1));

		// Load Weapon maps
		artFiles.put("IronDagger", new Image("/Images/Drops/Weapons/IronDagger.png", 8, 1));
		artFiles.put("SteelDagger", new Image("/Images/Drops/Weapons/SteelDagger.png", 8, 1));
		artFiles.put("Greatsword", new Image("/Images/Drops/Weapons/Greatsword.png", 8, 1));
		artFiles.put("Sword", new Image("/Images/Drops/Weapons/Sword.png", 8, 1));
		artFiles.put("Battleaxe", new Image("/Images/Drops/Weapons/Battleaxe.png", 8, 1));
		artFiles.put("Mace", new Image("/Images/Drops/Weapons/Mace.png", 8, 1));
		artFiles.put("Longbow", new Image("/Images/Drops/Weapons/Longbow.png", 8, 1));
		artFiles.put("Shortbow", new Image("/Images/Drops/Weapons/Shortbow.png", 8, 1));
		artFiles.put("RecurveCrossbow", new Image("/Images/Drops/Weapons/RecurveCrossbow.png", 8, 1));
		artFiles.put("CompoundCrossbow", new Image("/Images/Drops/Weapons/CompoundCrossbow.png", 8, 1));
		artFiles.put("FireStaff", new Image("/Images/Drops/Weapons/FireStaff.png", 8, 1));
		artFiles.put("IceStaff", new Image("/Images/Drops/Weapons/IceStaff.png", 8, 1));
		artFiles.put("EarthStaff", new Image("/Images/Drops/Weapons/EarthStaff.png", 8, 1));

		// Load HUD textures
		artFiles.put("BarInfoTop", new Image("/Images/HUD/BarInfoTop.png", 1, 1));
		artFiles.put("BarHealth", new Image("/Images/HUD/BarHealth.png", 1, 2));
		artFiles.put("BarMana", new Image("/Images/HUD/BarMana.png", 1, 2));
		artFiles.put("BarXP", new Image("/Images/HUD/BarXP.png", 1, 2));
		artFiles.put("Numbers", new Image("/Images/HUD/Numbers.png", 10, 1));

		artFiles.put("BarInfoBottom", new Image("/Images/HUD/BarInfoBottom.png", 1, 1));
	}

	public static void refreshRenderers() {
		base.initRenderData();
		stat.initRenderData();
		LevelMap map = Loop.getCurrentLevel().getMap();
		if (Loop.getCurrentLevel() != null) {
			irWall.initRenderData(map.getWalls(),
					new Vector2(artFiles.get("Walls").getFWidth(), artFiles.get("Walls").getFHeight()));
			irBack.initRenderData(map.getBackgroundTiles(),
					new Vector2(artFiles.get("Floor").getFWidth(), artFiles.get("Floor").getFHeight()));
			irFore.initRenderData(map.getForegroundTiles(),
					new Vector2(artFiles.get("Walls").getFWidth(), artFiles.get("Walls").getFHeight()));
		}
	}

	public void init() {

		initShaders();
		initShaderUniforms();
		initTextures();
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
