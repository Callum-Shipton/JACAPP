package input.controllers;

import static org.lwjgl.glfw.GLFW.glfwGetJoystickAxes;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickButtons;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.glfw.GLFW;

import input.Controller;
import input.Keyboard;

public class PS4Controller implements Controller {

	private float axisDead = 0.3f;
	private int id;
	private String name;
	private FloatBuffer axes;
	private ByteBuffer buttons;
	private ByteBuffer pov;

	public PS4Controller(int id, String name) {
		this.id = id;
		this.name = name;
		axes = glfwGetJoystickAxes(id);
		buttons = glfwGetJoystickButtons(id);
		// pov = glfwGetJoystickHats(id);
	}

	@Override
	public void poll() {
		axes = glfwGetJoystickAxes(id);
		buttons = glfwGetJoystickButtons(id);

		axisMap();
		buttonMap();

		// Axis Debugging
		/*
		 * int i = 0; while (axes.hasRemaining()) { float axis = axes.get();
		 * Logger.debug("Joystick " + id + ": axis " + i + ": " + axis,
		 * Category.CONTROLLER); i++; }
		 */

		// Button Debugging
		/*
		 * int i = 0; while (buttons.hasRemaining()) { byte button = buttons.get();
		 * Logger.debug("Joystick " + id + ": button " + i + ": " + button,
		 * Category.CONTROLLER); i++; }
		 */

		// pov = glfwGetJoystickHats(id);

	}

	@Override
	public void buttonMap() {
		int button = 0;
		while (buttons.hasRemaining()) {
			byte value = buttons.get();
			switch (button) {
			case 1: // X
				Keyboard.setKey(GLFW.GLFW_KEY_ENTER, value);
				break;
			case 2: // Circle
				Keyboard.setKey(GLFW.GLFW_KEY_M, value);
				break;
			case 4: // LB
				Keyboard.setKey(GLFW.GLFW_KEY_LEFT_SHIFT, value);
				break;
			case 5: // RB
				Keyboard.setKey(GLFW.GLFW_KEY_TAB, value);
				break;
			case 13: // Touch Button
				Keyboard.setKey(GLFW.GLFW_KEY_F, value);
				break;
			case 9: // Options
				Keyboard.setKey(GLFW.GLFW_KEY_P, value);
				break;
			case 14: // D-Pad UP
				Keyboard.setKey(GLFW.GLFW_KEY_4, value);
				break;
			case 15: // D-Pad RIGHT
				Keyboard.setKey(GLFW.GLFW_KEY_1, value);
				break;
			case 16: // D-Pad DOWN
				Keyboard.setKey(GLFW.GLFW_KEY_2, value);
				break;
			case 17: // D-Pad LEFT
				Keyboard.setKey(GLFW.GLFW_KEY_3, value);
				break;
			case 0: // Square
			case 3: // Triangle
			case 10: // Left Stick In
			case 11: // Right Stick In
			case 12: // PS Button
			case 6: // Left Trigger
			case 7: // Right Trigger
			case 8: // Share
				break;
			}
			button++;
		}

	}

	@Override
	public void axisMap() {
		int axis = 0;
		while (axes.hasRemaining()) {
			float value = axes.get();
			switch (axis) {

			case 0: // Left Stick X (-1 LEFT) (1 RIGHT)
				if (value < (-1 * this.axisDead)) {
					Keyboard.setKey(GLFW.GLFW_KEY_A, 1);
				} else if (value > this.axisDead) {
					Keyboard.setKey(GLFW.GLFW_KEY_D, 1);
				} else {
					Keyboard.setKey(GLFW.GLFW_KEY_A, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_D, 0);
				}
				break;
			case 1: // Left Stick Y (-1 DOWN) (1 UP)
				if (value < (-1 * this.axisDead)) {
					Keyboard.setKey(GLFW.GLFW_KEY_W, 1);
				} else if (value > this.axisDead) {
					Keyboard.setKey(GLFW.GLFW_KEY_S, 1);
				} else {
					Keyboard.setKey(GLFW.GLFW_KEY_S, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_W, 0);
				}
				break;
			case 2: // Right Stick X (-1 LEFT) (1 RIGHT)
				if (value < (-1 * this.axisDead)) {
					Keyboard.setKey(GLFW.GLFW_KEY_LEFT, 1);
				} else if (value > this.axisDead) {
					Keyboard.setKey(GLFW.GLFW_KEY_RIGHT, 1);
				} else {
					Keyboard.setKey(GLFW.GLFW_KEY_LEFT, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_RIGHT, 0);
				}
				break;
			case 5: // Right Stick Y (-1 DOWN) (1 UP)
				if (value < (-1 * this.axisDead)) {
					Keyboard.setKey(GLFW.GLFW_KEY_UP, 1);
				} else if (value > this.axisDead) {
					Keyboard.setKey(GLFW.GLFW_KEY_DOWN, 1);
				} else {
					Keyboard.setKey(GLFW.GLFW_KEY_DOWN, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_UP, 0);
				}
				break;

			case 3: // Left Trigger (-1 OUT) (1 IN)

				break;
			case 4: // Right Trigger (-1 OUT) (1 IN)
				if (value < 0) {
					Keyboard.setKey(GLFW.GLFW_KEY_SPACE, 0);
				} else if (value >= 0) {
					Keyboard.setKey(GLFW.GLFW_KEY_SPACE, 1);
				}
				break;
			}
			axis++;
		}
	}

}
