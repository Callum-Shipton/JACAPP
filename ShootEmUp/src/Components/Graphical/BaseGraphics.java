package Components.Graphical;

import Components.Component;
import Components.ComponentType;
import Display.DPDTRenderer;
import Display.Image;
import Display.Renderer;
import Object.Entity;

public abstract class BaseGraphics extends Component implements GraphicsComponent {

	private static final ComponentType type = ComponentType.GRAPHICS;
	
	protected float xPos;
	protected float yPos;
	protected float width;
	protected float height;
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

}
