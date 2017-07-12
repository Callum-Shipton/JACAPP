package gui.buttons;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_TRUE;

import gui.ButtonAction;
import loop.Loop;

public class ExitButton implements ButtonAction {
	@Override
	public void click() {
		glfwSetWindowShouldClose(Loop.getDisplay().getWindow(), GL_TRUE);
	}
}
