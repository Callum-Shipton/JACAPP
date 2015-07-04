package GUI.Menus;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_TRUE;

import org.lwjgl.glfw.GLFW;

import Display.Art;
import Display.Image;
import GUI.Button;
import Input.Keyboard;
import Main.ShootEmUp;

public class SaveMenu extends GuiMenu {
	
	static int selectedItem = 0;
	public static boolean saved;
	private Button back;
	private Button exit;
	private Button invButton;
	private Button skillButton;
	private Button magicButton;
	private Button mapButton;

    public SaveMenu(Image menuImage) {
        super(menuImage);
        selectedItem = 0;
        back = addButton(new Button(Art.backButton, 30, 30, 128,24));
        exit = addButton(new Button(Art.exitButton, 30, 64, 128,24));
        invButton = addButton(new Button(Art.invButton, 922, 0, 101, 102));
        skillButton = addButton(new Button(Art.skillButton, 922, 204, 101, 102));
        magicButton = addButton(new Button(Art.magicButton, 922, 102, 101, 102));
        mapButton = addButton(new Button(Art.mapButton, 922, 306, 101, 102));
    }

    @Override
    public void render() {
        super.render();
        
    }

    public void update() {
    	super.update();
    	if(invButton.hasClicked()){
    	    addMenu(new InventoryMenu(Art.invScreen));
    	    invButton.postAction();
    	}
    	if(magicButton.hasClicked()){
    	    addMenu(new MagicMenu(Art.magicScreen));
    	    magicButton.postAction();
    	}
    	if(skillButton.hasClicked()){
    	    addMenu(new SkillMenu(Art.skillScreen));
    	    skillButton.postAction();
    	}
    	if(mapButton.hasClicked()){
    	    addMenu(new MapMenu(Art.mapScreen));
    	    mapButton.postAction();
    	}
    	if(back.hasClicked()){
    		popMenu();
        	ShootEmUp.paused = false;
        	back.postAction();
    	}
    	if(exit.hasClicked()){
    		ShootEmUp.menuStack.clear();
    		addMenu(new MainMenu(Art.mainMenuScreen));
        	back.postAction();
    	}
    	if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 0){
        	popMenu();
        	ShootEmUp.paused = false;
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
