package gui;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_TRUE;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.inventory.BaseInventory;
import display.ImageProcessor;
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
import logging.Logger;
import loop.Loop;
import main.ShootEmUp;
import save.Save;

public abstract class ButtonHandler {

	public static final String MAIN_MENU_BACKGROUND = "MainMenuScreen";

	private static void back() {
		ShootEmUp.getMenuSystem().popMenu();
	}

	private static void selectCharacter(TypeAttack type) {
		Save save = ShootEmUp.getSave();
		if (save == null) {
			ShootEmUp.setPlayer(PlayerBuilder.buildPlayer(type));
			ShootEmUp.getMenuSystem().addMenu(new LevelSelectMenu(ImageProcessor.getImage(MAIN_MENU_BACKGROUND), 1));
		} else {
			if (save.getCharacter(type) != null) {
				PlayerBuilder.buildPlayer(type, save.getCharacter(type));
			} else {
				PlayerBuilder.buildPlayer(type);
			}
			ShootEmUp.getMenuSystem()
					.addMenu(new LevelSelectMenu(ImageProcessor.getImage(MAIN_MENU_BACKGROUND), save.getLevel()));
		}
	}

	private static void controls() {
		ShootEmUp.getMenuSystem().addMenu(new ControlsMenu(ImageProcessor.getImage(MAIN_MENU_BACKGROUND)));
	}

	private static void exit() {
		glfwSetWindowShouldClose(Loop.getDisplay().getWindow(), GL_TRUE);
	}

	private static void health() {
		BaseInventory BI = ShootEmUp.getPlayer().getComponent(TypeComponent.INVENTORY);
		if (BI.getLevelPoints() > 0) {
			BaseAttack BA = (ShootEmUp.getPlayer().getComponent(TypeComponent.ATTACK));
			BA.setMaxHealth(BA.getMaxHealth() + 1);
			BA.setHealth(BA.getHealth() + 1);
			BI.spendLevelPoints(1);
		}
	}

	private static void healthRegen() {
		BaseInventory BI = ShootEmUp.getPlayer().getComponent(TypeComponent.INVENTORY);
		if (BI.getLevelPoints() > 0) {
			BaseAttack BA = ShootEmUp.getPlayer().getComponent(TypeComponent.ATTACK);
			BA.setMaxHealthRegen((int) Math.ceil(BA.getMaxHealthRegen() / 2.0));
			BI.spendLevelPoints(1);
		}
	}

	private static void inventory() {
		ShootEmUp.getMenuSystem().addMenu(new InventoryMenu(ImageProcessor.getImage("InventoryScreen")));
	}

	private static void inventoryUpgrade() {
		BaseInventory PI = (ShootEmUp.getPlayer().getComponent(TypeComponent.INVENTORY));
		if (PI.getCoins() >= 5) {
			PI.addInventorySize(5);
			PI.spendCoins(5);
		}
	}

	private static void level(int level) {
		ShootEmUp.setCurrentLevel(new Level(ImageProcessor.LEVEL_FILE_LOCATION, level));
		ShootEmUp.getCurrentLevel().init();
		ShootEmUp.startGame();
	}

	private static void loadGame() throws Exception {
		Save save = ShootEmUp.getSave();

		if (save == null) {
			ShootEmUp.setSave(new Save());
		}
		save.load(1);

		int warriorLevel = 0;
		int archerLevel = 0;
		int mageLevel = 0;
		int battleMageLevel = 0;
		int rogueLevel = 0;

		if (save != null) {
			if (save.getCharacter(TypeAttack.WARRIOR) != null) {
				warriorLevel = save.getCharacter(TypeAttack.WARRIOR).getPlayerLevel();
			}
			if (save.getCharacter(TypeAttack.ARCHER) != null) {
				archerLevel = save.getCharacter(TypeAttack.ARCHER).getPlayerLevel();
			}
			if (save.getCharacter(TypeAttack.MAGE) != null) {
				mageLevel = save.getCharacter(TypeAttack.MAGE).getPlayerLevel();
			}
			if (save.getCharacter(TypeAttack.BATTLE_MAGE) != null) {
				battleMageLevel = save.getCharacter(TypeAttack.BATTLE_MAGE).getPlayerLevel();
			}
			if (save.getCharacter(TypeAttack.ROGUE) != null) {
				rogueLevel = save.getCharacter(TypeAttack.ROGUE).getPlayerLevel();
			}
		}

		ShootEmUp.getMenuSystem().addMenu(new CharacterSelectMenu(ImageProcessor.getImage(MAIN_MENU_BACKGROUND),
				warriorLevel, archerLevel, mageLevel, battleMageLevel, rogueLevel));
	}

	private static void magic() {
		ShootEmUp.getMenuSystem().addMenu(new UpgradesMenu(ImageProcessor.getImage("UpgradesScreen")));
	}

	private static void mainMenu() {
		ShootEmUp.getMenuSystem().setMainMenu(true);
		ShootEmUp.getMenuSystem().clearMenus();
		ShootEmUp.getMenuSystem().addMenu(new MainMenu(ImageProcessor.getImage(MAIN_MENU_BACKGROUND)));
	}

	private static void mana() {
		BaseInventory BI = ShootEmUp.getPlayer().getComponent(TypeComponent.INVENTORY);
		if (BI.getLevelPoints() > 0) {
			PlayerAttack PA = (ShootEmUp.getPlayer().getComponent(TypeComponent.ATTACK));
			PA.setMaxMana(PA.getMaxMana() + 1);
			PA.setMana(PA.getMana() + 1);
			BI.spendLevelPoints(1);
		}
	}

	private static void upgradeManaRegen() {
		BaseInventory BI = ShootEmUp.getPlayer().getComponent(TypeComponent.INVENTORY);
		if (BI.getLevelPoints() > 0) {
			PlayerAttack PA = ShootEmUp.getPlayer().getComponent(TypeComponent.ATTACK);
			PA.setMaxManaRegen((int) Math.ceil(PA.getMaxManaRegen() / 2.0));
			BI.spendLevelPoints(1);
		}
	}

	private static void map() {
		ShootEmUp.getMenuSystem().addMenu(new MapMenu(ImageProcessor.getImage("MapScreen")));
	}

	private static void mute() {
		ShootEmUp.getMusicPlayer().pause();
	}

	private static void newGame() {
		ShootEmUp.getMenuSystem().addMenu(new CharacterSelectMenu(ImageProcessor.getImage(MAIN_MENU_BACKGROUND)));
	}

	private static void options() {
		ShootEmUp.getMenuSystem().addMenu(new OptionsMenu(ImageProcessor.getImage(MAIN_MENU_BACKGROUND)));
	}

	private static void potionsUpgrade() {
		BaseInventory PI = (ShootEmUp.getPlayer().getComponent(TypeComponent.INVENTORY));
		if (PI.getCoins() >= 5) {
			PI.addMaxPotions(5);
			PI.spendCoins(5);
		}
	}

	private static void resume() {
		ShootEmUp.getMenuSystem().clearMenus();
		ShootEmUp.setPaused(false);
	}

	private static void save() {
		ShootEmUp.getMenuSystem().addMenu(new SaveMenu(ImageProcessor.getImage("SaveScreen")));
	}

	private static void saveGame() {
		if (ShootEmUp.getSave() == null) {
			ShootEmUp.setSave(new Save());
		}
		ShootEmUp.getSave().saveCharacter();
		ShootEmUp.getSave().saveToSystem(1);
		ShootEmUp.setSave(null);
	}

	public static void selectButton(TypeButton type) {
		switch (type) {
		case NEW_GAME:
			newGame();
			break;
		case LOAD_GAME:
			try {
				loadGame();
			} catch (Exception e) {
				Logger.error(e);
			}
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
			selectCharacter(TypeAttack.WARRIOR);
			break;
		case ARCHER:
			selectCharacter(TypeAttack.ARCHER);
			break;
		case MAGE:
			selectCharacter(TypeAttack.MAGE);
			break;
		case BATTLE_MAGE:
			selectCharacter(TypeAttack.BATTLE_MAGE);
			break;
		case ROGUE:
			selectCharacter(TypeAttack.ROGUE);
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
			upgradeManaRegen();
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

	private static void skills() {
		ShootEmUp.getMenuSystem().addMenu(new SkillMenu(ImageProcessor.getImage("SkillScreen")));
	}

	private static void sound() {
		ShootEmUp.getMenuSystem().addMenu(new SoundMenu(ImageProcessor.getImage(MAIN_MENU_BACKGROUND)));
	}
}
