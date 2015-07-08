package gui.menus;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import input.Keyboard;
import gui.Button;
import gui.ButtonType;
import main.ShootEmUp;

import org.lwjgl.glfw.GLFW;

import display.Art;
import display.Image;

public class MainMenu extends GuiMenu {
	
	static int selectedItem = 0;
	public static boolean saved;

    public MainMenu(Image menuImage) {
        super(menuImage);
        ShootEmUp.currentLevel = null;
        selectedItem = 0;
        addButton(new Button(ButtonType.NEW_GAME, Art.newGameButton, (ShootEmUp.WIDTH / 2) - (Art.newGameButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) - (Art.newGameButton.getHeight() * 2), 128,24));
        addButton(new Button(ButtonType.LOAD_GAME, Art.loadGameButton, (ShootEmUp.WIDTH / 2) - (Art.loadGameButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) - Art.loadGameButton.getHeight(), 128,24));
        addButton(new Button(ButtonType.OPTIONS, Art.optionsButton, (ShootEmUp.WIDTH / 2) - (Art.optionsButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2), 128,24));
        addButton(new Button(ButtonType.EXIT, Art.exitButton,  (ShootEmUp.WIDTH /  2) - (Art.exitButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) + Art.exitButton.getHeight(), 128,24));
    }

    @Override
    public void render() {
        super.render();
        
    }

    public void update() {
    	super.update();
    	if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 0){
    		addMenu(new LevelSelectMenu(Art.mainMenuScreen));
        	Keyboard.setKey(GLFW.GLFW_KEY_ENTER);
    	} else if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 1){
    		addMenu(new LoadMenu(Art.mainMenuScreen));
    		Keyboard.setKey(GLFW.GLFW_KEY_ENTER);
    	} else if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 2){
    		addMenu(new OptionsMenu(Art.mainMenuScreen));
    		Keyboard.setKey(GLFW.GLFW_KEY_ENTER);
    	} else if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 3){
    		glfwSetWindowShouldClose(ShootEmUp.d.getWindow(), GL_TRUE);
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
}
