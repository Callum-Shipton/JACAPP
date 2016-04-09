package gui;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import display.DPDTRenderer;
import display.Image;
import main.ShootEmUp;
import math.Vector2;

public class Button extends GuiComponent {

	private TypeButton type;

	private long window;

	private boolean isPressed;
	private boolean hovered = false;
	private final int w;
	private final int h;

	private final Image id;

	private boolean performClick = false;

	private DoubleBuffer Bx = BufferUtils.createDoubleBuffer(1);
	private DoubleBuffer By = BufferUtils.createDoubleBuffer(1);

	public Button(TypeButton type, Image id, float x, float y) {
		super(x, y);
		this.type = type;
		this.id = id;
		w = id.getWidth();
		h = id.getHeight() / 2;
		window = ShootEmUp.getDisplay().getWindow();
	}

	@Override
	public void update() {
		window = ShootEmUp.getDisplay().getWindow();
		Bx.clear();
		By.clear();

		glfwGetCursorPos(window, Bx, By);

		double mx = Bx.get();
		double my = By.get();

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

		if (performClick && (type != TypeButton.OTHER)) {
			ButtonHandler.selectButton(type);
			postAction();
		}
	}

	public void postAction() {
		performClick = false;
	}

	@Override
	public void render(DPDTRenderer stat) {
		if (isPressed || hovered) {
			stat.draw(id, new Vector2(x, y), new Vector2(w, h), 0, new Vector2(0, 1), new Vector2(1, 2));
		} else {
			stat.draw(id, new Vector2(x, y), new Vector2(w, h), 0, new Vector2(0, 0), new Vector2(1, 2));
		}
	}

	public void reset(int oldWidth, int oldHeight, int newWidth, int newHeight) {
		x = (int) ((x / oldWidth) * newWidth);
		y = (int) ((y / oldHeight) * newHeight);
	}

	public boolean hasClicked() {
		return performClick;
	}

	public Image getId() {
		return id;
	}

	public TypeButton getType() {
		return type;
	}

	public void setHovered(boolean b) {
		hovered = b;
	}
}
