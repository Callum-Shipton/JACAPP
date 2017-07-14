package display;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

import org.lwjgl.util.vector.Vector4f;

import math.Matrix4;
import math.Vector2;
import math.VectorMath;

public class Camera {

	// Camera variables
	private Vector4f box;
	private Matrix4 viewMatrix;
	private int viewMatrixLocation;
	private int viewMatrixLocationInst;

	Camera(int width, int height) {
		box = new Vector4f(0, 0, width, height);
		viewMatrix = new Matrix4();
		viewMatrixLocation = glGetUniformLocation(ImageProcessor.ShaderBase, "viewMatrix");
		viewMatrixLocationInst = glGetUniformLocation(ImageProcessor.ShaderInst, "viewMatrix");
	}

	public void setCameraFocus(float x, float y) {
		box.setX(x - (box.getZ() / 2));
		box.setY(y - (box.getW() / 2));

		updateViewMatrix();
	}

	private void updateViewMatrix() {

		viewMatrix.clearToIdentity();
		viewMatrix.translate(-box.getX(), -box.getY(), 0);

		glUseProgram(ImageProcessor.ShaderBase);
		glUniformMatrix4fv(this.viewMatrixLocation, false, viewMatrix.toBuffer());
		glUseProgram(0);

		glUseProgram(ImageProcessor.ShaderInst);
		glUniformMatrix4fv(this.viewMatrixLocationInst, false, viewMatrix.toBuffer());
		glUseProgram(0);
	}

	public void updateCameraSize(int width, int height) {
		box.set(box.getX(), box.getY(), width, height);
	}

	public boolean isVisible(Vector2 pos, Vector2 size) {
		box.setX(0.0f);
		return VectorMath.contains(box, new Vector4f(pos.x(), pos.y(), size.x(), size.y())) != null;
	}

}
