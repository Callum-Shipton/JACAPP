package input.controllers;

import java.util.HashMap;

import org.lwjgl.glfw.GLFW;

import input.Controller;

public class XboxController extends Controller {

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

	public static final int LEFT_STICK_X = 0;
	public static final int LEFT_STICK_Y = 1;
	public static final int RIGHT_STICK_X = 2;
	public static final int RIGHT_STICK_Y = 3;
	public static final int LEFT_TRIGGER = 4;
	public static final int RIGHT_TRIGGER = 5;

	public XboxController(int id, String name) {
		super(id, name);
	}

	@Override
	protected void setControllerBinding() {
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
	public void axisMap() {
		int axis = 0;
		while (axes.hasRemaining()) {
			float value = axes.get();
			switch (axis) {

			case LEFT_STICK_X: // Left Stick X (-1 LEFT) (1 RIGHT)
				setAxis(value, GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_D);
				break;
			case LEFT_STICK_Y: // Left Stick Y (-1 DOWN) (1 UP)
				setAxis(value, GLFW.GLFW_KEY_S, GLFW.GLFW_KEY_W);
				break;
			case RIGHT_STICK_X: // Right Stick X (-1 LEFT) (1 RIGHT)
				setAxis(value, GLFW.GLFW_KEY_LEFT, GLFW.GLFW_KEY_RIGHT);
				break;
			case RIGHT_STICK_Y: // Right Stick Y (-1 DOWN) (1 UP)
				setAxis(value, GLFW.GLFW_KEY_DOWN, GLFW.GLFW_KEY_UP);
				break;
			case LEFT_TRIGGER: // Left Trigger (-1 OUT) (1 IN)

				break;
			case RIGHT_TRIGGER: // Right Trigger (-1 OUT) (1 IN)
				setTrigger(value, GLFW.GLFW_KEY_SPACE);
			}
			axis++;
		}
	}
}
