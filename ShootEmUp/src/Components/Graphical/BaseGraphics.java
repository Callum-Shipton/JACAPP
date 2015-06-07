package Components.Graphical;

import Components.ComponentType;
import Display.DPDTRenderer;
import Display.Image;
import Object.Entity;

public abstract class BaseGraphics implements GraphicsComponent {

	private static final ComponentType type = ComponentType.GRAPHICS;
	
	protected Image image;
	
	@Override
	public abstract void update(Entity e);

}
