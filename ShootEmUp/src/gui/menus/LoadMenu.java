package gui.menus;

import input.Keyboard;
import gui.Button;
import gui.ButtonHandler;
import gui.ButtonType;
import main.ShootEmUp;

import org.lwjgl.glfw.GLFW;

import display.Art;
import display.Image;

public class LoadMenu extends GuiMenu{

	static int selectedItem = 0;
	public static boolean saved;

    public LoadMenu(Image menuImage) {
        super(menuImage);
        selectedItem = 0;
        addButton(new Button(ButtonType.BACK, Art.backButton, (ShootEmUp.width / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.backButton.getHeight() / 2), 128,24));
    }

    @Override
    public void render() {
        super.render();
        
    }

    public void update() {
    	super.update();

    	if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 0){
    		ButtonHandler.selectButton(ButtonType.BACK);
        	Keyboard.setKey(GLFW.GLFW_KEY_ENTER);
    	}
    }
}
