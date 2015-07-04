package GUI.Menus;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_TRUE;

import org.lwjgl.glfw.GLFW;

import Display.Art;
import Display.Image;
import GUI.Button;
import Input.Keyboard;
import Main.ShootEmUp;

public class MainMenu extends GuiMenu {
	
	static int selectedItem = 0;
	public static boolean saved;
	private Button newGame;
	private Button loadGame;
	private Button options;
	private Button exit;

    public MainMenu(Image menuImage) {
        super(menuImage);
        selectedItem = 0;
        newGame = addButton(new Button(Art.newGameButton, (ShootEmUp.WIDTH / 2) - (Art.newGameButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) - (Art.newGameButton.getHeight() * 2), 128,24));
        loadGame = addButton(new Button(Art.loadGameButton, (ShootEmUp.WIDTH / 2) - (Art.loadGameButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) - Art.loadGameButton.getHeight(), 128,24));
        options = addButton(new Button(Art.optionsButton, (ShootEmUp.WIDTH / 2) - (Art.optionsButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2), 128,24));
        exit = addButton(new Button(Art.exitButton,  (ShootEmUp.WIDTH /  2) - (Art.exitButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) + Art.exitButton.getHeight(), 128,24));
    }

    @Override
    public void render() {
        super.render();
        
    }

    public void update() {
    	super.update();
    	if(newGame.hasClicked()){
    		addMenu(new LevelSelectMenu(Art.mainMenuScreen));
        	newGame.postAction();
    	}
    	if(loadGame.hasClicked()){
    		addMenu(new LoadMenu(Art.mainMenuScreen));
        	loadGame.postAction();
    	}
    	if(options.hasClicked()){
    		addMenu(new OptionsMenu(Art.mainMenuScreen));
        	options.postAction();
    	}
    	if(exit.hasClicked()){
    		glfwSetWindowShouldClose(ShootEmUp.d.getWindow(), GL_TRUE);
        	exit.postAction();
    	}
    	if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 0){
    		addMenu(new LevelSelectMenu(Art.mainMenuScreen));
        	Keyboard.setKey(GLFW.GLFW_KEY_ENTER);
    	}
    	else if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 1){
    		glfwSetWindowShouldClose(ShootEmUp.d.getWindow(), GL_TRUE);
    	}
    	
        else if (Keyboard.getKey(GLFW.GLFW_KEY_DOWN) == 1) {
            selectedItem++;
            if (selectedItem > 1) {
                selectedItem = 0;
            }
        }
        else if (Keyboard.getKey(GLFW.GLFW_KEY_UP) == 1) {
            selectedItem--;
            if (selectedItem < 0) {
                selectedItem = 1;
            }
        }
    }

    public void addMenu(GuiMenu menu) {
		ShootEmUp.menuStack.add(menu);
	}
}
