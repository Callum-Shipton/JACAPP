package gui.buttons;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import gui.ButtonAction;
import loop.GameLoop;

public class ExitButton implements ButtonAction {
	@Override
	public void click() {
		glfwSetWindowShouldClose(GameLoop.getDisplay().getWindow(), true);
	}
}
