package components.graphical;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import components.Message;
import display.Art;
import display.Image;
import display.Renderer;
import loop.Loop;
import math.Matrix4;
import object.Entity;

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
		this.viewMatrixLocation = GL20.glGetUniformLocation(Art.ShaderBase, "viewMatrix");
		this.viewMatrixLocationInst = GL20.glGetUniformLocation(Art.ShaderInst, "viewMatrix");
		this.posLocation = GL20.glGetUniformLocation(Art.ShaderBase, "playerPos");
		this.posLocationInst = GL20.glGetUniformLocation(Art.ShaderInst, "playerPos");
	}

	@Override
	public int getDirection() {
		return this.direction;
	}

	@Override
	public void receive(Message m, Entity e) {
		if (m == Message.ENTITY_MOVED) {
			scrollScreen();
		}

	}

	public void scrollScreen() {

		this.viewMatrix.clearToIdentity();
		this.viewMatrix.translate(-getX() + ((Loop.getDisplay().getWidth() - getWidth()) / 2),
				-getY() + ((Loop.getDisplay().getHeight() - getHeight()) / 2), 0);
		this.matrix44Buffer.clear();
		this.matrix44Buffer = this.viewMatrix.toBuffer();

		GL20.glUseProgram(Art.ShaderBase);
		GL20.glUniformMatrix4(this.viewMatrixLocation, false, this.matrix44Buffer);
		GL20.glUniform2f(this.posLocation, this.x + (this.width / 2), this.y + (this.height / 2));
		GL20.glUseProgram(0);
		GL20.glUseProgram(Art.ShaderInst);
		GL20.glUniformMatrix4(this.viewMatrixLocationInst, false, this.matrix44Buffer);
		GL20.glUniform2f(this.posLocationInst, this.x + (this.width / 2), this.y + (this.height / 2));
		GL20.glUseProgram(0);
	}

	@Override
	public void setDirection(int direction) {
		this.direction = direction;
	}
}
