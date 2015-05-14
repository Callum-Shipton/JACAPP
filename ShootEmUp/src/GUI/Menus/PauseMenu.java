package GUI.Menus;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_TRUE;

import java.awt.event.KeyEvent;

import org.lwjgl.glfw.GLFW;

import Display.Art;
import Display.Image;
import GUI.Button;
import Input.Keyboard;
import Main.ShootEmUp;

public class PauseMenu extends GuiMenu {
	
	static int selectedItem = 0;
	public static boolean saved;
	private Button back;
	private Button exit;


    public PauseMenu(Image menuImage) {
        super(menuImage);
        selectedItem = 0;
        back = addButton(new Button(Art.back, 30, 30, 128,24));
        exit = addButton(new Button(Art.exit, 30, 64, 128,24));
    }

    @Override
    public void render() {
        super.render();
        
    }

    public void update() {
    	super.update();
    	if(back.hasClicked()){
    		popMenu();
        	ShootEmUp.paused = false;
        	back.postAction();
    	}
    	if(exit.hasClicked()){
    		glfwSetWindowShouldClose(ShootEmUp.d.getWindow(), GL_TRUE);
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
