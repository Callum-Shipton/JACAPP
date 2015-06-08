package Components.Graphical;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import Display.Art;
import Display.DPDTRenderer;
import Display.Image;
import Display.Renderer;
import Main.ShootEmUp;
import Math.Matrix4;
import Math.Vector2;
import Object.Entity;

public class PlayerGraphics extends AnimatedGraphics implements GraphicsComponent {



	private FloatBuffer matrix44Buffer;
	private Matrix4 viewMatrix;
	private int viewMatrixLocation;
	private int viewMatrixLocationInst;
	
	public PlayerGraphics(Entity e){
		viewMatrix = new Matrix4();
		viewMatrix.clearToIdentity();
		viewMatrix.translate(getX() + (ShootEmUp.WIDTH - getWidth()) / 2, getY()
				+ (ShootEmUp.HEIGHT - getHeight()) / 2, 0);
		viewMatrix.transpose();
		matrix44Buffer = BufferUtils.createFloatBuffer(16);
		viewMatrixLocation = GL20.glGetUniformLocation(Art.ShaderBase,
				"viewMatrix");
		viewMatrixLocationInst = GL20.glGetUniformLocation(Art.ShaderInst,
				"viewMatrix");
		scrollScreen(e);
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void scrollScreen(Entity e) {
		viewMatrix.clearToIdentity();
		viewMatrix.translate(getX() + (ShootEmUp.WIDTH - getWidth()) / 2, getY()
				+ (ShootEmUp.HEIGHT - getHeight()) / 2, 0);
		matrix44Buffer.clear();
		matrix44Buffer = viewMatrix.toBuffer();
		GL20.glUseProgram(Art.ShaderBase);
		GL20.glUniformMatrix4(viewMatrixLocation, false, matrix44Buffer);
		GL20.glUseProgram(0);
		GL20.glUseProgram(Art.ShaderInst);
		GL20.glUniformMatrix4(viewMatrixLocationInst, false, matrix44Buffer);
		GL20.glUseProgram(0);
	}





}
