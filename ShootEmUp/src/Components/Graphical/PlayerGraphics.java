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

public class PlayerGraphics extends BaseGraphics implements GraphicsComponent {

	private int direction;
	private boolean animating;
	private int animID = 0;
	private int animTime = 6;
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
		
		if (isAnimating()) {
			animID++;
			if (animID >= image.getFWidth() * animTime)
				animID = 0;
		}

	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isAnimating() {
		return animating;
	}

	public void setAnimating(boolean animating) {
		this.animating = animating;
	}

	public int getAnimID() {
		return animID;
	}

	public void setAnimID(int animID) {
		this.animID = animID;
	}

	public int getAnimTime() {
		return animTime;
	}

	public void setAnimTime(int animTime) {
		this.animTime = animTime;
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
