package gui.buttons;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import gui.ButtonAction;
import loop.Loop;

public class ExitButton implements ButtonAction {
	@Override
	public void click() {
		glfwSetWindowShouldClose(Loop.getDisplay().getWindow(), true);
	}
}
