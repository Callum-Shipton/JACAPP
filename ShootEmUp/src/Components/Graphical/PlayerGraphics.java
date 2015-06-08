package Components.Graphical;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import Display.Art;
import Display.DPDTRenderer;
import Display.Image;
import Main.ShootEmUp;
import Math.Matrix4;
import Object.Entity;

public class PlayerGraphics extends AnimatedGraphics implements GraphicsComponent {

	private int direction;

	private FloatBuffer matrix44Buffer;
	private Matrix4 viewMatrix;
	private int viewMatrixLocation;
	private int viewMatrixLocationInst;
	
	public PlayerGraphics(Entity e){
		viewMatrix = new Matrix4();
		viewMatrix.clearToIdentity();
		viewMatrix.translate(-e.getPosX() + (ShootEmUp.WIDTH - e.getWidth()) / 2, -e.getPosY()
				+ (ShootEmUp.HEIGHT - e.getHeight()) / 2, 0);
		viewMatrix.transpose();
		matrix44Buffer = BufferUtils.createFloatBuffer(16);
		viewMatrixLocation = GL20.glGetUniformLocation(Art.ShaderBase,
				"viewMatrix");
		viewMatrixLocationInst = GL20.glGetUniformLocation(Art.ShaderInst,
				"viewMatrix");
		scrollScreen(e);
	}
	
	@Override
	public void update(Entity e) {
		


	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void scrollScreen(Entity e) {
		viewMatrix.clearToIdentity();
		viewMatrix.translate(-e.getPosX() + (ShootEmUp.WIDTH - e.getWidth()) / 2, -e.getPosY()
				+ (ShootEmUp.HEIGHT - e.getHeight()) / 2, 0);
		matrix44Buffer.clear();
		matrix44Buffer = viewMatrix.toBuffer();
		GL20.glUseProgram(Art.ShaderBase);
		GL20.glUniformMatrix4(viewMatrixLocation, false, matrix44Buffer);
		GL20.glUseProgram(0);
		GL20.glUseProgram(Art.ShaderInst);
		GL20.glUniformMatrix4(viewMatrixLocationInst, false, matrix44Buffer);
		GL20.glUseProgram(0);
	}

	@Override
	public void render(Entity e, DPDTRenderer r) {
		// TODO Auto-generated method stub
		
	}



}
