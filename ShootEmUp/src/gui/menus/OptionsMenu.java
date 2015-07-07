package gui.menus;

import input.Keyboard;
import gui.Button;
import main.ShootEmUp;

import org.lwjgl.glfw.GLFW;

import display.Art;
import display.Image;

public class OptionsMenu extends GuiMenu{

	static int selectedItem = 0;
	public static boolean saved;
	private Button controls;
	private Button sound;
	private Button back;

    public OptionsMenu(Image menuImage) {
        super(menuImage);
        selectedItem = 0;
        controls = addButton(new Button(Art.controlsButton, (ShootEmUp.WIDTH / 2) - (Art.controlsButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) - (Art.controlsButton.getHeight()), 128,24));
        sound = addButton(new Button(Art.soundButton, (ShootEmUp.WIDTH / 2) - (Art.soundButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) - (Art.soundButton.getHeight() / 4), 128,24));
        back = addButton(new Button(Art.backButton, (ShootEmUp.WIDTH / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) + (Art.backButton.getHeight()), 128,24));
    }

    @Override
    public void render() {
        super.render();
        
    }

    public void update() {
    	super.update();
    	if(controls.hasClicked()){
    		addMenu(new ControlsMenu(Art.mainMenuScreen));
        	controls.postAction();
    	}
    	if(sound.hasClicked()){
    		addMenu(new SoundMenu(Art.mainMenuScreen));
        	sound.postAction();
    	}
    	if(back.hasClicked()){
    		popMenu();
        	back.postAction();
    	}
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
