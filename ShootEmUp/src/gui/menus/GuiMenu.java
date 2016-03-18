package gui.menus;

import gui.Button;
import gui.ButtonHandler;
import gui.ButtonType;
import input.Keyboard;

import java.util.*;

import org.lwjgl.glfw.GLFW;

import main.ShootEmUp;
import math.Vector2;
import display.Art;
import display.Image;

public abstract class GuiMenu {

    protected List<Button> buttons = new ArrayList<Button>();
    protected int buttonPointer = 0;
    protected Image menuImage;
    protected int x;
    protected int y;
    protected int w;
    protected int h;
    
    public GuiMenu(Image menuImage){
    	this.menuImage = menuImage;
    	x = 0;
    	y = 0;
    	w = menuImage.getWidth();
    	h = menuImage.getHeight();
    }

    protected Button addButton(Button button) {
        buttons.add(button);
        return button;
    }
    
    protected void removeButton(Button button) {
        buttons.remove(button);
    }

    public void render() {
    	Art.stat.draw(menuImage, new Vector2(x,y), new Vector2(ShootEmUp.width,ShootEmUp.height), 0, new Vector2(0,0));
        for (Button button : buttons) {
            button.render(Art.stat);
        }
    }

    public void update() {
        for (Button button : buttons) {
            button.update();
            if (button.hasClicked()){
            	ButtonHandler.selectButton(button.getType());
            	if(button.getType() != ButtonType.OTHER){
            		button.postAction();
            	}
            }
        }
        if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1){
    		Keyboard.setKey(GLFW.GLFW_KEY_ENTER);
    		ButtonHandler.selectButton(buttons.get(buttonPointer).getType());
    	} else if (Keyboard.getKey(GLFW.GLFW_KEY_DOWN) == 1) {
    		Keyboard.setKey(GLFW.GLFW_KEY_DOWN);
            buttonPointer++;
            if (buttonPointer > buttons.size()) {
                buttonPointer = 0;
            }
        }else if (Keyboard.getKey(GLFW.GLFW_KEY_UP) == 1) {
        	Keyboard.setKey(GLFW.GLFW_KEY_UP);
        	buttonPointer--;
            if (buttonPointer < 0) {
            	buttonPointer = buttons.size();
            }
        }
    }

    public void buttonPressed(Button button) {
    	
    }
    
    protected void popMenu() {
    	ShootEmUp.menuStack.pop();
    }
    
    public void reset (int oldWidth, int oldHeight, int newWidth, int newHeight){
    	for(Button b : buttons){
    		b.reset(oldWidth, oldHeight, newWidth, newHeight);
    	}
    }

}
