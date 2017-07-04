package components.graphical;

import org.lwjgl.opengl.GL20;

import components.Message;
import display.Image;
import display.ImageProcessor;
import display.Renderer;
import entity.Entity;
import loop.Loop;

public class PlayerGraphics extends AnimatedGraphics implements GraphicsComponent {

	private int posLocation;
	private int posLocationInst;
	
	public PlayerGraphics(Image image, Renderer r, float scale) {
		super(image, r, false, scale);
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

		Loop.getDisplay().getCamera().setCameraFocus(x+(width/2), y+(height/2));
		
		GL20.glUseProgram(ImageProcessor.ShaderBase);
		GL20.glUniform2f(this.posLocation, this.x + (this.width / 2), this.y + (this.height / 2));
		GL20.glUseProgram(0);
		GL20.glUseProgram(ImageProcessor.ShaderInst);
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
