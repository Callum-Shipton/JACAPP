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

public class MenuButton extends GuiComponent {

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

	public MenuButton(TypeButton type, Image id, float x, float y) {
		super(x, y);
		this.type = type;
		this.id = id;
		this.w = id.getWidth();
		this.h = id.getHeight() / 2;
		this.window = ShootEmUp.getDisplay().getWindow();
	}

	public Image getId() {
		return this.id;
	}

	public TypeButton getType() {
		return this.type;
	}

	public boolean hasClicked() {
		return this.performClick;
	}

	public void postAction() {
		this.performClick = false;
	}

	@Override
	public void render(DPDTRenderer stat) {
		if (this.isPressed || this.hovered) {
			stat.draw(this.id, new Vector2(this.x, this.y), new Vector2(this.w, this.h), 0, new Vector2(0, 1),
					new Vector2(1, 2));
		} else {
			stat.draw(this.id, new Vector2(this.x, this.y), new Vector2(this.w, this.h), 0, new Vector2(0, 0),
					new Vector2(1, 2));
		}
	}

	public void reset(int oldWidth, int oldHeight, int newWidth, int newHeight) {
		this.x = (int) ((this.x / oldWidth) * newWidth);
		this.y = (int) ((this.y / oldHeight) * newHeight);
	}

	public void setHovered(boolean b) {
		this.hovered = b;
	}

	@Override
	public void update() {
		this.window = ShootEmUp.getDisplay().getWindow();
		this.Bx.clear();
		this.By.clear();

		glfwGetCursorPos(this.window, this.Bx, this.By);

		double mx = this.Bx.get();
		double my = this.By.get();

		if ((mx >= this.x) && (my >= this.y) && (mx < (this.x + this.w)) && (my < (this.y + this.h))) {

			if (this.isPressed && (glfwGetMouseButton(this.window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE)) {
				this.performClick = true;
				this.isPressed = false;
			} else if (glfwGetMouseButton(this.window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
				this.isPressed = true;
			}
		}
		if (this.isPressed && (glfwGetMouseButton(this.window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE)) {
			this.isPressed = false;
		}

		if (this.performClick && (this.type != TypeButton.OTHER)) {
			ButtonHandler.selectButton(this.type);
			postAction();
		}
	}
}
