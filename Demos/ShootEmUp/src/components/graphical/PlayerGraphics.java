package components.graphical;

import org.lwjgl.opengl.GL20;

import components.interfaces.GraphicsComponent;
import display.BaseRenderSystem;
import display.Renderer;
import loop.GameLoop;

public class PlayerGraphics extends AnimatedGraphics implements GraphicsComponent {

	private transient int posLocation;
	private transient int posLocationInst;

	public PlayerGraphics(String imageId) {
		super(imageId, false);
		posLocation = GL20.glGetUniformLocation(BaseRenderSystem.ShaderBase.getProgramID(), "playerPos");
		posLocationInst = GL20.glGetUniformLocation(BaseRenderSystem.ShaderInst.getProgramID(), "playerPos");
	}

	public PlayerGraphics(PlayerGraphics playerGraphics) {
		this(playerGraphics.imageId);
	}

	@Override
	public int getDirection() {
		return this.direction;
	}

	public void scrollScreen() {

		GameLoop.getDisplay().getCamera().setCameraFocus(x + (width / 2), y + (height / 2));

		GL20.glUseProgram(BaseRenderSystem.ShaderBase.getProgramID());
		GL20.glUniform2f(posLocation, x + (width / 2), y + (height / 2));
		GL20.glUseProgram(0);
		GL20.glUseProgram(BaseRenderSystem.ShaderInst.getProgramID());
		GL20.glUniform2f(posLocationInst, x + (width / 2), y + (height / 2));
		GL20.glUseProgram(0);
	}

	@Override
	public void update() {
		super.update();
		scrollScreen();
	}

	@Override
	public void setDirection(int direction) {
		this.direction = direction;
	}
}
