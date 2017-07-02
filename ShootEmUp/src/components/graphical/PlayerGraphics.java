package components.graphical;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import components.Message;
import display.ImageProcessor;
import display.Image;
import display.Renderer;
import entity.Entity;
import loop.Loop;
import math.Matrix4;

public class PlayerGraphics extends AnimatedGraphics implements GraphicsComponent {

	private FloatBuffer matrix44Buffer;
	private Matrix4 viewMatrix;
	private int viewMatrixLocation;
	private int viewMatrixLocationInst;
	private int posLocation;
	private int posLocationInst;

	public PlayerGraphics(Image image, Renderer r, float scale) {
		super(image, r, false, scale);
		this.viewMatrix = new Matrix4();
		this.matrix44Buffer = BufferUtils.createFloatBuffer(16);
		this.viewMatrixLocation = GL20.glGetUniformLocation(ImageProcessor.ShaderBase, "viewMatrix");
		this.viewMatrixLocationInst = GL20.glGetUniformLocation(ImageProcessor.ShaderInst, "viewMatrix");
		this.posLocation = GL20.glGetUniformLocation(ImageProcessor.ShaderBase, "playerPos");
		this.posLocationInst = GL20.glGetUniformLocation(ImageProcessor.ShaderInst, "playerPos");
	}

	@Override
	public int getDirection() {
		return this.direction;
	}

	@Override
	public void receive(Message m, Entity e) {

	}

	public void scrollScreen() {

		this.viewMatrix.clearToIdentity();
		this.viewMatrix.translate(-getX() + ((Loop.getDisplay().getWidth() - getWidth()) / 2),
				-getY() + ((Loop.getDisplay().getHeight() - getHeight()) / 2), 0);
		this.matrix44Buffer.clear();
		this.matrix44Buffer = this.viewMatrix.toBuffer();

		GL20.glUseProgram(ImageProcessor.ShaderBase);
		GL20.glUniformMatrix4(this.viewMatrixLocation, false, this.matrix44Buffer);
		GL20.glUniform2f(this.posLocation, this.x + (this.width / 2), this.y + (this.height / 2));
		GL20.glUseProgram(0);
		GL20.glUseProgram(ImageProcessor.ShaderInst);
		GL20.glUniformMatrix4(this.viewMatrixLocationInst, false, this.matrix44Buffer);
		GL20.glUniform2f(this.posLocationInst, this.x + (this.width / 2), this.y + (this.height / 2));
		GL20.glUseProgram(0);
	}

	@Override 
	public void update(Entity e){
	super.update(e);	
	scrollScreen();
	}
	
	@Override
	public void setDirection(int direction) {
		this.direction = direction;
	}
}
