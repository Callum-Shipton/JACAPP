package gui;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import gui.menus.CharacterSelectMenu;
import gui.menus.ControlsMenu;
import gui.menus.InventoryMenu;
import gui.menus.LevelSelectMenu;
import gui.menus.LoadMenu;
import gui.menus.MagicMenu;
import gui.menus.MainMenu;
import gui.menus.MapMenu;
import gui.menus.OptionsMenu;
import gui.menus.SaveMenu;
import gui.menus.SkillMenu;
import gui.menus.SoundMenu;
import audio.music.BackgroundMusic;
import level.Level;
import main.ShootEmUp;
import components.ComponentType;
import components.attack.BaseAttack;
import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.inventory.BaseInventory;
import display.Art;

public abstract class ButtonHandler {
	
	public static void selectButton(ButtonType type){
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
			level1();
			break;
		case LEVEL2:
			level2();
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
		case BACK:
			back();
			break;
		case SOUND:
			sound();
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
		case MAGIC:
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
		case OTHER:
		}
	}
	
	private static void newGame(){
		ShootEmUp.addMenu(new LevelSelectMenu(Art.mainMenuScreen));
	}
	
	private static void loadGame(){
		ShootEmUp.addMenu(new LoadMenu(Art.mainMenuScreen));
	}
	
	private static void options(){
		ShootEmUp.addMenu(new OptionsMenu(Art.mainMenuScreen));
	}
	
	private static void exit(){
		glfwSetWindowShouldClose(ShootEmUp.d.getWindow(), GL_TRUE);
	}
	
	private static void level1(){
		ShootEmUp.currentLevel = new Level(Art.level1);
		ShootEmUp.currentLevel.init();
		ShootEmUp.addMenu(new CharacterSelectMenu(Art.mainMenuScreen));
	}
	
	private static void level2(){
		ShootEmUp.currentLevel = new Level(Art.level2);
		ShootEmUp.currentLevel.init();
		ShootEmUp.addMenu(new CharacterSelectMenu(Art.mainMenuScreen));
	}
	
	private static void warrior(){
		ShootEmUp.currentLevel.createPlayer(TypeAttack.WARRIOR);
		startGame();
	}
	
	private static void archer(){
		ShootEmUp.currentLevel.createPlayer(TypeAttack.ARCHER);
		startGame();
	}
	
	private static void mage(){
		ShootEmUp.currentLevel.createPlayer(TypeAttack.MAGE);
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
	
	private static void inventory(){
		ShootEmUp.addMenu(new InventoryMenu(Art.invScreen));	
	}
	
	private static void magic(){
		ShootEmUp.addMenu(new MagicMenu(Art.magicScreen));
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
		ShootEmUp.menuStack.clear();
		ShootEmUp.addMenu(new MainMenu(Art.mainMenuScreen));
	}
	
	private static void healthRegen(){
		BaseAttack BA = (BaseAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.ATTACK));
		BA.setMaxHealthRegen((int)Math.ceil(BA.getMaxHealthRegen()/2));
		((BaseInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.INVENTORY))).spendCoins(1);
	}
	
	private static void health(){
		BaseAttack BA = (BaseAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.ATTACK));
		BA.setMaxHealth(BA.getMaxHealth() + 1);
		BA.setHealth(BA.getHealth()+1);
		((BaseInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.INVENTORY))).spendCoins(1);
	}
	
	private static void manaRegen(){
		PlayerAttack PA = (PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.ATTACK));
		PA.setMaxManaRegen((int)Math.ceil(PA.getMaxManaRegen()/2));
		((BaseInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.INVENTORY))).spendCoins(1);
	}
	
	private static void mana(){
		PlayerAttack PA = (PlayerAttack) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.ATTACK));
		PA.setMaxMana(PA.getMaxMana() + 1);
		PA.setMana(PA.getMana() + 1);
		((BaseInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.INVENTORY))).spendCoins(1);
	}
	
	//Extra Methods
	

    private static void startGame(){
    	ShootEmUp.paused = false;
		ShootEmUp.clearMenus();
		ShootEmUp.m.stop(BackgroundMusic.MENU);
		ShootEmUp.m.play(BackgroundMusic.MAIN);
    }
}
