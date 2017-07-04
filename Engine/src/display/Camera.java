package display;

import org.lwjgl.opengl.GL20;

import loop.Loop;
import math.Matrix4;
import math.Vector2;
import math.Vector4;

public class Camera {

	//Camera variables
		private Vector4 box;
		private Matrix4 viewMatrix;
		private int viewMatrixLocation;
		private int viewMatrixLocationInst;
		
		Camera(int width, int height){
			box = new Vector4(0,0,width,height);
			viewMatrix = new Matrix4();
			viewMatrixLocation = GL20.glGetUniformLocation(ImageProcessor.ShaderBase, "viewMatrix");
			viewMatrixLocationInst = GL20.glGetUniformLocation(ImageProcessor.ShaderInst, "viewMatrix");
		}
		
		public void setCameraFocus(float x, float y) {
			box.x(x-(box.z()/2));
			box.y(y-(box.w()/2));
			
			updateViewMatrix();
		}

		private void updateViewMatrix() {
			
			viewMatrix.clearToIdentity();
			viewMatrix.translate(-box.x(), -box.y(), 0);
			
			GL20.glUseProgram(ImageProcessor.ShaderBase);
			GL20.glUniformMatrix4(this.viewMatrixLocation, false, viewMatrix.toBuffer());
			GL20.glUseProgram(0);
			
			GL20.glUseProgram(ImageProcessor.ShaderInst);
			GL20.glUniformMatrix4(this.viewMatrixLocationInst, false, viewMatrix.toBuffer());
			GL20.glUseProgram(0);
		}
		
		public void updateCameraSize(int width, int height) {
			box.z(width);
			box.w(height);
		}

		public boolean isVisible(Vector2 pos, Vector2 size) {
			return box.contains(new Vector4(pos.x(),pos.y(),size.x(),size.y())) != null;
		}
	
}