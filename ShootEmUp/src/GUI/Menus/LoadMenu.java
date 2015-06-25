package GUI.Menus;

import Display.Art;
import Display.Image;
import GUI.Button;
import Main.ShootEmUp;

public class LoadMenu extends GuiMenu{

	static int selectedItem = 0;
	public static boolean saved;
	private Button back;

    public LoadMenu(Image menuImage) {
        super(menuImage);
        selectedItem = 0;
        back = addButton(new Button(Art.backButton, (ShootEmUp.WIDTH / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.HEIGHT / 2) - (Art.backButton.getHeight() / 2), 128,24));
    }

    @Override
    public void render() {
        super.render();
        
    }

    public void update() {
    	super.update();
    	
    	if(back.hasClicked()){
    		popMenu();
        	back.postAction();
    	}
    	//KEYBOARD SELECTION
    	
    	/*
    	if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 0){
        	popMenu();
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
        */
    }

    public void addMenu(GuiMenu menu) {
		ShootEmUp.menuStack.add(menu);
	}
}
