package gui;

import java.nio.DoubleBuffer;

import main.ShootEmUp;
import math.Vector2;

import org.lwjgl.BufferUtils;

import display.DPDTRenderer;
import display.Image;
import static org.lwjgl.glfw.GLFW.*;

public class Button extends GuiComponent {
	
	private ButtonType type;
	
	private long window;

	private boolean isPressed;
	private int x;
	private int y;
	private final int w;
	private final int h;

	private final Image id;
	
	private boolean performClick = false;
	
	DoubleBuffer Bx = BufferUtils.createDoubleBuffer(1);
    DoubleBuffer By = BufferUtils.createDoubleBuffer(1);

	public Button(ButtonType type, Image id, int x, int y, int w, int h) {
		this.type = type;
		this.id = id;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		window = ShootEmUp.display.getWindow();
	}

	@Override
	public void update() {
		super.update();
		window = ShootEmUp.display.getWindow();
		Bx.clear();
		By.clear();
		
	    glfwGetCursorPos(window, Bx, By);
	    
	    double mx = Bx.get();
	    double my = By.get();

		if (mx >= x && my >= y && mx < (x + w) && my < (y + h)) {
			
			if (isPressed && glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE) {
				performClick = true;
				isPressed = false;
			} else if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
				isPressed = true;
			}
		}
		if (isPressed && glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE) {
			isPressed = false;
		} 
	}

	public void postAction() {
		performClick = false;
	}

	public void render(DPDTRenderer stat) {
			if (isPressed) {
				stat.draw(id, new Vector2(x,y), new Vector2(w,h), 0, new Vector2(0,1), new Vector2(1,2));
			} else {
				stat.draw(id, new Vector2(x,y), new Vector2(w,h), 0, new Vector2(0,0), new Vector2(1,2));
			}
	}
	
    public void reset (int oldWidth, int oldHeight, int newWidth, int newHeight){
    	x = (int)(((float)x/oldWidth)*newWidth);
    	y = (int)(((float)y/oldHeight)*newHeight);
    }

	public boolean hasClicked() {
		return performClick;
	}

	public Image getId() {
		return id;
	}

	public ButtonType getType(){
		return type;
	}
}
