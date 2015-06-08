package Components.Graphical;

import Components.Component;
import Components.ComponentType;
import Display.DPDTRenderer;
import Display.Image;
import Display.Renderer;
import Object.Entity;

public abstract class BaseGraphics extends Component implements GraphicsComponent {

	private static final ComponentType type = ComponentType.GRAPHICS;
	
	private float x;
	private float y;
	private float width;
	private float height;
	protected Image image;
	
	@Override
	public abstract void update(Entity e);
	
	@Override
	public abstract void render(Entity e, Renderer r);
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

}
