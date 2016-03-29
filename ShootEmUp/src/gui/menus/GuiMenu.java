package gui.menus;

import gui.Button;
import gui.ButtonHandler;
import gui.MenuItem;
import gui.TypeButton;
import input.Keyboard;

import java.util.*;

import org.lwjgl.glfw.GLFW;

import main.ShootEmUp;
import math.Vector2;
import display.Art;
import display.Image;

public abstract class GuiMenu {

    protected List<Button> buttons = new ArrayList<Button>();
    protected ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
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
        for(MenuItem menuItem: menuItems){
    		menuItem.render();
    	}
    }

    public void update() {
    	for(MenuItem menuItem: menuItems){
    		menuItem.update();
    	}
    	
        for (Button button : buttons) {
            button.update();
            if (button.hasClicked()){
            	ButtonHandler.selectButton(button.getType());
            	if(button.getType() != TypeButton.OTHER){
            		button.postAction();
            	}
            }
        }
        if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1){
    		Keyboard.setKey(GLFW.GLFW_KEY_ENTER);
    		ButtonHandler.selectButton(buttons.get(buttonPointer).getType());
    	} else if (Keyboard.getKey(GLFW.GLFW_KEY_DOWN) == 1) {
    		Keyboard.setKey(GLFW.GLFW_KEY_DOWN);
    		buttons.get(buttonPointer).setHovered(false);
            buttonPointer++;
            if (buttonPointer >= buttons.size()) {
                buttonPointer = 0;
            }
            buttons.get(buttonPointer).setHovered(true);
        }else if (Keyboard.getKey(GLFW.GLFW_KEY_UP) == 1) {
        	Keyboard.setKey(GLFW.GLFW_KEY_UP);
        	buttons.get(buttonPointer).setHovered(false);
        	buttonPointer--;
            if (buttonPointer < 0) {
            	buttonPointer = buttons.size() - 1;
            }
            buttons.get(buttonPointer).setHovered(true);
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
