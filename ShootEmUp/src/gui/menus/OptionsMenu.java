package gui.menus;

import input.Keyboard;
import gui.Button;
import gui.ButtonType;
import main.ShootEmUp;

import org.lwjgl.glfw.GLFW;

import display.Art;
import display.Image;

public class OptionsMenu extends GuiMenu{

	static int selectedItem = 0;
	public static boolean saved;

    public OptionsMenu(Image menuImage) {
        super(menuImage);
        selectedItem = 0;
        addButton(new Button(ButtonType.CONTROLS, Art.controlsButton, (ShootEmUp.WIDTH / 2) - (Art.controlsButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) - (Art.controlsButton.getHeight()), 128,24));
        addButton(new Button(ButtonType.SOUND, Art.soundButton, (ShootEmUp.WIDTH / 2) - (Art.soundButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) - (Art.soundButton.getHeight() / 4), 128,24));
        addButton(new Button(ButtonType.BACK, Art.backButton, (ShootEmUp.WIDTH / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) + (Art.backButton.getHeight()), 128,24));
    }

    @Override
    public void render() {
        super.render();
        
    }

    public void update() {
    	super.update();

    	if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 0){
    		addMenu(new ControlsMenu(Art.mainMenuScreen));
        	Keyboard.setKey(GLFW.GLFW_KEY_ENTER);
    	}
    	if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 1){
    		addMenu(new SoundMenu(Art.mainMenuScreen));;
        	Keyboard.setKey(GLFW.GLFW_KEY_ENTER);
    	}
    	if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 2){
        	popMenu();
        	Keyboard.setKey(GLFW.GLFW_KEY_ENTER);
    	}
    	
        else if (Keyboard.getKey(GLFW.GLFW_KEY_DOWN) == 1) {
            selectedItem++;
            if (selectedItem > 2) {
                selectedItem = 0;
            }
        }
        else if (Keyboard.getKey(GLFW.GLFW_KEY_UP) == 1) {
            selectedItem--;
            if (selectedItem < 0) {
                selectedItem = 2;
            }
        }
    }

    public void addMenu(GuiMenu menu) {
		ShootEmUp.menuStack.add(menu);
	}
}
