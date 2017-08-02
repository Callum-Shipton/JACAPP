package input.controllers;

import java.util.HashMap;

import org.lwjgl.glfw.GLFW;

import input.Controller;

public class PS4Controller extends Controller {

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

	public static final int LEFT_STICK_X = 0;
	public static final int LEFT_STICK_Y = 1;
	public static final int RIGHT_STICK_X = 2;
	public static final int RIGHT_STICK_Y = 5;
	public static final int LEFT_TRIGGER = 3;
	public static final int RIGHT_TRIGGER = 4;

	public PS4Controller(int id, String name) {
		super(id, name);
	}

	@Override
	protected void setControllerBinding() {
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
	public void axisMap() {
		int axis = 0;
		while (axes.hasRemaining()) {
			float value = axes.get();
			switch (axis) {

			case LEFT_STICK_X: // -1 LEFT) (1 RIGHT)
				setAxis(value, GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_D);
				break;
			case LEFT_STICK_Y: // (-1 DOWN) (1 UP)
				setAxis(value, GLFW.GLFW_KEY_W, GLFW.GLFW_KEY_S);
				break;
			case RIGHT_STICK_X: // (-1 LEFT) (1 RIGHT)
				setAxis(value, GLFW.GLFW_KEY_LEFT, GLFW.GLFW_KEY_RIGHT);
				break;
			case RIGHT_STICK_Y: // (-1 DOWN) (1 UP)
				setAxis(value, GLFW.GLFW_KEY_UP, GLFW.GLFW_KEY_DOWN);
				break;
			case LEFT_TRIGGER: // (-1 OUT) (1 IN)

				break;
			case RIGHT_TRIGGER: // (-1 OUT) (1 IN)
				setTrigger(value, GLFW.GLFW_KEY_SPACE);
				break;
			}
			axis++;
		}
	}
}
