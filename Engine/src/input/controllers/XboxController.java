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

public class XboxController implements Controller {

	private final float axisDead = 0.3f;
	private final int id;
	private final String name;
	private FloatBuffer axes;
	private ByteBuffer buttons;
	private ByteBuffer pov;

	private Map<Byte, Integer> controllerBinding;

	public static final byte A = 0;
	public static final byte B = 1;
	public static final byte X = 2;
	public static final byte Y = 3;
	public static final byte LB = 4;
	public static final byte RB = 5;
	public static final byte BACK = 6;
	public static final byte PAUSE = 7;
	public static final byte LEFT_STICK_IN = 8;
	public static final byte RIGHT_STICK_IN = 9;
	public static final byte D_PAD_UP = 10;
	public static final byte D_PAD_RIGHT = 11;
	public static final byte D_PAD_DOWN = 12;
	public static final byte D_PAD_LEFT = 13;

	public XboxController(int id, String name) {
		this.id = id;
		this.name = name;
		axes = glfwGetJoystickAxes(id);
		buttons = glfwGetJoystickButtons(id);
		// pov = glfwGetJoystickHats(id);
		setControllerBinding();
	}

	private void setControllerBinding() {
		controllerBinding = new HashMap<>();
		controllerBinding.put(A, GLFW.GLFW_KEY_ENTER);
		controllerBinding.put(B, GLFW.GLFW_KEY_M);
		controllerBinding.put(LB, GLFW.GLFW_KEY_LEFT_SHIFT);
		controllerBinding.put(RB, GLFW.GLFW_KEY_TAB);
		controllerBinding.put(BACK, GLFW.GLFW_KEY_F);
		controllerBinding.put(PAUSE, GLFW.GLFW_KEY_P);
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
		 * i = 0; while (buttons.hasRemaining()) { byte button = buttons.get();
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
					Keyboard.setKey(GLFW.GLFW_KEY_S, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_W, 0);
				} else if (value > axisDead) {
					Keyboard.setKey(GLFW.GLFW_KEY_W, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_S, 0);
				} else {
					Keyboard.setKey(GLFW.GLFW_KEY_W, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_S, 0);
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
			case 3: // Right Stick Y (-1 DOWN) (1 UP)
				if (value < (-1 * axisDead)) {
					Keyboard.setKey(GLFW.GLFW_KEY_DOWN, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_UP, 0);
				} else if (value > axisDead) {
					Keyboard.setKey(GLFW.GLFW_KEY_UP, 1);
					Keyboard.setKey(GLFW.GLFW_KEY_DOWN, 0);
				} else {
					Keyboard.setKey(GLFW.GLFW_KEY_UP, 0);
					Keyboard.setKey(GLFW.GLFW_KEY_DOWN, 0);
				}
				break;

			case 4: // Left Trigger (-1 OUT) (1 IN)

				break;
			case 5: // Right Trigger (-1 OUT) (1 IN)
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
