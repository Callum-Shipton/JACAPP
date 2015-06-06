package Components.Graphical;

import Display.DPDTRenderer;
import Display.Image;
import Object.Entity;

public abstract class BaseGraphics implements GraphicsComponent {

	protected Image image;
	
	@Override
	public abstract void update(Entity e);

}
