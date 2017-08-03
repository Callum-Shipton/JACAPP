package gui;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import display.DPDTRenderer;
import loop.Loop;

public class HoverButton extends GuiComponent {

	private Button button;
	private GuiComponent hoverComponent;
	private boolean isHovered = false;

	private DoubleBuffer bx = BufferUtils.createDoubleBuffer(1);
	private DoubleBuffer by = BufferUtils.createDoubleBuffer(1);

	public HoverButton(float x, float y, Button button, GuiComponent hoverComponent) {
		super(x, y);
		button.setX(x);
		button.setY(y);
		this.button = button;
		height = button.height;
		width = button.width;

		hoverComponent.setX(x);
		hoverComponent.setX(y);
		this.hoverComponent = hoverComponent;
	}

	@Override
	public void render(DPDTRenderer d) {
		button.render(d);
		if (isHovered) {
			hoverComponent.render(d);
		}
	}

	@Override
	public void update() {
		button.update();

		bx.clear();
		by.clear();
		glfwGetCursorPos(Loop.getDisplay().getWindow(), bx, by);
		double mx = bx.get();
		double my = by.get();

		if ((mx >= x) && (my >= y) && (mx < (x + width)) && (my < (y + height))) {
			isHovered = true;
			hoverComponent.update();
		} else {
			isHovered = false;
		}
	}

}
