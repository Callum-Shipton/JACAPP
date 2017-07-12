package gui;

import components.TypeComponent;
import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.inventory.BaseInventory;
import display.ImageProcessor;
import gui.menus.CharacterSelectMenu;
import gui.menus.MainMenu;
import gui.menus.MapMenu;
import gui.menus.OptionsMenu;
import gui.menus.SaveMenu;
import gui.menus.SkillMenu;
import gui.menus.SoundMenu;
import gui.menus.UpgradesMenu;
import logging.Logger;
import main.ShootEmUp;
import save.Save;

public abstract class ButtonHandler {

	public static final String MAIN_MENU_BACKGROUND = "MainMenuScreen";

	private static void loadGame() throws Exception {
		Save save = ShootEmUp.getSave();

		if (save == null) {
			save = new Save();
			ShootEmUp.setSave(save);
		}
		save.load(1);

		int warriorLevel = 0;
		int archerLevel = 0;
		int mageLevel = 0;
		int battleMageLevel = 0;
		int rogueLevel = 0;

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
		BaseInventory BI = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		if (BI.getLevelPoints() > 0) {
			PlayerAttack PA = (ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.ATTACK));
			PA.setMaxMana(PA.getMaxMana() + 1);
			PA.setMana(PA.getMana() + 1);
			BI.spendLevelPoints(1);
		}
	}

	private static void upgradeManaRegen() {
		BaseInventory BI = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		if (BI.getLevelPoints() > 0) {
			PlayerAttack PA = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.ATTACK);
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
		BaseInventory PI = (ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY));
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
		case SOUND:
			sound();
			break;
		case MUTE:
			mute();
			break;
		case RESUME:
			resume();
			break;
		case MAIN_MENU:
			mainMenu();
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
		case MANA_REGEN:
			upgradeManaRegen();
			break;
		case MANA:
			mana();
			break;
		case POTIONS_UPGRADE:
			potionsUpgrade();
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
