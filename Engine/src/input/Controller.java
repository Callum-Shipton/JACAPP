package input;

import static org.lwjgl.glfw.GLFW.glfwGetJoystickAxes;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickButtons;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Map;

public abstract class Controller {

	protected final int id;
	protected final String name;
	protected FloatBuffer axes;
	protected ByteBuffer buttons;
	protected ByteBuffer pov;

	protected Map<Byte, Integer> controllerBinding;

	protected static final float AXIS_DEAD = 0.3f;

	public Controller(int id, String name) {
		this.id = id;
		this.name = name;
		axes = glfwGetJoystickAxes(id);
		buttons = glfwGetJoystickButtons(id);
		// pov = glfwGetJoystickHats(id);
		setControllerBinding();
	}

	protected abstract void axisMap();

	protected abstract void setControllerBinding();

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
	};

	protected void buttonMap() {
		int button = 0;
		while (buttons.hasRemaining()) {
			byte value = buttons.get();
			Integer action = controllerBinding.get(button);
			if (action != null) {
				Keyboard.setKey(action, value);
			}
			button++;
		}
	};

	protected void setAxis(float value, int axisCommand1, int axisCommand2) {
		if (value < (-1 * AXIS_DEAD)) {
			Keyboard.setKey(axisCommand1, 1);
			Keyboard.setKey(axisCommand2, 0);
		} else if (value > AXIS_DEAD) {
			Keyboard.setKey(axisCommand2, 1);
			Keyboard.setKey(axisCommand1, 0);
		} else {
			Keyboard.setKey(axisCommand1, 0);
			Keyboard.setKey(axisCommand2, 0);
		}
	}

	protected void setTrigger(float value, int triggerCommand) {
		if (value < 0) {
			Keyboard.setKey(triggerCommand, 0);
		} else if (value >= 0) {
			Keyboard.setKey(triggerCommand, 1);
		}
	}
}
