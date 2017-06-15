package gui;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_TRUE;

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
import main.Logger;
import main.Loop;
import save.Save;

public abstract class ButtonHandler {

	private static void back() {
		Loop.getMenuSystem().popMenu();
	}

	private static void character(TypeAttack type) {
		if (Loop.getSave() == null) {
			PlayerBuilder.buildPlayer(type);
		} else {
			if (Loop.getSave().getCharacter(type) != null) {
				PlayerBuilder.buildPlayer(type, Loop.getSave().getCharacter(type));
			} else {
				PlayerBuilder.buildPlayer(type);
			}
		}
		Loop.getMenuSystem().addMenu(new LevelSelectMenu(Art.getImage("MainMenuScreen")));
	}

	private static void controls() {
		Loop.getMenuSystem().addMenu(new ControlsMenu(Art.getImage("MainMenuScreen")));
	}

	private static void exit() {
		glfwSetWindowShouldClose(Loop.getDisplay().getWindow(), GL_TRUE);
	}

	private static void health() {
		BaseInventory BI = Loop.getPlayer().getComponent(TypeComponent.INVENTORY);
		if (BI.getLevelPoints() > 0) {
			BaseAttack BA = (Loop.getPlayer().getComponent(TypeComponent.ATTACK));
			BA.setMaxHealth(BA.getMaxHealth() + 1);
			BA.setHealth(BA.getHealth() + 1);
			BI.spendLevelPoints(1);
		}
	}

	private static void healthRegen() {
		BaseInventory BI = Loop.getPlayer().getComponent(TypeComponent.INVENTORY);
		if (BI.getLevelPoints() > 0) {
			BaseAttack BA = Loop.getPlayer().getComponent(TypeComponent.ATTACK);
			BA.setMaxHealthRegen((int) Math.ceil(BA.getMaxHealthRegen() / 2.0));
			BI.spendLevelPoints(1);
		}
	}

	private static void inventory() {
		Loop.getMenuSystem().addMenu(new InventoryMenu(Art.getImage("InventoryScreen")));
	}

	private static void inventoryUpgrade() {
		BaseInventory PI = (Loop.getPlayer().getComponent(TypeComponent.INVENTORY));
		if (PI.getCoins() >= 5) {
			PI.addInventorySize(5);
			PI.spendCoins(5);
		}
	}

	private static void level(int level) {
		Loop.setCurrentLevel(new Level(Art.LEVEL_FILE_LOCATION, level));
		Loop.getCurrentLevel().init();
		Loop.startGame();
	}

	private static void loadGame() throws Exception {
		if (Loop.getSave() == null) {
			Loop.setSave(new Save());
		}
		Loop.getSave().load(1);
		Loop.getMenuSystem().addMenu(new LevelSelectMenu(Art.getImage("MainMenuScreen")));
	}

	private static void magic() {
		Loop.getMenuSystem().addMenu(new UpgradesMenu(Art.getImage("UpgradesScreen")));
	}

	private static void mainMenu() {
		Loop.getMenuSystem().setMainMenu(true);
		Loop.getMenuSystem().clearMenus();
		Loop.getMenuSystem().addMenu(new MainMenu(Art.getImage("MainMenuScreen")));
	}

	private static void mana() {
		BaseInventory BI = Loop.getPlayer().getComponent(TypeComponent.INVENTORY);
		if (BI.getLevelPoints() > 0) {
			PlayerAttack PA = (Loop.getPlayer().getComponent(TypeComponent.ATTACK));
			PA.setMaxMana(PA.getMaxMana() + 1);
			PA.setMana(PA.getMana() + 1);
			BI.spendLevelPoints(1);
		}
	}

	private static void upgradeManaRegen() {
		BaseInventory BI = Loop.getPlayer().getComponent(TypeComponent.INVENTORY);
		if (BI.getLevelPoints() > 0) {
			PlayerAttack PA = Loop.getPlayer().getComponent(TypeComponent.ATTACK);
			PA.setMaxManaRegen((int) Math.ceil(PA.getMaxManaRegen() / 2.0));
			BI.spendLevelPoints(1);
		}
	}

	private static void map() {
		Loop.getMenuSystem().addMenu(new MapMenu(Art.getImage("MapScreen")));
	}

	private static void mute() {
		Loop.getMusicPlayer().pause();
	}

	private static void newGame() {
		Loop.getMenuSystem().addMenu(new CharacterSelectMenu(Art.getImage("MainMenuScreen")));
	}

	private static void options() {
		Loop.getMenuSystem().addMenu(new OptionsMenu(Art.getImage("MainMenuScreen")));
	}

	private static void potionsUpgrade() {
		BaseInventory PI = (Loop.getPlayer().getComponent(TypeComponent.INVENTORY));
		if (PI.getCoins() >= 5) {
			PI.addMaxPotions(5);
			PI.spendCoins(5);
		}
	}

	private static void resume() {
		Loop.getMenuSystem().clearMenus();
		Loop.setPaused(false);
	}

	private static void save() {
		Loop.getMenuSystem().addMenu(new SaveMenu(Art.getImage("SaveScreen")));
	}

	private static void saveGame() {
		if (Loop.getSave() == null) {
			Loop.setSave(new Save());
		}
		Loop.getSave().saveCharacter();
		Loop.getSave().saveToSystem(1);
		Loop.setSave(null);
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
		Loop.getMenuSystem().addMenu(new SkillMenu(Art.getImage("SkillScreen")));
	}

	private static void sound() {
		Loop.getMenuSystem().addMenu(new SoundMenu(Art.getImage("MainMenuScreen")));
	}
}
