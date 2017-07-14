package input;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.*;

import input.controllers.XboxController;

import static org.lwjgl.glfw.GLFW.*;

import logging.Logger;
import logging.Logger.Category;

/**
 * The collection of controllers currently connected.
 *
 * 
 */
public class Controllers {

	/** The controllers available */
	private static Map<Integer,Controller> controllers = new HashMap<>();
	/** The number of controllers */
	private static int controllerCount = 0;
	
	private static GLFWJoystickCallback joystickCallback;

	public static void init(){
		glfwSetJoystickCallback(joystickCallback = new GLFWJoystickCallback() {

			@Override
			public void invoke(int jid, int status) {
				if(status == GLFW_CONNECTED) {
					createController(jid);
				}else if(status == GLFW_DISCONNECTED) {
					destroyController(jid);
				}
			}
		});
		int connectedController = 0;
		while(glfwJoystickPresent(connectedController) == true) {
			createController(connectedController);
			connectedController++;
		}
	}
	private static void destroyController(int jid) {
		String name = glfwGetJoystickName(jid);
		if(controllers.get(jid) == null) Logger.debug("Tried to destroy NULL Controller: " + jid + ": " + name, Category.CONTROLLER);
		else{
			controllers.remove(jid);
			controllerCount--;
		}
	}


	private static void createController(int jid) {
		String name = glfwGetJoystickName(jid);
		if(!controllers.isEmpty() && controllers.get(jid) != null) Logger.debug("Tried to overwrite Controller: " + jid + ": " + name, Category.CONTROLLER);
		else{
			switch(name) {
			case "Xbox 360 Controller":
				controllers.put(jid, new XboxController(jid,name));
			}
			FloatBuffer axes = glfwGetJoystickAxes(jid);
			ByteBuffer buttons = glfwGetJoystickButtons(jid);
			Logger.debug("Joystick " + jid + ": " + name + " axes: " + axes.capacity() + ", buttons: " + buttons.capacity(), Category.CONTROLLER);
			controllerCount++;
		}
	}

	/**
	 * Destroys any resources used by the controllers
	 */
	public static void destroy() {
		if (joystickCallback != null) {
			joystickCallback.free();
		}
	}

	/**
	 * Get a controller from the collection
	 *
	 * @param index
	 *            The index of the controller to retrieve
	 * @return The controller requested
	 */
	public static Controller getController(int index) {
		return controllers.get(index);
	}

	/**
	 * Retrieve a count of the number of controllers
	 *
	 * @return The number of controllers available
	 */
	public static int getControllerCount() {
		//TODO: Check if controllers size and count match?
		return controllers.size();
	}

	/**
	 * Poll the controllers available. This will both update their state and
	 * generate events that must be cleared.
	 */
	public static void poll() {
		for (int i = 0; i < controllers.size(); i++) {
			getController(i).poll();
		}
	}
}
