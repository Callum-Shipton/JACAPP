package display;


import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryStack.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import math.VectorMath;

public class Camera {

	// Camera variables
	private Vector4f box;
	private Matrix4f viewMatrix;
	private int viewMatrixLocation;
	private int viewMatrixLocationInst;

	Camera(int width, int height) {
		box = new Vector4f(0, 0, width, height);
		viewMatrix = new Matrix4f();
		viewMatrixLocation = glGetUniformLocation(ImageProcessor.ShaderBase, "viewMatrix");
		viewMatrixLocationInst = glGetUniformLocation(ImageProcessor.ShaderInst, "viewMatrix");
	}

	public void setCameraFocus(float x, float y) {
		box.setComponent(0, x - (box.z / 2));
		box.setComponent(1, y - (box.w / 2));

		updateViewMatrix();
	}

	private void updateViewMatrix() {

		viewMatrix.identity()
		.translate(-box.x(), -box.y(), 0);

		try (MemoryStack stack = stackPush()) {
			FloatBuffer buf = stack.callocFloat(16);
			buf = viewMatrix.get(buf);
			glUseProgram(ImageProcessor.ShaderBase);
			glUniformMatrix4fv(this.viewMatrixLocation, false, buf);
			glUseProgram(0);

			glUseProgram(ImageProcessor.ShaderInst);
			glUniformMatrix4fv(this.viewMatrixLocationInst, false, buf);
			glUseProgram(0);
		}
	}

	public void updateCameraSize(int width, int height) {
		box.set(box.z, box.y, width, height);
	}

	public boolean isVisible(Vector2f pos, Vector2f size) {
		return VectorMath.contains(box, new Vector4f(pos.x(), pos.y(), size.x(), size.y())) != null;
	}

}
