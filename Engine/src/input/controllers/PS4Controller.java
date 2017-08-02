package input.controllers;

import static org.lwjgl.glfw.GLFW.glfwGetJoystickAxes;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickButtons;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import input.Controller;
import input.Keyboard;

public class PS4Controller implements Controller {

	private final float axisDead = 0.3f;
	private final int id;
	private final String name;
	private FloatBuffer axes;
	private ByteBuffer buttons;
	private ByteBuffer pov;

	private Map<Byte, Integer> controllerBinding;

	public static final byte SQUARE = 0;
	public static final byte X = 1;
	public static final byte CIRCLE = 2;
	public static final byte TRIANGLE = 3;
	public static final byte L1 = 4;
	public static final byte R1 = 5;
	public static final byte L2 = 6;
	public static final byte R2 = 7;
	public static final byte SHARE = 8;
	public static final byte OPTIONS = 9;
	public static final byte LEFT_STICK_IN = 10;
	public static final byte RIGHT_STICK_IN = 11;
	public static final byte PS_BUTTON = 12;
	public static final byte TOUCH_BUTTON = 13;
	public static final byte D_PAD_UP = 14;
	public static final byte D_PAD_RIGHT = 15;
	public static final byte D_PAD_DOWN = 16;
	public static final byte D_PAD_LEFT = 17;

	public PS4Controller(int id, String name) {
		this.id = id;
		this.name = name;
		axes = glfwGetJoystickAxes(id);
		buttons = glfwGetJoystickButtons(id);
		// pov = glfwGetJoystickHats(id);
		setControllerBinding();
	}

	private void setControllerBinding() {
		controllerBinding = new HashMap<>();
		controllerBinding.put(X, GLFW.GLFW_KEY_ENTER);
		controllerBinding.put(CIRCLE, GLFW.GLFW_KEY_M);
		controllerBinding.put(L1, GLFW.GLFW_KEY_LEFT_SHIFT);
		controllerBinding.put(R1, GLFW.GLFW_KEY_TAB);
		controllerBinding.put(TOUCH_BUTTON, GLFW.GLFW_KEY_F);
		controllerBinding.put(OPTIONS, GLFW.GLFW_KEY_P);
		controllerBinding.put(D_PAD_UP, GLFW.GLFW_KEY_4);
		controllerBinding.put(D_PAD_RIGHT, GLFW.GLFW_KEY_1);
		controllerBinding.put(D_PAD_DOWN, GLFW.GLFW_KEY_2);
		controllerBinding.put(D_PAD_LEFT, GLFW.GLFW_KEY_3);
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
		 * int i = 0; while (buttons.hasRemaining()) { byte button =
		 * buttons.get(); Logger.debug("Joystick " + id + ": button " + i + ": "
		 * + button, Category.CONTROLLER); i++; }
		 */

		// pov = glfwGetJoystickHats(id);

	}

	@Override
	public void buttonMap() {
		int button = 0;
		while (buttons.hasRemaining()) {
			byte value = buttons.get();
			Keyboard.setKey(controllerBinding.get(button), value);
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
				if (value < (-1 * axisDead)) {
					Keyboard.setKey(GLFW.GLFW_KEY_A, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_D, 0);
				} else if (value > axisDead) {
					Keyboard.setKey(GLFW.GLFW_KEY_D, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_A, 0);
				} else {
					Keyboard.setKey(GLFW.GLFW_KEY_A, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_D, 0);
				}
				break;
			case 1: // Left Stick Y (-1 DOWN) (1 UP)
				if (value < (-1 * axisDead)) {
					Keyboard.setKey(GLFW.GLFW_KEY_W, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_S, 0);
				} else if (value > axisDead) {
					Keyboard.setKey(GLFW.GLFW_KEY_S, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_W, 0);
				} else {
					Keyboard.setKey(GLFW.GLFW_KEY_S, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_W, 0);
				}
				break;
			case 2: // Right Stick X (-1 LEFT) (1 RIGHT)
				if (value < (-1 * axisDead)) {
					Keyboard.setKey(GLFW.GLFW_KEY_LEFT, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_RIGHT, 0);
				} else if (value > axisDead) {
					Keyboard.setKey(GLFW.GLFW_KEY_RIGHT, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_LEFT, 0);
				} else {
					Keyboard.setKey(GLFW.GLFW_KEY_LEFT, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_RIGHT, 0);
				}
				break;
			case 5: // Right Stick Y (-1 DOWN) (1 UP)
				if (value < (-1 * axisDead)) {
					Keyboard.setKey(GLFW.GLFW_KEY_UP, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_DOWN, 0);
				} else if (value > axisDead) {
					Keyboard.setKey(GLFW.GLFW_KEY_DOWN, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_UP, 0);
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
