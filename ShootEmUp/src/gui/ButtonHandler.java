package gui;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_TRUE;

import audio.music.BackgroundMusic;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.inventory.BaseInventory;
import display.Art;
import gui.menus.CharacterSelectMenu;
import gui.menus.ControlsMenu;
import gui.menus.InventoryMenu;
import gui.menus.LevelSelectMenu;
import gui.menus.MainMenu;
import gui.menus.MapMenu;
import gui.menus.OptionsMenu;
import gui.menus.SaveMenu;
import gui.menus.SkillMenu;
import gui.menus.SoundMenu;
import gui.menus.UpgradesMenu;
import level.Level;
import level.PlayerBuilder;
import main.ShootEmUp;
import save.Save;

public abstract class ButtonHandler {

	public static void selectButton(TypeButton type) {
		switch (type) {
			case NEW_GAME:
				newGame();
				break;
			case LOAD_GAME:
				loadGame();
				break;
			case OPTIONS:
				options();
				break;
			case EXIT:
				exit();
				break;
			case LEVEL1:
				level(1);
				break;
			case LEVEL2:
				level(2);
				break;
			case LEVEL3:
				level(3);
				break;
			case WARRIOR:
				character(TypeAttack.WARRIOR);
				break;
			case ARCHER:
				character(TypeAttack.ARCHER);
				break;
			case MAGE:
				character(TypeAttack.MAGE);
				break;
			case BATTLE_MAGE:
				character(TypeAttack.BATTLE_MAGE);
				break;
			case ROGUE:
				character(TypeAttack.ROGUE);
				break;
			case BACK:
				back();
				break;
			case SOUND:
				sound();
				break;
			case MUTE:
				mute();
				break;
			case CONTROLS:
				controls();
				break;
			case RESUME:
				resume();
				break;
			case MAIN_MENU:
				mainMenu();
				break;
			case INVENTORY:
				inventory();
				break;
			case UPGRADES:
				magic();
				break;
			case SKILLS:
				skills();
				break;
			case MAP:
				map();
				break;
			case SAVE:
				save();
				break;
			case HEALTH_REGEN:
				healthRegen();
				break;
			case HEALTH:
				health();
				break;
			case MANA_REGEN:
				manaRegen();
				break;
			case MANA:
				mana();
				break;
			case POTIONS_UPGRADE:
				potionsUpgrade();
				break;
			case INVENTORY_UPGRADE:
				inventoryUpgrade();
				break;
			case SAVE_GAME:
				saveGame();
				break;
			default:
		}
	}

	private static void newGame() {
		ShootEmUp.addMenu(new LevelSelectMenu(Art.getImage("MainMenuScreen")));
	}

	private static void loadGame() {
		if(ShootEmUp.save == null){
			ShootEmUp.save = new Save();
		}
		ShootEmUp.save.load(1);
		ShootEmUp.addMenu(new LevelSelectMenu(Art.getImage("MainMenuScreen")));
	}

	private static void options() {
		ShootEmUp.addMenu(new OptionsMenu(Art.getImage("MainMenuScreen")));
	}

	private static void exit() {
		glfwSetWindowShouldClose(ShootEmUp.display.getWindow(), GL_TRUE);
	}

	private static void level(int level) {
		ShootEmUp.currentLevel = new Level(Art.levels, level);
		ShootEmUp.currentLevel.init();
		ShootEmUp.addMenu(new CharacterSelectMenu(Art.getImage("MainMenuScreen")));
	}

	private static void character(TypeAttack type) {
		if (ShootEmUp.save == null) {
			PlayerBuilder.buildPlayer(type);
		} else {
			if (ShootEmUp.save.getCharacter(type) != null) {
				PlayerBuilder.buildPlayer(type, ShootEmUp.save.getCharacter(type));
			} else {
				PlayerBuilder.buildPlayer(type);
			}
		}
		startGame();
	}

	private static void back() {
		ShootEmUp.menuStack.pop();
	}

	private static void controls() {
		ShootEmUp.addMenu(new ControlsMenu(Art.getImage("MainMenuScreen")));
	}

	private static void sound() {
		ShootEmUp.addMenu(new SoundMenu(Art.getImage("MainMenuScreen")));
	}

	private static void mute() {
		if (ShootEmUp.musicPause) {
			ShootEmUp.backgroundMusic.play(ShootEmUp.currentMusic);
		} else {
			ShootEmUp.backgroundMusic.pause(ShootEmUp.currentMusic);
		}
		ShootEmUp.musicPause = !ShootEmUp.musicPause;
	}

	private static void inventory() {
		ShootEmUp.addMenu(new InventoryMenu(Art.getImage("InventoryScreen")));
	}

	private static void magic() {
		ShootEmUp.addMenu(new UpgradesMenu(Art.getImage("UpgradesScreen")));
	}

	private static void skills() {
		ShootEmUp.addMenu(new SkillMenu(Art.getImage("SkillScreen")));
	}

	private static void map() {
		ShootEmUp.addMenu(new MapMenu(Art.getImage("MapScreen")));
	}

	private static void save() {
		ShootEmUp.addMenu(new SaveMenu(Art.getImage("SaveScreen")));
	}

	private static void resume() {
		ShootEmUp.menuStack.clear();
		ShootEmUp.paused = false;
	}

	private static void mainMenu() {
		ShootEmUp.mainMenu = true;
		ShootEmUp.menuStack.clear();
		ShootEmUp.addMenu(new MainMenu(Art.getImage("MainMenuScreen")));
	}

	private static void healthRegen() {
		BaseInventory BI = ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY);
		if (BI
				.getLevelPoints() > 0) {
			BaseAttack BA = (BaseAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK));
			BA.setMaxHealthRegen((int) Math.ceil(BA.getMaxHealthRegen() / 2));
			BI
					.spendLevelPoints(1);
		}
	}

	private static void health() {
		BaseInventory BI = ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY);
		if (BI
				.getLevelPoints() > 0) {
			BaseAttack BA = (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK));
			BA.setMaxHealth(BA.getMaxHealth() + 1);
			BA.setHealth(BA.getHealth() + 1);
			BI
					.spendLevelPoints(1);
		}
	}

	private static void manaRegen() {
		BaseInventory BI = ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY);
		if (BI
				.getLevelPoints() > 0) {
			PlayerAttack PA = (PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK));
			PA.setMaxManaRegen((int) Math.ceil(PA.getMaxManaRegen() / 2));
			BI
					.spendLevelPoints(1);
		}
	}

	private static void mana() {
		BaseInventory BI = ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY);
		if (BI
				.getLevelPoints() > 0) {
			PlayerAttack PA =(ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK));
			PA.setMaxMana(PA.getMaxMana() + 1);
			PA.setMana(PA.getMana() + 1);
			BI.spendLevelPoints(1);
		}
	}

	private static void inventoryUpgrade() {
		BaseInventory PI = (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY));
		if (PI.getCoins() >= 5) {
			PI.addInventorySize(5);
			PI.spendCoins(5);
		}
	}

	private static void potionsUpgrade() {
		BaseInventory PI =  (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY));
		if (PI.getCoins() >= 5) {
			PI.addMaxPotions(5);
			PI.spendCoins(5);
		}
	}

	private static void saveGame() {
		if (ShootEmUp.save == null) {
			ShootEmUp.save = new Save();
		}
		ShootEmUp.save.saveCharacter();
		ShootEmUp.save.saveToSystem(1);
		ShootEmUp.save = null;
	}
	// Extra Methods

	private static void startGame() {
		ShootEmUp.paused = false;
		ShootEmUp.mainMenu = false;
		ShootEmUp.clearMenus();
		ShootEmUp.backgroundMusic.stop(ShootEmUp.currentMusic);
		ShootEmUp.currentMusic = BackgroundMusic.MAIN;
		if (!ShootEmUp.musicPause) {
			ShootEmUp.backgroundMusic.play(ShootEmUp.currentMusic);
		}
	}
}
