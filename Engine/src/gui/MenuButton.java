package gui;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import java.nio.DoubleBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import display.DPDTRenderer;
import display.Image;
import loop.Loop;

public class MenuButton extends GuiComponent {

	private long window;

	private boolean isPressed;
	private boolean hovered = false;
	private final int w;
	private final int h;

	private final Image id;

	private boolean performClick = false;

	private DoubleBuffer bx = BufferUtils.createDoubleBuffer(1);
	private DoubleBuffer by = BufferUtils.createDoubleBuffer(1);

	private ButtonAction action;

	public MenuButton(Image id, float x, float y, ButtonAction action) {
		super(x, y);
		this.id = id;
		this.action = action;
		w = id.getWidth();
		h = id.getHeight() / 2;
		window = Loop.getDisplay().getWindow();
	}

	@Override
	public void render(DPDTRenderer stat) {
		if (isPressed || hovered) {
			stat.draw(id, new Vector2f(x, y), new Vector2f(w, h), 0, new Vector2f(0, 1), new Vector2f(1, 2));
		} else {
			stat.draw(id, new Vector2f(x, y), new Vector2f(w, h), 0, new Vector2f(0, 0), new Vector2f(1, 2));
		}
	}

	public void reset(int oldWidth, int oldHeight, int newWidth, int newHeight) {
		x = (int) ((x / oldWidth) * newWidth);
		y = (int) ((y / oldHeight) * newHeight);
	}

	public void setHovered(boolean b) {
		this.hovered = b;
	}

	@Override
	public void update() {
		window = Loop.getDisplay().getWindow();
		bx.clear();
		by.clear();

		glfwGetCursorPos(window, bx, by);

		double mx = bx.get();
		double my = by.get();

		if ((mx >= x) && (my >= y) && (mx < (x + w)) && (my < (y + h))) {

			if (isPressed && (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE)) {
				performClick = true;
				isPressed = false;
			} else if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
				isPressed = true;
			}
		}
		if (isPressed && (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE)) {
			isPressed = false;
		}

		if (performClick) {
			if (action != null) {
				action.click();
			}
			postAction();
		}
	}

	public Image getId() {
		return id;
	}

	public boolean hasClicked() {
		return performClick;
	}

	public void postAction() {
		performClick = false;
	}
}
