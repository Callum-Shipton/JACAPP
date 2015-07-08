package gui.menus;

import input.Keyboard;
import gui.Button;
import gui.ButtonType;
import main.ShootEmUp;

import org.lwjgl.glfw.GLFW;

import components.attack.TypeAttack;
import display.Art;
import display.Image;
import audio.music.BackgroundMusic;

public class CharacterSelectMenu extends GuiMenu {
	
	static int selectedItem = 0;
	public static boolean saved;

    public CharacterSelectMenu(Image menuImage) {
        super(menuImage);
        selectedItem = 0;
        addButton(new Button(ButtonType.WARRIOR, Art.warriorButton, (ShootEmUp.WIDTH / 2) - (Art.warriorButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) - (Art.warriorButton.getHeight() * 2), 128,24));
        addButton(new Button(ButtonType.ARCHER, Art.archerButton, (ShootEmUp.WIDTH / 2) - (Art.archerButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) - Art.archerButton.getHeight(), 128,24));
        addButton(new Button(ButtonType.MAGE, Art.mageButton, (ShootEmUp.WIDTH / 2) - (Art.mageButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2), 128,24));
        addButton(new Button(ButtonType.BACK, Art.backButton, (ShootEmUp.WIDTH / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) + Art.backButton.getHeight(), 128,24));
    }

    @Override
    public void render() {
        super.render();
        
    }

    public void update() {
    	super.update();
    	
    	if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 0){
    		ShootEmUp.currentLevel.createPlayer(TypeAttack.WARRIOR);
    		startGame();
    	} else if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 1){
    		ShootEmUp.currentLevel.createPlayer(TypeAttack.ARCHER);
    		startGame();
    	} else if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 2){
    		ShootEmUp.currentLevel.createPlayer(TypeAttack.MAGE);
    		startGame();
    	} else if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 3){
    		popMenu();
    	}
    	
        else if (Keyboard.getKey(GLFW.GLFW_KEY_DOWN) == 1) {
            selectedItem++;
            if (selectedItem > 3) {
                selectedItem = 0;
            }
        }
        else if (Keyboard.getKey(GLFW.GLFW_KEY_UP) == 1) {
            selectedItem--;
            if (selectedItem < 0) {
                selectedItem = 3;
            }
        }
    }

    public void addMenu(GuiMenu menu) {
		ShootEmUp.menuStack.add(menu);
	}
    
    public void startGame(){
    	ShootEmUp.paused = false;
		ShootEmUp.clearMenus();
		ShootEmUp.m.stop(BackgroundMusic.MENU);
		ShootEmUp.m.play(BackgroundMusic.MAIN);
    }
}
