package Components.Graphical;

import Display.Renderer;
import Object.Entity;

public interface GraphicsComponent {
	
	void update(Entity e);
	
	void render(Entity e);
	
}
