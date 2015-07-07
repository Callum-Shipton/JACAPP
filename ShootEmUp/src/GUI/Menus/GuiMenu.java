package GUI.Menus;

import java.util.*;

import Display.Art;
import Display.Image;
import GUI.Button;
import Main.ShootEmUp;
import Math.Vector2;

public abstract class GuiMenu {

    protected List<Button> buttons = new ArrayList<Button>();
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
    	Art.stat.draw(menuImage, new Vector2(x,y), new Vector2(ShootEmUp.WIDTH,ShootEmUp.HEIGHT), 0, new Vector2(0,0));
        for (Button button : buttons) {
            button.render(Art.stat);
        }
    }

    public void update() {

        for (Button button : buttons) {
            button.update();
        }
    }

    public void buttonPressed(Button button) {
    	
    }
    
    protected void popMenu() {
    	ShootEmUp.menuStack.pop();
    }

}
