package display;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.system.MemoryStack.stackPush;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import math.VectorMath;

public class Camera {

	// Camera variables
	private final Vector4f box;
	private final Matrix4f viewMatrix;
	private final int viewMatrixLocation;
	private final int viewMatrixLocationInst;
	private float levelWidth;
	private float levelHeight;

	Camera(int width, int height) {
		box = new Vector4f(0, 0, width, height);
		viewMatrix = new Matrix4f();
		viewMatrixLocation = glGetUniformLocation(ImageProcessor.ShaderBase.getProgramID(), "viewMatrix");
		viewMatrixLocationInst = glGetUniformLocation(ImageProcessor.ShaderInst.getProgramID(), "viewMatrix");
	}

	public void setCameraFocus(float x, float y) {
		float left = (x - (box.z / 2));
		float top = (y - (box.w / 2));

		if ((0 <= left) && ((left + box.z) <= levelWidth)) {
			box.setComponent(0, x - (box.z / 2));
		} else {
			if (0 > left) {
				box.setComponent(0, 0);
			} else if ((left + box.z) > levelWidth) {
				box.setComponent(0, levelWidth - box.z);
			}
		}

		if ((top > 0) && ((top + box.w) <= levelHeight)) {
			box.setComponent(1, y - (box.w / 2));
		} else {
			if (0 > top) {
				box.setComponent(1, 0);
			} else if ((top + box.w) > levelHeight) {
				box.setComponent(1, levelHeight - box.w);
			}
		}

		updateViewMatrix();

	}

	private void updateViewMatrix() {

		viewMatrix.identity().translate(-box.x(), -box.y(), 0);

		try (MemoryStack stack = stackPush()) {
			FloatBuffer buf = stack.callocFloat(16);
			buf = viewMatrix.get(buf);
			glUseProgram(ImageProcessor.ShaderBase.getProgramID());
			glUniformMatrix4fv(viewMatrixLocation, false, buf);
			glUseProgram(0);

			glUseProgram(ImageProcessor.ShaderInst.getProgramID());
			glUniformMatrix4fv(viewMatrixLocationInst, false, buf);
			glUseProgram(0);
		}
	}

	public void updateCameraSize(int width, int height) {
		box.set(box.z, box.y, width, height);
	}

	public boolean isVisible(Vector2f pos, Vector2f size) {
		return VectorMath.contains(box, new Vector4f(pos.x(), pos.y(), size.x(), size.y())) != null;
	}

	public void setLevelSize(Vector2f levelSize) {
		levelWidth = levelSize.x();
		levelHeight = levelSize.y();
	}
}
