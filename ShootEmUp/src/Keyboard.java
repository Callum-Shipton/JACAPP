import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {
	
	private static GLFWKeyCallback keyCallback;
	
	public static void keyCheck(long window, Entity player){	
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_W && (action == GLFW_REPEAT || action == GLFW_PRESS) )
                    player.moveVertically(1);
                if ( key == GLFW_KEY_S && (action == GLFW_REPEAT || action == GLFW_PRESS) )
                    player.moveVertically(-1);
                if ( key == GLFW_KEY_A && (action == GLFW_REPEAT || action == GLFW_PRESS) )
                    player.moveHorizontally(1);
                if ( key == GLFW_KEY_D && (action == GLFW_REPEAT || action == GLFW_PRESS) )
                    player.moveHorizontally(-1);
            }
        });
		
		System.out.println(player.getX() + " " + player.getY());
	}
}
