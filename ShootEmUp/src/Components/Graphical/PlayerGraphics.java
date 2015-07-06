package Components.Graphical;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import Components.Message;
import Display.Art;
import Display.Image;
import Display.Renderer;
import Main.ShootEmUp;
import Math.Matrix4;
import Object.Entity;

public class PlayerGraphics extends AnimatedGraphics implements GraphicsComponent {

	private FloatBuffer matrix44Buffer;
	private Matrix4 viewMatrix;
	private int viewMatrixLocation;
	private int viewMatrixLocationInst;
	private int posLocation;
	private int posLocationInst;
	
	public PlayerGraphics(Entity e, Image image, Renderer r){
		super(image, r, false);
		viewMatrix = new Matrix4();
		matrix44Buffer = BufferUtils.createFloatBuffer(16);
		viewMatrixLocation = GL20.glGetUniformLocation(Art.ShaderBase,
				"viewMatrix");
		viewMatrixLocationInst = GL20.glGetUniformLocation(Art.ShaderInst,
				"viewMatrix");
		posLocation = GL20.glGetUniformLocation(Art.ShaderBase,
				"playerPos");
		posLocationInst = GL20.glGetUniformLocation(Art.ShaderInst,
				"playerPos");
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void scrollScreen(Entity e) {

		viewMatrix.clearToIdentity();
		viewMatrix.translate(-getX() + (ShootEmUp.WIDTH - getWidth()) / 2, -getY()
				+ (ShootEmUp.HEIGHT - getHeight()) / 2, 0);
		matrix44Buffer.clear();
		matrix44Buffer = viewMatrix.toBuffer();
		


		GL20.glUseProgram(Art.ShaderBase);
		GL20.glUniformMatrix4(viewMatrixLocation, false, matrix44Buffer);
		GL20.glUniform2f(posLocation, x+(width/2), y+(height/2));
		GL20.glUseProgram(0);
		GL20.glUseProgram(Art.ShaderInst);
		GL20.glUniformMatrix4(viewMatrixLocationInst, false, matrix44Buffer);
		GL20.glUniform2f(posLocationInst, x+(width/2), y+(height/2));
		GL20.glUseProgram(0);
	}

	@Override
	public void receive(Message m, Entity e) {
		if(m == Message.ENTITY_MOVED){
			scrollScreen(e);
		}
		
	}




}
