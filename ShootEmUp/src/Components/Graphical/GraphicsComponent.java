package Components.Graphical;

import Display.DPDTRenderer;
import Object.Entity;

public interface GraphicsComponent {
	
	void update(Entity e);
	
	void render(Entity e, DPDTRenderer r);
	
}
