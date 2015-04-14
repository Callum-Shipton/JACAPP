package GUI;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import Main.ShootEmUp;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Button extends GuiComponent {

	private long window;

	private boolean isPressed;
	private boolean tabs = false;
	private final int x;
	private final int y;
	private final int w;
	private final int h;

	private final int id;

	private int ix;

	private int iy;
	private boolean performClick = false;
	
	DoubleBuffer Bx = BufferUtils.createDoubleBuffer(1);
    DoubleBuffer By = BufferUtils.createDoubleBuffer(1);



	public Button(int id, int buttonImageIndex, int x, int y, int w, int h) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.ix = buttonImageIndex % 2;
		this.iy = buttonImageIndex / 2;
		if (buttonImageIndex > 13) {
			tabs = true;
		}
		window = ShootEmUp.d.getWindow();
	}

	@Override
	public void update() {
		super.update();
		
	    glfwGetCursorPos(window, Bx, By);

		isPressed = false;
		if (Bx.get() >= x && By.get() >= y && Bx.get() < (x + w) && By.get() < (y + h)) {
			/*
			if (!tabs) {
				if (RpgComponent.menuStack.search(new TitleMenu(
						RpgComponent.GAME_WIDTH, RpgComponent.GAME_WIDTH)) == -1) {
					TitleMenu.selectedItem = (y - 160) / 30;
				}
				if (RpgComponent.menuStack.search(new PauseMenu()) == -1) {
					PauseMenu.selectedItem = (y - 30) / 30;
				}
				if (RpgComponent.menuStack.search(new QuestMenu()) == -1) {
					QuestMenu.selectedItem = (y - 30) / 30;
				}
				if (RpgComponent.menuStack.search(new SkillsMenu()) == -1) {
					SkillsMenu.selectedItem = (y - 30) / 30;
				}
				if (RpgComponent.menuStack.search(new MapMenu()) == -1) {
					MapMenu.selectedItem = (y - 30) / 30;
				}
				if (RpgComponent.menuStack.search(new SaveGameMenu()) == -1) {
					SaveGameMenu.selectedItem = (y - 30) / 30;
				}
			}

			*/
			
			if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE) {
				performClick = true;
			} else if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
				isPressed = true;
			}
		}
	}

	public void postAction() {
		performClick = false;
	}

	@Override
	public void render() {
			if (isPressed) {
				//Render pressed button
			} else {
				//Render normal button
			}
	}

	public boolean hasClicked() {
		return performClick;
	}

	public int getId() {
		return id;
	}

}
