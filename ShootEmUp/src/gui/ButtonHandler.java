package gui;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_TRUE;

import gui.menus.CharacterSelectMenu;
import gui.menus.ControlsMenu;
import gui.menus.InventoryMenu;
import gui.menus.LevelSelectMenu;
import gui.menus.UpgradesMenu;
import gui.menus.MainMenu;
import gui.menus.MapMenu;
import gui.menus.OptionsMenu;
import gui.menus.SaveMenu;
import gui.menus.SkillMenu;
import gui.menus.SoundMenu;
import audio.music.BackgroundMusic;
import level.Level;
import level.PlayerBuilder;
import main.ShootEmUp;
import save.Save;
import save.SaveHandler;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.inventory.PlayerInventory;
import display.Art;

public abstract class ButtonHandler {
	
	public static void selectButton(TypeButton type){
		switch(type){
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
			warrior();
			break;
		case ARCHER:
			archer();
			break;
		case MAGE:
			mage();
			break;
		case BATTLE_MAGE:
			battleMage();
			break;
		case ROGUE:
			rogue();
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
	
	private static void newGame(){
		ShootEmUp.addMenu(new LevelSelectMenu(Art.mainMenuScreen));
	}
	
	private static void loadGame(){
		ShootEmUp.save = SaveHandler.load(1);
		ShootEmUp.addMenu(new LevelSelectMenu(Art.mainMenuScreen));
	}
	
	private static void options(){
		ShootEmUp.addMenu(new OptionsMenu(Art.mainMenuScreen));
	}
	
	private static void exit(){
		glfwSetWindowShouldClose(ShootEmUp.display.getWindow(), GL_TRUE);
	}
	
	private static void level(int level){
		ShootEmUp.currentLevel = new Level(Art.levels, level);
		ShootEmUp.currentLevel.init();
		ShootEmUp.addMenu(new CharacterSelectMenu(Art.mainMenuScreen));
	}
	
	private static void warrior(){
		if(ShootEmUp.save == null){
			PlayerBuilder.buildPlayer(TypeAttack.WARRIOR);
		} else {
			if(ShootEmUp.save.getWarrior() != null){
				PlayerBuilder.buildPlayer(TypeAttack.WARRIOR, ShootEmUp.save.getWarrior());
			} else {
				PlayerBuilder.buildPlayer(TypeAttack.WARRIOR);
			}
		}
		startGame();
	}
	
	private static void archer(){
		if(ShootEmUp.save == null){
			PlayerBuilder.buildPlayer(TypeAttack.ARCHER);
		} else {
			if(ShootEmUp.save.getArcher() != null){
				PlayerBuilder.buildPlayer(TypeAttack.ARCHER, ShootEmUp.save.getArcher());
			} else {
				PlayerBuilder.buildPlayer(TypeAttack.ARCHER);
			}
		}
		startGame();
	}
	
	private static void mage(){
		if(ShootEmUp.save == null){
			PlayerBuilder.buildPlayer(TypeAttack.MAGE);
		} else {
			if(ShootEmUp.save.getMage() != null){
				PlayerBuilder.buildPlayer(TypeAttack.MAGE, ShootEmUp.save.getMage());
			} else {
				PlayerBuilder.buildPlayer(TypeAttack.MAGE);
			}
		}
		startGame();
	}
	
	private static void battleMage(){
		if(ShootEmUp.save == null){
			PlayerBuilder.buildPlayer(TypeAttack.BATTLE_MAGE);
		} else {
			if(ShootEmUp.save.getBattleMage() != null){
				PlayerBuilder.buildPlayer(TypeAttack.BATTLE_MAGE, ShootEmUp.save.getBattleMage());
			} else {
				PlayerBuilder.buildPlayer(TypeAttack.BATTLE_MAGE);
			}
		}
		startGame();
	}
	
	private static void rogue(){
		if(ShootEmUp.save == null){
			PlayerBuilder.buildPlayer(TypeAttack.ROGUE);
		} else {
			if(ShootEmUp.save.getRogue() != null){
				PlayerBuilder.buildPlayer(TypeAttack.ROGUE, ShootEmUp.save.getRogue());
			} else {
				PlayerBuilder.buildPlayer(TypeAttack.ROGUE);
			}
		}
		startGame();
	}
	
	private static void back(){
		ShootEmUp.menuStack.pop();
	}
	
	private static void controls(){
		ShootEmUp.addMenu(new ControlsMenu(Art.mainMenuScreen));
	}
	
	private static void sound(){
		ShootEmUp.addMenu(new SoundMenu(Art.mainMenuScreen));
	}
	
	private static void mute(){
		if(ShootEmUp.musicPause){
			ShootEmUp.backgroundMusic.play(ShootEmUp.currentMusic);
		} else {
			ShootEmUp.backgroundMusic.pause(ShootEmUp.currentMusic);
		}
		ShootEmUp.musicPause = !ShootEmUp.musicPause;
	}
	
	private static void inventory(){
		ShootEmUp.addMenu(new InventoryMenu(Art.invScreen));	
	}
	
	private static void magic(){
		ShootEmUp.addMenu(new UpgradesMenu(Art.upgradesScreen));
	}
	
	private static void skills(){
		ShootEmUp.addMenu(new SkillMenu(Art.skillScreen));
	}
	
	private static void map(){
		ShootEmUp.addMenu(new MapMenu(Art.mapScreen));
	}
	
	private static void save(){
		ShootEmUp.addMenu(new SaveMenu(Art.saveScreen));
	}
	
	private static void resume(){
		ShootEmUp.menuStack.clear();
    	ShootEmUp.paused = false;
	}
	
	private static void mainMenu(){
		ShootEmUp.mainMenu = true;
		ShootEmUp.menuStack.clear();
		ShootEmUp.addMenu(new MainMenu(Art.mainMenuScreen));
	}
	
	private static void healthRegen(){
		if(((PlayerInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY))).getLevelPoints() > 0){
			BaseAttack BA = (BaseAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK));
			BA.setMaxHealthRegen((int)Math.ceil(BA.getMaxHealthRegen()/2));
			((PlayerInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY))).spendLevelPoints(1);
		}
	}
	
	private static void health(){
		if(((PlayerInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY))).getLevelPoints() > 0){
			BaseAttack BA = (BaseAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK));
			BA.setMaxHealth(BA.getMaxHealth() + 1);
			BA.setHealth(BA.getHealth()+1);
			((PlayerInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY))).spendLevelPoints(1);
		}
	}
	
	private static void manaRegen(){
		if(((PlayerInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY))).getLevelPoints() > 0){
			PlayerAttack PA = (PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK));
			PA.setMaxManaRegen((int)Math.ceil(PA.getMaxManaRegen()/2));
			((PlayerInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY))).spendLevelPoints(1);
		}
	}
	
	private static void mana(){
		if(((PlayerInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY))).getLevelPoints() > 0){
			PlayerAttack PA = (PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK));
			PA.setMaxMana(PA.getMaxMana() + 1);
			PA.setMana(PA.getMana() + 1);
			((PlayerInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY))).spendLevelPoints(1);
		}
	}
	
	private static void inventoryUpgrade(){
		PlayerInventory PI = (PlayerInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY));
		if(PI.getCoins() >= 5){
			PI.addInventorySize(5);
			PI.spendCoins(5);
		}
	}
	
	private static void potionsUpgrade(){
		PlayerInventory PI = (PlayerInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY));
		if(PI.getCoins() >= 5){
			PI.addMaxPotions(5);
			PI.spendCoins(5);
		}
	}
	
	private static void saveGame(){
		if(ShootEmUp.save == null){
			ShootEmUp.save = new Save();
		}
		ShootEmUp.save.saveCharacter();
		SaveHandler.save(ShootEmUp.save, 1);
		ShootEmUp.save = null;
	}
	//Extra Methods
	

    private static void startGame(){
    	ShootEmUp.paused = false;
    	ShootEmUp.mainMenu = false;
		ShootEmUp.clearMenus();
		ShootEmUp.backgroundMusic.stop(ShootEmUp.currentMusic);
		ShootEmUp.currentMusic = BackgroundMusic.MAIN;
		if(!ShootEmUp.musicPause){
			ShootEmUp.backgroundMusic.play(ShootEmUp.currentMusic);
		}
    }
}
